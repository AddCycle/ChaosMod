package net.chaos.chaosmod.biomes;

import net.chaos.chaosmod.entity.animal.EntityBee;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHellDecorator;

public class BiomeHive extends Biome {

	public BiomeHive() {
		super(new BiomeProperties("The Hive").setTemperature(2.0f).setRainfall(0.0f).setRainDisabled());
		this.spawnableCreatureList.clear();
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityBee.class, 100, 3, 5));
//        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityHoneySlime.class, 50, 4, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCow.class, 100, 4, 4));
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 50, 4, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));
		this.decorator = new BiomeHellDecorator();
	}

	@Override
	public float getSpawningChance() {
		return 0.1f; // FIXME MAX
	}
}