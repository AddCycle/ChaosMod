package net.chaos.chaosmod.villagers;

import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
public class CustomBlacksmithProfession extends VillagerProfession {
	public static VillagerCareer CAREER;

	public CustomBlacksmithProfession()
    {
		super("chaosmod:custom_blacksmith_profession", "chaosmod:textures/entity/villager/viking_blacksmith.png", "chaosmod:textures/entity/zombie_villager/viking_zombie.png");
		this.initCareer();
    }
	
	public void initCareer() {
        CAREER = new VillagerCareer(this, "custom_blacksmith_career")
            .addTrade(1, new TradesLevel1()); // 0 in game if you try to spawn it with /summon
    }

	public static class TradesLevel1 implements ITradeList
	{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
			if(merchant instanceof Entity)
			{
				for (int j = 0; j < 4; ++j) {
					recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6 + random.nextInt(8)), new ItemStack(ModBlocks.CUSTOM_FLOWER, 16, j)));
				}
				for (int j = 0; j < 4; ++j) {
					recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6 + random.nextInt(8)), new ItemStack(ModBlocks.CUSTOM_LOG, 16, j)));
				}

				recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 10 + random.nextInt(11)), new ItemStack(ModItems.OXONIUM_NECKLACE)));
				recipeList.add(new MerchantRecipe(new ItemStack(ModBlocks.CUSTOM_FLOWER, 15 + random.nextInt(6)), new ItemStack(ModBlocks.BOSS_ALTAR)));

			}
		}
	}
}