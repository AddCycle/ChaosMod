package net.chaos.chaosmod.commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;

public class FindBlockCommand extends CommandBase {
	private static final int DEFAULT_RANGE = 30;
	private static final int MIN_RANGE = 30;
	private static final int MAX_RANGE = 2 * MIN_RANGE;

	@Override
	public String getName() {
		return "find";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return String.format("/find <mod:block_id> [range (%d,%d)]", MIN_RANGE, MAX_RANGE);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (args.length < 1) {
			throw new CommandException(getUsage(sender));
		}

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		int range = DEFAULT_RANGE;
		if (args.length > 1) {
			try {
				range = MathHelper.clamp(parseInt(args[1]), MIN_RANGE, MAX_RANGE);
			} catch (NumberInvalidException e) {
				throw new CommandException(getUsage(sender));
			}
		}

		int cnt = 0;

		BlockPos player_pos = player.getPosition();
		BlockPos pos1 = new BlockPos(player_pos.getX() - range, player_pos.getY() - range, player_pos.getZ() - range);
		BlockPos pos2 = new BlockPos(player_pos.getX() + range, player_pos.getY() + range, player_pos.getZ() + range);
		Block wanted = getBlockByText(sender, args[0]);

		for (BlockPos pos : BlockPos.getAllInBoxMutable(pos1, pos2)) {
			Block curr = player.getEntityWorld().getBlockState(pos).getBlock();

			if (!Block.isEqualTo(curr, wanted))
				continue;

			cnt++;
		}

		player.sendMessage(
				new TextComponentString(String.format("DONE -> found: %d %s blocks.", cnt, wanted.getRegistryName())));
	}
}