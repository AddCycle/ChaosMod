package net.chaos.chaosmod.common.capabilities.jobs;

import net.chaos.chaosmod.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerJobsProvider implements ICapabilitySerializable<NBTTagCompound> {

	private final PlayerJobs instance = new PlayerJobs();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityPlayerJobs.PLAYER_JOBS;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return hasCapability(capability, facing) ? CapabilityPlayerJobs.PLAYER_JOBS.cast(instance) : null;
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