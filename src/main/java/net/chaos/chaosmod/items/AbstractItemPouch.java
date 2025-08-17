package net.chaos.chaosmod.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class AbstractItemPouch extends ItemBase {

	public AbstractItemPouch(String name) {
		super(name);
		setMaxStackSize(1);
	}

	public void storeBlock(ItemStack stack, Item blockItem) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
		}

		if (!tag.hasKey("blockType")) {
			tag.setString("blockType", blockItem.getRegistryName().toString());
		}

		int count = tag.getInteger("count");
		tag.setInteger("count", count + 1);

		stack.setTagCompound(tag);
	}

	public ItemStack getStoredStack(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return ItemStack.EMPTY;
		}

		NBTTagCompound tag = stack.getTagCompound();
		String blockName = tag.getString("blockType");
		int count = tag.getInteger("count");

		if (blockName.isEmpty() || count <= 0) return ItemStack.EMPTY;

		Item blockItem = Item.getByNameOrId(blockName);
		if (blockItem == null) return ItemStack.EMPTY;

		return new ItemStack(blockItem, count);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
	    ItemStack stack = player.getHeldItem(hand);
	    boolean gaveSomething = false;

	    if (!world.isRemote) {
	        if (stack.hasTagCompound()) {
	            NBTTagCompound tag = stack.getTagCompound();
	            int count = tag.getInteger("count");
	            String blockName = tag.getString("blockType");

	            if (!blockName.isEmpty() && count > 0) {
	                Item item = Item.getByNameOrId(blockName);
	                if (item != null) {
	                    while (count > 0) {
	                        int toGive = Math.min(count, item.getItemStackLimit(new ItemStack(item)));
	                        ItemStack giveStack = new ItemStack(item, toGive);

	                        boolean added = player.inventory.addItemStackToInventory(giveStack);
	                        if (!added) break;

	                        count -= toGive;
	                        gaveSomething = true;
	                    }
	                }
	            }

	            tag.setInteger("count", count);
	            stack.setTagCompound(tag);
	        }

	        // Play sound only for this player if we gave something
	        if (gaveSomething) {
	            world.playSound(null, player.posX, player.posY, player.posZ, 
	                SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F,
	                ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
	        }
	    }

	    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			int count = tag.getInteger("count");
			String blockName = tag.getString("blockType");

			if (!blockName.isEmpty()) {
				Item item = Item.getByNameOrId(blockName);
				if (item != null) {
					String fmt = String.format("%sStored: %s%d %s.", TextFormatting.GRAY, TextFormatting.YELLOW, count, new ItemStack(item).getDisplayName());
					tooltip.add(fmt);
				}
			}
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
