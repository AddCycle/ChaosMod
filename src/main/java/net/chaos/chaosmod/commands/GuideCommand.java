package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import util.Reference;
import vazkii.patchouli.api.PatchouliAPI;

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
		World world = player.getEntityWorld();

		if (world.isRemote)
			return;

		if (!Loader.isModLoaded("patchouli"))
			player.sendMessage(new TextComponentString("Patchouli isn't installed!"));

		ItemStack book = PatchouliAPI.instance.getBookStack(Reference.PREFIX + "chaos_almanac");
		player.addItemStackToInventory(book);
	}
}