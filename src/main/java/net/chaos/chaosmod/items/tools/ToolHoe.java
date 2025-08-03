package net.chaos.chaosmod.items.tools;

import java.util.List;

import org.lwjgl.opengl.XRandR.Screen;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.gui.GuiFinalCredits;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import util.IHasModel;
import util.Reference;

public class ToolHoe extends ItemHoe implements IHasModel {

	public ToolHoe(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			tooltip.add(new TextComponentString(TextFormatting.DARK_RED + "Right click to launch credits").getFormattedText());
		} else {
			tooltip.add(new TextComponentString("Press " + TextFormatting.YELLOW + "[SHIFT]" + TextFormatting.RESET + " to see what it looks like").getFormattedText());
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (GuiScreen.isShiftKeyDown()) {
			playerIn.openGui(Main.instance, Reference.GUI_CREDITS_ID, worldIn, 0, 0, 0);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.TOOLS, ModTabs.ITEMS }; // You can add other tabs
    }
}