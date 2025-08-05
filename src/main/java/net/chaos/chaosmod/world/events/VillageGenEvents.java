package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

//@EventBusSubscriber
public class VillageGenEvents {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
	    if (event.getWorld() instanceof WorldServer && event.getWorld().provider.getDimension() == 0) {
	        ChunkGeneratorOverworld chunkGen = (ChunkGeneratorOverworld) ((WorldServer) event.getWorld()).getChunkProvider().chunkGenerator;
	        ReflectionHelper.setPrivateValue(ChunkGeneratorOverworld.class, chunkGen, new MapGenCustomVillage(), "villageGenerator", "field_185931_c");
	    }
	}

}
