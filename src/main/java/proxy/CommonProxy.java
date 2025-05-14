package proxy;

import net.chaos.chaosmod.client.renderer.tileentity.TileEntityBossAltarRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityOxoniumChestRenderer;
import net.chaos.chaosmod.tileentity.LanternTESR;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityLantern;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import util.broadcast.ClientMessageHandler;

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