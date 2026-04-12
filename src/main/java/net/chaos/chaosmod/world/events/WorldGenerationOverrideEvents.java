package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.world.gen.overworld.MapGenCustomCaves;
import net.chaos.chaosmod.world.structures.MapGenCustomCavesHell;
import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

/**
 * All events in this class tend to be fired on {@link net.minecraftforge.common.MinecraftForge#TERRAIN_GEN_BUS TERRAIN_GEN_BUS}
 */
public class WorldGenerationOverrideEvents {

	@SubscribeEvent
	public void replaceStructureGenerator(InitMapGenEvent event) {
		if (event.getType() == InitMapGenEvent.EventType.VILLAGE && event.getOriginalGen() instanceof MapGenVillage) {
			Main.getLogger().info("CUSTOM VILLAGE REPLACEMENT");
			event.setNewGen(new MapGenCustomVillage());
		}
		
		// custom structures addition
		if (event.getType() == EventType.CUSTOM) {
			Main.getLogger().info("worldGenerationOverrides : " + event.getClass().getName());
		}

		if (!Loader.isModLoaded(Reference.BIOMESOPLENTY)) {
			if (event.getType() == InitMapGenEvent.EventType.NETHER_CAVE) {
				Main.getLogger().info("CUSTOM NETHER CAVES REPLACEMENT");
				event.setNewGen(new MapGenCustomCavesHell()); // tends to add interesting caves to nether
			} else if (event.getType() == InitMapGenEvent.EventType.CAVE) {
				Main.getLogger().info("CUSTOM OVERWORLD CAVES REPLACEMENT");
				event.setNewGen(new MapGenCustomCaves()); // tends to add interesting caves to overworld
			}
		}
	}
}
