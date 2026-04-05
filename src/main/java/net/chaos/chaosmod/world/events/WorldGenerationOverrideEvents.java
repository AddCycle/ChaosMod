package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.world.gen.overworld.MapGenCustomCaves;
import net.chaos.chaosmod.world.structures.MapGenCustomCavesHell;
import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

public class WorldGenerationOverrideEvents {

	@SubscribeEvent
	public void replaceStructureGenerator(InitMapGenEvent event) {
		if (event.getType() == InitMapGenEvent.EventType.VILLAGE && event.getOriginalGen() instanceof MapGenVillage) {
			Main.getLogger().info("CUSTOM VILLAGE REPLACEMENT");
			event.setNewGen(new MapGenCustomVillage());
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
