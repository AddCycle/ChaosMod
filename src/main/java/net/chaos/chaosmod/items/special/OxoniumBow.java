package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import util.IHasModel;

public class OxoniumBow extends ItemBow implements IHasModel {
	private boolean low;
	
	public OxoniumBow(String name, ToolMaterial material) {
		this.setMaxStackSize(1);
		this.setMaxDamage(600);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (playerIn.getHealth() / playerIn.getMaxHealth() <= 0.5) {
			low = true;
		} else {
			low = false;
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public EntityArrow customizeArrow(EntityArrow arrow) {
		double base = 2.0D;
		if (low) arrow.setDamage(base * 2); else arrow.setDamage(base);
		return super.customizeArrow(arrow);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.GENERAL_TAB, CreativeTabs.COMBAT };
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
