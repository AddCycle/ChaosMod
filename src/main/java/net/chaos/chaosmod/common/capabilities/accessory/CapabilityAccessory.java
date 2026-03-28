package net.chaos.chaosmod.common.capabilities.accessory;

import net.chaos.chaosmod.client.inventory.AccessoryImpl;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityAccessory {

	@CapabilityInject(IAccessory.class)
	public static final Capability<IAccessory> ACCESSORY = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IAccessory.class, new Storage(), AccessoryImpl::new);
	}

	public static class Storage implements IStorage<IAccessory> {

		@Override
		public NBTBase writeNBT(Capability<IAccessory> capability, IAccessory instance, EnumFacing side) {
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<IAccessory> capability, IAccessory instance, EnumFacing side, NBTBase nbt) {
			instance.deserializeNBT((NBTTagCompound) nbt);
		}
	}

}
