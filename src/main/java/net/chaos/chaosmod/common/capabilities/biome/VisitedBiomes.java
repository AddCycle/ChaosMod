package net.chaos.chaosmod.common.capabilities.biome;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;

public class VisitedBiomes {
	private Set<Biome> visitedBiomes = new HashSet<>();
	
	public void addBiome(Biome biome) {
		visitedBiomes.add(biome);
	}

	public void removeBiome(Biome biome) {
		visitedBiomes.remove(biome);
	}

	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();

		int[] array = visitedBiomes.stream().mapToInt(b -> Biome.getIdForBiome(b)).toArray();

		tag.setIntArray("visitedBiomes", array);

		return tag;
	}

	public void deserializeNBT(NBTTagCompound nbt) {
		visitedBiomes.clear();

		int[] array = nbt.getIntArray("visitedBiomes");

		for (int id : array) {
			Biome biome = Biome.getBiome(id);
			if (biome != null) {
				visitedBiomes.add(biome);
			}
		}
	}

	public Set<Biome> getVisitedBiomes() {
		return visitedBiomes;
	}

	public void copyFrom(VisitedBiomes other) {
		this.visitedBiomes.clear();
		this.visitedBiomes.addAll(other.visitedBiomes);
	}
}