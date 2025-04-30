package net.chaos.chaosmod.items.tools;

import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class AllemaniteSword extends ToolSword {
	public Random rand;

	public AllemaniteSword(String name, ToolMaterial material) {
		super(name, material);
		rand = new Random();
		this.setNoRepair();
		this.setMaxDamage(4000);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentTranslation("item.allemanite_sword.tooltip", new Style().setColor(TextFormatting.RED)).getFormattedText());
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		World world_pl = player.getEntityWorld();
		World world_en = entity.getEntityWorld();
		if (world_pl == world_en && !world_en.isRemote && !world_pl.isRemote) {
			entity.setFire(rand.nextInt(3) + 1); // between 1 & 3 seconds
			/*player.attackEntityFrom(new DamageSource("source.custom.allemanite_sword") {
				@Override
				public boolean canHarmInCreative() {
					return true;
				}
				
				@Override
				public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
					return new TextComponentTranslation("item.allemanite_sword.death_message1", new Style().setColor(TextFormatting.RED));
				}
			}, 0.1f);*/
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

}
