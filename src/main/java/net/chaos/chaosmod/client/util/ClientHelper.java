package net.chaos.chaosmod.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientHelper {

	public static void playTotemEffect(Item item) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			Minecraft.getMinecraft().entityRenderer.displayItemActivation(new ItemStack(item));
		});
	}
}