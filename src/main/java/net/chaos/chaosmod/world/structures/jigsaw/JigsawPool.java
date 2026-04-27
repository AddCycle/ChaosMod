package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.ResourceLocation;
import util.Reference;

public class JigsawPool {
	public static final JigsawPool EMPTY = new JigsawPool(new ResourceLocation("minecraft:empty"),
			new ResourceLocation("minecraft:empty"), Collections.emptyList());

	public static final JigsawPool TEST_POOL = new JigsawPool(new ResourceLocation(Reference.MODID, "test_pool"),
			EMPTY.getName(), testPool());

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

	public static List<Pair<ResourceLocation, Integer>> testPool() {
		List<Pair<ResourceLocation, Integer>> elt = new ArrayList<>();
		elt.add(Pair.of(new ResourceLocation(Reference.MODID, "corridor1"), 50));
		elt.add(Pair.of(new ResourceLocation(Reference.MODID, "test_room1"), 50));
		return elt;
	}
	public ResourceLocation getName() { return name; }

	public ResourceLocation getFallback() { return fallback; }
}