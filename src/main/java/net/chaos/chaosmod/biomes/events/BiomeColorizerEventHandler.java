package net.chaos.chaosmod.biomes.events;

import net.chaos.chaosmod.init.ModBiomes;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class BiomeColorizerEventHandler {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onBlockColorsInit(ColorHandlerEvent.Block event) {
	    BlockColors colors = event.getBlockColors();

	    colors.registerBlockColorHandler((state, world, pos, tintIndex) -> {
	        BlockPlanks.EnumType type = state.getValue(BlockOldLeaf.VARIANT);

            if (type == BlockPlanks.EnumType.SPRUCE)
            {
                return ColorizerFoliage.getFoliageColorPine();
            }
            else if (type == BlockPlanks.EnumType.BIRCH)
            {
            	return isCustomBiome(world, pos) ? BiomeColorHelper.getFoliageColorAtPos(world, pos) : ColorizerFoliage.getFoliageColorBirch();
            }
            else
            {
                return world != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(world, pos) : ColorizerFoliage.getFoliageColorBasic();
            }
	    }, Blocks.LEAVES);
	}
	
	@SideOnly(Side.CLIENT)
	private static boolean isCustomBiome(IBlockAccess world, BlockPos pos) {
		if (world == null || pos == null) return false;

		Biome biome = world.getBiome(pos);
		if (biome == null) return false;
		
		if (biome == ModBiomes.SPRING_BIOME) return true;
		return false;
	}
}