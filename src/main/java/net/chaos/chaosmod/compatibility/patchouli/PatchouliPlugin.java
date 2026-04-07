package net.chaos.chaosmod.compatibility.patchouli;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import util.Reference;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliPlugin {
	
	public static void giveBook(EntityPlayer player) {
		ItemStack book = PatchouliAPI.instance.getBookStack(Reference.PREFIX + "chaos_almanac");
		if (book == null) return;
		player.addItemStackToInventory(book);
	}
}