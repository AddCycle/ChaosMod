package net.chaos.chaosmod.items.tools;

import static util.blockstates.BlockHelper.destroyConnectedBlocks;
import static util.blockstates.BlockHelper.isOreBlock;

import java.util.List;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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
		if (world.isRemote)
			return true;

		if (entityLiving instanceof EntityPlayer && mode == 1) {
			if (isOreBlock(state)) {
				destroyConnectedBlocks(world, pos, state, (EntityPlayer) entityLiving, stack);
			}
		}

		return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (worldIn.isRemote)
			return super.onItemRightClick(worldIn, playerIn, handIn);

		mode++;
		mode %= 2;

		playerIn.sendMessage(new TextComponentString("mode = " + getMode(mode)));

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
	
	private String getMode(int mode) {
		return (mode == 1 ? "VEIN MINER" : "NORMAL");
	}
}