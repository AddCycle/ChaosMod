package net.chaos.chaosmod.init;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.boss.entities.EntityRevengeBlazeBoss;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import util.Reference;

public class ModEntities {
	
	public static void registerEntities() {
		registerEntity("forge_guardian", EntityForgeGuardian.class, Reference.ENTITY_FORGE_GUARDIAN, 50, 31, 3093247);
		registerEntity("revenge_blaze_boss", EntityRevengeBlazeBoss.class, Reference.REVENGE_BLAZE_BOSS, 50, 31, 3093247);
		registerEntity("small_blue_fireball", EntitySmallBlueFireball.class, Reference.SMALL_BLUE_FIREBALL, 50, 31, 3093247);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entity, name, id, Main.instance, range, 1, true, color1, color2);
	}

}
