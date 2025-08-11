package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.commands.generation.LoadStructCommand;
import net.chaos.chaosmod.commands.generation.LocateCustomVillage;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandsManager {

	public static void registerCommands(FMLServerStartingEvent event) {
    	event.registerServerCommand(new FindBlockCommand());
    	event.registerServerCommand(new GuideCommand());
        event.registerServerCommand(new CraftCommand());
        event.registerServerCommand(new FurnaceCommand());
        event.registerServerCommand(new UltimateDebuggerCommand());
        event.registerServerCommand(new SetHomeCommand());
        event.registerServerCommand(new HomeCommand());
        event.registerServerCommand(new DelHomeCommand());
        event.registerServerCommand(new FireCommand());
        event.registerServerCommand(new LocalizeCommand());
        event.registerServerCommand(new LocateCustomVillage());
        event.registerServerCommand(new LoadStructCommand());
		if (!Loader.isModLoaded("mathsmod")) {
			event.registerServerCommand(new TopCommand());
			event.registerServerCommand(new DimensionWarpCommand());
			event.registerServerCommand(new HealCommand());
			event.registerServerCommand(new FeedCommand());
		}
	}
}
