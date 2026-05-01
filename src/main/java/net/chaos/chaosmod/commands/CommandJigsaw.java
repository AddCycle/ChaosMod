package net.chaos.chaosmod.commands;

import java.util.Collections;
import java.util.List;

import net.chaos.chaosmod.world.structures.jigsaw.JigsawAssembler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import util.Reference;

public class CommandJigsaw extends CommandBase {
	private static final PlacementSettings DEFAULT_PLACEMENT = new PlacementSettings();

	@Override
	public String getName() {
		return "jigsaw";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/jigsaw <structure_start> <x> <y> <z> [depth] (max 20)";
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return args.length > 1 && args.length <= 4 ? getTabCompletionCoordinate(args, 1, targetPos) : Collections.emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 4) {
			throw new WrongUsageException(getUsage(sender));
		}

		String structureStart = args[0];
		BlockPos pos = parseBlockPos(sender, args, 1, false);
		int maxDepth = args.length >= 5 ? Math.min(parseInt(args[4]), 20) : 4;
		String[] name = ResourceLocation.splitObjectName(structureStart);
		ResourceLocation start = name == null ? new ResourceLocation(Reference.MODID, structureStart) : new ResourceLocation(structureStart);
		JigsawAssembler assembler = new JigsawAssembler(start, DEFAULT_PLACEMENT);
		assembler.assemble(server, pos, maxDepth);
	}
}