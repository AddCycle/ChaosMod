package net.chaos.chaosmod.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AllemaniteArmor extends ArmorBase {
	EntityEquipmentSlot slot;

	public AllemaniteArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot, ModelBiped biped)
	{
		if(stack != ItemStack.EMPTY)
		{
			if(stack.getItem() instanceof ItemArmor)
			{
				ModelCustomArmour armourModel = new ModelCustomArmour(slot);
				
				armourModel.isSneak = biped.isSneak;
				armourModel.isRiding = biped.isRiding;
				armourModel.isChild = biped.isChild;
				armourModel.rightArmPose = biped.rightArmPose;
				armourModel.leftArmPose = biped.leftArmPose;
				
				return armourModel;
			}
		}
		
		return null;
	}
}
