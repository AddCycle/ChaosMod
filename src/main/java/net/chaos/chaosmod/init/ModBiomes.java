package net.chaos.chaosmod.init;

import net.chaos.chaosmod.biomes.CustomBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import util.Reference;

public class ModBiomes {
	public static final Biome GIANT_MOUNTAIN = new CustomBiome();

    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        GIANT_MOUNTAIN.setRegistryName(new ResourceLocation(Reference.MODID, "giant_mountains"));
        event.getRegistry().register(GIANT_MOUNTAIN);
    }

}
