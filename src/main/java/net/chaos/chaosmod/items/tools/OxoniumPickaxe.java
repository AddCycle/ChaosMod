package net.chaos.chaosmod.items.tools;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import util.IHasModel;

public class OxoniumPickaxe extends ItemPickaxe implements IHasModel {
	private int mode = 0;

	public OxoniumPickaxe(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);

		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String tip = String.format("%s[BUFF] %sVein mining", TextFormatting.GREEN, TextFormatting.RESET);
		tooltip.add(tip);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (!world.isRemote && entityLiving instanceof EntityPlayer && mode == 1) {
			if (isOreBlock(state)) {
				veinMine(world, pos, state, (EntityPlayer) entityLiving, stack);
			}
		}
		return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
	}

	private boolean isOreBlock(IBlockState state) {
		Block block = state.getBlock();

		// Normalize special vanilla cases
		if (block == Blocks.LIT_REDSTONE_ORE) {
			block = Blocks.REDSTONE_ORE;
			state = normalizeOre(block).getDefaultState();
		}

		// If the block itself is explicitly one of the known vanilla ores
		if (block == Blocks.COAL_ORE ||
				block == Blocks.IRON_ORE ||
				block == Blocks.GOLD_ORE ||
				block == Blocks.DIAMOND_ORE ||
				block == Blocks.EMERALD_ORE ||
				block == Blocks.LAPIS_ORE ||
				block == Blocks.QUARTZ_ORE ||
				block == Blocks.REDSTONE_ORE) {
			return true;
		}

		// Otherwise, check OreDictionary (modded ores usually register here)
		Item item = Item.getItemFromBlock(block);
		if (item == Items.AIR) {
			return false; // no item form, can't be ore
		}

		ItemStack stack = new ItemStack(item, 1, block.damageDropped(state));
		if (stack.isEmpty()) {
			return false;
		}

		for (int id : OreDictionary.getOreIDs(stack)) {
			String name = OreDictionary.getOreName(id);
			if (name != null && name.toLowerCase().startsWith("ore")) {
				return true;
			}
		}

		return false;
	}

	private void veinMine(World world, BlockPos origin, IBlockState targetState, EntityPlayer player, ItemStack tool) {
		Queue<BlockPos> toCheck = new LinkedList<>();
		Set<BlockPos> visited = new HashSet<>();

		toCheck.add(origin);

		while (!toCheck.isEmpty()) {
			BlockPos pos = toCheck.poll();

			if (visited.contains(pos)) continue;
			visited.add(pos);

			IBlockState state = world.getBlockState(pos);
			// NEW: normalize before comparing
			Block current = normalizeOre(state.getBlock());
			Block target = normalizeOre(targetState.getBlock());

			if (current == target) { // instead of state.getBlock() == targetState.getBlock()
				// Break this block like normal
				Block block = state.getBlock();
				block.onBlockDestroyedByPlayer(world, pos, state);
				block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), tool);

				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
				int xp = block.getExpDrop(state, world, pos, fortune);
				if (xp > 0) block.dropXpOnBlockBreak(world, pos, xp);

				world.setBlockToAir(pos);

				// Check neighbors
				for (EnumFacing face : EnumFacing.values()) {
					BlockPos neighbor = pos.offset(face);
					if (!visited.contains(neighbor)) {
						Block neighborBlock = normalizeOre(world.getBlockState(neighbor).getBlock());
						if (neighborBlock == target) { // instead of == targetState.getBlock()
							toCheck.add(neighbor);
						}
					}
				}
			}
		}
	}

	private Block normalizeOre(Block block) {
		if (block == Blocks.LIT_REDSTONE_ORE) return Blocks.REDSTONE_ORE;
		return block;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			playerIn.sendMessage(new TextComponentString("mode = " + (mode == 0 ? "vein miner" : "normal")));
			if (mode == 0) mode = 1; else mode = 0;
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.ITEMS, CreativeTabs.TOOLS };
	}

}
