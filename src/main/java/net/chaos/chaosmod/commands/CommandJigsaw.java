package net.chaos.chaosmod.commands;

import java.util.Collections;
import java.util.List;

import net.chaos.chaosmod.world.structures.jigsaw.JigsawAssembler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import util.Reference;

public class CommandJigsaw extends CommandBase {

	@Override
	public String getName() {
		return "jigsaw";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/jigsaw [<x> <y> <z>] (start)";
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return args.length > 0 && args.length <= 3 ? getTabCompletionCoordinate(args, 0, targetPos) : Collections.emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO : template + placementsettings + replace jigsaws with air + connecting them the correct orientation
		BlockPos pos = BlockPos.ORIGIN;
		if (args.length >= 3) {
			 pos = parseBlockPos(sender, args, 0, false);
		}

		ResourceLocation start = new ResourceLocation(Reference.MODID, "test_room1");
		JigsawAssembler assembler = new JigsawAssembler(start, new PlacementSettings().setRotation(Rotation.COUNTERCLOCKWISE_90));
		assembler.assemble(server, pos, 4); // 4 levels deep
//		assembler.assemble(server, pos);

	}
}