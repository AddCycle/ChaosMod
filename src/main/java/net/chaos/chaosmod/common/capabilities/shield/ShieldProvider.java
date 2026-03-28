package net.chaos.chaosmod.common.capabilities.shield;

import net.chaos.chaosmod.client.inventory.shield.ShieldImpl;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

// TODO : needs to do more of a active-blocking player action than a passive one (boring)
public class ShieldProvider implements ICapabilitySerializable<NBTTagCompound> {
	private final ShieldImpl instance = new ShieldImpl();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityShield.SHIELD;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return hasCapability(capability, facing) ? CapabilityShield.SHIELD.cast(instance) : null;
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
