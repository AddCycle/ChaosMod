package net.chaos.chaosmod.common;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CommonUtils {
	
	/**
	 * Creates an EntityItem that cannot be lost as long as the player is nearby
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param stack
	 * @return
	 */
	public static EntityItem createBossLoot(World world, double x, double y, double z, ItemStack stack) {
		EntityItem e = new EntityItem(world, x, y, z, stack) {
			@Override
			protected void entityInit() {
				super.entityInit();
				isImmuneToFire = true;
			}
		};

		e.setNoDespawn();
		e.setNoPickupDelay();

		return e;
	}
}