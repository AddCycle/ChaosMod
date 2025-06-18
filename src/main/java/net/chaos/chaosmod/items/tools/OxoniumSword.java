package net.chaos.chaosmod.items.tools;

import java.util.Random;

import net.chaos.chaosmod.client.particles.CoinParticles;
import net.chaos.chaosmod.client.particles.ColoredSweepParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class OxoniumSword extends ToolSword {

	public OxoniumSword(String name, ToolMaterial material) {
		super(name, material);
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		// TODO Auto-generated method stub
		return super.onDroppedByPlayer(item, player);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		// spawnSweepParticles(player.getEntityWorld(), player);
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (entityLiving instanceof EntityPlayer) spawnSweepParticles(entityLiving.getEntityWorld(), (EntityPlayer)entityLiving);
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		spawnCoinParticles(worldIn, playerIn);
		playerIn.swingArm(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	public void spawnSweepParticles(World worldIn, EntityPlayer playerIn)
    {
		Random random = new Random();
        double d0 = (double)(-MathHelper.sin(playerIn.rotationYaw * 0.017453292F));
        double d1 = (double)MathHelper.cos(playerIn.rotationYaw * 0.017453292F);
        
        Minecraft mc = Minecraft.getMinecraft();
        
        if (worldIn.isRemote) mc.effectRenderer.addEffect(
        	new ColoredSweepParticle(mc.getTextureManager(), worldIn, playerIn.posX + d0, playerIn.posY + (double)playerIn.height * 0.5D, playerIn.posZ + d1, d0, 0.0D, d1, random.nextInt(0xffffff + 1)));

        // ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, playerIn.posX + d0, playerIn.posY + (double)playerIn.height * 0.5D, playerIn.posZ + d1, 0, d0, 0.0D, d1, 0.0D);
    }

	public void spawnCoinParticles(World worldIn, EntityPlayer playerIn)
    {
        double d0 = (double)(-MathHelper.sin(playerIn.rotationYaw * 0.017453292F));
        double d1 = (double)MathHelper.cos(playerIn.rotationYaw * 0.017453292F);
        
        Minecraft mc = Minecraft.getMinecraft();
        
        if (worldIn.isRemote) mc.effectRenderer.addEffect(
        	new CoinParticles(mc.getTextureManager(), worldIn, playerIn.posX + d0, playerIn.posY + (double)playerIn.height * 0.5D, playerIn.posZ + d1, d0, 0.0D, d1));

        // ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, playerIn.posX + d0, playerIn.posY + (double)playerIn.height * 0.5D, playerIn.posZ + d1, 0, d0, 0.0D, d1, 0.0D);
    }

}
