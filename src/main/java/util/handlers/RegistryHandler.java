package util.handlers;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.CustomPlanks;
import net.chaos.chaosmod.compatibility.mathsmod.MathsModCompatibility;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModEnchants;
import net.chaos.chaosmod.init.ModFluidBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.init.ModPotionTypes;
import net.chaos.chaosmod.init.ModPotions;
import net.chaos.chaosmod.recipes.CustomSmeltingRegistry;
import net.chaos.chaosmod.villagers.CustomProfessions;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import proxy.IBlockModel;
import proxy.IItemModel;
import util.Reference;
import util.handlers.entity.RenderHandler;

@EventBusSubscriber
public class RegistryHandler {
	// Blocks before items corrects issue of : oxonium_carrots requires blocks at init
	static {
        // Ensures all items are initialized after blocks are created
        ModItems.initItems();
    }

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		event.getRegistry().registerAll(ModFluidBlocks.FLUID_BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	// This event is for registering models of items/blocks
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for (Block block : ModBlocks.BLOCKS) {
			if (block instanceof IBlockModel) {
				((IBlockModel)block).registerModels();
			}
		}

		for (Block block : ModFluidBlocks.FLUID_BLOCKS) {
			if (block instanceof IBlockModel) {
				((IBlockModel)block).registerModels();
			}
		}

		for (Item item : ModItems.ITEMS) {
			if (item instanceof IItemModel) {
				((IItemModel)item).registerModels();
			}
		}
		
        RenderHandler.registerCustomMeshesAndStates();
	}
	
	@SubscribeEvent
	public static void onPotionRegister(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll(ModPotions.POTION_VIKING);
	}
	
