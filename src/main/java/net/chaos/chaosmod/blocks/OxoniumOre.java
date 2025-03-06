package net.chaos.chaosmod.blocks;

import java.util.Random;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class OxoniumOre extends BlockBase {

	public OxoniumOre(String name, Material material) {
		super(name, material);
		//setUnlocalizedName(name); // ce truc m'a fait enculé en gros ca prend le nom sans le modid:name (juste name)
		//setRegistryName(name); // idem je veux plus les voir
		setSoundType(SoundType.METAL);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(ModTabs.GENERAL_TAB);
	}
	
	// Only if you want a custom drop
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModItems.OXONIUM;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		int max = 4;
		int min = 1;
		return rand.nextInt(max) + min;
	}
}