package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.blocks.AllemaniteOre;
import net.chaos.chaosmod.blocks.EnderiteBlock;
import net.chaos.chaosmod.blocks.EnderiteOre;
import net.chaos.chaosmod.blocks.LitFurnace;
import net.chaos.chaosmod.blocks.OxoniumBlock;
import net.chaos.chaosmod.blocks.OxoniumFurnace;
import net.chaos.chaosmod.blocks.OxoniumOre;
import net.chaos.chaosmod.blocks.OxoniumStairs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

// initialize the blocks and declare them based on a vanilla block base type
public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block OXONIUM_BLOCK = new OxoniumBlock("oxonium_block", Material.IRON);
	public static final Block OXONIUM_ORE = new OxoniumOre("oxonium_ore", Material.ROCK);
	public static final Block OXONIUM_STAIRS = new OxoniumStairs("oxonium_stairs", OXONIUM_BLOCK.getDefaultState());
	public static final Block ALLEMANITE_ORE = new AllemaniteOre("allemanite_ore", Material.ROCK);
	public static final Block ENDERITE_ORE = new EnderiteOre("enderite_ore", Material.ROCK);
	public static final Block ENDERITE_BLOCK = new EnderiteBlock("enderite_block", Material.IRON);
	public static final Block OXONIUM_FURNACE = new OxoniumFurnace("oxonium_furnace", false);
	public static final Block LIT_OXONIUM_FURNACE = new LitFurnace("lit_oxonium_furnace", true);
}