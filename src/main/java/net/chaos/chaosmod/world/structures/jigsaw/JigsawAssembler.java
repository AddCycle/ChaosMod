package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;

import net.chaos.chaosmod.Main;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

public class JigsawAssembler {
	private ResourceLocation startStructure;
	private PlacementSettings startSettings;
	private static final int flag = 2|16;

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

	// UPDATE : seems good enough
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
	    
	    startingTemplate.addBlocksToWorld(world, start, startSettings, flag);
	    placedBoxes.add(startBox);

	    populateChestsLootable(world, startingTemplate, start, world.rand, startSettings, LootTableList.CHESTS_SIMPLE_DUNGEON);

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
	        
	        // 1. try priority pieces first (minCount not satisfied)
	        for (ResourceLocation candidateRes : pool.getPriorityPieces(world.rand, pending.depth, placementCounts)) {
	            PlaceResult result = tryPlace(manager, server, candidateRes, pending, placedBoxes, pool, placementCounts);
	            if (result == null) continue;

	            commitPlace(world, manager, server, pending, result, placedBoxes, queue, candidateRes, placementCounts);
	            placed = true;
	            break;
	        }
	        
	        if (!placed) {
	        	for (ResourceLocation candidateRes : pool.getShuffled(world.rand, pending.depth)) {
	        		PlaceResult result = tryPlace(manager, server, candidateRes, pending, placedBoxes, pool, placementCounts);
	        		if (result == null) continue;
	        		commitPlace(world, manager, server, pending, result, placedBoxes, queue, candidateRes, placementCounts);
	        		placed = true;
	        		break;
	        	}
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

        result.nextTemplate.addBlocksToWorld(world, result.nextOrigin, result.matchSettings, flag);
        placedBoxes.add(result.nextBox);
        
	    populateChestsLootable(world, result.nextTemplate, result.nextOrigin, world.rand, result.matchSettings, LootTableList.CHESTS_WOODLAND_MANSION);

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
	    world.setBlockState(pos, block.getDefaultState(), flag);
	}

	private void populateChestsLootable(WorldServer world, Template template, BlockPos origin, Random rand, PlacementSettings placementSettings, ResourceLocation loot) {
		Map<BlockPos, String> map = template.getDataBlocks(origin, placementSettings);

		for (Entry<BlockPos, String> entry : map.entrySet())
		{
			if ("chest".equals(entry.getValue()))
			{
				BlockPos blockpos2 = entry.getKey();
				world.setBlockState(blockpos2, Blocks.AIR.getDefaultState(), 3);
				TileEntity tileentity = world.getTileEntity(blockpos2);
				if (!(tileentity instanceof TileEntityChest)) {
				    tileentity = world.getTileEntity(blockpos2.down());
				}
				if (tileentity instanceof TileEntityChest) {
					long seed = blockpos2.getX() * 341873128712L 
					          + blockpos2.getY() * 132897987541L 
					          + blockpos2.getZ();
				    ((TileEntityChest) tileentity).setLootTable(loot, seed);
				} else {
				    Main.getLogger().warn("No chest tile entity found at {} for loot population", blockpos2);
				}
			}
		}
	}
}