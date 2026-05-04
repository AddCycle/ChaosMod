package net.chaos.chaosmod.items;

import javax.annotation.Nullable;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPlayerTracker extends ItemBase {

	public ItemPlayerTracker() {
		super("player_tracking_compass");

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

            	double angle;

            	if (worldIn.provider.isSurfaceWorld())
            	{
            		double yaw = flag ? (double)entity.rotationYaw : this.getFrameRotation((EntityItemFrame)entity);
            		yaw = MathHelper.positiveModulo(yaw / 360.0D, 1.0D);
            		double entityAngle = this.getClosestEntityToAngle(worldIn, entity) / (Math.PI * 2D) + 0.25D;
            		angle = 0.5D - (yaw - 0.25D - entityAngle);
            	}
            	else
            	{
            		angle = Math.random();
            	}

            	if (flag)
            	{
            		angle = this.wobble(worldIn, angle);
            	}

            	return MathHelper.positiveModulo((float)angle, 1.0F);
            }

            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double angle)
            {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick)
                {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = angle - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }

            @SideOnly(Side.CLIENT)
            private double getFrameRotation(EntityItemFrame entityFrame)
            {
                return (double)MathHelper.wrapDegrees(180 + entityFrame.facingDirection.getHorizontalIndex() * 90);
            }

            @SideOnly(Side.CLIENT)
            private double getClosestEntityToAngle(World world, Entity self)
            {
            	WorldClient clientworld = (WorldClient)world;

            	double minDist = Double.MAX_VALUE;
            	EntityPlayer closest = null;
            	for (EntityPlayer player : clientworld.playerEntities) {
                    if (player == self) continue;
                    double dist = self.getDistance(player);
                    if (dist < minDist) {
                    	minDist = dist;
                    	closest = player;
                    }
                }
                
            	if (closest == null) return 0.0D;
//            	Main.getLogger().info("Closest found entity: {}, being within : {}", closest.getName(), minDist);
            	return Math.atan2(closest.posZ - self.posZ, closest.posX - self.posX);
            }
        });
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		EntityPlayer closest = null;
	    double minDist = Double.MAX_VALUE;

	    for (EntityPlayer player : worldIn.playerEntities) {
	        if (player == playerIn) continue;
	        double dist = playerIn.getDistance(player);
//	        if (dist > 10000) continue; // fine small amount of players
	        if (dist < minDist) {
	            minDist = dist;
	            closest = player;
	        }
	    }

	    if (closest == null) {
	        playerIn.sendStatusMessage(new TextComponentString("No player found within range"), true);
	    } else {
	        playerIn.sendStatusMessage(new TextComponentString(
	            "Closest player: " + closest.getName() + " within: " + minDist), true);
	    }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
}