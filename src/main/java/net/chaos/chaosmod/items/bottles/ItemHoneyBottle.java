package net.chaos.chaosmod.items.bottles;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHoneyBottle extends ItemBase {

	public ItemHoneyBottle() {
		super("honey_bottle");
		setMaxStackSize(16);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
	        EntityPlayer player = (EntityPlayer) entityLiving;
//            player.addPotionEffect(new PotionEffect(ModPotionTypes.HONEY, 200, 0));

	        if (!player.capabilities.isCreativeMode) {
	            stack.shrink(1);

	            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);

	            if (!player.inventory.addItemStackToInventory(bottle)) {
	                player.dropItem(bottle, false);
	            }
	        }
	    }
	    return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return false;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MISC, ModTabs.POTIONS, CreativeTabs.SEARCH };
    }
}
