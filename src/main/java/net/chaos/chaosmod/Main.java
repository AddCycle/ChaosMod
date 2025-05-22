package net.chaos.chaosmod;

import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.CustomPlanks;
import net.chaos.chaosmod.commands.CheatCommand;
import net.chaos.chaosmod.commands.CraftCommand;
import net.chaos.chaosmod.commands.DimensionWarpCommand;
import net.chaos.chaosmod.commands.FeedCommand;
import net.chaos.chaosmod.commands.FindBlockCommand;
import net.chaos.chaosmod.commands.FireCommand;
import net.chaos.chaosmod.commands.FurnaceCommand;
import net.chaos.chaosmod.commands.GuideCommand;
import net.chaos.chaosmod.commands.HealCommand;
import net.chaos.chaosmod.commands.HomeCommand;
import net.chaos.chaosmod.commands.LocalizeCommand;
import net.chaos.chaosmod.commands.SetHomeCommand;
import net.chaos.chaosmod.commands.TopCommand;
import net.chaos.chaosmod.commands.generation.LoadStructCommand;
import net.chaos.chaosmod.config.ModConfig;
import net.chaos.chaosmod.gui.GuiHandler;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.init.ModEntities;
import net.chaos.chaosmod.network.GuideCommandMessage;
import net.chaos.chaosmod.network.GuideCommandMessage.GuideMessageHandler;
import net.chaos.chaosmod.network.PacketAccessorySync;
import net.chaos.chaosmod.network.PacketForgeCraft;
import net.chaos.chaosmod.network.PacketOpenAccessoryGui;
import net.chaos.chaosmod.recipes.machine.MachineRecipeRegistry;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.chaos.chaosmod.tileentity.TileEntityCookieJar;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityLantern;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.chaos.chaosmod.world.ModWorldGen;
import net.chaos.chaosmod.world.events.FightEvents;
import net.chaos.chaosmod.world.events.PlayerAchivementsEvents;
import net.chaos.chaosmod.world.events.PlayerLifeEvents;
import net.chaos.chaosmod.world.events.PlayerTickBiomeEvent;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
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
import net.minecraftforge.oredict.OreDictionary;
import proxy.CommonProxy;
import util.Reference;
import util.broadcast.MessageDisplayText;
import util.broadcast.MessageDisplayTextHandler;
import util.handlers.BlockPlaceHandler;
import util.handlers.CommandMessageHandler;
import util.handlers.PlayerInHandler;
import util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = "net.chaos.chaosmod.config.ModGuiFactory")
public class Main
{
	// Helps launch the ChaosMod
	@Instance
	public static Main instance;
	
