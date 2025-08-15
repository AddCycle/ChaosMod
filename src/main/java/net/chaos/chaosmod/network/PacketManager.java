package net.chaos.chaosmod.network;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.inventory.shield.PacketShieldSync;
import net.chaos.chaosmod.gui.GuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.broadcast.MessageDisplayText;

public class PacketManager {

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

	public static void initMessages() {
		int id = 0;
    	network.registerMessage(MessageDisplayText.MessageDisplayTextHandler.class, MessageDisplayText.class, id++, Side.CLIENT);
		network.registerMessage(JobsCommandMessage.JobsMessageHandler.class, JobsCommandMessage.class, id++, Side.CLIENT);
		network.registerMessage(PacketOpenAccessoryGui.Handler.class, PacketOpenAccessoryGui.class, id++, Side.SERVER);
		network.registerMessage(PacketAccessorySync.Handler.class, PacketAccessorySync.class, id++, Side.CLIENT);
		network.registerMessage(PacketShieldSync.Handler.class, PacketShieldSync.class, id++, Side.CLIENT);
		network.registerMessage(PacketForgeCraft.Handler.class, PacketForgeCraft.class, id++, Side.SERVER);
		network.registerMessage(PacketSpawnCustomParticle.ClientHandler.class, PacketSpawnCustomParticle.class, id++, Side.CLIENT);
		network.registerMessage(PacketShowFireOverlay.Handler.class, PacketShowFireOverlay.class, id++, Side.CLIENT);
		network.registerMessage(PacketSyncJobs.Handler.class, PacketSyncJobs.class, id++, Side.CLIENT);
		network.registerMessage(PacketSyncPlayerJobs.Handler.class, PacketSyncPlayerJobs.class, id++, Side.CLIENT);
	}

	public static void init() {
		initMessages();
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
}
