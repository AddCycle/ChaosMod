package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.Main;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import util.Reference;

public class BackCommand extends CommandBase {

	@Override
	public String getName() {
		return "back";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/back";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);

		String deathKey = Reference.PREFIX + "lastDeathPosition";
		NBTTagCompound persistentData = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (!persistentData.hasKey(deathKey)) {
			player.sendMessage(new TextComponentString("Lucky you! You never died"));
			return;
		}

		int[] deathPos = persistentData.getIntArray(deathKey);
		player.setPositionAndUpdate(deathPos[0], deathPos[1], deathPos[2]);
		
		Main.getLogger().info("You died at: %d, %d, %d", deathPos[0], deathPos[1], deathPos[2]);
	}
}