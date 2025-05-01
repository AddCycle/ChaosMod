package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class TileEntityForge extends TileEntityLockableLoot implements ITickable {
	private NonNullList<ItemStack> content = NonNullList.withSize(4, ItemStack.EMPTY);
	private int fabricationTime;

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public boolean isEmpty() {
		return content.isEmpty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getName() {
		return new TextComponentTranslation("gui.forge_interface_block.title", new TextComponentString("")
				.setStyle(new Style().setColor(TextFormatting.DARK_PURPLE).setBold(true))).getFormattedText();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ForgeInterfaceContainer(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return Reference.MODID + ":forge_interface_block";
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return content;
	}
	
	@Override
    public ItemStack getStackInSlot(int index) {
        return this.content.get(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.content.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }
    
    /*public int getBurnTime() {
    	// later to fuel it ?
    }*/
    
    public int getItemFabricationTime() {
    	return 20; // 1s
		// return 20 * 60 * 3; // 3 min
    }

	@Override
	public void update() {
		// int burnTime = TileEntityForge.getItemBurnTime(fuelStack);
		int burnTime = getItemFabricationTime();
		boolean burning = burnTime > 0;

	    if (burnTime > 0) {
	        burnTime--;
	    }

	    if (!world.isRemote) {
	        if (burnTime == 0 && canSmelt()) {
	            // burnTime = getCustomFuelValue(fuelStack);
	            if (burnTime > 0) {
	            	// later
	                // fuelStack.shrink(1);
	            }
	        }

	        if (burning && canSmelt()) {
	            fabricationTime++;
	            if (fabricationTime >= getItemFabricationTime()) {
	                fabricItem();
	                fabricationTime = 0;
	            }
	        } else {
	            fabricationTime = 0;
	        }

	        markDirty(); // Save tile state
	    }
	}
	
	/*
	 * LATER
	 */
	/*public static int getCustomFuelValue(ItemStack stack) {
	    if (stack.getItem() == Items.COAL) return 1600;
	    if (stack.getItem() == ItemInit.MY_FUEL) return 3200;
	    return 0;
	}*/

	private void fabricItem() {
		this.content.get(0).shrink(1);
		this.content.get(1).shrink(1);
		this.content.get(2).shrink(1);
		this.setInventorySlotContents(3, new ItemStack(ModItems.ENDERITE_AXE));
	}

	private boolean canSmelt() {
		return true; // later
	}

}