	@SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
        event.getRegistry().registerAll(ModPotionTypes.VIKING_FRIEND_TYPE);
    }
	
	@SubscribeEvent
    public static void onBiomeRegister(RegistryEvent.Register<Biome> event) {
        ModBiomes.registerBiomes();
        event.getRegistry().registerAll(ModBiomes.BIOMES.toArray(new Biome[0]));
    }

	@SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(ModEnchants.ENCHANTS.toArray(new Enchantment[0]));
    }

	@SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
	    IForgeRegistry<IRecipe> registry = event.getRegistry();

	    int maxDamage = (new ItemStack(ModItems.TINKERERS_HAMMER)).getMaxDamage();

	    for (int dmg = 0; dmg < maxDamage; dmg++) {
	        NonNullList<Ingredient> inputs = NonNullList.create();
	        inputs.add(Ingredient.fromStacks(new ItemStack(ModItems.ENDERITE_INGOT)));
	        inputs.add(Ingredient.fromStacks(new ItemStack(ModItems.TINKERERS_HAMMER, 1, dmg)));

	        ShapelessRecipes recipe = new ShapelessRecipes(
	            "chaosmod",
	            new ItemStack(ModBlocks.FORGE_WALLS, 4),
	            inputs
	        );

	        // Unique registry name per damage value
	        ResourceLocation recipeID = new ResourceLocation(Reference.MODID, "forge_wall_recipe_" + dmg);
	        recipe.setRegistryName(recipeID);
	        registry.register(recipe);
	    }
	    
	    /*
	     * Mathsmod adding a recipe
	     */
	    MathsModCompatibility.registerSampleRecipe();
	}
	
	@SubscribeEvent
	public void onVillagerProfessionRegister(RegistryEvent.Register<VillagerProfession> event) {
		Main.getLogger().info("REGISTERING VILLAGERS PROFESSIONS : ", event.getResult().name());
		event.getRegistry().registerAll(CustomProfessions.CUSTOM_VIKING_TRADER);
	}
	
	@SubscribeEvent
	public void onVanillaFuelRegister(FurnaceFuelBurnTimeEvent event) {
		Item item = event.getItemStack().getItem();
		if (item == Item.getItemFromBlock(ModBlocks.CUSTOM_SAPLINGS)) {
			event.setBurnTime(100);
		} else
		if (item == Item.getItemFromBlock(ModBlocks.CUSTOM_LEAVES)) {
			event.setBurnTime(100);
		} else
		if (item == Item.getItemFromBlock(ModBlocks.CUSTOM_DOOR)) {
			event.setBurnTime(200);
		} else
		if (item == Item.getItemFromBlock(ModBlocks.CUSTOM_PLANK)) {
			event.setBurnTime(300);
		} else
		if (item == Item.getItemFromBlock(ModBlocks.CUSTOM_LOG)) {
			event.setBurnTime(300);
		} else
		if (item == Item.getItemFromBlock(ModBlocks.CUSTOM_LADDER)) {
			event.setBurnTime(300);
		} else
		if (item == Item.getItemFromBlock(ModBlocks.SNOWY_STAIRS) ||
			item == Item.getItemFromBlock(ModBlocks.MAPLE_STAIRS) ||
			item == Item.getItemFromBlock(ModBlocks.ENDER_STAIRS) ||
			item == Item.getItemFromBlock(ModBlocks.OLIVE_STAIRS)) {
			event.setBurnTime(300);
		} else
		if (item == Item.getItemFromBlock(ModBlocks.SNOWY_FENCE) ||
			item == Item.getItemFromBlock(ModBlocks.MAPLE_FENCE) ||
			item == Item.getItemFromBlock(ModBlocks.ENDER_FENCE) ||
			item == Item.getItemFromBlock(ModBlocks.OLIVE_FENCE)) {
			event.setBurnTime(300);
		}
		if (item == Item.getItemFromBlock(ModBlocks.CUSTOM_PRESSURE_PLATE)) {
			event.setBurnTime(300);
		}
	}
	
	public static void onSmeltingRegister() {
		// To be able to smelt the modded items only in custom furnace oxonium or >higher
		GameRegistry.addSmelting(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM_INGOT), 0.2f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM_INGOT), 0.4f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.ALLEMANITE_ORE, new ItemStack(ModItems.ALLEMANITE_INGOT), 0.8f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.ENDERITE_ORE, new ItemStack(ModItems.ENDERITE_INGOT), 1.2f);
	}
	
	public static void onBrewingRecipeRegister() {
		registerPotion(ModBlocks.CUSTOM_FLOWER, ModPotionTypes.VIKING_FRIEND_TYPE);
	}
	
	public static void onTagsRegister() {
        for (CustomLog.CustomLogVariant variant : CustomLog.CustomLogVariant.values()) {
            OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.CUSTOM_LOG, 1, variant.getMeta()));
        }

        for (CustomPlanks.CustomPlankVariant variant : CustomPlanks.CustomPlankVariant.values()) {
            OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.CUSTOM_PLANK, 1, variant.getMeta()));
        }

        // custom wood items
        OreDictionary.registerOre("stairWood", ModBlocks.SNOWY_STAIRS);
        OreDictionary.registerOre("stairWood", ModBlocks.MAPLE_STAIRS);
        OreDictionary.registerOre("stairWood", ModBlocks.ENDER_STAIRS);
        OreDictionary.registerOre("stairWood", ModBlocks.OLIVE_STAIRS);
        OreDictionary.registerOre("doorWood", ModBlocks.CUSTOM_DOOR);
        OreDictionary.registerOre("fenceWood", ModBlocks.SNOWY_FENCE);
        OreDictionary.registerOre("fenceWood", ModBlocks.MAPLE_FENCE);
        OreDictionary.registerOre("fenceWood", ModBlocks.ENDER_FENCE);
        OreDictionary.registerOre("fenceWood", ModBlocks.OLIVE_FENCE);
        OreDictionary.registerOre("treeSapling", ModBlocks.CUSTOM_SAPLINGS);
        OreDictionary.registerOre("treeLeaves", ModBlocks.CUSTOM_LEAVES);

        // custom ores
        OreDictionary.registerOre("oreOxonium", ModBlocks.OXONIUM_ORE);
        OreDictionary.registerOre("oreAllemanite", ModBlocks.ALLEMANITE_ORE);
        OreDictionary.registerOre("oreEnderite", ModBlocks.ENDERITE_ORE);
	}
	
	public static void registerPotion(Item item, PotionType type) {
		registerPotion(item, type, 0);
	}

	public static void registerPotion(Block block, PotionType type) {
		registerPotion(block, type, 0);
	}

	public static void registerPotion(Item item, PotionType type, int data) {
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(item, 1, data), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), type));
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), type), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), type));
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), type), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), type));
	}

	public static void registerPotion(Block block, PotionType type, int data) {
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(block, 1, data), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), type));
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), type), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), type));
		BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), type), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), type));
	}
}