	// This is for server and client handling
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);;

	// Basically, a logger is for broadcasting messages through the console to help you debug your mod implementation
    private static Logger logger;
    
    public static Logger getLogger() {
    	return logger;
    }

    // Before the initialization of the Mod (When the forge window is not opened yet)
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        logger = event.getModLog();
    	logger.info("CHAOSMOD PRE-INIT PHASE {}", event.getModState());
    	ModConfig.init(event.getSuggestedConfigurationFile());
        ModCapabilities.register(); // for necklace and other things
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        // OreDictionary.registerOre("ingotEnderite", new ItemStack(ModItems.ENDERITE_INGOT));
	    // OreDictionary.registerOre("toolTinkerHammer", new ItemStack(ModItems.TINKERERS_HAMMER, 1, OreDictionary.WILDCARD_VALUE));
        // GameRegistry.registerWorldGenerator(new ModGenSurface(), 0);
        // GameRegistry.registerWorldGenerator(new CustomDungeonBuilder(), 2);
        ModEntities.registerEntities();
        // ChaosModPacketHandler.registerMessage();
		// Main.network.registerMessage(OxoniumFurnaceMessageHandler.class, OxoniumFurnaceMessage.class, 1, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        // CapabilityManager.INSTANCE.register(IAccessory.class, new AccessoryStorage(), AccessoryImpl::new); // for the necklace and other accessories
    	logger.info("CHAOSMOD INIT PHASE {}", event.getModState());
    	network.registerMessage(MessageDisplayTextHandler.class, MessageDisplayText.class, 0, Side.CLIENT);
		network.registerMessage(GuideMessageHandler.class, GuideCommandMessage.class, 1, Side.CLIENT);
		network.registerMessage(PacketOpenAccessoryGui.Handler.class, PacketOpenAccessoryGui.class, 2, Side.SERVER);
		network.registerMessage(PacketAccessorySync.Handler.class, PacketAccessorySync.class, 3, Side.CLIENT);
		network.registerMessage(PacketForgeCraft.Handler.class, PacketForgeCraft.class, 4, Side.SERVER);
		// network.registerMessage(PacketSyncItemNBT.Handler.class, PacketSyncItemNBT.class, 5, Side.CLIENT);
        // GameRegistry.registerWorldGenerator(new WorldGenCustomDungeon(), 2);
        // GameRegistry.registerWorldGenerator(new WorldGenCaveDungeon(), 3);
        MinecraftForge.EVENT_BUS.register(new PlayerTickBiomeEvent());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new BlockPlaceHandler());
        MinecraftForge.EVENT_BUS.register(new CommandMessageHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerInHandler());
        RegistryHandler.onSmeltingRegister();
        MachineRecipeRegistry.init();
        BiomeDictionary.addTypes(ModBiomes.GIANT_MOUNTAIN, BiomeDictionary.Type.MOUNTAIN);
        // BiomeDictionary.addTypes(ModBiomes.CUSTOM_HELL, BiomeDictionary.Type.NETHER);
        BiomeManager.removeBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(Biomes.PLAINS, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.GIANT_MOUNTAIN, 50));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.NETHER_CAVES, 50));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.ENDER_GARDEN, 50));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.CHAOS_LAND_BIOME, 50));
        // BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.CUSTOM_HELL, 50));
        /*DimensionManager.unregisterDimension(-1);
        DimensionManager.registerDimension(-1, DimensionType.register(
            "custom_hell", "_hell", -1, CustomHellProvider.class, false
        ));*/
        // TODO : refactor that other part (it's forge tags to match any recipe using planks)
        // OreDictionary.registerOre("toolTinkerHammer", new ItemStack(ModItems.TINKERERS_HAMMER, 1, OreDictionary.WILDCARD_VALUE));
        for (CustomLog.CustomLogVariant variant : CustomLog.CustomLogVariant.values()) {
            OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.CUSTOM_LOG, 1, variant.getMeta()));
        }
        for (CustomPlanks.CustomPlankVariant variant : CustomPlanks.CustomPlankVariant.values()) {
            OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.CUSTOM_PLANK, 1, variant.getMeta()));
        }
        // ModStructures.registerStructures();
    }

    // After the launching of the instance
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit(event);
    	logger.info("CHAOSMOD POST-INIT PHASE {}", event.getModState());
        MinecraftForge.EVENT_BUS.register(new FightEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerLifeEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerAchivementsEvents());
    	GameRegistry.registerTileEntity(TileEntityOxoniumFurnace.class, new ResourceLocation(Reference.MODID, "oxonium_furnace"));
    	GameRegistry.registerTileEntity(TileEntityOxoniumChest.class, new ResourceLocation(Reference.MODID, "oxonium_chest"));
    	GameRegistry.registerTileEntity(TileEntityBossAltar.class, new ResourceLocation(Reference.MODID, "boss_altar"));
    	GameRegistry.registerTileEntity(TileEntityForge.class, new ResourceLocation(Reference.MODID, "forge_interface"));
    	GameRegistry.registerTileEntity(TileEntityLantern.class, new ResourceLocation(Reference.MODID, "lantern"));
    	GameRegistry.registerTileEntity(TileEntityCookieJar.class, new ResourceLocation(Reference.MODID, "cookie_jar"));
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
        event.registerServerCommand(new SetHomeCommand());
        event.registerServerCommand(new HomeCommand());
        event.registerServerCommand(new FireCommand());
        event.registerServerCommand(new HealCommand());
        event.registerServerCommand(new FeedCommand());
        event.registerServerCommand(new LocalizeCommand());
        event.registerServerCommand(new LoadStructCommand());
    }
}
