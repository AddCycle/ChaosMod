package net.chaos.chaosmod.biomes;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHell;
public class CustomBiomeHell extends BiomeHell {
	public static final CustomBiomeHell INSTANCE = new CustomBiomeHell();

	public CustomBiomeHell() {
		super(new Biome.BiomeProperties("Custom Hell"));
		// this.topBlock = Blocks.QUARTZ_BLOCK.getDefaultState();
		// this.fillerBlock = Blocks.MAGMA.getDefaultState();
		this.spawnableMonsterList.clear(); // Customize mobs if desired
		this.spawnableMonsterList.add(new SpawnListEntry(EntityPig.class, 50, 5, 10));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 50, 5, 10));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 50, 5, 10));
		
		// ModBiomes.BIOMES.add(this);
		// Add custom features like lava lakes, structures, etc.
	}
}
