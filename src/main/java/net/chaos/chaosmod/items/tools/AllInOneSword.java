package net.chaos.chaosmod.items.tools;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class AllInOneSword extends ToolSword {

	public AllInOneSword(String name, ToolMaterial material) {
		super(name, material);
		this.setMaxDamage(-1);
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity,
			EntityLivingBase attacker) {
		return true;
	}
	
	@Override
	public float getAttackDamage() {
		return 15.0f; // slightly better than diamond since there is no netherite
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

	    // Apply your logic here
	    if (!worldIn.isRemote) {
	        // Apply custom tag
	        stack.getOrCreateSubCompound("ChaosData").setBoolean("Charged", true);
	        // Send feedback
	        playerIn.sendMessage(new TextComponentString("Your weapon is now charged!"));
	    }

	    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		NBTTagCompound tag = stack.getSubCompound("ChaosData");
	    return tag != null && tag.getBoolean("Charged");
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

	    if (slot == EntityEquipmentSlot.MAINHAND) {
	        NBTTagCompound tag = stack.getSubCompound("ChaosData");
	        if (tag != null && tag.getBoolean("Charged")) {
	            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
	                new AttributeModifier("Charged boost", 4.0, 0)); // +4 damage
	        }
	    }

	    return modifiers;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false; // FIXME : temporary disable until doing a custom layer model to replace the enchantement layer for the charge
	}

}
