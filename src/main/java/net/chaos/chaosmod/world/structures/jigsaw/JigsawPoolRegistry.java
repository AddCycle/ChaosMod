package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class JigsawPoolRegistry {
	private static final Map<ResourceLocation, JigsawPool> POOLS = new HashMap<>();

    public static void register(JigsawPool pool) {
        POOLS.put(pool.getName(), pool);
    }

    public static JigsawPool get(ResourceLocation name) {
        return POOLS.getOrDefault(name, JigsawPool.EMPTY);
    }

    public static JigsawPool get(String name) {
        return get(new ResourceLocation(name));
    }

    public static void clear() {
        POOLS.clear();
    }

	public static void registerTestPools() {
		register(JigsawPool.EMPTY);
		register(JigsawPool.DUNGEON_POOL);
	}
}