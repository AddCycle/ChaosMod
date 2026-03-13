package net.chaos.chaosmod.network;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.inventory.shield.PacketShieldSync;
import net.chaos.chaosmod.gui.GuiHandler;
import net.chaos.chaosmod.network.market.PacketSyncMarket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.broadcast.MessageDisplayText;

public class PacketManager {
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	private static int id = 0;
	
	private static <REQ extends IMessage, REPLY extends IMessage> void register(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side) {
		network.registerMessage(handler, message, id++, side);
	}

	public static void initMessages() {
    	register(MessageDisplayText.MessageDisplayTextHandler.class, MessageDisplayText.class, Side.CLIENT);
		register(JobsCommandMessage.JobsMessageHandler.class, JobsCommandMessage.class, Side.CLIENT);
		register(PacketOpenAccessoryGui.Handler.class, PacketOpenAccessoryGui.class, Side.SERVER);
		register(PacketAccessorySync.Handler.class, PacketAccessorySync.class, Side.CLIENT);
		register(PacketShieldSync.Handler.class, PacketShieldSync.class, Side.CLIENT);
		register(PacketForgeCraft.Handler.class, PacketForgeCraft.class, Side.SERVER);
		register(PacketSpawnCustomParticle.ClientHandler.class, PacketSpawnCustomParticle.class, Side.CLIENT);
		register(PacketShowFireOverlay.Handler.class, PacketShowFireOverlay.class, Side.CLIENT);
		register(PacketSyncJobs.Handler.class, PacketSyncJobs.class, Side.CLIENT);
		register(PacketSyncPlayerJobs.Handler.class, PacketSyncPlayerJobs.class, Side.CLIENT);
		register(PacketSyncMarket.Handler.class, PacketSyncMarket.class, Side.CLIENT);
	}

	public static void init() {
		initMessages();
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
}