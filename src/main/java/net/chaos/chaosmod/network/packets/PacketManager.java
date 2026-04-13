package net.chaos.chaosmod.network.packets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.gui.GuiHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import util.Reference;
import util.annotations.ModPacket;

public class PacketManager {
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	private static int id = 0;
	
	@SuppressWarnings("unchecked")
	public static void registerPackets(FMLPreInitializationEvent event) {
		ASMDataTable table = event.getAsmData();

		List<ASMData> packets = new ArrayList<>(table.getAll(ModPacket.class.getName()));

		packets.sort(Comparator.comparing(ASMData::getClassName));
		
		for (ASMData entry : packets) {
			try {

	            Class<?> clazz = Class.forName(entry.getClassName());
	            ModPacket annotation = clazz.getAnnotation(ModPacket.class);

	            Class<?> handler = null;

	            for (Class<?> inner : clazz.getDeclaredClasses()) {
	                if (IMessageHandler.class.isAssignableFrom(inner)) {
	                    handler = inner;
	                    break;
	                }
	            }

	            Class<? extends IMessage> messageClass = (Class<? extends IMessage>) clazz;
	            Class<? extends IMessageHandler<IMessage, IMessage>> handlerClass = (Class<? extends IMessageHandler<IMessage, IMessage>>) handler;

	            IMessageHandler<IMessage, IMessage> handlerInstance = handlerClass.newInstance();

	            if (annotation.modid().equals(Reference.MODID) || annotation.modid().isEmpty())
	            {
	            	network.registerMessage(handlerInstance, messageClass, id++, annotation.side());
	            	Main.getLogger().info("Registered packet: {}", clazz.getSimpleName());
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		Main.getLogger().info("Packet registration done : {}", Math.max(0, id-1));
	}

	public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
}