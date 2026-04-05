package net.chaos.chaosmod.commands.debug;

import java.util.List;
import java.util.Random;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

public class CommandTestFishing extends CommandBase {

	@Override
	public String getName() {
		return "testfishing";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/testfishing";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
		EntityPlayerMP player = (EntityPlayerMP) sender;
		WorldServer world = (WorldServer) player.world;

		LootContext.Builder builder = new LootContext.Builder(world);
		builder.withLuck(player.getLuck()).withPlayer(player).withLootedEntity(player);

		List<ItemStack> loots = world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING)
				.generateLootForPools(new Random(), builder.build());

		player.sendMessage(new TextComponentString("=== Fishing Loot Test ==="));
		player.sendMessage(new TextComponentString("Biome: " + world.getBiome(player.getPosition()).getBiomeName()));

		for (ItemStack stack : loots) {
			player.sendMessage(new TextComponentString("  - " + stack.getDisplayName() + " x" + stack.getCount()));
		}

		if (loots.isEmpty()) {
			player.sendMessage(new TextComponentString("  (no items)"));
		}
	}
}