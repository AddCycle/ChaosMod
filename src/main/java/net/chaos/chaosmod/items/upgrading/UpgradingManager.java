package net.chaos.chaosmod.items.upgrading;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.inventory.InventoryUpgrading;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * FIXME : be careful to save the NBT of the stack in order to preserve enchants & durability
 */
public class UpgradingManager {
	private static final List<IUpgradingRecipe> REGISTRY = Lists.newArrayList();
	
	// FIXME : make a delegate class to register recipes on mod init
	// TODO : add necklaces too
	static {
		addGenericToolRecipes(RecipeInputOutput.list(
			RecipeInputOutput.of(Items.DIAMOND_HELMET, ModItems.OXONIUM_HELMET),
			RecipeInputOutput.of(Items.DIAMOND_CHESTPLATE, ModItems.OXONIUM_CHESTPLATE),
			RecipeInputOutput.of(Items.DIAMOND_LEGGINGS, ModItems.OXONIUM_LEGGINGS),
			RecipeInputOutput.of(Items.DIAMOND_BOOTS, ModItems.OXONIUM_BOOTS),
			RecipeInputOutput.of(Items.DIAMOND_SWORD, ModItems.OXONIUM_SWORD),
			RecipeInputOutput.of(Items.DIAMOND_PICKAXE, ModItems.OXONIUM_PICKAXE),
			RecipeInputOutput.of(Items.DIAMOND_AXE, ModItems.OXONIUM_AXE),
			RecipeInputOutput.of(Items.DIAMOND_SHOVEL, ModItems.OXONIUM_SHOVEL),
			RecipeInputOutput.of(Items.DIAMOND_HOE, ModItems.OXONIUM_HOE)
		), ModItems.OXONIUM_INGOT);
		
		addGenericToolRecipes(RecipeInputOutput.list(
				RecipeInputOutput.of(ModItems.OXONIUM_HELMET, ModItems.ALLEMANITE_HELMET),
				RecipeInputOutput.of(ModItems.OXONIUM_CHESTPLATE, ModItems.ALLEMANITE_CHESTPLATE),
				RecipeInputOutput.of(ModItems.OXONIUM_LEGGINGS, ModItems.ALLEMANITE_LEGGINGS),
				RecipeInputOutput.of(ModItems.OXONIUM_BOOTS, ModItems.ALLEMANITE_BOOTS),
				RecipeInputOutput.of(ModItems.OXONIUM_SWORD, ModItems.ALLEMANITE_SWORD),
				RecipeInputOutput.of(ModItems.OXONIUM_PICKAXE, ModItems.ALLEMANITE_PICKAXE),
				RecipeInputOutput.of(ModItems.OXONIUM_AXE, ModItems.ALLEMANITE_AXE),
				RecipeInputOutput.of(ModItems.OXONIUM_SHOVEL, ModItems.ALLEMANITE_SHOVEL),
				RecipeInputOutput.of(ModItems.OXONIUM_HOE, ModItems.ALLEMANITE_HOE)
			), ModItems.ALLEMANITE_INGOT);

		addGenericToolRecipes(RecipeInputOutput.list(
				RecipeInputOutput.of(ModItems.ALLEMANITE_HELMET, ModItems.ENDERITE_HELMET),
				RecipeInputOutput.of(ModItems.ALLEMANITE_CHESTPLATE, ModItems.ENDERITE_CHESTPLATE),
				RecipeInputOutput.of(ModItems.ALLEMANITE_LEGGINGS, ModItems.ENDERITE_LEGGINGS),
				RecipeInputOutput.of(ModItems.ALLEMANITE_BOOTS, ModItems.ENDERITE_BOOTS),
				RecipeInputOutput.of(ModItems.ALLEMANITE_SWORD, ModItems.ENDERITE_SWORD),
				RecipeInputOutput.of(ModItems.ALLEMANITE_PICKAXE, ModItems.ENDERITE_PICKAXE),
				RecipeInputOutput.of(ModItems.ALLEMANITE_AXE, ModItems.ENDERITE_AXE),
				RecipeInputOutput.of(ModItems.ALLEMANITE_SHOVEL, ModItems.ENDERITE_SHOVEL),
				RecipeInputOutput.of(ModItems.ALLEMANITE_HOE, ModItems.ENDERITE_HOE)
			), ModItems.ENDERITE_INGOT);
	}

    @Nullable
    public static IUpgradingRecipe findMatchingRecipe(InventoryUpgrading inventory, World worldIn)
    {
        for (IUpgradingRecipe irecipe : REGISTRY)
        {
            if (irecipe.matches(inventory, worldIn))
            {
                return irecipe;
            }
        }

        return null;
    }
    
    public static NonNullList<ItemStack> getRemainingItems(InventoryUpgrading inventory, World worldIn)
    {
        for (IUpgradingRecipe irecipe : REGISTRY)
        {
            if (irecipe.matches(inventory, worldIn))
            {
                return irecipe.getRemainingItems(inventory);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            nonnulllist.set(i, inventory.getStackInSlot(i));
        }

        return nonnulllist;
    }
    
    public static void addGenericToolRecipes(List<RecipeInputOutput> pieces, Item mineral) {
    	for (RecipeInputOutput r : pieces) {
    		addRecipe(r.input, mineral, r.output);
    	}
    }
    
    public static IUpgradingRecipe addRecipe(ItemStack input1, ItemStack input2, ItemStack output) {
    	SimpleUpgradingRecipe recipe = new SimpleUpgradingRecipe(input1, input2, output);
    	register(recipe);
    	return recipe;
    }

    public static IUpgradingRecipe addRecipe(Item input1, Item input2, Item output) {
    	return addRecipe(new ItemStack(input1), new ItemStack(input2), new ItemStack(output));
    }

    public static void register(IUpgradingRecipe recipe) {
    	REGISTRY.add(recipe);
    }
    
    static class RecipeInputOutput {
    	public Item input;
    	public Item output;

		public RecipeInputOutput(Item input, Item output) {
			this.input = input;
			this.output = output;
		}
		
		public static RecipeInputOutput of(Item input, Item output) {
			return new RecipeInputOutput(input, output);
		}
		
		public static List<RecipeInputOutput> list(RecipeInputOutput ...recipes) {
			List<RecipeInputOutput> list = new ArrayList<>();
			for (RecipeInputOutput r : recipes) {
				list.add(r);
			}
			return list;
		}
    }
    
    // TODO : later-on to simplify the registry
    static class ArmorSet {
    	
    }
}