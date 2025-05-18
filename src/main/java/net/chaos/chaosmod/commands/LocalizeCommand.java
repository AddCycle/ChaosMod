package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class LocalizeCommand extends CommandBase {
	@Override
    public String getName() {
        return "locateforgeoutpost";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/locateforgeoutpost";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        BlockPos from = sender.getPosition();
        World world = sender.getEntityWorld();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
