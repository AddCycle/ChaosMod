package net.chaos.chaosmod;

import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.CustomPlanks;
import net.chaos.chaosmod.commands.CommandsManager;
import net.chaos.chaosmod.config.ModConfig;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.init.ModDimensions;
import net.chaos.chaosmod.init.ModEntities;
import net.chaos.chaosmod.init.ModSounds;
import net.chaos.chaosmod.network.PacketManager;
import net.chaos.chaosmod.recipes.machine.MachineRecipeRegistry;
import net.chaos.chaosmod.tileentity.TileEntityBeam;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.chaos.chaosmod.tileentity.TileEntityCookieJar;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityLantern;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.chaos.chaosmod.tileentity.TileEntityTrophyBase;
import net.chaos.chaosmod.villagers.CustomProfessions;
import net.chaos.chaosmod.world.ModWorldGen;
import net.chaos.chaosmod.world.events.EntitiesDeathEvents;
import net.chaos.chaosmod.world.events.PlayerAchivementsEvents;
import net.chaos.chaosmod.world.events.PlayerFightEvents;
import net.chaos.chaosmod.world.events.PlayerLifeEvents;
import net.chaos.chaosmod.world.events.PlayerTickBiomeEvent;
import net.chaos.chaosmod.world.events.WorldGenerationOverrideEvents;
import net.chaos.chaosmod.world.gen.chaosland.CustomWoodlandMansion;
import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.chaos.chaosmod.world.structures.StructureCustomVillage;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import proxy.CommonProxy;
import util.Reference;
import util.blockstates.RenderBlockOutlinesEvent;
import util.handlers.PlayerInHandler;
import util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = "net.chaos.chaosmod.config.ModGuiFactory")
public class Main
{
	// Helps launch the ChaosMod
	@Instance
	public static Main instance = Init();
	
	private static Main Init() {
		MapGenStructureIO.registerStructure(MapGenCustomVillage.Start.class, "custom_village");
		StructureCustomVillage.registerVillagePieces();
		MapGenStructureIO.registerStructure(CustomWoodlandMansion.Start.class, "Custom Mansion");
		return instance;
	}
	
	// This is for server and client handling
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

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
        ModSounds.registerSounds();
        ModCapabilities.register(); // for in-game-accessories
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        ModEntities.registerEntities();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    	logger.info("CHAOSMOD INIT PHASE {}", event.getModState());
        PacketManager.init();
    	MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenerationOverrideEvents());
        CustomProfessions.registerCustomProfessions();
        MinecraftForge.EVENT_BUS.register(new RenderBlockOutlinesEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerTickBiomeEvent());
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
        ModDimensions.init();
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
    	// VillagerTradeHandler.onRegisterTrades();
        MinecraftForge.EVENT_BUS.register(new EntitiesDeathEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerFightEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerLifeEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerAchivementsEvents());
    	GameRegistry.registerTileEntity(TileEntityOxoniumFurnace.class, new ResourceLocation(Reference.MODID, "oxonium_furnace"));
    	GameRegistry.registerTileEntity(TileEntityOxoniumChest.class, new ResourceLocation(Reference.MODID, "oxonium_chest"));
    	GameRegistry.registerTileEntity(TileEntityBossAltar.class, new ResourceLocation(Reference.MODID, "boss_altar"));
    	GameRegistry.registerTileEntity(TileEntityForge.class, new ResourceLocation(Reference.MODID, "forge_interface"));
    	GameRegistry.registerTileEntity(TileEntityLantern.class, new ResourceLocation(Reference.MODID, "lantern"));
    	GameRegistry.registerTileEntity(TileEntityCookieJar.class, new ResourceLocation(Reference.MODID, "cookie_jar"));
    	GameRegistry.registerTileEntity(TileEntityBeam.class, new ResourceLocation(Reference.MODID, "beam_block"));
    	GameRegistry.registerTileEntity(TileEntityTrophyBase.class, new ResourceLocation(Reference.MODID, "trophy_base"));
    }
    
    @EventHandler
    public void ServerInit(FMLServerStartingEvent event) {
    	CommandsManager.registerCommands(event);
    }
}
