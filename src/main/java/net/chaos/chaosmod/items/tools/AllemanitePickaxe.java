package net.chaos.chaosmod.items.tools;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import util.IHasModel;

public class AllemanitePickaxe extends ItemPickaxe implements IHasModel {
	int radius = 1;
	public int mode = 0;

	public AllemanitePickaxe(String name, ToolMaterial material) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(name);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && mode == 1) {
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
                    EnumFacing enumfacing = EnumFacing.getDirectionFromEntityLiving(pos, entityLiving);
                    BlockPos bPos = this.rotate(enumfacing, new BlockPos(i, j, 0)).add(pos);
                    if(worldIn.getBlockState(bPos).getBlockHardness(worldIn, bPos) >= 0 && this.canHarvestBlock(worldIn.getBlockState(bPos)))
                    {
                    	if(entityLiving instanceof EntityPlayer)
                        {
                    		worldIn.getBlockState(bPos).getBlock().onBlockDestroyedByPlayer(worldIn, bPos, worldIn.getBlockState(bPos));
                            worldIn.getBlockState(bPos).getBlock().harvestBlock(worldIn, (EntityPlayer) entityLiving, bPos, worldIn.getBlockState(bPos), worldIn.getTileEntity(bPos), stack);
                            worldIn.destroyBlock(bPos, false);
                        }
                    	else
                    	{
                            worldIn.destroyBlock(bPos, true);
                    	}
                    }
				}
			}
		}
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			playerIn.sendMessage(new TextComponentString("mode = " + (mode == 0 ? "3x3" : "normal")));
			if (mode == 0) mode = 1; else mode = 0;
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	 private BlockPos rotate(EnumFacing facing, BlockPos pos)
	 {
		 switch (facing)
		 {
		 case NORTH:
		 default:
			 return pos;
		 case WEST:
			 return new BlockPos(-pos.getZ(), pos.getY(), pos.getX());
		 case EAST:
			 return new BlockPos(-pos.getZ(), pos.getY(), -pos.getX());
		 case SOUTH:
			 return new BlockPos(-pos.getX(), pos.getY(), -pos.getZ());
		 case UP:
			 return new BlockPos(pos.getX(), pos.getZ(), -pos.getY());
		 case DOWN:
			 return new BlockPos(pos.getX(), pos.getZ(), pos.getY());
		 }
	 }
	 
	 @Override
	public CreativeTabs[] getCreativeTabs() {
		 return new CreativeTabs[] { ModTabs.GENERAL_TAB, CreativeTabs.TOOLS };
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
