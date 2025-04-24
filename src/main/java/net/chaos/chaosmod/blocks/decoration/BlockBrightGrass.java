package net.chaos.chaosmod.blocks.decoration;

import java.awt.Color;
import java.util.Random;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.IHasModel;

public class BlockBrightGrass extends BlockGrass implements IHasModel {
	public BlockBrightGrass() {
        setRegistryName("bright_grass");
        setUnlocalizedName("bright_grass");

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
    }


	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.randomTick(worldIn, pos, state, rand);
        
        // Assign random bright color here
        int color = getRandomBrightColor();
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler((state1, world, pos1, tintIndex) -> color, this);
    }

    int getRandomBrightColor() {
        // Random bright color (RGB values are high for brightness)
        Random rand = new Random();
        int r = rand.nextInt(128) + 128; // Bright range for red (128 to 255)
        int g = rand.nextInt(128) + 128; // Bright range for green (128 to 255)
        int b = rand.nextInt(128) + 128; // Bright range for blue (128 to 255)
        return new Color(r, g, b).getRGB(); // Return as RGB integer
    }
}
