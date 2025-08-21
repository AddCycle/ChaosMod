package net.chaos.chaosmod.items.events;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class ItemTooltipEventHandler {
	
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() == Item.getItemFromBlock(Blocks.FURNACE)) {
			String fmt = String.format(TextFormatting.GRAY + "[TEMPERATURE] : < 1070 °C");
			event.getToolTip().add(fmt);
		}
	}
}
