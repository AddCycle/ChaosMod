package net.chaos.chaosmod.items;

import java.util.Collections;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

/**
 * TODO : write to NBT the current biome searched and with shift-click maybe cycle through biomes
 * TODO : later make a gui to type in the id of the searched biome (with completion or suggestions)
 */
public class ItemBiomeCompass extends ItemBase {
	private static final ResourceLocation BIOME_RL = new ResourceLocation(Reference.MODID, "spring_biome");
	private static final String BIOMEPOS_TAG = "biomepos";

	public ItemBiomeCompass() {
		super("biome_compass");
		
        this.addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            double rotation;
            @SideOnly(Side.CLIENT)
            double rota;
            @SideOnly(Side.CLIENT)
            long lastUpdateTick;

            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if (entityIn == null && !stack.isOnItemFrame())
            	{
            		return 0.0F;
            	}

            	boolean flag = entityIn != null;
            	Entity entity = (Entity)(flag ? entityIn : stack.getItemFrame());

            	if (worldIn == null)
            	{
            		worldIn = entity.world;
            	}

            	double d0;

            	if (worldIn.provider.isSurfaceWorld())
            	{
            		double d1 = flag ? (double)entity.rotationYaw : this.getFrameRotation((EntityItemFrame)entity);
            		d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
            		double d2 = this.getBiomeToAngle(stack, worldIn, entity) / (Math.PI * 2D);
            		d0 = 0.5D - (d1 - 0.25D - d2);
            	}
            	else
            	{
            		d0 = Math.random();
            	}

            	if (flag)
            	{
            		d0 = this.wobble(worldIn, d0);
            	}

            	return MathHelper.positiveModulo((float)d0, 1.0F);
            }

            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double p_185093_2_)
            {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick)
                {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = p_185093_2_ - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }

            @SideOnly(Side.CLIENT)
            private double getFrameRotation(EntityItemFrame p_185094_1_)
            {
                return (double)MathHelper.wrapDegrees(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
            }

            @SideOnly(Side.CLIENT)
            private double getBiomeToAngle(ItemStack stack, World world, Entity entity)
            {
            	NBTTagCompound tag = stack.getOrCreateSubCompound("data");

                if (!tag.hasKey(BIOMEPOS_TAG)) {
                    return Math.random();
                }

                BlockPos blockpos = BlockPos.fromLong(tag.getLong(BIOMEPOS_TAG));
                if (blockpos == null) return 0.0D;
                return Math.atan2((double)blockpos.getZ() - entity.posZ, (double)blockpos.getX() - entity.posX);
            }
        });
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		ItemStack stack = player.getHeldItemMainhand();
		NBTTagCompound tag = stack.getOrCreateSubCompound("data");
		Biome biome = ForgeRegistries.BIOMES.getValue(BIOME_RL);
		if (biome == null) {
			player.sendStatusMessage(new TextComponentString("No biome with this id exists"), true);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
		}
		int range = 2000;
		BlockPos blockpos = null;
		if (!tag.hasKey(BIOMEPOS_TAG)) {
			blockpos = world.getBiomeProvider().findBiomePosition((int)player.posX, (int)player.posZ, range, Collections.singletonList(biome), world.rand);
			if (blockpos == null) {
				player.sendStatusMessage(new TextComponentString("Biome not found within range"), true);
				return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
			}
			tag.setLong(BIOMEPOS_TAG, blockpos.toLong());
		} else {
			blockpos = BlockPos.fromLong(tag.getLong(BIOMEPOS_TAG));
		}
		if (blockpos == null) {
			player.sendStatusMessage(new TextComponentString("Biome not found within range"), true);
		} else {
			player.sendStatusMessage(new TextComponentString("Biome found at: " + blockpos), true);
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
	}
}