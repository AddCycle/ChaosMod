package net.chaos.chaosmod.commands.build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import net.chaos.chaosmod.commands.BlockEntry;
import net.chaos.chaosmod.world.events.build.BuildEvents;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommandRandomize extends CommandBase {

	@Override
	public String getName() {
		return "/randomize";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "//randomize <type> [<block>:<weight> ... ] [<block>:<meta>:<weight> ... ]"; // later <noise_type (base,perlin,simplex)>
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO : even with 0 args, just randomize using random blocks from registry
		if (args.length < 1) {
			throw new WrongUsageException(getUsage(sender));
		}

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty() || stack.getTagCompound() == null || !stack.getTagCompound().hasKey(BuildEvents.BUILD_TAG)) {
			throw new WrongUsageException("You must hold the wand");
		}

		NBTTagCompound tag = stack.getOrCreateSubCompound(BuildEvents.BUILD_TAG);
		if (!tag.hasKey("pos1", Constants.NBT.TAG_INT_ARRAY) || !tag.hasKey("pos2", Constants.NBT.TAG_INT_ARRAY)) {
			return;
		}

		int[] p1 = tag.getIntArray("pos1");
		int[] p2 = tag.getIntArray("pos2");
		BlockPos pos1 = new BlockPos(p1[0], p1[1], p1[2]);
		BlockPos pos2 = new BlockPos(p2[0], p2[1], p2[2]);
		
		String type = args[0];
		List<BlockEntry> pool = getPool(sender, args);
		
		pool.forEach(entry -> player.sendMessage(new TextComponentString("block: " + entry.getBlock().getRegistryName() + ", weight: " + entry.getWeight())));
		
		List<BlockSnapshot> list = BuildManager.getBlockSnapshots(player, pos1, pos2);
		
		UUID playerId = player.getPersistentID();
		BuildManager.pushUndoSnapshots(playerId, list);
		BuildManager.clearRedoSnapshots(playerId);

		StructureBoundingBox box = BuildManager.getBoundingBox(pos1, pos2);

		switch (type) {
			case "hollow":
				BuildManager.hollowRandomly(server.getEntityWorld(), box, pool);
				break;
			case "outline":
				BuildManager.outlineRandomly(server.getEntityWorld(), box, pool);
				break;
			default:
				BuildManager.randomize(player.getEntityWorld(), pool, list);
				break;
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, new String[] {"base", "outline", "hollow"});
		}
		return args.length > 1 ? getListOfStringsMatchingLastWord(args, ForgeRegistries.BLOCKS.getKeys()) :  Collections.emptyList();
	}
	
	private List<BlockEntry> getPool(ICommandSender sender, String[] args) throws CommandException {
		List<BlockEntry> pool = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			String[] parts = args[i].split(":");

		    if (parts.length < 2) throw new WrongUsageException(getUsage(sender));

		    int weight = parseInt(parts[parts.length - 1]);

		    int meta = 0;
		    int blockEndIndex;

		    try {
		        meta = parseInt(parts[parts.length - 2]);
		        blockEndIndex = parts.length - 2;
		    } catch (NumberInvalidException e) {
		        blockEndIndex = parts.length - 1;
		    }

		    String blockId = String.join(":", Arrays.copyOfRange(parts, 0, blockEndIndex));
            Block block = getBlockByText(sender, blockId);
            pool.add(new BlockEntry(block, weight, meta));
		}
		
		return pool;
	}
}