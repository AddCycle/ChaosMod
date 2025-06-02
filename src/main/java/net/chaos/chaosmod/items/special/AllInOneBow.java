package net.chaos.chaosmod.items.special;

import java.util.List;

import net.chaos.chaosmod.items.AbstractCustomBow;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AllInOneBow extends AbstractCustomBow {

	public AllInOneBow(String name, ToolMaterial material) {
		super(name, material);
		this.setMaxDamage(-1);
		this.drawTime = 5.0f;
		// this.damageMultiplier = 3.0f;
		this.damageMultiplier = 100.0f; // FIXME : too much
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("item.all_in_one_bow.tooltip"); // TODO : add translation
	}
	
	@Override
	public FontRenderer getFontRenderer(ItemStack stack) {
		// return new FontRenderer(GameSettings.COLON_SPLITTER, new ResourceLocation(""), TextureManager obj, true);
		return super.getFontRenderer(stack);
	}

}
