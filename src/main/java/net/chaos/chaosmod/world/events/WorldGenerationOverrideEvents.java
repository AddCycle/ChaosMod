package net.chaos.chaosmod.world.events;

import java.util.Random;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.world.structures.MapGenCustomCavesHell;
import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class WorldGenerationOverrideEvents {
	public static final Random rand = new Random();

	@SubscribeEvent
	public void replaceStructureGenerator(InitMapGenEvent event) {
		System.out.println("InitMapGenEvent triggered: " + event.getType());

        if (event.getType() == InitMapGenEvent.EventType.VILLAGE && event.getOriginalGen() instanceof MapGenVillage) {
            Main.getLogger().info("CUSTOM VILLAGE REPLACEMENT");
            event.setNewGen(new MapGenCustomVillage());
        }
        
        if (Loader.isModLoaded("mathsmod") && event.getType() == InitMapGenEvent.EventType.VILLAGE) {
        	Main.getLogger().info("MathsMod is loaded, detecting SirojusVillageGen...");
        	if (event.getOriginalGen().getClass().getName().equals("com.mod.mathsmod.add.World.dims.generator.structures.MapGenSirojusVillage")) {
        		Main.getLogger().info("!!!!!!!!!!!!!!!!!!!!! Found MapGenSirojusVillage !!!!!!!!!!!!!!!!!!!!!");
        	}
        }
        
        if (!Loader.isModLoaded("biomesoplenty") && event.getType() == InitMapGenEvent.EventType.NETHER_CAVE) {
            Main.getLogger().info("CUSTOM NETHER CAVES REPLACEMENT");
            event.setNewGen(new MapGenCustomCavesHell());
        }
	}
}
