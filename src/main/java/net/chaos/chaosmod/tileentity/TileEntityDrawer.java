package net.chaos.chaosmod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
public class TileEntityDrawer extends TileEntity implements ITickable, IInventory {
	private ItemStack stack = ItemStack.EMPTY;
	private final int limit;
	
	public TileEntityDrawer() {
		this.limit = 64;
	};

	public TileEntityDrawer(int limit) {
		this.limit = limit;
	};

	@Override
	public void update() {
		// do tick updates here maybe count rendering or with renderer but updating the texture
	}

	public ItemStack getStack() {
        return stack;
    }

    public int getLimit(ItemStack stack) {
        return limit;
    }
    
    public ItemStack removeAll() {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack out = stack.copy();
        stack = ItemStack.EMPTY;
        markDirty();
        return out;
    }

    // Adds items to the drawer respecting the limit
    public int addStack(ItemStack toAdd) {
        if (toAdd.isEmpty()) return 0;

        int res = 0;
        if (stack.isEmpty()) {
            int addCount = Math.min(limit, toAdd.getCount());
            stack = toAdd.copy();            // copies item type, count, metadata, AND NBT
            stack.setCount(addCount);        // adjust count to fit limit
            res = addCount;
        } else if (stack.isItemEqual(toAdd) && ItemStack.areItemStackTagsEqual(stack, toAdd)) {
            int space = limit - stack.getCount();
            int addCount = Math.min(space, toAdd.getCount());
            stack.grow(addCount);
            res = addCount;
        }

        markDirty();
        return res;
    }

    // Removes items from the drawer
    public ItemStack removeStack(int amount) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        int removed = Math.min(amount, stack.getCount());
        ItemStack out = stack.splitStack(removed);
        if (stack.getCount() <= 0) stack = ItemStack.EMPTY;
        markDirty();
        return out;
    }

    // Shift-left: drops everything on the ground
    public void dropAll(EntityPlayer player) {
        if (!stack.isEmpty()) {
            player.entityDropItem(stack, 0);
            stack = ItemStack.EMPTY;
            markDirty();
        }
    }

    // Shift-right: puts everything into player inventory
    public void giveAllToPlayer(EntityPlayer player) {
        if (!stack.isEmpty()) {
            boolean allAdded = player.inventory.addItemStackToInventory(stack);
            if (!allAdded) {
                // drop remaining items on the ground
                player.entityDropItem(stack, 0);
            }
            stack = ItemStack.EMPTY;
            markDirty();
        }
    }

    // Right-click: puts items from hand into drawer
    public void addFromHand(EntityPlayer player) {
        ItemStack hand = player.getHeldItemMainhand();
        if (!hand.isEmpty()) {
            addStack(hand);

            // calculate leftover in hand
            int leftover = Math.max(0, hand.getCount() - getLimit(hand));
            if (leftover > 0) {
                hand.setCount(leftover);
            } else {
                hand = ItemStack.EMPTY;
            }

            // update the player's main hand
            player.setHeldItem(EnumHand.MAIN_HAND, hand);
        }
    }
    
    // ############# FIXME : verify if it is allowed to have the same name for tag multiple drawer types ###################
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	super.writeToNBT(compound);
    	compound.setTag("Stack", stack.writeToNBT(new NBTTagCompound()));
    	return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	stack = new ItemStack(compound.getCompoundTag("Stack"));
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
    	NBTTagCompound tag = new NBTTagCompound();
    	writeToNBT(tag);
    	return new SPacketUpdateTileEntity(pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
    	NBTTagCompound tag = super.getUpdateTag();
        writeToNBT(tag);
        return tag;
    }

    // IInventory implementation for one slot
    @Override public int getSizeInventory() { return 1; }
    @Override public boolean isEmpty() { return stack.isEmpty(); }
    @Override public ItemStack getStackInSlot(int index) { return stack; }
    @Override public ItemStack decrStackSize(int index, int count) { return removeStack(count); }
    @Override public ItemStack removeStackFromSlot(int index) { return removeStack(stack.getCount()); }
    @Override public void setInventorySlotContents(int index, ItemStack stack) { this.stack = stack; markDirty(); }
    @Override public int getInventoryStackLimit() { return limit; }
    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isRemote) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }
    @Override public boolean isUsableByPlayer(EntityPlayer player) { return true; }
    @Override public void openInventory(EntityPlayer player) {}
    @Override public void closeInventory(EntityPlayer player) {}
    @Override public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }
    @Override public int getField(int id) { return 0; }
    @Override public void setField(int id, int value) {}
    @Override public int getFieldCount() { return 0; }
    @Override public void clear() { stack = ItemStack.EMPTY; }
    @Override public String getName() { return null; }
    @Override public boolean hasCustomName() { return false; }
    @Override public ITextComponent getDisplayName() { return null; }
}
