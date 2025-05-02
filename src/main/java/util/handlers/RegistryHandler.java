package util.handlers;

import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.recipes.CustomSmeltingRegistry;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import util.IHasModel;

// Pour l'etape de build du mod
@EventBusSubscriber
public class RegistryHandler {
	public static final Item OXONIUM_FURNACE = new ItemBlock(ModBlocks.OXONIUM_FURNACE);
	
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
    public static void onBiomeRegister(RegistryEvent.Register<Biome> event) {
        ModBiomes.registerBiomes(event);
    }
	
	public static void fastRender(Item item) {
		fastRender(item, 0);
	}
	
	public static void fastRender(Item item, int meta) {
		
	}
	
	
	public static void renderItems() {
		fastRender(OXONIUM_FURNACE);
	}

	@EventHandler
	public static void otherRegistries() {
	}
	
	public static void onSmeltingRegister() {
		// To be able to smelt the modded items only in custom furnace oxonium or >higher
		GameRegistry.addSmelting(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM_INGOT), 0.2f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM_INGOT), 0.4f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.ALLEMANITE_ORE, new ItemStack(ModItems.ALLEMANITE_INGOT), 0.8f);
		CustomSmeltingRegistry.addSmelting(ModBlocks.ENDERITE_ORE, new ItemStack(ModItems.ENDERITE_INGOT), 1.2f);
	}
}
