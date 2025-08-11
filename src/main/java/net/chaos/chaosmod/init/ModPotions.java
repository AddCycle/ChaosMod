package net.chaos.chaosmod.init;

import net.chaos.chaosmod.potion.BrewingRecipeVikingPotion;
import net.chaos.chaosmod.potion.PotionVikingFriend;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class ModPotions {
    public static final Potion POTION_VIKING = new PotionVikingFriend(false, 0x0000ff);

    // brewing recipes
    public static final IBrewingRecipe POTION_VIKING_RECIPE = new BrewingRecipeVikingPotion();
}
