package util.handlers;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.world.ModWorldGen;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
	
	@EventHandler
	public static void onSmeltingRegister() {
		GameRegistry.addSmelting(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM), 20);
		GameRegistry.addSmelting(ModBlocks.ALLEMANITE_ORE, new ItemStack(ModItems.ALLEMANITE_INGOT), 100);
	}
}
