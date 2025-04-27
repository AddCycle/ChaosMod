package net.chaos.chaosmod.items.tools;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.particles.CustomSwingParticle;
import net.chaos.chaosmod.client.particles.CustomSwingParticle.Factory;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.particle.ParticleSwordSlash;
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
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		// player.getEntityWorld().spawnParticle(EnumParticleTypes.SWEEP_ATTACK, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 0.0, 0.0, 1.0);
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
