package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ITeleporter;
import util.dimensions.DimensionUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class DimensionWarpCommand extends CommandBase {
	private int dimension_id;
	private String dimension_name;
	private Entity entity;

	@Override
	public String getName() {
		return "warp";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/warp <dimension_id OR dimension_name> [player]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		DimensionUtil dim_handler = new DimensionUtil();
        if (args.length != 1 && args.length != 2)
        {
            throw new WrongUsageException("/warp <dimension_id OR dimension_name> [player]");
        }
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		System.out.println(player.getEntityWorld().provider.getDimensionType());
		if (args.length == 1)
		{
			entity = getEntity(server, sender, player.getName()); 
		} else {
			entity = getEntity(server, sender, args[1]);
		}
		if (entity == null) {
			throw new CommandException("Entity not found : player -> " + player.getName() + " -> sender name -> " + sender.getName());
		}
		BlockPos pos = entity.getPosition();
		boolean error = false;
		try {
			dimension_id = parseInt(args[0]);
		} catch (NumberInvalidException w) {
			dimension_name = args[0];
			error = true;
		}
		if (error) {
			dimension_id = dim_handler.getDimensionId(dimension_name);
		}
		if (!DimensionManager.isDimensionRegistered(dimension_id))
        {
            throw new CommandException("There is no such dimension: look at them with /forge dimensions", dimension_id);
        }
		if (this.dimension_id == entity.dimension)
		{
            throw new CommandException("You are currently in the dimension: " + dimension_name + "(" + dimension_id + ")");
		}
		entity.changeDimension(dimension_id, new CommandTeleporter(pos));
	}

    public static class CommandTeleporter implements ITeleporter
    {
        private final BlockPos targetPos;

        CommandTeleporter(BlockPos targetPos)
        {
            this.targetPos = targetPos;
        }

        @Override
        public void placeEntity(World world, Entity entity, float yaw)
        {
            entity.moveToBlockPosAndAngles(targetPos, yaw, entity.rotationPitch);
        }
    }

}
