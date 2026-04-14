package net.chaos.chaosmod.commands.overrides;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandLocate;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.util.text.event.HoverEvent;

public class LocateCommandReplacement extends CommandLocate {
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 1) {
            throw new WrongUsageException("commands.locate.usage", new Object[0]);
		}

		String structureName = args[0];
		BlockPos blockpos = sender.getEntityWorld().findNearestStructure(structureName, sender.getPosition(), false);

		if (blockpos == null) {
			throw new CommandException("commands.locate.failure", new Object[] {structureName});
		}

		TextComponentTranslation result = new TextComponentTranslation("commands.locate.success",
				new Object[] { structureName, blockpos.getX(), blockpos.getZ() });

		sender.sendMessage(result);
		sender.sendMessage(getTeleportString(sender.getName(), blockpos));
	}

	private static TextComponentString getTeleportString(String senderName, BlockPos pos) {
		TextComponentString teleport = new TextComponentString("TELEPORT");

		Style style = teleport.getStyle();
		String coords = String.format("%s %s %s", pos.getX(), 100, pos.getZ()); // arbitrary Y value

		style.setColor(TextFormatting.RED).setBold(true);

		style.setClickEvent(new ClickEvent(Action.RUN_COMMAND, String.format("/tp %s %s", senderName, coords)));

		style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Teleport to structure")));

		return teleport;
	}
}