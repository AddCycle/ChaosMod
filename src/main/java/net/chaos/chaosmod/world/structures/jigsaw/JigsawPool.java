package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import net.minecraft.util.ResourceLocation;
import util.Reference;

/**
 * TODO : add a maxCount on pieces in the assembler (ex: so the boss_room appears only-once)
 * TODO : add a pieceLast flag to seal dead-ends of structures
 */
public class JigsawPool {
	public static final JigsawPool EMPTY = new JigsawPool(new ResourceLocation("minecraft:empty"),
			new ResourceLocation("minecraft:empty"), Collections.emptyList());

	public static final JigsawPool DUNGEON_POOL = new JigsawPool(new ResourceLocation(Reference.MODID, "dungeon"), EMPTY.getName(), dungeonPool()); // working with depth 

	private final ResourceLocation name;
	private final ResourceLocation fallback;
	private final List<Triple<ResourceLocation, Integer, PieceConstraint>> elements;
	private final int totalWeight;

	public JigsawPool(ResourceLocation name, ResourceLocation fallback,
			List<Triple<ResourceLocation, Integer, PieceConstraint>> elements) {
		this.name = name;
		this.fallback = fallback;
		this.elements = elements;
		this.totalWeight = elements.stream().mapToInt(Triple::getMiddle).sum();
	}
	
	public static JigsawPool unconstrained(ResourceLocation name, ResourceLocation fallback,
            List<Pair<ResourceLocation, Integer>> pairs) {
        List<Triple<ResourceLocation, Integer, PieceConstraint>> elements = new ArrayList<>();
        for (Pair<ResourceLocation, Integer> p : pairs) {
            elements.add(Triple.of(p.getLeft(), p.getRight(), null));
        }
        return new JigsawPool(name, fallback, elements);
    }
	
	public List<ResourceLocation> getShuffled(Random rand, int currentDepth) {
	    List<ResourceLocation> weighted = new ArrayList<>();
	    for (Triple<ResourceLocation, Integer, PieceConstraint> entry : elements) {
	    	PieceConstraint dc = entry.getRight();
	    	if (dc != null && dc.placeLast) continue;
	    	if (dc != null && !dc.allows(currentDepth)) continue;
	        for (int i = 0; i < entry.getMiddle(); i++) {
	            weighted.add(entry.getLeft());
	        }
	    }

	    Collections.shuffle(weighted, rand);
	    List<ResourceLocation> result = new ArrayList<>();
	    Set<ResourceLocation> seen = new LinkedHashSet<>();
	    for (ResourceLocation rl : weighted) {
	        if (seen.add(rl)) result.add(rl);
	    }
	    return result;
	}
	
	public List<ResourceLocation> getShuffled(Random rand) {
		return getShuffled(rand, -1); // no constraints
	}
	
	public List<ResourceLocation> getPlaceLastCandidates(Random rand, int currentDepth) {
	    List<ResourceLocation> weighted = new ArrayList<>();
	    for (Triple<ResourceLocation, Integer, PieceConstraint> entry : elements) {
	        PieceConstraint dc = entry.getRight();
	        if (dc == null || !dc.placeLast) continue; // only placeLast entries
	        if (!dc.allows(currentDepth)) continue;
	        for (int i = 0; i < entry.getMiddle(); i++) {
	            weighted.add(entry.getLeft());
	        }
	    }
	    Collections.shuffle(weighted, rand);
	    List<ResourceLocation> result = new ArrayList<>();
	    Set<ResourceLocation> seen = new LinkedHashSet<>();
	    for (ResourceLocation rl : weighted) {
	        if (seen.add(rl)) result.add(rl);
	    }
	    return result;
	}
	
