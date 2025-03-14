package net.chaos.chaosmod.network;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.network.GuideCommandMessage.GuideMessageHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ChaosModPacketHandler {
	public static void registerMessage() {
		Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("chaosmod");
		Main.network.registerMessage(GuideMessageHandler.class, GuideCommandMessage.class, 0, Side.CLIENT);
	}
}
