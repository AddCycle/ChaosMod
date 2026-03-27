package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.common.capabilities.biome.CapabilityVisitedBiomes;
import net.chaos.chaosmod.common.capabilities.biome.VisitedBiomes;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.biome.Biome;

public class BiomesCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "biomes";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/biomes";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		
		VisitedBiomes cap = player.getCapability(CapabilityVisitedBiomes.VISITED_BIOMES, null);
		if (cap == null) return;
		
		for (Biome b : cap.getVisitedBiomes()) {
			ResourceLocation name = b.getRegistryName();
			if (name != null) {
				player.sendMessage(new TextComponentString(name.toString()));
			}
		}
	}
}