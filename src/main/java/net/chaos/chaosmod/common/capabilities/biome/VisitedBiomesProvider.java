package net.chaos.chaosmod.common.capabilities.biome;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class VisitedBiomesProvider implements ICapabilitySerializable<NBTTagCompound> {
	
	private final VisitedBiomes instance = new VisitedBiomes();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityVisitedBiomes.VISITED_BIOMES;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return hasCapability(capability, facing) ? CapabilityVisitedBiomes.VISITED_BIOMES.cast(instance) : null;
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
