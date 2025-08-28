package net.chaos.chaosmod.blocks.abstracted;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MaterialHelper {
	public static SoundType getSoundFromMaterial(Material material) {
	    if (material == Material.WOOD) return SoundType.WOOD;
	    if (material == Material.ROCK) return SoundType.STONE;
	    if (material == Material.IRON) return SoundType.METAL;
	    if (material == Material.GROUND) return SoundType.GROUND;
	    if (material == Material.SAND) return SoundType.SAND;
	    if (material == Material.GLASS) return SoundType.GLASS;
	    if (material == Material.PLANTS || material == Material.LEAVES) return SoundType.PLANT;
	    if (material == Material.WATER) return SoundType.LADDER; // kinda arbitrary
	    // fallback
	    return SoundType.STONE;
	}
	
	public static void applyMaterialProperties(Block block, Material material) {
	    // Default sound
	    if (material == Material.WOOD) {
	        // block.setSoundType(SoundType.WOOD);
	        block.setHardness(2.0F);
	        block.setResistance(5.0F);
	    } else if (material == Material.ROCK) {
	        // block.setSoundType(SoundType.STONE);
	        block.setHardness(1.5F);
	        block.setResistance(10.0F);
	    } else if (material == Material.IRON) {
	        // block.setSoundType(SoundType.METAL);
	        block.setHardness(5.0F);
	        block.setResistance(10.0F);
	    } else if (material == Material.GROUND || material == Material.SAND) {
	        // block.setSoundType(SoundType.GROUND);
	        block.setHardness(0.5F);
	        block.setResistance(2.5F);
	    } else if (material == Material.PLANTS || material == Material.LEAVES) {
	        // block.setSoundType(SoundType.PLANT);
	        block.setHardness(0.0F);
	        block.setResistance(0.0F);
	    } else if (material == Material.GLASS) {
	        // block.setSoundType(SoundType.GLASS);
	        block.setHardness(0.3F);
	        block.setResistance(1.5F);
	    } else {
	        // fallback
	        // block.setSoundType(SoundType.STONE);
	        block.setHardness(1.0F);
	        block.setResistance(5.0F);
	    }
	}

}
