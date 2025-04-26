package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.blocks.AllemaniteBlock;
import net.chaos.chaosmod.blocks.AllemaniteOre;
import net.chaos.chaosmod.blocks.BlockBase;
import net.chaos.chaosmod.blocks.BossAltar;
import net.chaos.chaosmod.blocks.EnderiteBlock;
import net.chaos.chaosmod.blocks.EnderiteOre;
import net.chaos.chaosmod.blocks.OxoniumBlock;
import net.chaos.chaosmod.blocks.OxoniumChest;
import net.chaos.chaosmod.blocks.OxoniumFurnace;
import net.chaos.chaosmod.blocks.OxoniumOre;
import net.chaos.chaosmod.blocks.OxoniumStairs;
import net.chaos.chaosmod.blocks.decoration.BlockBrightGrass;
import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.MineralBricks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextFormatting;

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
	public static final Block LIT_OXONIUM_FURNACE = new OxoniumFurnace("lit_oxonium_furnace", true).setLightLevel(1.0f);
	public static final Block ALLEMANITE_BLOCK = new AllemaniteBlock("allemanite_block", Material.IRON);
	public static final Block OXONIUM_CHEST = new OxoniumChest("oxonium_chest", OxoniumChest.Type.BASIC); // or TRAP
	public static final Block BOSS_ALTAR = new BossAltar("boss_altar", Material.ROCK);
	public static final Block OXONIUM_BRICKS = new MineralBricks("oxonium_bricks", Material.ROCK, TextFormatting.DARK_BLUE, "You want to build ?");
	public static final Block ALLEMANITE_BRICKS = new MineralBricks("allemanite_bricks", Material.ROCK, TextFormatting.RED, "Well go on...");
	public static final Block ENDERITE_BRICKS = new MineralBricks("enderite_bricks", Material.ROCK, TextFormatting.DARK_PURPLE, "You will need this to relax after what you have been through");
	public static final Block CUSTOM_FLOWER = new BlockCustomFlower();
	public static final Block CUSTOM_GRASS = new BlockBrightGrass();
}