package net.chaos.chaosmod.config;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ConfigEventHandler {

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MODID)) {
            ModConfig.getConfig().save(); // Save current in-memory config to file
            ModConfig.loadConfig();
        }
    }

    public static void JVM_load() {}
}