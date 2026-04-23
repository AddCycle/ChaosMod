package net.chaos.chaosmod.commands.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class CommandPlace extends CommandBase {
	public static final String[] STRUCTURES_LIST = getAll(
			getEndCityStructurePieces(),
			getIglooStructurePieces());

	@Override
	public String getName() {
		return "place";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/place <feature> (only works for vanilla structures for now)";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, STRUCTURES_LIST) : Collections.emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 1) {
			notifyCommandListener(sender, this, getUsage(sender));
			return;
		}

		WorldServer world = (WorldServer) sender.getEntityWorld();
		BlockPos pos = sender.getPosition().add(0, 0, 5); // Spawn 5 blocks in front

		ResourceLocation templateName = new ResourceLocation(args[0]);
		TemplateManager manager = world.getStructureTemplateManager();
		Template template = manager.getTemplate(world.getMinecraftServer(), templateName);

		if (template == null || template.getSize().equals(BlockPos.ORIGIN)) {
			notifyCommandListener(sender, this, "Template not found or empty: " + templateName);
			return;
		}

		PlacementSettings settings = new PlacementSettings()
				.setMirror(Mirror.NONE)
				.setRotation(Rotation.CLOCKWISE_90)
				.setIgnoreEntities(false)
				.setChunk(null)
				.setReplacedBlock(Blocks.LAPIS_BLOCK)
				.setIgnoreStructureBlock(false);

		template.addBlocksToWorld(world, pos, settings);

		notifyCommandListener(sender, this, "Template loaded: " + templateName + " at " + pos);
	}
	
	private static String[] getIglooStructurePieces() {
		return new String[] {
			getIglooComponentString("igloo_bottom"),
			getIglooComponentString("igloo_middle"),
			getIglooComponentString("igloo_top")
		};
	}
	
	private static String[] getEndCityStructurePieces() {
		return new String[] {
			getEndCityComponentString("base_floor"),
			getEndCityComponentString("base_roof"),
			getEndCityComponentString("bridge_end"),
			getEndCityComponentString("bridge_gentle_stairs"),
			getEndCityComponentString("bridge_piece"),
			getEndCityComponentString("bridge_steep_stairs"),
			getEndCityComponentString("fat_tower_base"),
			getEndCityComponentString("fat_tower_middle"),
			getEndCityComponentString("fat_tower_top"),
			getEndCityComponentString("second_floor_2"),
			getEndCityComponentString("second_floor"),
			getEndCityComponentString("second_roof"),
			getEndCityComponentString("ship"),
			getEndCityComponentString("third_floor_b"),
			getEndCityComponentString("third_floor_c"),
			getEndCityComponentString("third_floor"),
			getEndCityComponentString("third_roof"),
			getEndCityComponentString("tower_base"),
			getEndCityComponentString("tower_floor"),
			getEndCityComponentString("tower_piece"),
			getEndCityComponentString("tower_top")
		};
	}
	
	private static String[] getAll(String[] ...lists) {
		List<String> res = new ArrayList<String>();
		for (String[] list : lists) {
			for (String s : list) {
				res.add(s);
			}
		}
		
		return res.toArray(new String[0]);
	}

	private static String getEndCityComponentString(String name) {
		return "endcity/" + name;
	}

	private static String getIglooComponentString(String name) {
		return "igloo/" + name;
	}
}