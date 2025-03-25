package net.chaos.chaosmod.items.materials;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.text.format.colors.ColorEnum;
import util.text.format.style.StyleEnum;

public class EnderiteShard extends ItemBase {

	public EnderiteShard(String name) {
		super(name);
	}

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ColorEnum.DARK_PURPLE + "" + StyleEnum.BOLD + "I ain't that strong tho");
    }
	
	// on check la zone en 3x3
	private boolean is_correct_setup(BlockPos pos, World worldIn) {
		BlockPos under_layer = pos.down();
		BlockPos[] to_verify = 
			{
				under_layer, under_layer.north(), under_layer.south(), under_layer.east(), under_layer.west(),
				under_layer.north().east(), under_layer.north().west(),
				under_layer.south().east(), under_layer.south().west(),
				under_layer.east().north(), under_layer.east().south(),
				under_layer.west().north(), under_layer.west().south()
			};
		for (BlockPos p : to_verify) {
			if (!Block.isEqualTo(worldIn.getBlockState(p).getBlock(), ModBlocks.ALLEMANITE_BLOCK)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		System.out.println(facing + " -> direction");
		TileEntity te = worldIn.getTileEntity(pos);

		float yaw = player.getPitchYaw().y;
		float pitch = player.getPitchYaw().x;
		
		BlockPos pos1 = player.getPosition();

		EntityForgeGuardian gardien1 = new EntityForgeGuardian(worldIn);
		BlockPos left_g_pos = getPosFromFacingRight(facing, pos);
		if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
			return EnumActionResult.FAIL;
		}
		gardien1.setPosition(left_g_pos.getX() + 0.5f, left_g_pos.getY(), left_g_pos.getZ() + 0.5f);
		// Petit pb de rotation vers le sud :) laissons ca comme ca c'est la ref a l'epee du sud dont a parle JeanRobetPerez XD
		gardien1.setRenderYawOffset(facing.getHorizontalAngle());

		EntityForgeGuardian gardien2 = new EntityForgeGuardian(worldIn);
		BlockPos right_d_pos = getPosFromFacingLeft(facing, pos);
		if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
			return EnumActionResult.FAIL;
		}
		gardien2.setPosition(right_d_pos.getX() + 0.5f, right_d_pos.getY(), right_d_pos.getZ() + 0.5f);
		// Petit pb de rotation vers le sud :) laissons ca comme ca c'est la ref a l'epee du sud dont a parle JeanRobetPerez XD
		// gardien2.setRenderYawOffset(facing.getHorizontalAngle());
		gardien2.setRenderYawOffset(EnumFacing.getDirectionFromEntityLiving(player.getPosition(), player).getHorizontalAngle());

		if (te instanceof TileEntityOxoniumFurnace) {
			if (is_correct_setup(pos, worldIn)) {
				NBTTagCompound tag = new NBTTagCompound();
				te.writeToNBT(tag);
				te.readFromNBT(new NBTTagCompound());
				worldIn.setBlockToAir(pos);
				worldIn.setBlockState(pos, ModBlocks.OXONIUM_FURNACE.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand));
				TileEntity new_te = worldIn.getTileEntity(pos);
				worldIn.setBlockState(pos.up(), Blocks.FIRE.getStateForPlacement(worldIn, pos.up(), facing, hitX, hitY, hitZ, 0, player, hand));
				new_te.readFromNBT(tag);
				player.getHeldItemMainhand().shrink(1);
				if (worldIn.isRemote)
					player.sendMessage(new TextComponentString("Are you sure you wanna do this ?").setStyle(new Style().setColor(TextFormatting.GOLD).setBold(true)));
				if (!worldIn.isRemote) worldIn.spawnEntity(gardien1);
				if (!worldIn.isRemote) worldIn.spawnEntity(gardien2);
			} else {
				if (worldIn.isRemote)
					player.sendMessage(new TextComponentString("Hum I'm not sure about the setup...").setStyle(new Style().setColor(TextFormatting.RED).setItalic(true)));
			}
		}
		return EnumActionResult.SUCCESS;
	}
	
	// get the block next to it from the facing
	private BlockPos getPosFromFacingRight(EnumFacing block_facing, @Nonnull BlockPos init) {
		BlockPos res = init;
		switch (block_facing) {
			case NORTH:
				res = res.south().west();
				break;
			case SOUTH:
				res = res.north().east();
				break;
			case EAST:
				res = res.west().north();
				break;
			case WEST:
				res = res.east().south();
				break;
			default: return res;
		}
		return res;
	}

	private BlockPos getPosFromFacingLeft(EnumFacing block_facing, @Nonnull BlockPos init) {
		BlockPos res = init;
		switch (block_facing) {
			case NORTH:
				res = res.south().east();
				break;
			case SOUTH:
				res = res.north().west();
				break;
			case EAST:
				res = res.west().south();
				break;
			case WEST:
				res = res.east().north();
				break;
			default: return res;
		}
		return res;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.GENERAL_TAB }; // You can add other tabs
    }

}
