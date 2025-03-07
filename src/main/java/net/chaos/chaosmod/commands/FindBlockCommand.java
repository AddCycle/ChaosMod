package net.chaos.chaosmod.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

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
		return "/find or /scan <block_id> (for example, minecraft:sand)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		Random random = new Random();
		int box = 50;
		int cnt = 0;
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Block block = player.getEntityWorld().getBlockState(new BlockPos(0, 0, 0)).getBlock();
		BlockPos player_pos = player.getPosition();
		BlockPos pos1 = new BlockPos(player_pos.getX() - box, player_pos.getY() - box, player_pos.getZ() - box);
		BlockPos pos2 = new BlockPos(player_pos.getX() + box, player_pos.getY() + box, player_pos.getZ() + box);
		Block wanted = ModBlocks.ALLEMANITE_ORE;
		if (args.length >= 1) {
			wanted = ModBlocks.OXONIUM_ORE;
		}
		for(BlockPos pos : BlockPos.getAllInBox(pos1, pos2)) {
			Block curr = player.getEntityWorld().getBlockState(pos).getBlock();
				if (Block.isEqualTo(curr, wanted)) {
					System.out.println("FOUND: " + wanted + " ! at: " + pos);
					cnt++;
				}
		}
		System.out.println("FIND DONE, found: " + cnt);
	}
}
