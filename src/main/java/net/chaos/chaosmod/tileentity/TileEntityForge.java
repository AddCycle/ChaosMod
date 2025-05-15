package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.chaos.chaosmod.recipes.machine.ForgeRecipe;
import net.chaos.chaosmod.recipes.machine.MachineRecipeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
	private int totalFabricationTime = 100;

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

	/* OLD UPDATE
	 * @Override
	 *
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
	}*/
	
	/*
	 * LATER
	 */
	/*public static int getCustomFuelValue(ItemStack stack) {
	    if (stack.getItem() == Items.COAL) return 1600;
	    if (stack.getItem() == ItemInit.MY_FUEL) return 3200;
	    return 0;
	}*/
    
    @Override
    public void update() {
    	if (!world.isRemote) {
    	    // System.out.println("canSmelt: " + canSmelt());
    	    // System.out.println("input3: " + content.get(3)); // or whichever slot is input
    	}
    	if (canSmelt()) {
    	    fabricationTime++;
    	    if (fabricationTime >= getItemFabricationTime()) {
    	        fabricationTime = 0;
    	        fabricItem();
    	        markDirty();
    	    }
    	} else {
    	    fabricationTime = 0;
    	}
    }

    public int getFabricationTime() {
        return fabricationTime;
    }

    public int getItemFabricationTime() {
        return totalFabricationTime; 
    }

	private void fabricItem() {
		ItemStack input1 = content.get(0);
	    ItemStack input2 = content.get(1);
	    ItemStack input3 = content.get(2);
	    ItemStack output = content.get(3);
	    /*System.out.println("Checking recipe for: " + input1.getCount());
	    System.out.println("Checking recipe for: " + input2.getCount());
	    System.out.println("Checking recipe for: " + input3.getCount());*/

	    for (ForgeRecipe recipe : MachineRecipeRegistry.RECIPES) {
	        if (recipe.matches(input1, input2, input3)) {
	            if (output.isEmpty()) {
	                content.set(3, recipe.output.copy());
	            } else {
	                output.grow(recipe.output.getCount());
	            }

	            input1.shrink(recipe.input1.getCount());
	            input2.shrink(recipe.input2.getCount());
	            input3.shrink(recipe.input3.getCount());
	            markDirty();
	            break;
	        }
	    }
	}

	private boolean canSmelt() {
		for (ForgeRecipe recipe : MachineRecipeRegistry.RECIPES) {
	        ItemStack input1 = recipe.input1;
	        ItemStack input2 = recipe.input2;
	        ItemStack input3 = recipe.input3;

	        boolean match1 = input1.isEmpty() || ItemStack.areItemsEqual(content.get(0), input1);
	        boolean match2 = input2.isEmpty() || ItemStack.areItemsEqual(content.get(1), input2);
	        boolean match3 = input3.isEmpty() || ItemStack.areItemsEqual(content.get(2), input3)
	                          && content.get(2).getCount() >= input3.getCount();

	        if (match1 && match2 && match3) {
	            return true;
	        }
	    }
	    return false;
		/*ItemStack input1 = content.get(0);
	    ItemStack input2 = content.get(1);
	    ItemStack input3 = content.get(2);
	    ItemStack output = content.get(3);
	    System.out.println("Checking recipe for: " + input1.getCount());
	    System.out.println("Checking recipe for: " + input2.getCount());
	    System.out.println("Checking recipe for: " + input3.getCount());

	    for (ForgeRecipe recipe : MachineRecipeRegistry.RECIPES) {
	        if (recipe.matches(input1, input2, input3)) {
	            if (output.isEmpty()) return true;
	            if (!ItemStack.areItemsEqual(output, recipe.output)) return false;
	            return output.getCount() + recipe.output.getCount() <= output.getMaxStackSize();
	        }
	    }

	    return false;*/
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
	    super.writeToNBT(compound);
	    ItemStackHelper.saveAllItems(compound, content);
	    compound.setInteger("FabricTime", fabricationTime);
	    return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
	    super.readFromNBT(compound);
	    content = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
	    ItemStackHelper.loadAllItems(compound, content);
	    fabricationTime = compound.getInteger("FabricTime");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
	    NBTTagCompound nbtTag = new NBTTagCompound();
	    this.writeToNBT(nbtTag);
	    return new SPacketUpdateTileEntity(this.pos, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
	    this.readFromNBT(packet.getNbtCompound());
	}

	public int getProgressScaled(int pixels) {
		int currentTime = this.getField(0); // synced from server
		int totalTime = this.getField(1);   // synced from server
		return totalTime != 0 ? currentTime * pixels / totalTime : 0;
	}
	
	@Override
	public int getField(int id) {
	    switch (id) {
	        case 0: return fabricationTime;
	        case 1: return getItemFabricationTime();
	        // other fields if needed
	        default: return 0;
	    }
	}

	@Override
	public void setField(int id, int value) {
	    switch (id) {
	        case 0: fabricationTime = value; break;
	        // total time is constant, may not need setField
	    }
	}

	@Override
	public int getFieldCount() {
	    return 2;  // number of fields to sync
	}

}
