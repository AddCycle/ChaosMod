package net.chaos.chaosmod.init;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import util.Reference;

public class ModDamageSources {
	public static final DamageSource ROCK_DAMAGE = new DamageSource(Reference.MODID + ":rock");
	public static final DamageSource MENHIR_DAMAGE = new DamageSource(Reference.MODID + ":menhir");
	public static final DamageSource DRAGON_FIREBALL_DAMAGE = new DamageSource(Reference.MODID + ":dragon_fireball");
	public static final DamageSource LASER_DAMAGE = new DamageSource((Reference.MODID + ":eye_laser_boss")) {
		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
			return new TextComponentTranslation("damage.eye_boss.you_should_read_documentation"); // add localization
		}
	};
	public static final DamageSource BLUE_FIRE = new DamageSource(Reference.MODID + ":blue_fire");

}
