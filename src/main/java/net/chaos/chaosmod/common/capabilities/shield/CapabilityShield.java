package net.chaos.chaosmod.common.capabilities.shield;

import net.chaos.chaosmod.client.inventory.shield.IShield;
import net.chaos.chaosmod.client.inventory.shield.ShieldImpl;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityShield {

	@CapabilityInject(IShield.class)
	public static final Capability<IShield> SHIELD = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IShield.class, new Storage(), ShieldImpl::new);
	}

	public static class Storage implements IStorage<IShield> {

		@Override
		public NBTBase writeNBT(Capability<IShield> capability, IShield instance, EnumFacing side) {
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<IShield> capability, IShield instance, EnumFacing side, NBTBase nbt) {
			instance.deserializeNBT((NBTTagCompound) nbt);
		}
	}
}
