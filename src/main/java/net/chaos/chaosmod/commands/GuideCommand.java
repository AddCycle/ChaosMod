package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import util.Reference;
import vazkii.patchouli.api.PatchouliAPI;

public class GuideCommand extends CommandBase {

	@Override
	public String getName() {
		return "guide";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/guide (gives you the chaos_almanac)";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}


	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		World world = player.getEntityWorld();
		if (!world.isRemote) {
			ItemStack book = PatchouliAPI.instance.getBookStack(Reference.PREFIX + "chaos_almanac");
			player.addItemStackToInventory(book);
		}
	}

}
