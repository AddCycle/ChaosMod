package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
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
            System.out.println("Replacing vanilla village generator with custom one.");
            event.setNewGen(new MapGenCustomVillage());
        }
	}

}
