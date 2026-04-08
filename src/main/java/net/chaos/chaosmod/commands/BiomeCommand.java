package net.chaos.chaosmod.commands;

import java.util.Collections;
import java.util.List;

import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.broadcast.MessageDisplayBiomeName;

public class BiomeCommand extends CommandBase {

	@Override
	public String getName() {
		return "biome";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/biome [biome_id] [range] (default 1000 max: 10000)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		World world = player.getEntityWorld();
		BlockPos playerPos = player.getPosition();

		if (args.length == 0) {
			Biome biome = world.getBiome(playerPos);
			if (biome != null) {
				String data = biome.getRegistryName().toString();
				PacketManager.network.sendTo(new MessageDisplayBiomeName(data), (EntityPlayerMP) player);

				String msg = String.format("The biome is %s", biome.getRegistryName());
				player.sendMessage(new TextComponentString(msg));
			}
			return;
		}

		String biomeId = args[0];

		Biome biome = ForgeRegistries.BIOMES.getValue(getRLFromBiomeId(biomeId));
		
		if (biome == null) {
		    player.sendMessage(new TextComponentString("Biome not found in registry: " + biomeId));
		    return;
		}

		int range = 1000;
		if (args.length > 1) {
			range = MathHelper.clamp(parseInt(args[1]), 1000, 10000);
		}

		BlockPos biomePos = world.getBiomeProvider().findBiomePosition(
			    playerPos.getX(), playerPos.getZ(), range,
			    Collections.singletonList(biome),
			    world.rand);

		if (biomePos == null) {
			player.sendMessage(new TextComponentString("Biome not found within range " + range));
			return;
		}

		int top = world.getTopSolidOrLiquidBlock(biomePos).getY(); // forces the chunk to load
		String coords = String.format("%d %d %d", biomePos.getX(),
				top, biomePos.getZ());
		String msg = String.format("The biome %s is located at: %s ", biome.getRegistryName(), coords);
		TextComponentString text = new TextComponentString(msg);
		TextComponentString teleport = new TextComponentString("TELEPORT");
		
		Style style = teleport.getStyle();

		style.setColor(TextFormatting.RED).setBold(true);
		
		style.setClickEvent(new ClickEvent(Action.RUN_COMMAND, String.format("/tp %s %s", player.getName(), coords)));
		
		style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Teleport to biome")));

		player.sendMessage(text.appendSibling(teleport));
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, ForgeRegistries.BIOMES.getKeys()) : Collections.emptyList();
	}

	private ResourceLocation getRLFromBiomeId(String id) {
		if (!id.matches(".*:.*")) {
			return new ResourceLocation("minecraft", id);
		}

		String[] str = id.split(":");

		ResourceLocation rl = new ResourceLocation(str[0], str[1]);

		return rl;
	}
}