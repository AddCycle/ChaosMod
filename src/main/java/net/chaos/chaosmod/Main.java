package net.chaos.chaosmod;

import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.blocks.AllemaniteOre;
import net.chaos.chaosmod.blocks.BlockBase;
import net.chaos.chaosmod.commands.DimensionWarpCommand;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.ModWorldGen;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxy.CommonProxy;
import util.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{

	// Helps launch the ChaosMod
	@Instance
	public static Main instance;
	
	// This is for server and client handling
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	// Basically, a logger is for broadcasting messages through the console to help you debug your mod implementation
    private static Logger logger;

    // Before the initialization of the Mod (When the forge window is not opened yet)
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
        logger.info("Generating world ore >> {}", ModBlocks.ALLEMANITE_ORE.getRegistryName());
    }

    // During the loading of the fuc** forge mod builder (with my respects)
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        proxy.init(event);
    }
    
    // After the launching of the instance
    @EventHandler
    public void postInit(FMLInitializationEvent event)
    {
    	proxy.postInit(event);
    }
    
    @EventHandler
    public void ServerInit(FMLServerStartingEvent event) {
    	event.registerServerCommand(new DimensionWarpCommand());
    }
}
