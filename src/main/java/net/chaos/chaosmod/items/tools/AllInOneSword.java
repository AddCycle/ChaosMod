package net.chaos.chaosmod.items.tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

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

}
