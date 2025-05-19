package net.chaos.chaosmod.client.inventory;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
import net.chaos.chaosmod.network.PacketAccessorySync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class AccessoryInventory implements IInventory {
	private final EntityPlayer player;

	public AccessoryInventory(EntityPlayer player) {
		this.player = player;
	}

	public IAccessory getCap() {
		return player.getCapability(ModCapabilities.ACCESSORY, null);
	}

	@Override
	public String getName() {
		return "AccessoryInventory";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		IAccessory cap = getCap();
		return cap == null || cap.getAccessoryItem().isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index == 0) {
			IAccessory cap = getCap();
			return cap != null ? cap.getAccessoryItem() : ItemStack.EMPTY;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index == 0) {
			ItemStack current = getStackInSlot(0);
			if (!current.isEmpty()) {
				return current.splitStack(count);
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index == 0) {
			IAccessory cap = getCap();
			if (cap != null) {
				ItemStack stack = cap.getAccessoryItem();
				cap.setAccessoryItem(ItemStack.EMPTY);
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			IAccessory cap = getCap();
			if (cap != null) {
				cap.setAccessoryItem(stack);
				if (!player.world.isRemote) {  // only sync when changed on server side
			        syncAccessoryToClients(player, stack);
			    }
			}
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && stack.getItem() instanceof OxoniumNecklace;
	}

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() {
		IAccessory cap = getCap();
		if (cap != null) {
			cap.setAccessoryItem(ItemStack.EMPTY);
		}
	}
	
	public void syncAccessoryToClients(EntityPlayer player, ItemStack necklaceStack) {
	    if (!player.world.isRemote) {
	    	Main.getLogger().info("Syncing accessory to clients for player " + player.getName() + " with stack: " + necklaceStack);
	        Main.network.sendToAll(new PacketAccessorySync(player, necklaceStack));
	    }
	}

	public static AccessoryInventory fromPlayer(EntityPlayer player) {
	    return new AccessoryInventory(player);
	}

}
