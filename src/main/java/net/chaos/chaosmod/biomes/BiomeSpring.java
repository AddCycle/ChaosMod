package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.biomes.gen.treegen.WorldGenBirchTreeHive;
import net.chaos.chaosmod.biomes.util.BiomeUtils;
import net.chaos.chaosmod.blocks.BlockRose;
import net.chaos.chaosmod.entity.animal.EntityBee;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeSpring extends Biome {
    protected static final WorldGenBirchTreeHive BIRCH_TREE = new WorldGenBirchTreeHive(false, false);
	private static final int GRASS_YELLOW_LIGHT = 0xd4c40f;
	private static final int GRASS_YELLOW_DARK  = 0x8a7a00;
	
	public BiomeSpring() {
        super(new Biome.BiomeProperties("Spring Biome")
            .setBaseHeight(Biomes.PLAINS.getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(1.2F)
//            .setWaterColor(65535)
            .setRainfall(0.0f)
            .setRainDisabled());

        this.decorator.treesPerChunk = 8;

        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();

        this.decorator.generateFalls = false; // lava/water lakes

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityBee.class, 20, 1, 3));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 10, 2, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 10, 3, 5));
//        this.spawnableCreatureList.add(new SpawnListEntry(EntityPicsou.class, 4, 8, 10));
//        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 2, 5, 5));

        this.flowers.clear();
        this.addFlower(((BlockRose) ModBlocks.ROSE_FLOWER).getStateFromMeta(0), 10);
    }
	
	/**
	 * Plains behavior-code
	 */
	@Override
	public EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
        double d0 = GRASS_COLOR_NOISE.getValue((double)pos.getX() / 200.0D, (double)pos.getZ() / 200.0D);

        if (d0 < -0.8D)
        {
            int j = rand.nextInt(4);

            switch (j)
            {
                case 0:
                    return BlockFlower.EnumFlowerType.ORANGE_TULIP;
                case 1:
                    return BlockFlower.EnumFlowerType.RED_TULIP;
                case 2:
                    return BlockFlower.EnumFlowerType.PINK_TULIP;
                case 3:
                default:
                    return BlockFlower.EnumFlowerType.WHITE_TULIP;
            }
        }
        else if (rand.nextInt(3) > 0)
        {
            int i = rand.nextInt(3);

            if (i == 0)
            {
                return BlockFlower.EnumFlowerType.POPPY;
            }
            else
            {
                return i == 1 ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY;
            }
        }
        else
        {
            return BlockFlower.EnumFlowerType.DANDELION;
        }
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return BIRCH_TREE;
	}
	
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return super.getSkyColorByTemp(currentTemperature);
	}

	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		double scale = 0.01;
		double noise = (1 + GRASS_COLOR_NOISE.getValue(pos.getX() * scale, pos.getZ() * scale)) / 2;

		return BiomeUtils.blendColors(noise, 0xffaf00, 0xffff00); // tweaking
	}

	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		double scale = 0.01;
		double noise = (1 + GRASS_COLOR_NOISE.getValue(pos.getX() * scale, pos.getZ() * scale)) / 2;
		return BiomeUtils.blendColors(noise, 0xffaf00, 0xffff00); // tweaking
	}
}