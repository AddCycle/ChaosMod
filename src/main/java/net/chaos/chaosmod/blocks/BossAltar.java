package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.cutscene.CutsceneManager;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import util.IHasModel;

public class BossAltar extends Block implements ITileEntityProvider, IHasModel {
	/*
	 * From 0 -> 3 :
	 * 0 = Mountain Giant
	 * -1 = Blaze
	 * 1 = Eye of truth
	 * 2 = Chaos Master, Spawning on himself
	 */
	public static int bossType = 0;

	public BossAltar(String name, Material material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setLightLevel(12.0f);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockFurnaces(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
	    return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
	    return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityBossAltar) {
			TileEntityBossAltar te_2 = ((TileEntityBossAltar) te);
			if (te_2.isAnimating) {
				return false;
			}
			if (hand.equals(EnumHand.MAIN_HAND) && playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(ModItems.OXONIUM_INGOT))) {
				te_2.r = 0.001f; te_2.g = 0.0f; te_2.b = 1.0f;
				bossType = 0;
			} else if (hand.equals(EnumHand.MAIN_HAND) && playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(ModItems.GIANT_HEART))) {
				bossType = -1;
				te_2.r = 1.0f; te_2.g = 0.0f; te_2.b = 0.0f;
			} else if (hand.equals(EnumHand.MAIN_HAND) && playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(ModItems.BLAZING_HEART))) {
				bossType = 1;
				te_2.r = 0.5f; te_2.g = 0.0f; te_2.b = 0.5f;
			} else {
				return false;
			}
			if (!playerIn.capabilities.isCreativeMode) {
				if (!worldIn.isRemote) playerIn.getHeldItemMainhand().shrink(1);
			}
			if (worldIn.isRemote) CutsceneManager.startCutscene(pos.north(10).up(2));
			if (!worldIn.isRemote) te_2.triggerAnimation(20 * 6);
		}
		return true;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!worldIn.isRemote) player.sendMessage(new TextComponentTranslation("chaosmod.boss_altar.message"));
		for (EntityPlayer pl : worldIn.playerEntities) {
			if (!worldIn.isRemote)
			pl.sendMessage(new TextComponentTranslation("chaosmod.boss_altar.message2")
				.setStyle(new Style()
				.setColor(TextFormatting.LIGHT_PURPLE)
				.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("chaosmod.boss_altar.show_text").setStyle(new Style().setColor(TextFormatting.DARK_RED))))
				.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "chaosmod.boss_altar.suggested_command"))
				));
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBossAltar();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
