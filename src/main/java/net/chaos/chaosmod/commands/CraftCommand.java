package net.chaos.chaosmod.commands;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

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
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		World world = player.getEntityWorld();
		// world.setBlockState(player.getPosition(), Blocks.CRAFTING_TABLE.getDefaultState()); FIXED
		player.displayGui(new BlockWorkbench.InterfaceCraftingTable(world, player.getPosition()) {
			@Override
			public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
				return new ContainerWorkbench(playerInventory, world, player.getPosition()) {
					@Override
					public boolean canInteractWith(EntityPlayer playerIn) {
						return true;
					}
				};
			}
		});
		notifyCommandListener(sender, this, "commands.craft.successful", new Object[] {player.getName()});
	}

}
