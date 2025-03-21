package net.chaos.chaosmod;

import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.blocks.AllemaniteOre;
import net.chaos.chaosmod.blocks.BlockBase;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityOxoniumChestRenderer;
import net.chaos.chaosmod.commands.CheatCommand;
import net.chaos.chaosmod.commands.CraftCommand;
import net.chaos.chaosmod.commands.DimensionWarpCommand;
import net.chaos.chaosmod.commands.FindBlockCommand;
import net.chaos.chaosmod.commands.FurnaceCommand;
import net.chaos.chaosmod.commands.GuideCommand;
import net.chaos.chaosmod.commands.TopCommand;
import net.chaos.chaosmod.gui.GuiHandler;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.network.ChaosModPacketHandler;
import net.chaos.chaosmod.network.GuideCommandMessage;
import net.chaos.chaosmod.network.OxoniumFurnaceMessage;
import net.chaos.chaosmod.network.OxoniumFurnaceMessage.OxoniumFurnaceMessageHandler;
import net.chaos.chaosmod.network.GuideCommandMessage.GuideMessageHandler;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.chaos.chaosmod.world.ModWorldGen;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import proxy.CommonProxy;
import util.Reference;
import util.handlers.BlockPlaceHandler;
import util.handlers.CommandMessageHandler;
import util.handlers.PlayerInHandler;
import util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{
	// Helps launch the ChaosMod
	@Instance
	public static Main instance;
	
	// This is for server and client handling
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper network;

	// Basically, a logger is for broadcasting messages through the console to help you debug your mod implementation
    private static Logger logger;
    
    public static Logger getLogger() {
    	return logger;
    }

    // Before the initialization of the Mod (When the forge window is not opened yet)
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
        // ChaosModPacketHandler.registerMessage();
		Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("chaosmod");
		Main.network.registerMessage(GuideMessageHandler.class, GuideCommandMessage.class, 0, Side.CLIENT);
		// Main.network.registerMessage(OxoniumFurnaceMessageHandler.class, OxoniumFurnaceMessage.class, 1, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("ALLEMANITE ORE >> {}", ModBlocks.ALLEMANITE_ORE.getRegistryName());
        proxy.init(event);
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        RegistryHandler.onSmeltingRegister();
        MinecraftForge.EVENT_BUS.register(new BlockPlaceHandler());
        MinecraftForge.EVENT_BUS.register(new CommandMessageHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerInHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }
    
    // After the launching of the instance
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit(event);
    	GameRegistry.registerTileEntity(TileEntityOxoniumFurnace.class, new ResourceLocation(Reference.MODID, "oxonium_furnace"));
    	GameRegistry.registerTileEntity(TileEntityOxoniumChest.class, new ResourceLocation(Reference.MODID, "oxonium_chest"));
    }
    
    @EventHandler
    public void ServerInit(FMLServerStartingEvent event) {
    	event.registerServerCommand(new DimensionWarpCommand());
    	event.registerServerCommand(new TopCommand());
    	event.registerServerCommand(new FindBlockCommand());
    	event.registerServerCommand(new GuideCommand());
        event.registerServerCommand(new CraftCommand());
        event.registerServerCommand(new FurnaceCommand());
        event.registerServerCommand(new CheatCommand());
    }
}
