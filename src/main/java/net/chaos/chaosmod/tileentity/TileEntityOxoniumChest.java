package net.chaos.chaosmod.tileentity;

import javax.annotation.Nullable;

import net.chaos.chaosmod.blocks.OxoniumChest;
import net.chaos.chaosmod.inventory.OxoniumChestContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import util.handlers.OxoniumDoubleChestItemHandler;

public class TileEntityOxoniumChest extends TileEntityLockableLoot implements ITickable {

    private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
    public boolean adjacentChestChecked;
    public TileEntityOxoniumChest adjacentChestZNeg;
    public TileEntityOxoniumChest adjacentChestXPos;
    public TileEntityOxoniumChest adjacentChestXNeg;
    public TileEntityOxoniumChest adjacentChestZPos;
    public float lidAngle;
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;
    private OxoniumChest.Type cachedChestType;
    public String customName;
    public ResourceLocation lootTableToApply = null;

    public TileEntityOxoniumChest()
    {
    }

    public TileEntityOxoniumChest(OxoniumChest.Type typeIn)
    {
        this.cachedChestType = typeIn;
    }

    @Override
    public int getSizeInventory()
    {
        return 27;
    }

    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.chestContents)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    public String getName()
    {
    	return new TextComponentTranslation("tile.oxonium_chest.name").setStyle(new Style().setColor(TextFormatting.DARK_BLUE).setBold(true)).getFormattedText();
    }

    public static void registerFixesChest(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityOxoniumChest.class, new String[] {"Items"}));
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound))
        {
            ItemStackHelper.loadAllItems(compound, this.chestContents);
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        System.out.println("Writing loot table to NBT: " + this.lootTable);

        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.chestContents);
        }

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        return compound;
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
        doubleChestHandler = null;
    }

    @SuppressWarnings("incomplete-switch")
    private void setNeighbor(TileEntityOxoniumChest chestTe, EnumFacing side)
    {
        if (chestTe.isInvalid())
        {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked)
        {
            switch (side)
            {
                case NORTH:

                    if (this.adjacentChestZNeg != chestTe)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case SOUTH:

                    if (this.adjacentChestZPos != chestTe)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case EAST:

                    if (this.adjacentChestXPos != chestTe)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case WEST:

                    if (this.adjacentChestXNeg != chestTe)
                    {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }

    public void checkForAdjacentChests()
    {
        /*if (!this.adjacentChestChecked)
        {
            if (this.world == null || !this.world.isAreaLoaded(this.pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbors
            this.adjacentChestChecked = true;
            this.adjacentChestXNeg = this.getAdjacentChest(EnumFacing.WEST);
            this.adjacentChestXPos = this.getAdjacentChest(EnumFacing.EAST);
            this.adjacentChestZNeg = this.getAdjacentChest(EnumFacing.NORTH);
            this.adjacentChestZPos = this.getAdjacentChest(EnumFacing.SOUTH);
        }*/
    }

    @Nullable
    protected TileEntityOxoniumChest getAdjacentChest(EnumFacing side)
    {
        BlockPos blockpos = this.pos.offset(side);

        if (this.isChestAt(blockpos))
        {
            TileEntity tileentity = this.world.getTileEntity(blockpos);

            if (tileentity instanceof TileEntityOxoniumChest)
            {
                TileEntityOxoniumChest tileentitychest = (TileEntityOxoniumChest)tileentity;
                tileentitychest.setNeighbor(this, side.getOpposite());
                return tileentitychest;
            }
        }

        return null;
    }

    private boolean isChestAt(BlockPos posIn)
    {
        if (this.world == null)
        {
            return false;
        }
        else
        {
            Block block = this.world.getBlockState(posIn).getBlock();
            return block instanceof OxoniumChest && ((OxoniumChest)block).chestType == this.getChestType();
        }
    }

    @Override
    public void update()
    {
    	if (!this.world.isRemote && lootTableToApply != null) {
    		this.setLootTable(lootTableToApply, this.world.rand.nextLong());
    		lootTableToApply = null;
    		System.out.println("Loot table set (delayed) at " + this.getPos());
    	}
        this.checkForAdjacentChests();
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        ++this.ticksSinceSync;

        if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0)
        {
            this.numPlayersUsing = 0;
            float f = 5.0F;

            for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)i - 5.0F), (double)((float)j - 5.0F), (double)((float)k - 5.0F), (double)((float)(i + 1) + 5.0F), (double)((float)(j + 1) + 5.0F), (double)((float)(k + 1) + 5.0F))))
            {
                if (entityplayer.openContainer instanceof OxoniumChestContainer)
                {
                    IInventory iinventory = ((OxoniumChestContainer)entityplayer.openContainer).getLowerChestInventory();

                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this))
                    {
                        ++this.numPlayersUsing;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        float f1 = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
        {
            double d1 = (double)i + 0.5D;
            double d2 = (double)k + 0.5D;

            if (this.adjacentChestZPos != null)
            {
                d2 += 0.5D;
            }

            if (this.adjacentChestXPos != null)
            {
                d1 += 0.5D;
            }

            this.world.playSound((EntityPlayer)null, d1, (double)j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += 0.1F;
            }
            else
            {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f3 = 0.5F;

            if (this.lidAngle < 0.5F && f2 >= 0.5F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
            {
                double d3 = (double)i + 0.5D;
                double d0 = (double)k + 0.5D;

                if (this.adjacentChestZPos != null)
                {
                    d0 += 0.5D;
                }

                if (this.adjacentChestXPos != null)
                {
                    d3 += 0.5D;
                }

                this.world.playSound((EntityPlayer)null, d3, (double)j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1)
        {
            this.numPlayersUsing = type;
            return true;
        }
        else
        {
            return super.receiveClientEvent(id, type);
        }
    }

    public void openInventory(EntityPlayer player)
    {
        if (!player.isSpectator())
        {
            if (this.numPlayersUsing < 0)
            {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);

            if (this.getChestType() == OxoniumChest.Type.TRAP)
            {
                this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), false);
            }
        }
    }

    public void closeInventory(EntityPlayer player)
    {
        if (!player.isSpectator() && this.getBlockType() instanceof OxoniumChest)
        {
            --this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);

            if (this.getChestType() == OxoniumChest.Type.TRAP)
            {
                this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), false);
            }
        }
    }

    public OxoniumDoubleChestItemHandler doubleChestHandler;

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing)
    {
        if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if(doubleChestHandler == null || doubleChestHandler.needsRefresh())
                doubleChestHandler = OxoniumDoubleChestItemHandler.get(this);
            if (doubleChestHandler != null && doubleChestHandler != OxoniumDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE)
                return (T) doubleChestHandler;
        }
        return super.getCapability(capability, facing);
    }

    public net.minecraftforge.items.IItemHandler getSingleChestHandler()
    {
        return super.getCapability(net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    }


    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }

    public OxoniumChest.Type getChestType()
    {
        if (this.cachedChestType == null)
        {
            if (this.world == null || !(this.getBlockType() instanceof OxoniumChest))
            {
                return OxoniumChest.Type.BASIC;
            }

            this.cachedChestType = ((OxoniumChest)this.getBlockType()).chestType;
        }

        return this.cachedChestType;
    }

    public String getGuiID()
    {
        return "chaosmod:oxonium_chest";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        // this.fillWithLoot(playerIn); FIXME : maybe this was the issue ?
        return new OxoniumChestContainer(playerInventory, this, playerIn);
    }

    protected NonNullList<ItemStack> getItems()
    {
        return this.chestContents;
    }
    
    @Override
    public void setLootTable(ResourceLocation p_189404_1_, long p_189404_2_) {
    	super.setLootTable(p_189404_1_, p_189404_2_);
    }
    
    @Override
    public ResourceLocation getLootTable() {
    	return super.getLootTable();
    }
}
