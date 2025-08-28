package net.chaos.chaosmod.particle.events;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class TextureStitchingEvent {

	@SubscribeEvent
	public static void onTextureAtlasStitching(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(new ResourceLocation(Reference.MODID, "particle/money"));
	}

}
