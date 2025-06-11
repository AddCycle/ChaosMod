package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.blocks.AllemaniteBlock;
import net.chaos.chaosmod.blocks.AllemaniteOre;
import net.chaos.chaosmod.blocks.BlockBeam;
import net.chaos.chaosmod.blocks.BlockTrophy;
import net.chaos.chaosmod.blocks.BossAltar;
import net.chaos.chaosmod.blocks.ConnectedBlock;
import net.chaos.chaosmod.blocks.CookieJarBlock;
import net.chaos.chaosmod.blocks.CustomBlockSapling;
import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.CustomPlanks;
import net.chaos.chaosmod.blocks.DimensionPortal;
import net.chaos.chaosmod.blocks.EnderiteBlock;
import net.chaos.chaosmod.blocks.EnderiteForgeWalls;
import net.chaos.chaosmod.blocks.EnderiteOre;
import net.chaos.chaosmod.blocks.FakeLightBlock;
import net.chaos.chaosmod.blocks.ForgeInterfaceBlock;
import net.chaos.chaosmod.blocks.OxoniumBlock;
import net.chaos.chaosmod.blocks.OxoniumChest;
import net.chaos.chaosmod.blocks.OxoniumFurnace;
import net.chaos.chaosmod.blocks.OxoniumOre;
import net.chaos.chaosmod.blocks.abstracted.AbstractDoor;
import net.chaos.chaosmod.blocks.abstracted.AbstractFences;
import net.chaos.chaosmod.blocks.abstracted.AbstractLadder;
import net.chaos.chaosmod.blocks.abstracted.AbstractPressurePlate;
import net.chaos.chaosmod.blocks.abstracted.AbstractStairs;
import net.chaos.chaosmod.blocks.crops.OxoniumCarrotBlock;
import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.BlockLantern;
import net.chaos.chaosmod.blocks.decoration.CustomLeaves;
import net.chaos.chaosmod.blocks.decoration.MineralBricks;
import net.chaos.chaosmod.blocks.decoration.OxoniumStairs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.text.TextFormatting;

// initialize the blocks and declare them based on a vanilla block base type
public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block OXONIUM_CARROT_BLOCK = new OxoniumCarrotBlock("oxonium_carrots");
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
	public static final Block OXONIUM_BRICKS = new MineralBricks("oxonium_bricks", new Material(MapColor.BLUE), TextFormatting.DARK_BLUE, "You want to build ?");
	public static final Block ALLEMANITE_BRICKS = new MineralBricks("allemanite_bricks", new Material(MapColor.PINK), TextFormatting.RED, "Well go on...");
	public static final Block ENDERITE_BRICKS = new MineralBricks("enderite_bricks", new Material(MapColor.PURPLE), TextFormatting.DARK_PURPLE, "You will need this to relax after what you have been through");
	public static final Block FORGE_INTERFACE_BLOCK = new ForgeInterfaceBlock("forge_interface_block", Material.IRON);
	public static final Block FORGE_WALLS = new EnderiteForgeWalls("enderite_forge_walls", ModItems.BLOCK_MATERIAL_ENDERITE);
	public static final Block CUSTOM_FLOWER = new BlockCustomFlower("custom_flower");
	public static final Block CUSTOM_LOG = new CustomLog("custom_log");
	public static final Block CUSTOM_PLANK = new CustomPlanks("custom_plank", Material.WOOD);
	public static final Block CUSTOM_STAIRS = new AbstractStairs("custom_stairs", CUSTOM_PLANK.getDefaultState()); // .withProperty(CustomPlanks.VARIANT, CustomPlankVariant.SNOWY) TODO : for later
	public static final Block CUSTOM_FENCES = new AbstractFences("custom_fences", Material.WOOD, MapColor.LIGHT_BLUE);
	public static final Block CUSTOM_DOOR = new AbstractDoor("custom_door", Material.WOOD);
	public static final Block CUSTOM_LADDER = new AbstractLadder("custom_ladder", Material.WOOD); // .withProperty(CustomPlanks.VARIANT, CustomPlankVariant.SNOWY) TODO : for later
	public static final Block CUSTOM_PRESSURE_PLATE = new AbstractPressurePlate("custom_pressure_plate", Material.WOOD); // .withProperty(CustomPlanks.VARIANT, CustomPlankVariant.SNOWY) TODO : for later
	public static final Block CUSTOM_LEAVES = new CustomLeaves("custom_leaves");
	public static final Block CUSTOM_SAPLINGS = new CustomBlockSapling("custom_sapling", Material.PLANTS);
	public static final Block LANTERN = new BlockLantern("lantern");
	public static final Block CONNECTED_BLOCK = new ConnectedBlock("connected_block", Material.IRON);
	public static final Block COOKIE_JAR = new CookieJarBlock("cookie_jar");
	public static final Block BEAM_BLOCK = new BlockBeam("block_beam");
	public static final Block TROPHY_BLOCK = new BlockTrophy("trophy_block", 0);
	public static final Block BRAVE_TROPHY = new BlockTrophy("brave_trophy", 1);
	public static final Block FIREFIGHTER_TROPHY = new BlockTrophy("firefighter_trophy", 2);
	public static final Block EYE_TROPHY = new BlockTrophy("eye_trophy", 3);
	public static final Block TALENT_TROPHY = new BlockTrophy("talent_trophy", 4);
	public static final Block PORTAL_FRAMES = new DimensionPortal("dim_portal");
	public static final Block FAKE_LIGHT_BLOCK = new FakeLightBlock("fake_light_block");

}