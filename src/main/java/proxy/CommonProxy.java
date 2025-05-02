package proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import util.broadcast.ClientMessageHandler;
import util.handlers.RegistryHandler;

// Il semble que toute la partie serveur et surtout pour les entities soit handle here
public class CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {}

	public void preInit(FMLPreInitializationEvent event) {
	}

	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ClientMessageHandler.class);
	}

	public void postInit(FMLPostInitializationEvent event) {
		
	}

	public void registerVariantRenderer(Item item, int meta, String filename, String variant) {
		// TODO Auto-generated method stub
		
	}
}