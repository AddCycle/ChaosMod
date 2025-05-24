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
public class CustomFarmerProfession extends VillagerProfession {
	public static VillagerCareer CAREER;

	public CustomFarmerProfession()
    {
		super("chaosmod:custom_profession", "chaosmod:textures/entity/villager/viking_blacksmith.png", "chaosmod:textures/entity/zombie_villager/viking_zombie.png");
		this.initCareer();
		System.out.println("Profession init");
		// new VillagerCareer(this, "custom_profession").addTrade(1, new CustomFarmerProfession.TradesLevel1());
    }
	
	public void initCareer() {
        CAREER = new VillagerCareer(this, "custom_career")
            .addTrade(1, new TradesLevel1());
    }

	public static class TradesLevel1 implements ITradeList
	{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
			if(merchant instanceof Entity)// ((Entity) merchant).dimension == MathsDims.Sirojus.getId())
			{
				for(int i = 0; i < 4; ++i)
				{
					for (int j = 0; j < 4; ++j) {
						recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6 + random.nextInt(8)), new ItemStack(ModBlocks.CUSTOM_FLOWER, 16, j)));
					}
					for (int j = 0; j < 4; ++j) {
						recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6 + random.nextInt(8)), new ItemStack(ModBlocks.CUSTOM_LOG, 16, j)));
					}
				}

				recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 4 + random.nextInt(3)), new ItemStack(ModItems.OXONIUM_BOOTS)));
				recipeList.add(new MerchantRecipe(new ItemStack(ModBlocks.CUSTOM_FLOWER, 15 + random.nextInt(6)), new ItemStack(ModBlocks.BOSS_ALTAR)));

			}
		}
	}
}