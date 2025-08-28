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

public class OxoniumHoe extends ToolHoe {

	public OxoniumHoe(String name, ToolMaterial material) {
		super(name, material);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);

	    if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
	        return EnumActionResult.FAIL;
	    }

	    // process the clicked block + its 4 direct neighbors
	    BlockPos[] targets = new BlockPos[] {
	        pos,
	        pos.north(),
	        pos.south(),
	        pos.east(),
	        pos.west()
	    };

	    boolean success = false;

	    for (BlockPos targetPos : targets) {
	        int hook = ForgeEventFactory.onHoeUse(stack, player, worldIn, targetPos);
	        if (hook != 0) {
	            if (hook > 0) success = true;
	            continue; // hook handled this block
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
	                }
	            }
	        }
	    }

	    return success ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
	}
	
	/*private void spreadTill(ItemStack stack, EntityPlayer player, World world, BlockPos start, EnumFacing facing) {
	    Queue<BlockPos> toProcess = new ArrayDeque<>();
	    Set<BlockPos> visited = new HashSet<>();
	    toProcess.add(start);

	    while (!toProcess.isEmpty()) {
	        BlockPos pos = toProcess.poll();
	        if (!visited.add(pos)) continue; // skip if already handled

	        IBlockState state = world.getBlockState(pos);
	        Block block = state.getBlock();

	        if (world.isAirBlock(pos.up()) && (block == Blocks.GRASS || block == Blocks.GRASS_PATH)) {
	            setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
	            // add neighbors
	            toProcess.add(pos.north());
	            toProcess.add(pos.south());
	            toProcess.add(pos.east());
	            toProcess.add(pos.west());
	        } else if (world.isAirBlock(pos.up()) && block == Blocks.DIRT) {
	            BlockDirt.DirtType type = state.getValue(BlockDirt.VARIANT);
	            if (type == BlockDirt.DirtType.DIRT) {
	                setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
	                toProcess.add(pos.north());
	                toProcess.add(pos.south());
	                toProcess.add(pos.east());
	                toProcess.add(pos.west());
	            } else if (type == BlockDirt.DirtType.COARSE_DIRT) {
	                setBlock(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
	                toProcess.add(pos.north());
	                toProcess.add(pos.south());
	                toProcess.add(pos.east());
	                toProcess.add(pos.west());
	            }
	        }
	    }
	}*/
}
