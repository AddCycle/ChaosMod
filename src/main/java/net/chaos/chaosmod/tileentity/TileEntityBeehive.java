package net.chaos.chaosmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBeehive extends TileEntity {
	private int beeCount;
	
	public TileEntityBeehive() {
		this.beeCount = 2; // TODO random
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("beeCount", beeCount);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.beeCount = compound.getInteger("beeCount");
	}
	
	public void setBeeCount(int beeCount) { this.beeCount = beeCount; }
	public int getBeeCount() { return beeCount; }
}