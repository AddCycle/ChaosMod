package net.chaos.chaosmod.jobs.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import util.Reference;

/**
 * All Players progress have to be saved here inside world storage
 * In order for each client to query others progression
 */
public class WorldSavedDataJobs extends WorldSavedData {
	public static final String DATA_JOBS = Reference.PREFIX + "_jobsRankData";

	public WorldSavedDataJobs() {
		super(DATA_JOBS);
	}
	
	public WorldSavedDataJobs(String s) {
		super(s);
	}
	
	// The persistent store: UUID → { name, jobs NBT }
    private final Map<UUID, String> names = new HashMap<>();
    private final Map<UUID, NBTTagCompound> jobsData = new HashMap<>();

    // Called whenever a player's data should be saved/updated
    public void updatePlayer(EntityPlayerMP player) {
        UUID uuid = player.getPersistentID();
        PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);

        names.put(uuid, player.getName());
        if (jobs != null) {
            jobsData.put(uuid, jobs.serializeNBT());
        }

        markDirty(); // tells Forge to write to disk on next save
    }

    public Map<UUID, String> getNames() {
        return Collections.unmodifiableMap(names);
    }

    public Map<UUID, NBTTagCompound> getJobsData() {
        return Collections.unmodifiableMap(jobsData);
    }

    // --- NBT serialization ---

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        names.clear();
        jobsData.clear();

        NBTTagList list = nbt.getTagList("players", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound entry = list.getCompoundTagAt(i);
            UUID uuid = UUID.fromString(entry.getString("uuid"));
            names.put(uuid, entry.getString("name"));
            if (entry.hasKey("jobs")) {
                jobsData.put(uuid, entry.getCompoundTag("jobs"));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();
        for (UUID uuid : names.keySet()) {
            NBTTagCompound entry = new NBTTagCompound();
            entry.setString("uuid", uuid.toString());
            entry.setString("name", names.get(uuid));
            if (jobsData.containsKey(uuid)) {
                entry.setTag("jobs", jobsData.get(uuid));
            }
            list.appendTag(entry);
        }
        nbt.setTag("players", list);
        return nbt;
    }

    public static WorldSavedDataJobs get(World world) {
        MapStorage storage = world.getMapStorage();
        WorldSavedDataJobs data = (WorldSavedDataJobs) storage.getOrLoadData(WorldSavedDataJobs.class, DATA_JOBS);
        if (data == null) {
            data = new WorldSavedDataJobs();
            storage.setData(DATA_JOBS, data);
        }
        return data;
    }
}