	public List<ResourceLocation> getPriorityPieces(Random rand, int depth, Map<ResourceLocation, Integer> placementCounts) {
	    List<ResourceLocation> list = new ArrayList<>();

	    for (Triple<ResourceLocation, Integer, PieceConstraint> entry : elements) {
	        ResourceLocation res = entry.getLeft();
	        PieceConstraint dc = entry.getRight();

	        if (dc == null) continue;
	        if (dc.minCount < 0) continue;

	        int count = placementCounts.getOrDefault(res, 0);

	        // only include if minCount not reached
	        if (count < dc.minCount && dc.allows(depth)) {
	        	for (int i = 0; i < entry.getMiddle(); i++) {
	        	    list.add(res);
	        	}
	        }
	    }

	    Collections.shuffle(list, rand);
	    return list;
	}
	
	public PieceConstraint getConstraint(ResourceLocation res) {
	    for (Triple<ResourceLocation, Integer, PieceConstraint> entry : elements) {
	        if (entry.getLeft().equals(res)) return entry.getRight();
	    }
	    return null;
	}

	public ResourceLocation getRandomTemplate(Random rand) {
		if (elements.isEmpty())
			return fallback;
		int roll = rand.nextInt(totalWeight);
		for (Triple<ResourceLocation, Integer, PieceConstraint> entry : elements) {
			roll -= entry.getMiddle();
			if (roll < 0)
				return entry.getLeft();
		}
		return elements.get(0).getLeft();
	}

	// command /setblock ~ ~ ~ minecraft:chest 2 replace {LootTable:"chaosmod:dungeon_chest"}
	public static List<Triple<ResourceLocation, Integer, PieceConstraint>> dungeonPool() {
		List<Triple<ResourceLocation, Integer, PieceConstraint>> elts = new ArrayList<>();
		addPiece(elts, "corridor1", 25, null);
		addPiece(elts, "main_room", 10, new PieceConstraint(1, 1, 1, 1)); // only once next to the first one
		addPiece(elts, "corner1", 25, null);
		addPiece(elts, "chest1", 10, new PieceConstraint(4, -1, -1, -1, true)); // only at the end of a corridor
		addPiece(elts, "stairs1", 10, new PieceConstraint(3, -1));
		addPiece(elts, "boss_room", 20, new PieceConstraint(4, -1, 1, 1, true)); // only once
		return elts;
	}

	private static void addPiece(List<Triple<ResourceLocation, Integer, PieceConstraint>> elts, String pieceName, int weight, PieceConstraint constraint) {
		addPiece(elts, Reference.MODID, pieceName, weight, constraint);
	}
	
	private static void addPiece(List<Triple<ResourceLocation, Integer, PieceConstraint>> elts, String namespace, String pieceName, int weight, PieceConstraint constraint) {
		elts.add(Triple.of(new ResourceLocation(namespace, pieceName), weight, constraint));
	}

	public ResourceLocation getName() { return name; }

	public ResourceLocation getFallback() { return fallback; }
	
	public List<Triple<ResourceLocation, Integer, PieceConstraint>> getElements() { return elements; }
	
	// TODO : rename PieceConstraint
	public static class PieceConstraint {
        public final int minDepth;
        public final int maxDepth;
        public final int minCount; // FIXME : absolutely not working (for now the fix is to put a high weight on the piece and when it's chose no more can be generated so you'd be sure one is present)
        public final int maxCount;
        public final boolean placeLast;
        
        public PieceConstraint() {
        	this(-1, -1); // no constraints
        }

        public PieceConstraint(int minDepth, int maxDepth) {
        	this(minDepth, maxDepth, -1, -1, false);
        }

        public PieceConstraint(int minDepth, int maxDepth, int minCount, int maxCount) {
        	this(minDepth, maxDepth, minCount, maxCount, false);
        }
        
        public PieceConstraint(int minDepth, int maxDepth, int minCount, int maxCount, boolean placeLast) {
			this.minDepth = minDepth;
			this.maxDepth = maxDepth;
			this.minCount = minCount;
			this.maxCount = maxCount;
			this.placeLast = placeLast;
		}

		public boolean allows(int depth) {
            if (minDepth >= 0 && depth < minDepth) return false;
            if (maxDepth >= 0 && depth > maxDepth) return false;
            return true;
        }
		
		public boolean allowsCount(int currentCount) {
	        if (maxCount >= 0 && currentCount >= maxCount) return false;
	        return true;
	    }
    }
}