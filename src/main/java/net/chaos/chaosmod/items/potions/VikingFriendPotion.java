package net.chaos.chaosmod.items.potions;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.IHasModel;

public class VikingFriendPotion extends ItemPotion implements IHasModel {
	
	public VikingFriendPotion(String name) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);

		ModItems.ITEMS.add(this);
	}

    @SideOnly(Side.CLIENT)
    public ItemStack getDefaultInstance()
    {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), PotionTypes.WATER);
    }

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
