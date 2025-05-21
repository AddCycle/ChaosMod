package net.chaos.chaosmod.blocks;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.CustomPlanks.CustomPlankVariant;
import net.chaos.chaosmod.blocks.decoration.CustomLeaves;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.world.gen.WorldGenCustomTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import util.IHasModel;

public class CustomBlockSapling extends BlockBush implements IHasModel, IGrowable {
	BlockSapling block;
    public static final PropertyEnum<CustomPlankVariant> TYPE = PropertyEnum.<CustomPlankVariant>create("type", CustomPlankVariant.class);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
    	private static final Map<CustomPlanks.CustomPlankVariant, WorldGenerator> treeMap = new EnumMap<>(CustomPlanks.CustomPlankVariant.class);

	public CustomBlockSapling(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, CustomPlanks.CustomPlankVariant.SNOWY).withProperty(STAGE, Integer.valueOf(0)));
        // this.setCreativeTab(CreativeTabs.DECORATIONS);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockLeaves(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName()
    {
        return I18n.translateToLocal(this.getUnlocalizedName() + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);

            if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (((Integer)state.getValue(STAGE)).intValue() == 0)
        {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        }
        else
        {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    // TODO
    /*public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
        int i = 0;
        int j = 0;
        boolean flag = false;

        switch ((BlockPlanks.EnumType)state.getValue(TYPE))
        {
            case SPRUCE:
                label68:

                for (i = 0; i >= -1; --i)
                {
                    for (j = 0; j >= -1; --j)
                    {
                        if (this.isTwoByTwoOfType(worldIn, pos, i, j, BlockPlanks.EnumType.SPRUCE))
                        {
                            worldgenerator = new WorldGenMegaPineTree(false, rand.nextBoolean());
                            flag = true;
                            break label68;
                        }
                    }
                }

                if (!flag)
                {
                    i = 0;
                    j = 0;
                    worldgenerator = new WorldGenTaiga2(true);
                }

                break;
            case BIRCH:
                worldgenerator = new WorldGenBirchTree(true, false);
                break;
            case JUNGLE:
                IBlockState iblockstate = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
                IBlockState iblockstate1 = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
                label82:

                for (i = 0; i >= -1; --i)
                {
                    for (j = 0; j >= -1; --j)
                    {
                        if (this.isTwoByTwoOfType(worldIn, pos, i, j, BlockPlanks.EnumType.JUNGLE))
                        {
                            worldgenerator = new WorldGenMegaJungle(true, 10, 20, iblockstate, iblockstate1);
                            flag = true;
                            break label82;
                        }
                    }
                }

                if (!flag)
                {
                    i = 0;
                    j = 0;
                    worldgenerator = new WorldGenTrees(true, 4 + rand.nextInt(7), iblockstate, iblockstate1, false);
                }

                break;
            case ACACIA:
                worldgenerator = new WorldGenSavannaTree(true);
                break;
            case DARK_OAK:
                label96:

                for (i = 0; i >= -1; --i)
                {
                    for (j = 0; j >= -1; --j)
                    {
                        if (this.isTwoByTwoOfType(worldIn, pos, i, j, BlockPlanks.EnumType.DARK_OAK))
                        {
                            worldgenerator = new WorldGenCanopyTree(true);
                            flag = true;
                            break label96;
                        }
                    }
                }

                if (!flag)
                {
                    return;
                }

            case OAK:
        }

        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

        if (flag)
        {
            worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
        }
        else
        {
            worldIn.setBlockState(pos, iblockstate2, 4);
        }

        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j)))
        {
            if (flag)
            {
                worldIn.setBlockState(pos.add(i, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            }
            else
            {
                worldIn.setBlockState(pos, state, 4);
            }
        }
    }*/

    public WorldGenerator generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    	CustomPlanks.CustomPlankVariant variant = state.getValue(TYPE);
        WorldGenerator generator = treeMap.get(variant);

        if (generator != null) {
            // Remove sapling before generating
            worldIn.setBlockToAir(pos);

            // Try to generate the tree
            if (!generator.generate(worldIn, rand, pos)) {
                // Restore the sapling if generation failed
                worldIn.setBlockState(pos, state, 3);
            }

            return generator;
        }

        return null;
    }

    private boolean isTwoByTwoOfType(World worldIn, BlockPos pos, int p_181624_3_, int p_181624_4_, CustomPlankVariant type)
    {
        return this.isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_), type) && this.isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_), type) && this.isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_ + 1), type) && this.isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_ + 1), type);
    }

    /**
     * Check whether the given BlockPos has a Sapling of the given type
     */
    public boolean isTypeAt(World worldIn, BlockPos pos, CustomPlankVariant type)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        return iblockstate.getBlock() == this && iblockstate.getValue(TYPE) == type;
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return ((CustomPlankVariant)state.getValue(TYPE)).getMeta();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (CustomPlankVariant blockplanks$enumtype : CustomPlankVariant.values())
        {
            items.add(new ItemStack(this, 1, blockplanks$enumtype.getMeta()));
        }
    }

    /**
     * Whether this IGrowable can grow
     */
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.grow(worldIn, pos, state, rand);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, CustomPlankVariant.byMetadata(meta & 7)).withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((CustomPlankVariant)state.getValue(TYPE)).getMeta();
        i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, STAGE});
    }
    
    static {
        treeMap.put(CustomPlanks.CustomPlankVariant.SNOWY, new WorldGenCustomTree(ModBlocks.CUSTOM_LOG.getDefaultState(), ModBlocks.CUSTOM_LEAVES.getDefaultState(), Blocks.AIR.getDefaultState()));
        treeMap.put(CustomPlanks.CustomPlankVariant.MAPLE, new WorldGenCustomTree(((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(1), ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(1), Blocks.AIR.getDefaultState()));
        treeMap.put(CustomPlanks.CustomPlankVariant.ENDER, new WorldGenCustomTree(((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(2), ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(2), Blocks.AIR.getDefaultState()));
        treeMap.put(CustomPlanks.CustomPlankVariant.OLIVE, new WorldGenCustomTree(((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(3), ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(3), Blocks.AIR.getDefaultState()));
    }

}
