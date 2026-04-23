package net.chaos.chaosmod;

import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.biomes.gen.events.BiomeGenEventHandler;
import net.chaos.chaosmod.commands.CommandsManager;
import net.chaos.chaosmod.common.capabilities.CapabilityEventHandler;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModDimensions;
import net.chaos.chaosmod.init.ModEntities;
import net.chaos.chaosmod.init.ModFluids;
import net.chaos.chaosmod.init.ModLootTableList;
import net.chaos.chaosmod.init.ModSounds;
import net.chaos.chaosmod.init.ModStructures;
import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.recipes.machine.MachineRecipeRegistry;
import net.chaos.chaosmod.tileentity.TileEntityManager;
import net.chaos.chaosmod.villagers.CustomProfessions;
import net.chaos.chaosmod.world.ModWorldGen;
import net.chaos.chaosmod.world.events.WorldGenerationOverrideEvents;
import net.chaos.chaosmod.world.events.terraingen.OreGenOverrideEvents;
import net.chaos.chaosmod.world.structures.VillageAdditionalStructure;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import proxy.CommonProxy;
import util.Reference;
import util.annotations.ClientBusHandler;
import util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, dependencies = Reference.OPTIONAL_DEPENDENCIES)
public class Main {
	@Instance
	public static Main instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	private static Logger logger;

	static {
		ModFluids.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		logger = event.getModLog();
		logger.info("CHAOSMOD PRE-INIT PHASE {}", event.getModState());
		ClientBusHandler.init();
		ModSounds.registerSounds();
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
		ModEntities.registerEntities();
		ModFluids.registerFluids();
		CapabilityEventHandler.registerAllCapabilities(event);
		PacketManager.registerPackets(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		logger.info("CHAOSMOD INIT PHASE {}", event.getModState());
		ModLootTableList.registerLootTables();
		PacketManager.init();
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageAdditionalStructure.CreationHandler());
		MinecraftForge.ORE_GEN_BUS.register(new OreGenOverrideEvents());
		MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenerationOverrideEvents());
		CustomProfessions.registerCustomProfessions();
		RegistryHandler.onSmeltingRegister();
		RegistryHandler.onBrewingRecipeRegister();
		MachineRecipeRegistry.init();
		ModBiomes.init();
		ModDimensions.init();
		RegistryHandler.onTagsRegister();

		ModStructures.registerStructures();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		logger.info("CHAOSMOD POST-INIT PHASE {}", event.getModState());
		// VillagerTradeHandler.onRegisterTrades();
		TileEntityManager.registerTileEntities();
		BiomeGenEventHandler.addAdditionalBiomesFromMod(Reference.MATHSMOD, "pink_forest", "unusual_forest",
				"desolate_lands", "green_plain");

		Loader.instance().getModList().forEach((container) -> {
			Main.getLogger().info("mods loaded : {}", container.getModId());
		});
	}

	@EventHandler
	public void interModCommunication(IMCEvent event) {}

	@EventHandler
	public void ServerInit(FMLServerStartingEvent event) {
		JobsManager.init();
		CommandsManager.registerCommands(event);
	}

	public static Logger getLogger() { return logger; }
}