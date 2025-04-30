package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;

public class BossAltar extends BlockBase implements ITileEntityProvider {
	/*
	 * From 0 -> 3 :
	 * 0 = Mountain Giant
	 * -1 = Blaze
	 * 1 = Eye of truth
	 * 2 = Chaos Master **actually I want him to spawn with another manner so for now ## DISABLED ##
	 */
	public static int bossType = 0;

	public BossAltar(String name, Material material) {
		super(name, material);
		this.setLightLevel(12.0f);
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
			} else if (hand.equals(EnumHand.MAIN_HAND) && playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(ModItems.ALLEMANITE_INGOT))) {
				bossType = -1;
				te_2.r = 0.0f; te_2.g = 0.0f; te_2.b = 1.0f;
			} else if (hand.equals(EnumHand.MAIN_HAND) && playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(ModItems.ENDERITE_INGOT))) {
				bossType = 1;
				te_2.r = 0.5f; te_2.g = 0.0f; te_2.b = 0.5f;
			} else {
				return false;
			}
			if (!playerIn.capabilities.isCreativeMode) {
				if (!worldIn.isRemote) playerIn.getHeldItemMainhand().shrink(1);
			}
			if (!worldIn.isRemote) te_2.triggerAnimation(20 * 6);
		}
		return true;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!worldIn.isRemote) player.sendMessage(new TextComponentString("Vous n'auriez pas du faire cela !"));
		if (!worldIn.isRemote) {
			player.attackEntityFrom(new DamageSource("killed_by_game") {
				@Override
				public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
					return new TextComponentString(entityLivingBaseIn.getName() + " est tombé ko en voulant faire le mongol... XD");
				}
			}, Float.MAX_VALUE);
		}
		for (EntityPlayer pl : worldIn.playerEntities) {
			if (!worldIn.isRemote)
			pl.sendMessage(new TextComponentString("Encore un C** comme le Djo...")
				.setStyle(new Style()
				.setColor(TextFormatting.LIGHT_PURPLE)
				.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Djo est vraiment C**, TFTG").setStyle(new Style().setColor(TextFormatting.DARK_RED))))
				.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Eh oui mon con !"))
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

}
