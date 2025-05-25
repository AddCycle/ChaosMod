package net.chaos.chaosmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;
public class ModSounds {
	public static final SoundEvent PUTIN_WIDE_WALK = createSound("wide_walk");
	public static final SoundEvent HIGHEST_OP = createSound("highest");
	public static final SoundEvent FIRE_FORCE_OP = createSound("fire_force");
	public static final SoundEvent CHAINSAWMAN_OP = createSound("chainsawman");
	public static final SoundEvent ZANKYOU_SANKYA = createSound("zankyou_sankya");
	public static final SoundEvent BLACK_ROVER = createSound("black_rover");
	public static final SoundEvent BLACK_CATCHER = createSound("black_catcher");
	public static final SoundEvent GRANDEUR = createSound("grandeur");
	public static final SoundEvent HOLLOW_PURPLE = createSound("hollow_purple");
	
	private static SoundEvent createSound(String name) {
        ResourceLocation rl = new ResourceLocation(Reference.MODID, name);
        return new SoundEvent(rl).setRegistryName(rl);
    }
	
	public static void registerSounds() {
		ForgeRegistries.SOUND_EVENTS.register(PUTIN_WIDE_WALK);
        ForgeRegistries.SOUND_EVENTS.register(HIGHEST_OP);
		ForgeRegistries.SOUND_EVENTS.register(FIRE_FORCE_OP);
        ForgeRegistries.SOUND_EVENTS.register(CHAINSAWMAN_OP);
        ForgeRegistries.SOUND_EVENTS.register(ZANKYOU_SANKYA);
        ForgeRegistries.SOUND_EVENTS.register(BLACK_ROVER);
        ForgeRegistries.SOUND_EVENTS.register(BLACK_CATCHER);
        ForgeRegistries.SOUND_EVENTS.register(GRANDEUR);
        ForgeRegistries.SOUND_EVENTS.register(HOLLOW_PURPLE);
	}
}
