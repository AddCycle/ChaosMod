package net.chaos.chaosmod.commands;

import net.minecraft.block.BlockWorkbench.InterfaceCraftingTable;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import util.craft.CraftingGuiUtil;

public class CraftCommand extends CommandBase {

	@Override
	public String getName() {
		return "craft";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/craft";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player_client_side = getCommandSenderAsPlayer(sender);
		World world = player_client_side.getEntityWorld();
		// player_client_side.openGui("chaosmod", "minecraft:crafting_gui", , getRequiredPermissionLevel(), getRequiredPermissionLevel(), getRequiredPermissionLevel());
		world.setBlockState(player_client_side.getPosition(), Blocks.CRAFTING_TABLE.getDefaultState());
		player_client_side.displayGui(new InterfaceCraftingTable(world, player_client_side.getPosition()));
		notifyCommandListener(sender, this, "commands.craft.successful", new Object[] {player_client_side.getName()});
	}

}
