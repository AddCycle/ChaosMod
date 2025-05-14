package net.chaos.chaosmod.client.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class AccessoryStorage implements Capability.IStorage<IAccessory> {

    @Override
    public NBTBase writeNBT(Capability<IAccessory> capability, IAccessory instance, EnumFacing side) {
        return instance.getAccessoryItem().serializeNBT();
    }

    @Override
    public void readNBT(Capability<IAccessory> capability, IAccessory instance, EnumFacing side, NBTBase nbt) {
        instance.setAccessoryItem(new ItemStack((NBTTagCompound) nbt));
    }
}