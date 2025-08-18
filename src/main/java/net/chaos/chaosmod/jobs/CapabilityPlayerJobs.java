package net.chaos.chaosmod.jobs;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import util.Reference;

/**
 * This class stores the PlayerJobs completion client-side
 * You should see the PlayerJobs class for more infos
 */
public class CapabilityPlayerJobs {
	public static final ResourceLocation KEY = new ResourceLocation(Reference.MODID, "player_jobs");

	@CapabilityInject(PlayerJobs.class)
	public static Capability<PlayerJobs> PLAYER_JOBS = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(PlayerJobs.class, new Storage(), PlayerJobs::new);
	}
	
	public static class Storage implements Capability.IStorage<PlayerJobs> {

        @Override
        public NBTBase writeNBT(Capability<PlayerJobs> capability, PlayerJobs instance, EnumFacing side) {
            // Serialize PlayerJobs to NBT
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<PlayerJobs> capability, PlayerJobs instance, EnumFacing side, NBTBase nbt) {
            // Deserialize PlayerJobs from NBT
            if (nbt instanceof NBTTagCompound) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }
    }
}
