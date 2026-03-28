package net.chaos.chaosmod.common.capabilities.accessory;

import net.chaos.chaosmod.client.inventory.AccessoryImpl;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AccessoryProvider implements ICapabilitySerializable<NBTTagCompound>{
	private final AccessoryImpl instance = new AccessoryImpl();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityAccessory.ACCESSORY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return hasCapability(capability, facing) ? CapabilityAccessory.ACCESSORY.cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return instance.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		instance.deserializeNBT(nbt);
	}
}
