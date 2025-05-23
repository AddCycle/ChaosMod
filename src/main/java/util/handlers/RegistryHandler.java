package util.handlers;

import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.init.ModPotionTypes;
import net.chaos.chaosmod.init.ModPotions;
import net.chaos.chaosmod.init.ModSounds;
import net.chaos.chaosmod.recipes.CustomSmeltingRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import util.IHasModel;
import util.Reference;

// Pour l'etape de build du mod
@EventBusSubscriber
public class RegistryHandler {
	
	// S'occupe d'enregistrer tous les items definis dans ItemBase.java
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}

	// S'occupe d'enregistrer tous les blocks definis dans BlockBase.java
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	// En gros cette partie sert a grouper plusieurs items/blocks dans une meme interface qui est simplement un groupe d'objets
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for (Item item : ModItems.ITEMS) {
			if (item instanceof IHasModel) {
				((IHasModel)item).registerModels();
			}
		}

		for (Block block : ModBlocks.BLOCKS) {
			if (block instanceof IHasModel) {
				((IHasModel)block).registerModels();
			}
		}
			
	}
	
	@SubscribeEvent
	public static void onPotionRegister(RegistryEvent.Register<Potion> event) {
		event.getRegistry().register(ModPotions.POTION_VIKING); // registerAll() later
	}
	
	@SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
        event.getRegistry().register(ModPotionTypes.VIKING_FRIEND_TYPE);
    }
	
	@SubscribeEvent
    public static void onBiomeRegister(RegistryEvent.Register<Biome> event) {
        ModBiomes.registerBiomes();
        event.getRegistry().registerAll(ModBiomes.BIOMES.toArray(new Biome[0]));
    }
	
	/*public static void fastRender(Item item) {
		fastRender(item, 0);
	}
	
	public static void fastRender(Item item, int meta) {
		
	}*/

	@SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		System.out.println("Registering custom recipes");
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
	        ResourceLocation recipeID = new ResourceLocation("chaosmod", "forge_wall_recipe_" + dmg);
	        recipe.setRegistryName(recipeID);
	        registry.register(recipe);
	    }
	    
	    if (Loader.isModLoaded("mathsmod")) {
	        Item JeanRobertPerezItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mathsmod", "kurayum_ingot"));
	        if (JeanRobertPerezItem != null) {
	        	ShapedOreRecipe recipe = new ShapedOreRecipe(
	                    new ResourceLocation("chaosmod", "collab_recipe1"), // registry name
	                    new ItemStack(ModItems.CHAOS_HEART), // result
	                    " I ", "ICI", " I ",
	                    'I', JeanRobertPerezItem,
	                    'C', new ItemStack(ModItems.ENDERITE_INGOT, 3)
	                );
	                recipe.setRegistryName(new ResourceLocation("chaosmod", "collab_recipe1"));
	                ForgeRegistries.RECIPES.register(recipe);
	        }
	    }
	}
	
	/*@SubscribeEvent
	public void onSoundRegister(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(
				ModSounds.HIGHEST_OP,
				ModSounds.PUTIN_WIDE_WALK
			);
	}*/
	
	public static void onSmeltingRegister() {
		// To be able to smelt the modded items only in custom furnace oxonium or >higher
		GameRegistry.addSmelting(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM_INGOT), 0.2f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM_INGOT), 0.4f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.ALLEMANITE_ORE, new ItemStack(ModItems.ALLEMANITE_INGOT), 0.8f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.ENDERITE_ORE, new ItemStack(ModItems.ENDERITE_INGOT), 1.2f);
	}
}
