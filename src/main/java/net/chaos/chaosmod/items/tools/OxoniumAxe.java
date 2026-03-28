package net.chaos.chaosmod.items.tools;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import net.chaos.chaosmod.world.events.tree.TreeTask;
import net.chaos.chaosmod.world.events.tree.TreeTaskManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class OxoniumAxe extends ToolAxe {
	private int mode = 0;

	public OxoniumAxe(String name, ToolMaterial material) {
		super(name, material);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String tip = String.format("%s[SAWMILL] %sHarvests the entire trunk (right-click to toggle)", TextFormatting.GREEN,
				TextFormatting.RESET);
		tooltip.add(tip);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (worldIn.isRemote)
			return super.onItemRightClick(worldIn, playerIn, handIn);

		mode++;
		mode %= 2;

		playerIn.sendMessage(new TextComponentString("mode = " + (mode == 1 ? "SAWMILL" : "NORMAL")));

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (worldIn.isRemote)
			return true;

		if (entityLiving instanceof EntityPlayer && mode == 1) {
			if (isLog(state)) {
				destroyTreeTrunk(worldIn, pos, state, (EntityPlayer) entityLiving, stack);
			}
		}

		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	private void destroyTreeTrunk(World world, BlockPos origin, IBlockState targetState, EntityPlayer player,
			ItemStack stack) {
		Queue<BlockPos> toCheck = new LinkedList<>();
		Set<BlockPos> visited = new HashSet<>();
		Set<BlockPos> logs = new LinkedHashSet<>();

		toCheck.add(origin);

		while (!toCheck.isEmpty()) {
			BlockPos pos = toCheck.poll();

			if (visited.contains(pos))
				continue;

			visited.add(pos);

			IBlockState state = world.getBlockState(pos);
			Block current = state.getBlock();
			Block target = targetState.getBlock();

			if (current == target) {
				logs.add(pos);

				for (EnumFacing facing : EnumFacing.values()) {
					BlockPos neighbor = pos.offset(facing);
					if (!visited.contains(neighbor)) {
						Block neighborBlock = world.getBlockState(neighbor).getBlock();
						if (neighborBlock == target) {
							toCheck.add(neighbor);
						}
					}
				}
			}
		}
		
		TreeTask task = new TreeTask(new LinkedList<>(logs), player, stack);
		TreeTaskManager.addTask(world, task);
	}

	private boolean isLog(IBlockState state) {
		ItemStack stack = new ItemStack(state.getBlock());
		for (int id : OreDictionary.getOreIDs(stack)) {
			String name = OreDictionary.getOreName(id);
			if (name.equalsIgnoreCase("logWood")) {
				return true;
			}
		}
		
		if (stack.getItem().getRegistryName().getResourcePath().endsWith("_log")) {
			return true;
		}

		return state.getBlock().isWood(null, null);
	}
}
