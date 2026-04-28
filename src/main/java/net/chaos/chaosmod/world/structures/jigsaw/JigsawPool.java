package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.ResourceLocation;
import util.Reference;

public class JigsawPool {
	public static final JigsawPool EMPTY = new JigsawPool(new ResourceLocation("minecraft:empty"),
			new ResourceLocation("minecraft:empty"), Collections.emptyList());

//	public static final JigsawPool TEST_POOL = new JigsawPool(new ResourceLocation(Reference.MODID, "test_pool"),
//			EMPTY.getName(), testPool());

	public static final JigsawPool DUNGEON_POOL = new JigsawPool(new ResourceLocation(Reference.MODID, "dungeon"),
			EMPTY.getName(), dungeonPool());

	private final ResourceLocation name;
	private final ResourceLocation fallback;
	// Pair of (template ResourceLocation, weight)
	private final List<Pair<ResourceLocation, Integer>> elements;
	private final int totalWeight;

	public JigsawPool(ResourceLocation name, ResourceLocation fallback,
			List<Pair<ResourceLocation, Integer>> elements) {
		this.name = name;
		this.fallback = fallback;
		this.elements = elements;
		this.totalWeight = elements.stream().mapToInt(Pair::getRight).sum();
	}
	
	public List<ResourceLocation> getShuffled(Random rand) {
	    List<Pair<ResourceLocation, Integer>> copy = new ArrayList<>(elements);
	    // weighted shuffle: just collect all keys, weight times, then shuffle
	    List<ResourceLocation> weighted = new ArrayList<>();
	    for (Pair<ResourceLocation, Integer> entry : copy) {
	        for (int i = 0; i < entry.getRight(); i++) {
	            weighted.add(entry.getLeft());
	        }
	    }
	    Collections.shuffle(weighted, rand);
	    // deduplicate while preserving shuffle order
	    List<ResourceLocation> result = new ArrayList<>();
	    Set<ResourceLocation> seen = new LinkedHashSet<>();
	    for (ResourceLocation rl : weighted) {
	        if (seen.add(rl)) result.add(rl);
	    }
	    return result;
	}

	public ResourceLocation getRandomTemplate(Random rand) {
		if (elements.isEmpty())
			return fallback;
		int roll = rand.nextInt(totalWeight);
		for (Pair<ResourceLocation, Integer> entry : elements) {
			roll -= entry.getRight();
			if (roll < 0)
				return entry.getLeft();
		}
		return elements.get(0).getLeft();
	}

	// command /setblock ~ ~ ~ minecraft:chest 2 replace {LootTable:"chaosmod:dungeon_chest"}
	public static List<Pair<ResourceLocation, Integer>> dungeonPool() {
		List<Pair<ResourceLocation, Integer>> elt = new ArrayList<>();
		elt.add(Pair.of(new ResourceLocation(Reference.MODID, "corridor1"), 30));
		elt.add(Pair.of(new ResourceLocation(Reference.MODID, "main_room"), 20));
		elt.add(Pair.of(new ResourceLocation(Reference.MODID, "corner1"), 30));
		elt.add(Pair.of(new ResourceLocation(Reference.MODID, "chest1"), 10));
		elt.add(Pair.of(new ResourceLocation(Reference.MODID, "stairs1"), 10));
		return elt;
	}
	public ResourceLocation getName() { return name; }

	public ResourceLocation getFallback() { return fallback; }
}