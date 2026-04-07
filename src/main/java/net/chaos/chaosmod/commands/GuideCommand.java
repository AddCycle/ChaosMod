package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.compatibility.patchouli.PatchouliPlugin;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Loader;

public class GuideCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "guide";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/guide (gives chaosmod:chaos_almanac)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);

		if (!Loader.isModLoaded("patchouli")) {
			player.sendMessage(new TextComponentString("Patchouli isn't installed!"));
			return;
		}

		PatchouliPlugin.giveBook(player);
	}
}