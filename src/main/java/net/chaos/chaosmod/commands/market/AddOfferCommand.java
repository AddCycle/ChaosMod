package net.chaos.chaosmod.commands.market;

import net.chaos.chaosmod.commands.AbstractPermissionFreeCommand;
import net.chaos.chaosmod.market.MarketData;
import net.chaos.chaosmod.market.MarketDataHandler;
import net.chaos.chaosmod.market.MarketOffer;
import net.chaos.chaosmod.network.market.PacketSyncMarket;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;

public class AddOfferCommand extends AbstractPermissionFreeCommand {
	@Override
    public String getName() {
        return "addoffer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/addoffer <price>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(new TextComponentString("Only players can use this command."));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(new TextComponentString("Usage: /addoffer <price>"));
            return;
        }

        int price;
        try {
            price = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(new TextComponentString("Price must be a number."));
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) sender;
        ItemStack held = player.getHeldItemMainhand();

        if (held.isEmpty()) {
            player.sendMessage(new TextComponentString("Hold an item in your hand to create an offer."));
            return;
        }

        // Add the offer to world data
        MarketData data = MarketDataHandler.get(player.world);
        ItemStack doppleganger = held.copy();
        data.addOffer(new MarketOffer(doppleganger, price));
        player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);

        // Sync to all players
        PacketManager.network.sendToAll(new PacketSyncMarket(data));

        player.sendMessage(new TextComponentString("Added offer: " +
                doppleganger.getDisplayName() + " for " + price + " emeralds"));
    }
}
