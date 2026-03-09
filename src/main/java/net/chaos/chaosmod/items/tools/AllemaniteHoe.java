package net.chaos.chaosmod.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class AllemaniteHoe extends ToolHoe {

	public AllemaniteHoe(String name, ToolMaterial material) {
		super(name, material);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);

	    if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
	        return EnumActionResult.FAIL;
	    }

	    BlockPos[] targets = new BlockPos[] {
	        pos,

	        pos.north(),
	        pos.south(),
	        pos.east(),
	        pos.west(),

	        pos.north().east(),
	        pos.south().west(),
	        pos.east().south(),
	        pos.west().north()
	    };

	    boolean success = false;

	    for (BlockPos targetPos : targets) {
	        int hook = ForgeEventFactory.onHoeUse(stack, player, worldIn, targetPos);
	        if (hook != 0) {
	            if (hook > 0) success = true;
	            continue;
	        }

	        IBlockState state = worldIn.getBlockState(targetPos);
	        Block block = state.getBlock();

	        if (facing != EnumFacing.DOWN && worldIn.isAirBlock(targetPos.up())) {
	            if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
	                this.setBlock(stack, player, worldIn, targetPos, Blocks.FARMLAND.getDefaultState());
	                success = true;
	            }
	            else if (block == Blocks.DIRT) {
	                switch (state.getValue(BlockDirt.VARIANT)) {
	                    case DIRT:
	                        this.setBlock(stack, player, worldIn, targetPos, Blocks.FARMLAND.getDefaultState());
	                        success = true;
	                        break;
	                    case COARSE_DIRT:
	                        this.setBlock(stack, player, worldIn, targetPos,
	                                Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
	                        success = true;
	                        break;
						default:
							break;
	                }
	            }
	        }
	    }

	    return success ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
	}
}