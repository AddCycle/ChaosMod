package net.chaos.chaosmod.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class MoneyStorage implements IMoney {
	private int money = 1000;

	@Override
	public int get() {
		return money;
	}

	@Override
	public void set(int value) {
		money = value;
	}

	@Override
	public void add(int amount) {
		money += amount;
	}

	public static class Storage implements Capability.IStorage<IMoney> {

		@Override
		public NBTBase writeNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Money", instance.get());
			return tag;
		}

		@Override
		public void readNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound tag = (NBTTagCompound) nbt;
			instance.set(tag.getInteger("Money"));
		}
		
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(IMoney.class, new Storage(), MoneyStorage::new);
	}
		
}
