package net.chaos.chaosmod.client.renderer.player;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModKeybinds;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketTaunt;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

// FIXME : Not working rn
//@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class RenderPlayerHandEvent {
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {

		if (ModKeybinds.tauntX.isPressed()) {
			PacketManager.network.sendToServer(new PacketTaunt(0));
		} else if (ModKeybinds.tauntY.isPressed()) {
			PacketManager.network.sendToServer(new PacketTaunt(1));
		} else if (ModKeybinds.tauntZ.isPressed()) {
			PacketManager.network.sendToServer(new PacketTaunt(2));
		}
	}

	@SubscribeEvent
	public static void onRenderHand(RenderPlayerEvent.Pre event) {

		EntityPlayer player = event.getEntityPlayer();

		int taunt = player.getEntityData().getInteger("taunt");
		if (taunt < 0) return;

		ModelPlayer model = event.getRenderer().getMainModel();

		if (taunt == 0) {
			Main.getLogger().info("tauntx");
	        model.bipedRightArm.rotateAngleX = (float) Math.toRadians(60);
		} else if (taunt == 1) {
			Main.getLogger().info("taunty");
	        model.bipedRightArm.rotateAngleY = (float) Math.toRadians(60);
	 	} else if (taunt == 2) {
			Main.getLogger().info("tauntz");
	        model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(60);
	 	}
	}
}