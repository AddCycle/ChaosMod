package net.chaos.chaosmod.commands;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class FindBlockCommand extends CommandBase {
	public ArrayList<Block> block_list = new ArrayList<Block>(); // to recode to a cartographer tool way

	@Override
	public String getName() {
		return "find";
	}
	
	@Override
	public List<String> getAliases() {
		ArrayList<String> aliases = new ArrayList<>();
		aliases.add("scan");
		return aliases;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// return "/find or /scan <block_id> (for example, minecraft:sand)";
		return "/find or /scan <block_id> 0, 1, 2";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		int box = 300;
		int cnt = 0;
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		BlockPos player_pos = player.getPosition();
		BlockPos pos1 = new BlockPos(player_pos.getX() - box, player_pos.getY() - box, player_pos.getZ() - box);
		BlockPos pos2 = new BlockPos(player_pos.getX() + box, player_pos.getY() + box, player_pos.getZ() + box);
		Block wanted = null;
		if (args.length == 1) {
			switch (parseInt(args[0])) {
			case 0:
				wanted = ModBlocks.OXONIUM_ORE;
				break;
			case 1:
				wanted = ModBlocks.ALLEMANITE_ORE;
				break;
			case 2:
				wanted = ModBlocks.ENDERITE_ORE;
				break;
			}
		}
		for(BlockPos pos : BlockPos.getAllInBox(pos1, pos2)) {
			Block curr = player.getEntityWorld().getBlockState(pos).getBlock();
				if (Block.isEqualTo(curr, wanted)) {
					player.sendMessage(new TextComponentString("found: " + wanted.getLocalizedName() + " & " + wanted.getUnlocalizedName() + " at pos : " + pos));
					cnt++;
				}
		}
		player.sendMessage(new TextComponentString("DONE -> found: " + cnt + " blocks."));
	}
}
