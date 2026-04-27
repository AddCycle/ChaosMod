package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.structures.JigsawBlock;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class TemplateUtils {
	
	// just a little test to get the blocks names along with their blockpos ?
    public static Map<BlockPos, String> getJigsawBlocks(Template template, BlockPos pos, PlacementSettings placementIn)
    {
        Map<BlockPos, String> map = Maps.<BlockPos, String>newHashMap();
        StructureBoundingBox structureboundingbox = placementIn.getBoundingBox();

        for (Template.BlockInfo template$blockinfo : template.blocks)
        {
            BlockPos blockpos = Template.transformedBlockPos(placementIn, template$blockinfo.pos).add(pos);

            if (structureboundingbox == null || structureboundingbox.isVecInside(blockpos))
            {
                IBlockState iblockstate = template$blockinfo.blockState;

                if (iblockstate.getBlock() == ModBlocks.JIGSAW_BLOCK && template$blockinfo.tileentityData != null)
                {
                    map.put(blockpos, template$blockinfo.tileentityData.getString("pool"));
                }
            }
        }

        return map;
    }

    public static List<JigsawConnector> getJigsawConnectors(Template template, BlockPos pos, PlacementSettings placementIn)
    {
        List<JigsawConnector> list = Lists.newArrayList();
        StructureBoundingBox structureboundingbox = placementIn.getBoundingBox();

        for (Template.BlockInfo template$blockinfo : template.blocks)
        {
        	BlockPos localPos = Template.transformedBlockPos(placementIn, template$blockinfo.pos);
            BlockPos blockpos = localPos.add(pos);

            if (structureboundingbox == null || structureboundingbox.isVecInside(blockpos))
            {
                IBlockState iblockstate = template$blockinfo.blockState;

                if (iblockstate.getBlock() == ModBlocks.JIGSAW_BLOCK && template$blockinfo.tileentityData != null)
                {
                	JigsawConnector connector = new JigsawConnector();
                	connector.localPos = localPos;
                	connector.worldPos = blockpos;
                	connector.targetPool = template$blockinfo.tileentityData.getString("pool");
                	connector.turnsInto = new ResourceLocation(template$blockinfo.tileentityData.getString("turnsInto"));

                	EnumFacing rawFacing = iblockstate.getValue(JigsawBlock.FACING);
//                	connector.facing = rawFacing;
                	EnumFacing rotatedFacing = placementIn.getRotation().rotate(rawFacing);
                	connector.facing = placementIn.getMirror().mirror(rotatedFacing);

                	list.add(connector);
                }
            }
        }

        return list;
    }
	
	public static List<Template.BlockInfo> getBlockInfos(Template template) {
		return template.blocks;
	}
	
	/**
	 * DEBUG utilities
	 */
	public static void printAllJigsaws(Map<BlockPos, String> map) {
        Main.getLogger().info("********** STRUCTURE JIGSAWS ************");
        map.forEach((pos, name) -> {
        	Main.getLogger().info("pool: {}, pos {}", name, pos.toString());
        });
	}

	public static void printAllJigsaws(Template template) {
        Map<BlockPos, String> jigsaw_map = getJigsawBlocks(template, BlockPos.ORIGIN, new PlacementSettings());
        Main.getLogger().info("********** STRUCTURE JIGSAWS ************");
        jigsaw_map.forEach((pos, name) -> {
        	Main.getLogger().info("pool: {}, pos {}", name, pos.toString());
        });
	}
}