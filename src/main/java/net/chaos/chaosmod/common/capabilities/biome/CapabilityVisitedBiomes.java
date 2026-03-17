package net.chaos.chaosmod.common.capabilities.biome;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityVisitedBiomes {

	@CapabilityInject(VisitedBiomes.class)
	public static final Capability<VisitedBiomes> VISITED_BIOMES = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(VisitedBiomes.class, new Storage(), VisitedBiomes::new);
	}
	
	public static class Storage implements Capability.IStorage<VisitedBiomes> {

        @Override
        public NBTBase writeNBT(Capability<VisitedBiomes> capability, VisitedBiomes instance, EnumFacing side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<VisitedBiomes> capability, VisitedBiomes instance, EnumFacing side, NBTBase nbt) {
            if (nbt instanceof NBTTagCompound) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }
    }

}
