package net.chaos.chaosmod.items.tools;

import com.google.common.collect.Multimap;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
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
	
	/*@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

	    if (!worldIn.isRemote) {
	        // Set Charged = true
	        NBTTagCompound root = stack.getTagCompound();
	        if (root == null) {
	            root = new NBTTagCompound();
	            stack.setTagCompound(root);
	        }
	        NBTTagCompound chaosData = root.getCompoundTag("ChaosData");
	        chaosData.setBoolean("Charged", true);
	        root.setTag("ChaosData", chaosData);

	        // Send packet to client
	        Main.network.sendTo(
	            new net.chaos.chaosmod.network.PacketSyncItemNBT(playerIn.inventory.currentItem, stack.getTagCompound()),
	            (EntityPlayerMP) playerIn
	        );

	        // Notify player
	        playerIn.sendMessage(new TextComponentString("Your weapon is now charged!"));
	    }

	    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}*/
	
	/*@Override
	public boolean hasEffect(ItemStack stack) {
		System.out.println("hasEffect re-evaluated: " + stack); // Debug
	    NBTTagCompound tag = stack.getSubCompound("ChaosData");
	    return tag != null && tag.getBoolean("Charged");
	}*/
	
	/*@Override
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
	}*/
	
	/*@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		NBTTagCompound tag = stack.getSubCompound("ChaosData");
	    if (tag != null && tag.getBoolean("Charged")) {
	        // Apply extra damage
	        target.attackEntityFrom(DamageSource.causeMobDamage(attacker), target.getMaxHealth() * 0.2f);

	        // Clear charged tag
	        tag.setBoolean("Charged", false);

	        if (attacker instanceof EntityPlayerMP) {
	            EntityPlayerMP playerMP = (EntityPlayerMP) attacker;
	            int slot = playerMP.inventory.currentItem;

	            // Remove and re-set the item to force visual re-equip
	            playerMP.inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
	            playerMP.inventory.setInventorySlotContents(slot, stack);

	            // Ensure the client gets this change
	            playerMP.connection.sendPacket(new net.minecraft.network.play.server.SPacketSetSlot(
	                0, // Player inventory window
	                slot + 36, // Slot index (hotbar starts at 36)
	                stack
	            ));
	        }
	    }

	    return super.hitEntity(stack, target, attacker);
	}*/
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true; // FIXME : temporary disable until doing a custom layer model to replace the enchantement layer for the charge
	}

}
