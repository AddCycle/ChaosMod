package net.chaos.chaosmod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ConfigSyncHandler {

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Reference.MODID.equals(event.getModID())) {
            ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
        }
    }
}