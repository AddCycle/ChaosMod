package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.cutscene.CutsceneManager;
import net.chaos.chaosmod.entity.boss.entities.EntityEyeCrystalBoss;
import net.chaos.chaosmod.entity.projectile.EntityMenhir;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.network.PacketManager;
import net.chaos.chaosmod.network.PacketSpawnCustomParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TesterWand extends ItemBase {
	public int projectile;
	public int max;

	public TesterWand(String name) {
		super(name);
		this.setMaxStackSize(1);
		projectile = 0;
		max = 6;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (!player.world.isRemote) entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 600);
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
	    ItemStack stack = playerIn.getHeldItem(handIn);
	    
	    if (playerIn.isSneaking()) {
	    	if (!worldIn.isRemote) {
	    		if (projectile >= max) projectile = 0; else projectile++;
	    		playerIn.sendMessage(new TextComponentString("projectile = " + projectile));
	    	}
	    } else {
	    	switch (projectile) {
	    	case 0:
	    		EntityRock rock = new EntityRock(worldIn, playerIn);
	    		rock.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
	    		if (!worldIn.isRemote) worldIn.spawnEntity(rock);
	    		break;
	    	case 1:
	    		Vec3d look = playerIn.getLookVec();
	    		EntitySmallBlueFireball fireball = new EntitySmallBlueFireball(worldIn, playerIn, look.x, look.y, look.z);
	    		fireball.setPosition(
	    			playerIn.posX + look.x * 1.5,
	    			playerIn.posY + playerIn.getEyeHeight() - 0.1,
	    			playerIn.posZ + look.z * 1.5
	    		);
	    		if (!worldIn.isRemote) worldIn.spawnEntity(fireball);
	    		break;
	    	case 2:
	    		EntityMenhir menhir = new EntityMenhir(worldIn, playerIn);
	    		menhir.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.0F, 1.0F);
	    		if (!worldIn.isRemote) worldIn.spawnEntity(menhir);
	    		break;
	    	case 3:
	    		playerIn.sendMessage(new TextComponentString("STARTING cutscene : "));
	    		CutsceneManager.startCutscene(playerIn.getPosition());
	    		break;
	    	case 4:
	    		playerIn.sendMessage(new TextComponentString("spawning boss eye crystal"));
	    		EntityEyeCrystalBoss boss = new EntityEyeCrystalBoss(worldIn, playerIn.posX + 0.5, playerIn.posY + 0.5, playerIn.posZ + 0.5);
	    		boss.setBeamTarget(boss.getPosition().south(10));
	    		if (!worldIn.isRemote) worldIn.spawnEntity(boss);
	    		break;
	    	case 5:
	    		if (!worldIn.isRemote) {
	    			PacketManager.network.sendToAll(
                new PacketSpawnCustomParticle("sweep", playerIn.posX, playerIn.posY, playerIn.posZ));
	    			playerIn.sendMessage(new TextComponentString("spawning particles"));
	    		}
	    		break;
	    	case 6:
	    		if (!worldIn.isRemote) {
	    			playerIn.sendMessage(new TextComponentString("blue fire test"));
	    			playerIn.attackEntityFrom(ModDamageSources.BLUE_FIRE, 4.0f);
	    			// PacketManager.network.sendToAll(new PacketShowFireOverlay(true, 10));
	    		}
	    		break;
	    	default:
	    		break;
	    	}
	    }
	    
	    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

}
