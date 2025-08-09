package net.chaos.chaosmod.client.inventory.shield;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.network.PacketManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ShieldInventory implements IInventory {
	private final EntityPlayer player;

	public ShieldInventory(EntityPlayer player) {
		this.player = player;
	}

	public IShield getCap() {
		return player.getCapability(ModCapabilities.SHIELD, null);
	}

	@Override
	public String getName() {
		return "ShieldInventory";
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
		IShield cap = getCap();
		return cap == null || cap.getShieldItem().isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index == 0) {
			IShield cap = getCap();
			return cap != null ? cap.getShieldItem() : ItemStack.EMPTY;
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
			IShield cap = getCap();
			if (cap != null) {
				ItemStack stack = cap.getShieldItem();
				cap.setShieldItem(ItemStack.EMPTY);
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			IShield cap = getCap();
			if (cap != null) {
				cap.setShieldItem(stack);
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
		return index == 0 && stack.getItem() instanceof ItemShield;
	}

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() {
		IShield cap = getCap();
		if (cap != null) {
			cap.setShieldItem(ItemStack.EMPTY);
		}
	}
	
	public void syncAccessoryToClients(EntityPlayer player, ItemStack shieldStack) {
	    if (!player.world.isRemote) {
	    	Main.getLogger().info("Syncing shield to clients for player " + player.getName() + " with stack: " + shieldStack);
	        PacketManager.network.sendToAll(new PacketShieldSync(player, shieldStack));
	    }
	}

	public static ShieldInventory fromPlayer(EntityPlayer player) {
	    return new ShieldInventory(player);
	}

}