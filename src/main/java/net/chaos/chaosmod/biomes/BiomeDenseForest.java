package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.biomes.gen.treegen.WorldGenRootTree;
import net.chaos.chaosmod.biomes.gen.treegen.WorldGenTallBirchTree;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class BiomeDenseForest extends Biome {
	public static final WorldGenRootTree ROOT_TREE_GEN = new WorldGenRootTree();
	public static final WorldGenTallBirchTree TALL_TREE_GEN = new WorldGenTallBirchTree();
	public static final WorldGenTallGrass GRASS_GEN1 = new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
    private static final WorldGenBlockBlob FOREST_ROCK_GENERATOR1 = new WorldGenBlockBlob(Blocks.MOSSY_COBBLESTONE, 0);
    private static final WorldGenBlockBlob FOREST_ROCK_GENERATOR2 = new WorldGenBlockBlob(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE).getBlock(), 0);

	public static final IBlockState GRASS = Blocks.GRASS.getDefaultState();
	public static final IBlockState PODZOL = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
	public static final IBlockState COARSE_DIRT = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);

	public BiomeDenseForest() {
		super(new Biome.BiomeProperties("Density Forest")
			.setBaseHeight(Biomes.PLAINS.getBaseHeight())
			.setHeightVariation(0.1F)
			.setTemperature(0.4F)
			.setRainfall(0.4f));
		
		this.decorator.treesPerChunk = 10;
		this.decorator.extraTreeChance = 2.0f;
		
		this.decorator.mushroomsPerChunk = 1;
//		this.decorator.waterlilyPerChunk = 3; TODO : set waterlilys manually in replaceBiomeBlocks()

		this.topBlock = Blocks.GRASS.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();

		this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 2, 2, 4));

		this.flowers.clear();
		this.addFlower(ModBlocks.CUSTOM_FLOWER.getDefaultState(), 5);
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		this.fillerBlock = Blocks.DIRT.getDefaultState();
		double t = (MathHelper.clamp(noiseVal, -1, 1) + 1.0) * 0.5;

		if (t < 0.25) {
			this.topBlock = GRASS;
		} else if (t > 0.75) {
			this.topBlock = PODZOL;
		} else {
			this.topBlock = COARSE_DIRT;
		}

		super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}
	
	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
        if (TerrainGen.decorate(worldIn, rand, new ChunkPos(pos), DecorateBiomeEvent.Decorate.EventType.ROCK))
        {
            int i = rand.nextInt(3);

            for (int j = 0; j < i; ++j)
            {
                int k = rand.nextInt(16) + 8;
                int l = rand.nextInt(16) + 8;
                BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
                getRandomWorldGenForRocks(rand).generate(worldIn, rand, blockpos);
            }
        }

        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);

        if(TerrainGen.decorate(worldIn, rand, new ChunkPos(pos), DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        for (int i1 = 0; i1 < 7; ++i1)
        {
            int j1 = rand.nextInt(16) + 8;
            int k1 = rand.nextInt(16) + 8;
            int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
            DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
        }

		super.decorate(worldIn, rand, pos);
	}
	
	@SubscribeEvent
	public static void additionalDecorations(DecorateBiomeEvent.Decorate event) {
		if (event.getType() == EventType.PUMPKIN) {
			int chance = 8;
			generatePumpkinWithChance(event.getWorld(), event.getRand(), event.getPlacementPos(), chance);	
		}
	}

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand) {
		return GRASS_GEN1;
	}

	public WorldGenerator getRandomWorldGenForRocks(Random rand) {
		return rand.nextInt(5) < 3 ? FOREST_ROCK_GENERATOR2 : FOREST_ROCK_GENERATOR1;
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return rand.nextInt(5) < 3 ? TALL_TREE_GEN : BIG_TREE_FEATURE;
	}

	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		double scale = 0.01;
		double noise = (1 + GRASS_COLOR_NOISE.getValue(pos.getX() * scale, pos.getZ() * scale)) / 2;
		int green = (int) MathHelper.clamp(noise * 255, 125, 200);
		return green << 8;
	}

	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		double scale = 0.01;
		double noise = (1 + GRASS_COLOR_NOISE.getValue(pos.getX() * scale, pos.getZ() * scale)) / 2;
		int green = (int) MathHelper.clamp(noise * 255, 100, 200);
		return green << 8;
	}
	
	private static void generatePumpkinWithChance(World world, Random rand, BlockPos chunkPos, int chance) {
        if (rand.nextInt(chance) == 0)
        {
            int i5 = rand.nextInt(16) + 8;
            int k9 = rand.nextInt(16) + 8;
            int j13 = world.getHeight(chunkPos.add(i5, 0, k9)).getY() * 2;

            if (j13 > 0)
            {
                int k16 = rand.nextInt(j13);
                (new WorldGenPumpkin()).generate(world, rand, chunkPos.add(i5, k16, k9));
            }
        }
		
	}
}