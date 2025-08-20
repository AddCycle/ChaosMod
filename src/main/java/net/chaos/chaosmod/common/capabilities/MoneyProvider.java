package net.chaos.chaosmod.common.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MoneyProvider implements ICapabilitySerializable<NBTTagCompound>{
	
	@CapabilityInject(IMoney.class)
	public static final Capability<IMoney> MONEY_CAPABILITY = null;
	
	private final IMoney instance = new MoneyStorage();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == MONEY_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == MONEY_CAPABILITY ? MONEY_CAPABILITY.cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) MONEY_CAPABILITY.getStorage().writeNBT(MONEY_CAPABILITY, instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		MONEY_CAPABILITY.getStorage().readNBT(MONEY_CAPABILITY, instance, null, nbt);
		
	}

}