package net.chaos.chaosmod.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Block block = player.getEntityWorld().getBlockState(new BlockPos(0, 0, 0)).getBlock();
		BlockPos player_pos = player.getPosition();
		System.out.println("Le BLOCK en question en 0, 0, 0, est: " + block.getLocalizedName());
		System.out.println("Son nom est : " + block.getUnlocalizedName());
		System.out.println("Tu es en : " + player_pos);
	}
}
