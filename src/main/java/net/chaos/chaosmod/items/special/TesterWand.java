package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.entity.projectile.EntityMenhir;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.world.gen.procedural.DungeonGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TesterWand extends ItemBase {
	public int projectile;
	public int max;

	public TesterWand(String name) {
		super(name);
		this.setMaxStackSize(1);
		projectile = 0;
		max = 4;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
	    ItemStack stack = playerIn.getHeldItem(handIn);
	    
	    if (!worldIn.isRemote) {
	    	if (playerIn.isSneaking()) {
	    		if (projectile >= max) projectile = 0; else projectile++;
	    		playerIn.sendMessage(new TextComponentString("projectile = " + projectile));
	    	} else {
	    		switch (projectile) {
				case 0:
					EntityRock rock = new EntityRock(worldIn, playerIn);
					rock.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
					worldIn.spawnEntity(rock);
					break;
				case 1:
					Vec3d look = playerIn.getLookVec();
					EntitySmallBlueFireball fireball = new EntitySmallBlueFireball(worldIn, playerIn, look.x, look.y, look.z);
					fireball.setPosition(
					    playerIn.posX + look.x * 1.5,
					    playerIn.posY + playerIn.getEyeHeight() - 0.1,
					    playerIn.posZ + look.z * 1.5
					);
					worldIn.spawnEntity(fireball);
					break;
				case 2:
					EntityMenhir menhir = new EntityMenhir(worldIn, playerIn);
					menhir.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.0F, 1.0F);
					worldIn.spawnEntity(menhir);
					break;
				default:
					break;
				}
	    	}
	    }
	    
	    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

}
