package net.chaos.chaosmod.items.tools;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.gui.GuiFinalCredits;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import util.IHasModel;

public class ToolHoe extends ItemHoe implements IHasModel {

	public ToolHoe(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.openGui(Main.instance, 2, worldIn, 0, 0, 0);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.TOOLS, ModTabs.GENERAL_TAB }; // You can add other tabs
    }
}