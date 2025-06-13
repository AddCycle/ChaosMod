package net.chaos.chaosmod.init;

import net.chaos.chaosmod.client.inventory.AccessoryImpl;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.client.inventory.shield.IShield;
import net.chaos.chaosmod.client.inventory.shield.ShieldImpl;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {
	@CapabilityInject(IAccessory.class)
    public static final Capability<IAccessory> ACCESSORY = null;

	@CapabilityInject(IShield.class)
	public static final Capability<IShield> SHIELD = null;

    public static void register() {
    	CapabilityManager.INSTANCE.register(IAccessory.class, new Capability.IStorage<IAccessory>() {
    	    @Override
    	    public NBTBase writeNBT(Capability<IAccessory> capability, IAccessory instance, EnumFacing side) {
    	        return instance.serializeNBT();
    	    }

    	    @Override
    	    public void readNBT(Capability<IAccessory> capability, IAccessory instance, EnumFacing side, NBTBase nbt) {
    	        instance.deserializeNBT((NBTTagCompound) nbt);
    	    }
    	}, AccessoryImpl::new);

    	CapabilityManager.INSTANCE.register(IShield.class, new Capability.IStorage<IShield>() {
    	    @Override
    	    public NBTBase writeNBT(Capability<IShield> capability, IShield instance, EnumFacing side) {
    	        return instance.serializeNBT();
    	    }

    	    @Override
    	    public void readNBT(Capability<IShield> capability, IShield instance, EnumFacing side, NBTBase nbt) {
    	        instance.deserializeNBT((NBTTagCompound) nbt);
    	    }
    	}, ShieldImpl::new);
    }

}
