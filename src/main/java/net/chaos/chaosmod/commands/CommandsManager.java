package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.commands.debug.CommandTestFishing;
import net.chaos.chaosmod.commands.generation.LoadStructCommand;
import net.chaos.chaosmod.commands.hunt.HuntCommand;
import net.chaos.chaosmod.commands.jobs.JobsCommand;
import net.chaos.chaosmod.commands.market.AddOfferCommand;
import net.chaos.chaosmod.commands.market.BuyCommand;
import net.chaos.chaosmod.commands.market.MarketCommand;
import net.chaos.chaosmod.commands.market.MoneyCommand;
import net.chaos.chaosmod.commands.market.SellCommand;
import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import util.Reference;

public class CommandsManager {

	public static void registerCommands(FMLServerStartingEvent event) {
		register(event,
		new GuideCommand(),
		new FindBlockCommand(),
		new JobsCommand(),
		new CraftCommand(),
		new FurnaceCommand(),
		new SetHomeCommand(),
		new HomeCommand(),
		new HomeListCommand(),
		new DelHomeCommand(),
		new FireCommand(),
		new LoadStructCommand(),
		new MoneyCommand(),
		new MarketCommand(),
		new BuyCommand(),
		new SellCommand(),
		new AddOfferCommand(),
		new HuntCommand(),
		new BackCommand(),
		new BiomeCommand(),
		new BiomesCommand(),
		new CommandTestFishing()
		);

		if (!Loader.isModLoaded(Reference.MATHSMOD)) {
			register(event,
			new DimensionWarpCommand(),
			new HealCommand(),
			new FeedCommand(),
			new TopCommand());
		}
	}

	private static void register(FMLServerStartingEvent event, ICommand... commands) {
		for (ICommand command : commands) {
			event.registerServerCommand(command);
		}
	}
}
