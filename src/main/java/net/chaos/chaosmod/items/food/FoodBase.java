package net.chaos.chaosmod.items.food;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
//import net.minecraft.potion.PotionEffect;
import util.IHasModel;

public class FoodBase extends ItemFood implements IHasModel {

	public FoodBase(String name, int amount, float saturation, boolean isAnimalFood) {
		super(amount, saturation, isAnimalFood);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.FOOD);
		setAlwaysEdible(); // meme plus faim
		//setPotionEffect(PotionEffect.readCustomPotionEffectFromNBT(), saturation)
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.FOOD, ModTabs.GENERAL_TAB }; // You can add other tabs
    }
}
