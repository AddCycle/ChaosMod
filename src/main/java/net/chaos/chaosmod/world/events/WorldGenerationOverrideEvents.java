package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.world.structures.MapGenCustomCavesHell;
import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class WorldGenerationOverrideEvents {

	static {
        System.out.println("Static block triggered for WorldGenerationOverrideEvents.");
    }

	@SubscribeEvent
	public void replaceStructureGenerator(InitMapGenEvent event) {
		System.out.println("InitMapGenEvent triggered: " + event.getType());

        if (event.getType() == InitMapGenEvent.EventType.VILLAGE) {
            Main.getLogger().info("CUSTOM VILLAGE REPLACEMENT");
            event.setNewGen(new MapGenCustomVillage());
        }
        
        if (!Loader.isModLoaded("biomesoplenty") && event.getType() == InitMapGenEvent.EventType.NETHER_CAVE) {
            Main.getLogger().info("CUSTOM NETHER CAVES REPLACEMENT");
            event.setNewGen(new MapGenCustomCavesHell());
        }
	}

}
