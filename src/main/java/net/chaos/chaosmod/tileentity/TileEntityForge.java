package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class TileEntityForge extends TileEntityLockableLoot {
	private NonNullList<ItemStack> content = NonNullList.withSize(4, ItemStack.EMPTY);

	@Override
	public int getSizeInventory() {
		return 9;
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

}
