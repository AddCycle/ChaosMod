package net.chaos.chaosmod.items;

import net.chaos.chaosmod.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class AbstractItemMetadatas extends ItemBase {

	public AbstractItemMetadatas(String name) {
		super(name);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedNameInefficiently(ItemStack stack) {
		int meta = this.getMetadata(stack);
        switch (meta) {
            case 0: return super.getUnlocalizedName() + ".1000";
            case 1: return super.getUnlocalizedName() + ".2000";
            case 2: return super.getUnlocalizedName() + ".5000";
            default: return super.getUnlocalizedName();
        }
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
	        items.add(new ItemStack(this, 1, 0)); // 1000
	        items.add(new ItemStack(this, 1, 1)); // 2000
	        items.add(new ItemStack(this, 1, 2)); // 5000
	    }
	}
	
	@Override
	public void registerModels() {
		String reg_name = this.getRegistryName().getResourcePath();
		Main.proxy.registerVariantRenderer(this, 0, reg_name + "_1000", "inventory");
		Main.proxy.registerVariantRenderer(this, 1, reg_name + "_2000", "inventory");
		Main.proxy.registerVariantRenderer(this, 2, reg_name + "_5000", "inventory");
	}
}