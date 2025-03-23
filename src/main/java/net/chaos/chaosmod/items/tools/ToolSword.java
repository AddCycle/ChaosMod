package net.chaos.chaosmod.items.tools;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.particles.CustomSwingParticle;
import net.chaos.chaosmod.client.particles.CustomSwingParticle.Factory;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEmitter;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import util.IHasModel;

public class ToolSword extends ItemSword implements IHasModel {
	
	// Unexpectedly working it's a miracle thanks to the forums : to set into vanilla tabs Combat and another tab an item
	// Because it's a nullable method i think it's working fine YAAAY !!!

	public ToolSword(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		entityLiving.setGlowing(true);
		entityLiving.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entityLiving.posX, entityLiving.posY, entityLiving.posZ, 1.0D, 1.0D, 1.0D, null);
		// RenderGlobal renderpart = new RenderGlobal(Minecraft.getMinecraft());
		/*ParticleManager manager = new ParticleManager(entityLiving.getEntityWorld(), Minecraft.getMinecraft().getTextureManager());
		manager.spawnEffectParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), entityLiving.getPosition().getX(), entityLiving.getPosition().getY(), entityLiving.getPosition().getZ(), 0.0D, 0.0D, 0.0D);
		entityLiving.getEntityWorld().spawnParticle(EnumParticleTypes.SWEEP_ATTACK, true, entityLiving.getPitchYaw().x, entityLiving.getPitchYaw().y, entityLiving.getPosition().getZ(), 0.0D, 1.0D, 0.0D, null);*/
		//return super.onEntitySwing(entityLiving, stack);
		return false;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		/*CustomSwingParticle.Factory factory = new Factory();
		factory.createParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), player.getEntityWorld(), entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ(), 0.0D, 0.0D, 0.0D, null);
		ParticleManager manager = new ParticleManager(player.getEntityWorld(), Minecraft.getMinecraft().getTextureManager());
		manager.registerParticle(100, factory);*/
		// player.getEntityWorld().spawnParticle(EnumParticleTypes.SWEEP_ATTACK, true, player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks()).x + 1, player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks()).y + 2, player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks()).z, 0.0D, 1.0D, 0.0D, null);
		/*ParticleManager manager = new ParticleManager(player.getEntityWorld(), Minecraft.getMinecraft().getTextureManager());
		manager.registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new CustomSwingParticle.Factory());
		manager.spawnEffectParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 1, 1, 1, null);*/
		player.getEntityWorld().spawnAlwaysVisibleParticle(100, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 1.0D, 1.0D, 1.0D, null);
		entity.setDead();
		entity.onUpdate();

		// manager.emitParticleAtEntity(player, EnumParticleTypes.SWEEP_ATTACK);
		// player.spawnSweepParticles();
		System.out.println(String.format("Looking at : %f %f %f",player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks()).x, player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks()).y, player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks()).z));
		System.out.println("TATANE");

		return false;
		// return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setGlowing(false);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.TOOLS, ModTabs.GENERAL_TAB }; // You can add other tabs
    }
}
