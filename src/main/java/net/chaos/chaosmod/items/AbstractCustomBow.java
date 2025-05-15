package net.chaos.chaosmod.items;

import java.util.Random;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.IHasModel;

public abstract class AbstractCustomBow extends ItemBow implements IHasModel {
	// private boolean low;
	public float drawTime = 20.0f; // default is 20.0F
	public float damageMultiplier = 1.0f;
	public int streak;
	
	public AbstractCustomBow(String name, ToolMaterial material) {
		this.setMaxStackSize(1);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		/* STARTING TO OVERRIDE THE DRAWING ANIMATION FASTER++ */
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
	        @SideOnly(Side.CLIENT)
	        public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
	            if (entity == null) return 0.0F;
	            if (entity.getActiveItemStack() != stack) return 0.0F;

	            // Vanilla is 20.0F, but we want faster animation (e.g., full draw at 5 ticks)
	            // return Math.min(1.0F, (stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / 5.0F);
	            return Math.min(1.0F, (stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / drawTime);
	        }
	    });

	    this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
	        @SideOnly(Side.CLIENT)
	        public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
	            return (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack) ? 1.0F : 0.0F;
	        }
	    });
		/* END TO OVERRIDE THE DRAWING ANIMATION FASTER++ */
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0F - getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
	}

	public float getFastArrowVelocity(int charge) {
	    // float velocity = (float)charge / 2.5F; // faster draw x8
	    float velocity = (float)(charge / this.drawTime); // faster draw x4
	    velocity = (velocity * velocity + velocity * 2.0F) / 3.0F;
	    return velocity > 1.0F ? 1.0F : velocity;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getFastArrowVelocity(i);

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote)
                    {
                        ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
                        entityarrow = this.customizeArrow(entityarrow);
                        entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        if (f == 1.0F)
                        {
                            entityarrow.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

                        if (j > 0)
                        {
                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

                        if (k > 0)
                        {
                            entityarrow.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
                        {
                            entityarrow.setFire(100);
                        }

                        stack.damageItem(1, entityplayer);

                        if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
                        {
                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.spawnEntity(entityarrow);
                        entityplayer.sendMessage(new TextComponentString("Streak : " + streak));
                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
	}

	@Override
	public EntityArrow customizeArrow(EntityArrow arrow) {
		Random rand = new Random();
		float multiplier = damageMultiplier;
		if (streak == 10) {
			if (rand.nextInt(2) == 1) multiplier *= 3;
			streak = 0;
		} else {
			streak++;
		}
		arrow.setDamage(arrow.getDamage() * multiplier);
		return super.customizeArrow(arrow);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.GENERAL_TAB, CreativeTabs.COMBAT };
	}
}
