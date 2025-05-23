package net.chaos.chaosmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

public class ModSounds {
	public static final SoundEvent PUTIN_WIDE_WALK = createSound("wide_walk");
	public static final SoundEvent HIGHEST_OP = createSound("highest");
	
	private static SoundEvent createSound(String name) {
        ResourceLocation rl = new ResourceLocation(Reference.MODID, name);
        return new SoundEvent(rl).setRegistryName(rl);
    }
	
	public static void registerSounds() {
		ForgeRegistries.SOUND_EVENTS.register(PUTIN_WIDE_WALK);
        ForgeRegistries.SOUND_EVENTS.register(HIGHEST_OP);
	}
}
