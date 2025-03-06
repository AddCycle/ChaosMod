package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.items.armor.ArmorBase;
import net.chaos.chaosmod.items.food.FoodEffectBase;
import net.chaos.chaosmod.items.tools.ToolAxe;
import net.chaos.chaosmod.items.tools.ToolHoe;
import net.chaos.chaosmod.items.tools.ToolPickaxe;
import net.chaos.chaosmod.items.tools.ToolSpade;
import net.chaos.chaosmod.items.tools.ToolSword;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;
import util.Reference;

//initialize the blocks and declare them based on a vanilla block base type
public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final ToolMaterial MATERIAL_OXONIUM = EnumHelper.addToolMaterial("material_oxonium", 2, 250, 6.0F, 2.0F, 14);
	public static final ArmorMaterial ARMOR_MATERIAL_OXONIUM = EnumHelper.addArmorMaterial("armor_material_oxonium", Reference.MODID + ":oxonium", 14,
			new int[] { 2, 5, 7, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f);
	
	//Items
	public static final Item OXONIUM = new ItemBase("oxonium");
	
	//Tools
	public static final ItemSword OXONIUM_SWORD = new ToolSword("oxonium_sword", MATERIAL_OXONIUM);
	public static final ItemPickaxe OXONIUM_PICKAXE = new ToolPickaxe("oxonium_pickaxe", MATERIAL_OXONIUM);
	public static final ItemAxe OXONIUM_AXE = new ToolAxe("oxonium_axe", MATERIAL_OXONIUM);
	public static final ItemSpade OXONIUM_SHOVEL = new ToolSpade("oxonium_shovel", MATERIAL_OXONIUM);
	public static final ItemHoe OXONIUM_HOE = new ToolHoe("oxonium_hoe", MATERIAL_OXONIUM);
	
	//Armor
	public static final Item OXONIUM_HELMET = new ArmorBase("oxonium_helmet", ARMOR_MATERIAL_OXONIUM, 1, EntityEquipmentSlot.HEAD);
	public static final Item OXONIUM_CHESTPLATE = new ArmorBase("oxonium_chestplate", ARMOR_MATERIAL_OXONIUM, 1, EntityEquipmentSlot.CHEST);
	public static final Item OXONIUM_LEGGINGS = new ArmorBase("oxonium_leggings", ARMOR_MATERIAL_OXONIUM, 2, EntityEquipmentSlot.LEGS);
	public static final Item OXONIUM_BOOTS = new ArmorBase("oxonium_boots", ARMOR_MATERIAL_OXONIUM, 1, EntityEquipmentSlot.FEET);
	
	//Food
	//public static final Item OXONIUM_CARROT = new FoodBase("oxonium_carrot", 3, 3.0f, false);
	public static final Item OXONIUM_CARROT = new FoodEffectBase("oxonium_carrot", 3, 3.0f, false, new PotionEffect(MobEffects.RESISTANCE, 60*20, 1, false, true));
}