package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

	public void assemble(MinecraftServer server, BlockPos start, int maxDepth) {
	    WorldServer world = (WorldServer) server.getEntityWorld();
	    TemplateManager manager = world.getStructureTemplateManager();

	    Queue<PendingConnection> queue = new LinkedList<>();
	    List<StructureBoundingBox> placedBoxes = new ArrayList<>(); // all placed pieces from the start

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

	        // try candidates until one fits or we exhaust options
	        List<ResourceLocation> candidates = pool.getShuffled(world.rand);
	        boolean placed = false;

	        for (ResourceLocation candidateRes : candidates) {
	            Template nextTemplate = manager.getTemplate(server, candidateRes);

	            // try all rotations for this candidate
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

	            if (match == null) continue; // this candidate has no matching connector, try next

	            // compute where this piece would land
	            BlockPos attachPoint = pending.jigsawPos.offset(pending.facing);
	            BlockPos nextOrigin = attachPoint.subtract(match.localPos);

	            // check intersections
	            BlockPos nextSize = nextTemplate.getSize();
	            StructureBoundingBox nextBox = computeBoundingBox(nextOrigin, nextSize, matchSettings.getRotation());

	            boolean intersects = false;
	            for (StructureBoundingBox existing : placedBoxes) {
	                if (intersects(existing, nextBox)) {
	                    intersects = true;
	                    break;
	                }
	            }

	            if (intersects) continue; // try next

	            // safe to place here
	            nextTemplate.addBlocksToWorld(world, nextOrigin, matchSettings);
	            placedBoxes.add(nextBox);

	            replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	            replaceJigsaw(world, nextOrigin.add(match.localPos), match.turnsInto);

	            for (JigsawConnector nc : TemplateUtils.getJigsawConnectors(nextTemplate, nextOrigin, matchSettings)) {
	                if (nc.worldPos.equals(attachPoint)) continue;

	                PendingConnection next = new PendingConnection();
	                next.jigsawPos = nc.worldPos;
	                next.facing = nc.facing;
	                next.targetPool = nc.targetPool;
	                next.turnsInto = nc.turnsInto;
	                next.depth = pending.depth + 1;
	                queue.add(next);
	            }

	            placed = true;
	            break; // stop trying candidates, this one worked
	        }

	        if (!placed) {
	            // all candidates either had no matching connector or intersected, seal it
	            replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	        }
	    }
	}
	
	public static StructureBoundingBox computeBoundingBox(BlockPos origin, BlockPos size, Rotation rotation) {
	    // Rotate the size vector around the origin
	    // Template placement rotates around (0,0) so we need to account for that
	    int sx = size.getX();
	    int sy = size.getY();
	    int sz = size.getZ();

	    // After rotation, the max corner might flip — compute all 8 isn't needed,
	    // just the rotated far corner
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