package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import net.chaos.chaosmod.Main;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

public class JigsawAssembler {
	private ResourceLocation startStructure;
	private PlacementSettings startSettings;

	static class PendingConnection {
		BlockPos jigsawPos; // worldPos
		EnumFacing facing;
		String targetPool;
		int depth;
		ResourceLocation turnsInto;
		StructureBoundingBox parentBounds;
	}
	
	static class PlaceResult {
        final BlockPos nextOrigin;
        final PlacementSettings matchSettings;
        final JigsawConnector match;
        final Template nextTemplate;
        final StructureBoundingBox nextBox;

        PlaceResult(BlockPos nextOrigin, PlacementSettings matchSettings,
                    JigsawConnector match, Template nextTemplate,
                    StructureBoundingBox nextBox) {
            this.nextOrigin = nextOrigin;
            this.matchSettings = matchSettings;
            this.match = match;
            this.nextTemplate = nextTemplate;
            this.nextBox = nextBox;
        }
    }
	
	public JigsawAssembler() {
		this(new ResourceLocation(Reference.MODID, "test_room1"));
	}
	
	public JigsawAssembler(ResourceLocation startStructure) {
		this(startStructure, new PlacementSettings());
	}

	public JigsawAssembler(ResourceLocation startStructure, PlacementSettings startSettings) {
		this.startStructure = startStructure;
		this.startSettings = startSettings;
	}
	
	/** FIXME : NEXT version not meeting requirements pieces almost all the time for 5 tries lagging server
	public void assembleWithRetry(MinecraftServer server, BlockPos start, int maxDepth, int maxAttempts) {
	    WorldServer world = (WorldServer) server.getEntityWorld();

	    for (int attempt = 0; attempt < maxAttempts; attempt++) {
	        // snapshot before attempting
	        List<Pair<BlockPos, IBlockState>> snapshot = new ArrayList<>();
	        Map<ResourceLocation, Integer> placementCounts = assemble(server, start, maxDepth, snapshot);

	        if (minCountsSatisfied(JigsawPoolRegistry.get(JigsawPool.DUNGEON_POOL.getName().toString()), placementCounts)) {
	            return; // success
	        }

	        // restore snapshot and retry
	        Main.getLogger().info("minCount not satisfied, attempt {}/{}, restoring...", attempt + 1, maxAttempts);
	        for (Pair<BlockPos, IBlockState> entry : snapshot) {
	            world.setBlockState(entry.getLeft(), entry.getRight(), 2);
	        }
	    }

	    Main.getLogger().warn("Could not satisfy minCounts after {} attempts, keeping last result", maxAttempts);
	}

	// assemble now returns placementCounts and fills the snapshot list
	public Map<ResourceLocation, Integer> assemble(MinecraftServer server, BlockPos start, int maxDepth,
	                                                List<Pair<BlockPos, IBlockState>> snapshot) {
	    WorldServer world = (WorldServer) server.getEntityWorld();
	    TemplateManager manager = world.getStructureTemplateManager();

	    Queue<PendingConnection> queue = new LinkedList<>();
	    List<StructureBoundingBox> placedBoxes = new ArrayList<>();
	    Map<ResourceLocation, Integer> placementCounts = new HashMap<>();

	    Template startingTemplate = manager.getTemplate(server, startStructure);
	    BlockPos startSize = startingTemplate.getSize();
	    StructureBoundingBox startBox = computeBoundingBox(start, startSize, startSettings.getRotation());

	    snapshotArea(world, startBox, snapshot);
	    startingTemplate.addBlocksToWorld(world, start, startSettings);
	    placedBoxes.add(startBox);

	    for (JigsawConnector c : TemplateUtils.getJigsawConnectors(startingTemplate, start, startSettings)) {
	        PendingConnection pc = new PendingConnection();
	        pc.jigsawPos = c.worldPos;
	        pc.facing = c.facing;
	        pc.targetPool = c.targetPool;
	        pc.turnsInto = c.turnsInto;
	        pc.depth = 1;
	        queue.add(pc);
	    }

	    while (!queue.isEmpty()) {
	        PendingConnection pending = queue.poll();

	        if (pending.depth >= maxDepth) {
	            replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	            continue;
	        }

	        JigsawPool pool = JigsawPoolRegistry.get(pending.targetPool);
	        boolean placed = false;

	        for (ResourceLocation candidateRes : pool.getShuffled(world.rand, pending.depth)) {
	            PlaceResult result = tryPlace(manager, server, candidateRes, pending, placedBoxes, pool, placementCounts);
	            if (result == null) continue;
	            snapshotArea(world, result.nextBox, snapshot);
	            commitPlace(world, manager, server, pending, result, placedBoxes, queue, candidateRes, placementCounts);
	            placed = true;
	            break;
	        }

	        if (!placed) {
	            for (ResourceLocation candidateRes : pool.getPlaceLastCandidates(world.rand, pending.depth)) {
	                PlaceResult result = tryPlace(manager, server, candidateRes, pending, placedBoxes, pool, placementCounts);
	                if (result == null) continue;
	                snapshotArea(world, result.nextBox, snapshot);
	                commitPlace(world, manager, server, pending, result, placedBoxes, queue, candidateRes, placementCounts);
	                placed = true;
	                break;
	            }
	        }

	        if (!placed) {
	            replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	        }
	    }

	    return placementCounts;
	}

	// keep the old signature for convenience, just don't retry
	public void assemble(MinecraftServer server, BlockPos start, int maxDepth) {
	    assemble(server, start, maxDepth, new ArrayList<>());
	}

	private void snapshotArea(WorldServer world, StructureBoundingBox box, List<Pair<BlockPos, IBlockState>> snapshot) {
	    for (int x = box.minX; x <= box.maxX; x++) {
	        for (int y = box.minY; y <= box.maxY; y++) {
	            for (int z = box.minZ; z <= box.maxZ; z++) {
	                BlockPos pos = new BlockPos(x, y, z);
	                snapshot.add(Pair.of(pos, world.getBlockState(pos)));
	            }
	        }
	    }
	}

	private boolean minCountsSatisfied(JigsawPool pool, Map<ResourceLocation, Integer> placementCounts) {
	    for (Triple<ResourceLocation, Integer, JigsawPool.PieceConstraint> entry : pool.getElements()) {
	        JigsawPool.PieceConstraint dc = entry.getRight();
	        if (dc == null || dc.minCount < 0) continue;
	        int placed = placementCounts.getOrDefault(entry.getLeft(), 0);
	        if (placed < dc.minCount) {
	            Main.getLogger().info("minCount not met for {}: needed {}, got {}", entry.getLeft(), dc.minCount, placed);
	            return false;
	        }
	    }
	    return true;
	}
	**/

