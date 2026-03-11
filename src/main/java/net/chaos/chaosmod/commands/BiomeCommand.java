package net.chaos.chaosmod.commands;

import java.util.Collections;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeCommand extends CommandBase {

	@Override
	public String getName() {
		return "biome";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/biome [biome_id] [range] (default 100)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		World world = player.getEntityWorld();
		BlockPos playerPos = player.getPosition();

		if (args.length == 0) {
			Biome biome = world.getBiome(playerPos);
			if (biome != null) {
				String msg = String.format("The biome is %s", biome.getRegistryName());
				player.sendMessage(new TextComponentString(msg));
			}
			return;
		}

		String biomeId = null;
		if (args.length > 0) {
			biomeId = args[0];
		}
		Biome biome = ForgeRegistries.BIOMES.getValue(getRLFromBiomeId(biomeId));

		int range = 100;
		if (args.length > 1) {
			range = parseInt(args[1]);
		}

		BlockPos biomePos = world.getBiomeProvider().findBiomePosition(
			    playerPos.getX(), playerPos.getZ(), range,
			    Collections.singletonList(biome),
			    world.rand);

		if (biomePos == null) {
			player.sendMessage(new TextComponentString("Biome not found within range " + range));
			return;
		}

		String msg = String.format("The biome %s is located at: %d %d %d", biome.getRegistryName(), biomePos.getX(),
				biomePos.getY(), biomePos.getZ());
		player.sendMessage(new TextComponentString(msg));

		// TODO : use getModdedBiomesGenerators to extend the function
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