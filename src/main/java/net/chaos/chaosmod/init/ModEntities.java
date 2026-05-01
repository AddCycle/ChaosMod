package net.chaos.chaosmod.init;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.entity.EntityBipedBasic;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.entity.EntityEyeCrystal;
import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.entity.EntitySphere;
import net.chaos.chaosmod.entity.EntitySwordOfWrath;
import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.entity.LittleGiantEntity;
import net.chaos.chaosmod.entity.animal.EntityBear;
import net.chaos.chaosmod.entity.animal.EntityBee;
import net.chaos.chaosmod.entity.animal.EntityVulture;
import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityEyeCrystalBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityRevengeBlazeBoss;
import net.chaos.chaosmod.entity.projectile.EntityMenhir;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import util.Colors;
import util.Reference;

public class ModEntities {
	
	public static void registerEntityPlacements() {
		EntitySpawnPlacementRegistry.setPlacementType(EntityVulture.class, SpawnPlacementType.IN_AIR);
		EntitySpawnPlacementRegistry.setPlacementType(EntityBee.class, SpawnPlacementType.IN_AIR);
	}
	
	// TODO : add EntityBee spawn inside the forests near nests
	public static void registerEntitySpawn() {
		EntityRegistry.addSpawn(EntityVulture.class, 10, 3, 5, EnumCreatureType.CREATURE, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.MUTATED_DESERT, Biomes.MESA, Biomes.MESA_CLEAR_ROCK, Biomes.MESA_ROCK, Biomes.MUTATED_MESA, Biomes.MUTATED_MESA_CLEAR_ROCK, Biomes.MUTATED_MESA_ROCK);
	}

	public static void registerEntities() {
		registerEntities(
				new EntityFactory("forge_guardian", EntityForgeGuardian.class, Reference.ENTITY_FORGE_GUARDIAN,
						Colors.BLACK, Colors.PURPLE),
				new EntityFactory("revenge_blaze_boss", EntityRevengeBlazeBoss.class, Reference.REVENGE_BLAZE_BOSS,
						Colors.YELLOW, Colors.ORANGE),
				new EntityFactory("small_blue_fireball", EntitySmallBlueFireball.class, Reference.SMALL_BLUE_FIREBALL,
						Colors.BLACK, Colors.BLUE),
				new EntityFactory("mountain_giant_boss", EntityMountainGiantBoss.class, Reference.MOUNTAIN_GIANT_BOSS,
						Colors.BROWN, Colors.BLACK),
				new EntityFactory("rock", EntityRock.class, Reference.ENTITY_ROCK, Colors.BLACK, Colors.BLACK),
				new EntityFactory("chaos_sage", EntityChaosSage.class, Reference.CHAOS_SAGE, Colors.BLUE, Colors.BROWN),
				new EntityFactory("menhir", EntityMenhir.class, Reference.ENTITY_MENHIR, Colors.BLACK, Colors.BLACK),
				new EntityFactory("viking", EntityViking.class, Reference.ENTITY_VIKING, Colors.BLUE, Colors.RED),
				new EntityFactory("picsou", EntityPicsou.class, Reference.ENTITY_PICSOU, Colors.BLUE, Colors.ORANGE),
				new EntityFactory("little_big_giants", LittleGiantEntity.class, Reference.ENTITY_GIANTS, 50,
						Colors.ORANGE, Colors.YELLOW),
				new EntityFactory("eye_of_truth", EntityEyeCrystalBoss.class, Reference.EYE_OF_TRUTH, Colors.PURPLE,
						Colors.RED),
				new EntityFactory("eye_crystal", EntityEyeCrystal.class, Reference.EYE_CRYSTAL, Colors.PURPLE,
						Colors.YELLOW),
				new EntityFactory("chaos_master", ChaosMasterBoss.class, Reference.CHAOS_MASTER, Colors.CYAN,
						Colors.WHITE),
				new EntityFactory("entity_biped_base", EntityBipedBasic.class, Reference.ENTITY_BIPED_BASIC,
						Colors.CYAN, Colors.GREEN),
				new EntityFactory("entity_sphere", EntitySphere.class, Reference.ENTITY_SPHERE,
						Colors.BLACK, Colors.WHITE),
				new EntityFactory("sword_of_wrath", EntitySwordOfWrath.class, Reference.ENTITY_SWORD_OF_WRATH,
						Colors.RED, Colors.ORANGE),
				new EntityFactory("bear", EntityBear.class, Reference.ENTITY_BEAR,
						Colors.BROWN, Colors.ORANGE),
				new EntityFactory("vulture", EntityVulture.class, Reference.ENTITY_VULTURE,
						Colors.BROWN, Colors.RED),
				new EntityFactory("bee", EntityBee.class, Reference.ENTITY_BEE,
						Colors.YELLOW, Colors.BLACK)
				);
		
		registerEntitySpawn();
		registerEntityPlacements();
	}

	private static void registerEntities(EntityFactory... factories) {
		for (EntityFactory f : factories) {
			registerEntity(f.name, f.entityClass, f.id, f.range, f.color1, f.color2);
		}
	}

	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, Colors color1,
			Colors color2) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entity, name, id, Main.instance,
				range, 1, true, color1.getRGB(), color2.getRGB());
	}

	public static class EntityFactory {
		public String name;
		public Class<? extends Entity> entityClass;
		public int id;
		public int range = 50;
		public Colors color1;
		public Colors color2;

		public EntityFactory(String name, Class<? extends Entity> entityClass, int id, Colors color1, Colors color2) {
			this(name, entityClass, id, 50, color1, color2);
		}

		public EntityFactory(String name, Class<? extends Entity> entityClass, int id, int range, Colors color1,
				Colors color2) {
			this.name = name;
			this.entityClass = entityClass;
			this.id = id;
			this.range = range;
			this.color1 = color1;
			this.color2 = color2;
		}
	}
}