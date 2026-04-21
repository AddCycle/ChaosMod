package net.chaos.chaosmod.jobs.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientJobsCache {
	public static final ClientJobsCache INSTANCE = new ClientJobsCache();

    private final Map<UUID, PlayerJobs> cache = new HashMap<>();
    private final Map<UUID, String> names = new HashMap<>();

    private ClientJobsCache() {}

    public void update(Map<UUID, NBTTagCompound> rawData, Map<UUID, String> rawNames) {
        cache.clear();
        names.clear();
        names.putAll(rawNames);
        for (Map.Entry<UUID, NBTTagCompound> entry : rawData.entrySet()) {
            PlayerJobs jobs = new PlayerJobs();
            jobs.deserializeNBT(entry.getValue());
            cache.put(entry.getKey(), jobs);
        }
    }

    public Map<UUID, PlayerJobs> getAll() {
        return Collections.unmodifiableMap(cache);
    }
    
    public String getName(UUID uuid) {
        return names.getOrDefault(uuid, uuid.toString()); // fallback to UUID string if missing
    }

    @Nullable
    public PlayerJobs getForPlayer(UUID uuid) {
        return cache.get(uuid);
    }

    public void clear() {
        cache.clear();
        names.clear();
    }
}