package net.chaos.chaosmod.biomes.gen.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.chaos.chaosmod.biomes.gen.WorldGenFlowerPatch;
import net.chaos.chaosmod.biomes.gen.WorldGenTreeBark;
import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.CustomLog.CustomLogVariant;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class BiomeGenEventHandler {
	public static final ResourceLocation PINK_FOREST = new ResourceLocation(Reference.MATHSMOD, "pink_forest");
	public static final ResourceLocation DESOLATE_LANDS = new ResourceLocation(Reference.MATHSMOD, "desolate_lands");
	public static final ResourceLocation UNUSUAL_FOREST = new ResourceLocation(Reference.MATHSMOD, "unusual_forest");
	public static final ResourceLocation GREEN_PLAIN = new ResourceLocation(Reference.MATHSMOD, "green_plain");
	private static List<Biome> ALLOWED_BIOMES = new ArrayList<>(Arrays.asList(
		Biomes.PLAINS,
		Biomes.FOREST,
		Biomes.BIRCH_FOREST,
		Biomes.ROOFED_FOREST,
		Biomes.FOREST_HILLS,
		Biomes.MUTATED_FOREST,
		Biomes.MUTATED_BIRCH_FOREST,
		Biomes.SAVANNA,
		Biomes.TAIGA_HILLS,
		Biomes.SWAMPLAND,
		Biomes.MUSHROOM_ISLAND,
		Biomes.JUNGLE,
		ModBiomes.GIANT_MOUNTAIN,
		ModBiomes.NETHER_CAVES,
		ModBiomes.CHAOS_LAND_BIOME,
		ModBiomes.ENDER_GARDEN
	));

	@SubscribeEvent
	public static void post(DecorateBiomeEvent.Post event) {
		if (event.getWorld().isRemote) return;

		Biome biome = event.getWorld().getBiome(event.getPos());
		if (biome == null || !ALLOWED_BIOMES.contains(biome)) return;

		WorldGenTreeBark treeBarkGenerator = getBarkGenForBiome(biome);
		treeBarkGenerator.setGeneratedChunk(event.getChunkPos());
		treeBarkGenerator.generate(event.getWorld(), event.getRand(), event.getPos());

		WorldGenFlowerPatch worldGenFlowerPatch = new WorldGenFlowerPatch(ModBlocks.FLOWER_PATCH);
		worldGenFlowerPatch.setGeneratedChunk(event.getChunkPos());
		worldGenFlowerPatch.generate(event.getWorld(), event.getRand(), event.getPos()); // cascading worldgenlag
	}
	
	// TODO : refactor, let only the blockstate because LOG, LOG2 are useless for the gen
	private static WorldGenTreeBark getBarkGenForBiome(Biome biome) {
		IBlockState log = Blocks.LOG.getDefaultState();
		IBlockState log2 = Blocks.LOG2.getDefaultState();
		IBlockState oak = log.withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
		IBlockState spruce = log.withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
		IBlockState birch = log.withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
		IBlockState jungle = log.withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);

		IBlockState acacia = log2.withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA);
		IBlockState dark_oak = log2.withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK);

		if (biome == Biomes.PLAINS) return new WorldGenTreeBark(Blocks.LOG, oak);
		if (biome == Biomes.FOREST) return new WorldGenTreeBark(Blocks.LOG, oak);
		if (biome == Biomes.BIRCH_FOREST) return new WorldGenTreeBark(Blocks.LOG, birch);
		if (biome == Biomes.ROOFED_FOREST) return new WorldGenTreeBark(Blocks.LOG2, dark_oak);
		if (biome == Biomes.FOREST_HILLS) return new WorldGenTreeBark(Blocks.LOG, oak); // FIXME : and birch also
		if (biome == Biomes.MUTATED_FOREST) return new WorldGenTreeBark(Blocks.LOG, oak); // also birch
		if (biome == Biomes.MUTATED_BIRCH_FOREST) return new WorldGenTreeBark(Blocks.LOG, birch);
		if (biome == Biomes.SAVANNA) return new WorldGenTreeBark(Blocks.LOG2, acacia);
		if (biome == Biomes.TAIGA || biome == Biomes.TAIGA_HILLS || biome == Biomes.REDWOOD_TAIGA || biome == Biomes.REDWOOD_TAIGA_HILLS) return new WorldGenTreeBark(Blocks.LOG, spruce);
		if (biome == Biomes.SWAMPLAND) return new WorldGenTreeBark(Blocks.LOG, oak);
//		if (biome == Biomes.MUSHROOM_ISLAND) return new WorldGenTreeBark(Blocks.BROWN_MUSHROOM_BLOCK, acacia);
		if (biome == Biomes.JUNGLE) return new WorldGenTreeBark(Blocks.LOG, jungle);
		
		// MODDED BIOMES //
		IBlockState snowy_log = ModBlocks.CUSTOM_LOG.getDefaultState().withProperty(CustomLog.VARIANT, CustomLogVariant.SNOWY);
		IBlockState maple_log = ModBlocks.CUSTOM_LOG.getDefaultState().withProperty(CustomLog.VARIANT, CustomLogVariant.MAPLE);
		IBlockState ender_log = ModBlocks.CUSTOM_LOG.getDefaultState().withProperty(CustomLog.VARIANT, CustomLogVariant.ENDER);
		IBlockState olive_log = ModBlocks.CUSTOM_LOG.getDefaultState().withProperty(CustomLog.VARIANT, CustomLogVariant.OLIVE);
		if (biome == ModBiomes.GIANT_MOUNTAIN) return new WorldGenTreeBark(ModBlocks.CUSTOM_LOG, snowy_log);
		if (biome == ModBiomes.NETHER_CAVES) return new WorldGenTreeBark(ModBlocks.CUSTOM_LOG, maple_log);
		if (biome == ModBiomes.ENDER_GARDEN) return new WorldGenTreeBark(ModBlocks.CUSTOM_LOG, ender_log);
		if (biome == ModBiomes.CHAOS_LAND_BIOME) return new WorldGenTreeBark(ModBlocks.CUSTOM_LOG, olive_log);

		if (matchNames(biome.getRegistryName(), PINK_FOREST)) {
			Block cherry_log = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Reference.MATHSMOD, "cherry_log"));
			if (cherry_log != null) {
				IBlockState state = cherry_log.getDefaultState();
				return new WorldGenTreeBark(cherry_log, state);
			}
		}
		if (matchNames(biome.getRegistryName(), DESOLATE_LANDS)) {
			Block burned_log = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Reference.MATHSMOD, "burnt_log"));
			if (burned_log != null) {
				return new WorldGenTreeBark(burned_log, burned_log.getDefaultState());
			}
		}
		if (matchNames(biome.getRegistryName(), UNUSUAL_FOREST)) return new WorldGenTreeBark(Blocks.LOG);
		if (matchNames(biome.getRegistryName(), GREEN_PLAIN)) return new WorldGenTreeBark(Blocks.LOG);
		// END MODDED BIOMES //

		return new WorldGenTreeBark(Blocks.LOG);
	}
	
	private static boolean matchNames(ResourceLocation rl, ResourceLocation rl2) {
		return rl.equals(rl2);
	}
	
	/**
	 * 
	 * @param modid
	 * @param biomeNamespaces example if biomeReg = modid:pink_forest juste pass pink_forest
	 */
	public static void addAdditionalBiomesFromMod(String modid, String ...biomeNames) {
		if (!Loader.isModLoaded(modid)) return;
		for (String biomeId : biomeNames) {
			Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(modid, biomeId));
			if (biome == null) continue;
			ALLOWED_BIOMES.add(biome);
		}
	}
}