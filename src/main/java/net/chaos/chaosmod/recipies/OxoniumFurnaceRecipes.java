package net.chaos.chaosmod.recipies;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class OxoniumFurnaceRecipes {

    private static final OxoniumFurnaceRecipes OXONIUM_SMELTING_INSTANCE = new OxoniumFurnaceRecipes();
    private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static OxoniumFurnaceRecipes instance()
    {
        return OXONIUM_SMELTING_INSTANCE;
    }

    private OxoniumFurnaceRecipes()
    {
        for (ItemFishFood.FishType itemfishfood$fishtype : ItemFishFood.FishType.values())
        {
            if (itemfishfood$fishtype.canCook())
            {
                this.addSmeltingRecipe(new ItemStack(Items.FISH, 1, itemfishfood$fishtype.getMetadata()), new ItemStack(Items.COOKED_FISH, 1, itemfishfood$fishtype.getMetadata()), 0.35F);
            }
        }

        this.addSmeltingRecipeForBlock(Blocks.COAL_ORE, new ItemStack(Items.COAL), 0.1F);
        this.addSmeltingRecipeForBlock(Blocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE), 0.7F);
        this.addSmeltingRecipeForBlock(Blocks.LAPIS_ORE, new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
        this.addSmeltingRecipeForBlock(Blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2F);
        this.addSmelting(Items.CHAINMAIL_HELMET, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.CHAINMAIL_CHESTPLATE, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.CHAINMAIL_LEGGINGS, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.CHAINMAIL_BOOTS, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_PICKAXE, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_SHOVEL, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_AXE, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_HOE, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_SWORD, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_HELMET, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_CHESTPLATE, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_LEGGINGS, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_BOOTS, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.IRON_HORSE_ARMOR, new ItemStack(Items.IRON_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_PICKAXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_SHOVEL, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_AXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_HOE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_SWORD, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_HELMET, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_CHESTPLATE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_LEGGINGS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_BOOTS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmelting(Items.GOLDEN_HORSE_ARMOR, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.WHITE.getMetadata()), new ItemStack(Blocks.WHITE_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.ORANGE.getMetadata()), new ItemStack(Blocks.ORANGE_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.MAGENTA.getMetadata()), new ItemStack(Blocks.MAGENTA_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIGHT_BLUE.getMetadata()), new ItemStack(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.YELLOW.getMetadata()), new ItemStack(Blocks.YELLOW_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIME.getMetadata()), new ItemStack(Blocks.LIME_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PINK.getMetadata()), new ItemStack(Blocks.PINK_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GRAY.getMetadata()), new ItemStack(Blocks.GRAY_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.SILVER.getMetadata()), new ItemStack(Blocks.SILVER_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.CYAN.getMetadata()), new ItemStack(Blocks.CYAN_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PURPLE.getMetadata()), new ItemStack(Blocks.PURPLE_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLUE.getMetadata()), new ItemStack(Blocks.BLUE_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BROWN.getMetadata()), new ItemStack(Blocks.BROWN_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GREEN.getMetadata()), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.RED.getMetadata()), new ItemStack(Blocks.RED_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata()), new ItemStack(Blocks.BLACK_GLAZED_TERRACOTTA), 0.1F);
        this.addSmeltingRecipeForBlock(ModBlocks.OXONIUM_ORE, new ItemStack(ModItems.OXONIUM), 0.4f);
        this.addSmeltingRecipeForBlock(ModBlocks.ALLEMANITE_ORE, new ItemStack(ModItems.ALLEMANITE_INGOT), 0.8f); // l'exp ne marche pas pour celui-la
        // Je pense que c'est parce que je ne le mets pas dans le GameRegistry, or je vais voir comment creer mon propre registry je pense ou alors comment manuellement donner l'exp a mon joueur
    }

    public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
    {
        this.addSmelting(Item.getItemFromBlock(input), stack, experience);
    }

    public void addSmelting(Item input, ItemStack stack, float experience)
    {
        this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
    }

    public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience)
    {
        if (getSmeltingResult(input) != ItemStack.EMPTY) { net.minecraftforge.fml.common.FMLLog.log.info("Ignored smelting recipe with conflicting input: {} = {}", input, stack); return; }
        this.smeltingList.put(input, stack);
        this.experienceList.put(stack, Float.valueOf(experience));
    }

    public ItemStack getSmeltingResult(ItemStack stack)
    {
        for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getSmeltingList()
    {
        return this.smeltingList;
    }

    public float getSmeltingExperience(ItemStack stack)
    {
        // float ret = stack.getItem().getSmeltingExperience(stack);
        // if (ret != -1) return ret;

        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return ((Float)entry.getValue()).floatValue();
            }
        }

        return 0.0F;
    }
}