	// TODO : try a fix so that it prioritizes minCount structures that haven't reached that amount yet
	// WORKING VERSION restore if new impl fails
	public void assemble(MinecraftServer server, BlockPos start, int maxDepth) {
	    WorldServer world = (WorldServer) server.getEntityWorld();
	    TemplateManager manager = world.getStructureTemplateManager();

	    Queue<PendingConnection> queue = new LinkedList<>();
	    List<StructureBoundingBox> placedBoxes = new ArrayList<>(); // all placed pieces from the start
	    Map<ResourceLocation, Integer> placementCounts = new HashMap<>();

	    // place starting template
	    Template startingTemplate = manager.getTemplate(server, startStructure);
	    BlockPos startSize = startingTemplate.getSize();
	    StructureBoundingBox startBox = computeBoundingBox(start, startSize, startSettings.getRotation());

	    startingTemplate.addBlocksToWorld(world, start, startSettings);
	    placedBoxes.add(startBox);

	    // enqueue connectors from start
	    for (JigsawConnector c : TemplateUtils.getJigsawConnectors(startingTemplate, start, startSettings)) {
	        PendingConnection pc = new PendingConnection();
	        pc.jigsawPos = c.worldPos;
	        pc.facing = c.facing;
	        pc.targetPool = c.targetPool;
	        pc.turnsInto = c.turnsInto;
	        pc.depth = 1;
	        queue.add(pc);
	    }

	    while (!queue.isEmpty()) {
	        PendingConnection pending = queue.poll();

	        if (pending.depth >= maxDepth) {
	            replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	            continue;
	        }

	        JigsawPool pool = JigsawPoolRegistry.get(pending.targetPool);
	        boolean placed = false;
	        
	        for (ResourceLocation candidateRes : pool.getShuffled(world.rand, pending.depth)) {
                PlaceResult result = tryPlace(manager, server, candidateRes, pending, placedBoxes, pool, placementCounts);
                if (result == null) continue;
                commitPlace(world, manager, server, pending, result, placedBoxes, queue, candidateRes, placementCounts);
                placed = true;
                break;
            }
	        
	        // dead-ends
	        if (!placed) {
                for (ResourceLocation candidateRes : pool.getPlaceLastCandidates(world.rand, pending.depth)) {
                    PlaceResult result = tryPlace(manager, server, candidateRes, pending, placedBoxes, pool, placementCounts);
                    if (result == null) continue;

                    commitPlace(world, manager, server, pending, result, placedBoxes, queue, candidateRes, placementCounts);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
            }

	    }
	}
	
	/**
     * Tries to fit a candidate template against a pending connection.
     * Checks all rotations and intersection with already-placed boxes.
     * Returns a PlaceResult if it fits, or null if it doesn't.
     * Does NOT write anything to the world.
     * @see JigsawAssembler#commitPlace
     */
    private PlaceResult tryPlace(TemplateManager manager, MinecraftServer server,
                                  ResourceLocation candidateRes, PendingConnection pending,
                                  List<StructureBoundingBox> placedBoxes, 
                                  JigsawPool pool,
                                  Map<ResourceLocation, Integer> placementCounts) {
        Template nextTemplate = manager.getTemplate(server, candidateRes);
        JigsawPool.PieceConstraint dc = pool.getConstraint(candidateRes);
        if (dc != null && !dc.allowsCount(placementCounts.getOrDefault(candidateRes, 0))) return null;

        // find a connector on the candidate that faces opposite to the pending connection
        JigsawConnector match = null;
        PlacementSettings matchSettings = null;

        outer:
        for (Rotation rot : Rotation.values()) {
            PlacementSettings candidateSettings = new PlacementSettings().setRotation(rot);
            for (JigsawConnector c2 : TemplateUtils.getJigsawConnectors(nextTemplate, BlockPos.ORIGIN, candidateSettings)) {
                if (c2.facing == pending.facing.getOpposite()) {
                    match = c2;
                    matchSettings = candidateSettings;
                    break outer;
                }
            }
        }

        if (match == null) return null; // no connector fits this orientation

        // compute where the piece would land
        BlockPos attachPoint = pending.jigsawPos.offset(pending.facing);
        BlockPos nextOrigin = attachPoint.subtract(match.localPos);
        BlockPos nextSize = nextTemplate.getSize();
        StructureBoundingBox nextBox = computeBoundingBox(nextOrigin, nextSize, matchSettings.getRotation());

        // intersection check
        for (StructureBoundingBox existing : placedBoxes) {
            if (intersects(existing, nextBox)) return null;
        }

        return new PlaceResult(nextOrigin, matchSettings, match, nextTemplate, nextBox);
    }

    /**
     * commits a PlaceResult to the world: places blocks, replaces jigsaws,
     * registers the bounding box, and enqueues new connectors.
     */
    private void commitPlace(WorldServer world, TemplateManager manager, MinecraftServer server,
                              PendingConnection pending, PlaceResult result,
                              List<StructureBoundingBox> placedBoxes,
                              Queue<PendingConnection> queue,
                              ResourceLocation placedRes,
                              Map<ResourceLocation, Integer> placementCounts) {

    	placementCounts.merge(placedRes, 1, Integer::sum);

        BlockPos attachPoint = pending.jigsawPos.offset(pending.facing);

        result.nextTemplate.addBlocksToWorld(world, result.nextOrigin, result.matchSettings);
        placedBoxes.add(result.nextBox);

        replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
        replaceJigsaw(world, result.nextOrigin.add(result.match.localPos), result.match.turnsInto);

        for (JigsawConnector nc : TemplateUtils.getJigsawConnectors(result.nextTemplate, result.nextOrigin, result.matchSettings)) {
            if (nc.worldPos.equals(attachPoint)) continue;

            PendingConnection next = new PendingConnection();
            next.jigsawPos = nc.worldPos;
            next.facing = nc.facing;
            next.targetPool = nc.targetPool;
            next.turnsInto = nc.turnsInto;
            next.depth = pending.depth + 1;
            queue.add(next);
        }
    }
	
	public static StructureBoundingBox computeBoundingBox(BlockPos origin, BlockPos size, Rotation rotation) {
	    int sx = size.getX();
	    int sy = size.getY();
	    int sz = size.getZ();

	    BlockPos rotatedSize = Template.transformedBlockPos(
	        new PlacementSettings().setRotation(rotation),
	        new BlockPos(sx - 1, sy - 1, sz - 1)
	    );

	    int minX = origin.getX() + Math.min(0, rotatedSize.getX());
	    int minY = origin.getY();
	    int minZ = origin.getZ() + Math.min(0, rotatedSize.getZ());
	    int maxX = origin.getX() + Math.max(0, rotatedSize.getX());
	    int maxY = origin.getY() + sy - 1;
	    int maxZ = origin.getZ() + Math.max(0, rotatedSize.getZ());

	    return new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public static boolean intersects(StructureBoundingBox a, StructureBoundingBox b) {
	    return a.maxX >= b.minX && a.minX <= b.maxX
	        && a.maxY >= b.minY && a.minY <= b.maxY
	        && a.maxZ >= b.minZ && a.minZ <= b.maxZ;
	}
	
	private void replaceJigsaw(WorldServer world, BlockPos pos, ResourceLocation turnsInto) {
	    Block block = ForgeRegistries.BLOCKS.getValue(turnsInto);
	    if (block == null) {
	        Main.getLogger().warn("turnsInto block not found: {}, replacing with air", turnsInto);
	        block = Blocks.AIR;
	    }
	    world.setBlockState(pos, block.getDefaultState());
	}
}