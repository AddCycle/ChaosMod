package net.chaos.chaosmod.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public interface IRightClickableEntity {
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand);
	public default boolean canBeCollidedWith() {
		return true;
	}
}