package net.chaos.chaosmod.compatibility.mathsmod;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import util.Reference;

public class MathsModCompatibility {
	// Look at util.RegistryHandler
	public static void registerSampleRecipe() {
	    if (Loader.isModLoaded(Reference.MATHSMOD)) {
	    	Item itemJRP = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Reference.MATHSMOD, "kurayum_ingot"));
	    	if (itemJRP != null) {
	    		ShapedOreRecipe recipe = (ShapedOreRecipe) new ShapedOreRecipe(
	    				new ResourceLocation(Reference.MODID, "collab_recipe1"), // registry name
	    				new ItemStack(ModItems.CHAOS_HEART), // result
	    				" I ", // pattern
	    				"ICI",
	    				" I ",
	    				'I', itemJRP,
	    				'C', new ItemStack(ModItems.ENDERITE_INGOT, 3)
	    				).setRegistryName(new ResourceLocation(Reference.MODID, "collab_recipe1"));
	    		ForgeRegistries.RECIPES.register(recipe);
	    	}
	    }
	}
}