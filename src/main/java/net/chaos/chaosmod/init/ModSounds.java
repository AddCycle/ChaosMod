package net.chaos.chaosmod.init;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

public class ModSounds {
	public static final SoundEvent PUTIN_WIDE_WALK = createSound("wide_walk");
	public static final SoundEvent HIGHEST_OP = createSound("highest");
	public static final SoundEvent FIRE_FORCE_OP = createSound("fire_force");
	public static final SoundEvent CHAINSAWMAN_OP = createSound("chainsawman");
	
	private static SoundEvent createSound(String name) {
        ResourceLocation rl = new ResourceLocation(Reference.MODID, name);
        return new SoundEvent(rl).setRegistryName(rl);
    }
	
	public static void registerSounds() {
		ForgeRegistries.SOUND_EVENTS.register(PUTIN_WIDE_WALK);
        ForgeRegistries.SOUND_EVENTS.register(HIGHEST_OP);
		ForgeRegistries.SOUND_EVENTS.register(FIRE_FORCE_OP);
        ForgeRegistries.SOUND_EVENTS.register(CHAINSAWMAN_OP);
	}
}
