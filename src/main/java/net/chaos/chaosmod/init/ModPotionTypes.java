package net.chaos.chaosmod.init;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import util.Reference;
public class ModPotionTypes {
	public static final PotionType VIKING_FRIEND_TYPE = new PotionType(
	        "viking_friend", new PotionEffect[] {new PotionEffect(ModPotions.POTION_VIKING, 9600)}).setRegistryName(new ResourceLocation(Reference.MODID, "viking_friend"));
}