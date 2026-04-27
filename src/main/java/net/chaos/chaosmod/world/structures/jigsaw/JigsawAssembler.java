package net.chaos.chaosmod.world.structures.jigsaw;

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
	
	public void assemble(MinecraftServer server, BlockPos start) {
		WorldServer world = (WorldServer) server.getEntityWorld();
		TemplateManager manager = world.getStructureTemplateManager();

		Template startingTemplate = manager.getTemplate(server, startStructure);
		
		startingTemplate.addBlocksToWorld(world, start, startSettings);
		List<JigsawConnector> firstconnections = TemplateUtils.getJigsawConnectors(startingTemplate, start, startSettings);

		for (JigsawConnector c : firstconnections) {
			JigsawPool pool = JigsawPoolRegistry.get(c.targetPool);
			ResourceLocation pickedTemplate = pool.getRandomTemplate(world.rand);
			Template secondTemplate = manager.getTemplate(server, pickedTemplate);
//			PendingConnection pc = new PendingConnection();
//			pc.jigsawPos = c.worldPos;
//			pc.facing = c.facing;
//			pc.targetPool = c.targetPool;
//			pc.depth = 1;
			// FIXME : later add to a queue instead of building right now
//			pc.parentBounds = startingTemplate.getBoundingBox(settings, start);
			JigsawConnector match = null;
	        PlacementSettings matchSettings = null;

	        // try all 4 rotations until we find a connector that opposes ours
	        outer:
	        for (Rotation rot : Rotation.values()) {
	        	PlacementSettings secondSettings = new PlacementSettings().setRotation(rot);
	        	List<JigsawConnector> secondconnections = TemplateUtils.getJigsawConnectors(secondTemplate, BlockPos.ORIGIN, secondSettings);

	            for (JigsawConnector c2 : secondconnections) {
	                if (c2.facing == c.facing.getOpposite()) {
	                    match = c2;
	                    matchSettings = secondSettings;
	                    break outer;
	                }
	            }
	        }

	        if (match == null) {
	            System.out.println("No matching connector found for facing: " + c.facing);
	            replaceJigsaw(world, c.worldPos, c.turnsInto);
	            continue;
	        }

	        // one block in front of the open connector
	        BlockPos attachPoint = c.worldPos.offset(c.facing);

	        // match.localPos is already rotated by getJigsawConnectors, so this is correct
	        BlockPos secondOrigin = attachPoint.subtract(match.localPos);

	        secondTemplate.addBlocksToWorld(world, secondOrigin, matchSettings);
	        
	        // replace the jigsaws
	        replaceJigsaw(world, c.worldPos, c.turnsInto);
	        // the one from the second template (match)
	        replaceJigsaw(world, secondOrigin.add(match.localPos), match.turnsInto);
		}
	}
	
	public void assemble(MinecraftServer server, BlockPos start, int maxDepth) {
	    WorldServer world = (WorldServer) server.getEntityWorld();
	    TemplateManager manager = world.getStructureTemplateManager();

	    Queue<PendingConnection> queue = new LinkedList<>();

	    // place the starting template
	    Template startingTemplate = manager.getTemplate(server, startStructure);
	    startingTemplate.addBlocksToWorld(world, start, startSettings);

	    // enqueue all connectors from the starting template at depth 1
	    for (JigsawConnector c : TemplateUtils.getJigsawConnectors(startingTemplate, start, startSettings)) {
	        PendingConnection pc = new PendingConnection();
	        pc.jigsawPos = c.worldPos;
	        pc.facing = c.facing;
	        pc.targetPool = c.targetPool;
	        pc.turnsInto = c.turnsInto;
	        pc.depth = 1;
	        queue.add(pc);
	    }

	    // BFS
	    while (!queue.isEmpty()) {
	        PendingConnection pending = queue.poll();

	        // stop expanding past maxDepth
	        if (pending.depth >= maxDepth) {
	            replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	            continue;
	        }

	        JigsawPool pool = JigsawPoolRegistry.get(pending.targetPool);
	        ResourceLocation pickedTemplate = pool.getRandomTemplate(world.rand);
	        Template nextTemplate = manager.getTemplate(server, pickedTemplate);

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

	        if (match == null) {
	            Main.getLogger().warn("No matching connector for facing: {}", pending.facing);
	            replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	            continue;
	        }

	        BlockPos attachPoint = pending.jigsawPos.offset(pending.facing);
	        BlockPos nextOrigin = attachPoint.subtract(match.localPos);

	        nextTemplate.addBlocksToWorld(world, nextOrigin, matchSettings);

	        // replace the jigsaws that are facing to each other
	        replaceJigsaw(world, pending.jigsawPos, pending.turnsInto);
	        replaceJigsaw(world, nextOrigin.add(match.localPos), match.turnsInto);

	        // Enqueue all new open connectors from the placed template
	        for (JigsawConnector nc : TemplateUtils.getJigsawConnectors(nextTemplate, nextOrigin, matchSettings)) {
	            // skip the one we just consumed
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