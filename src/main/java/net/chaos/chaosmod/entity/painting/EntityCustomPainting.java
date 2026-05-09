package net.chaos.chaosmod.entity.painting;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCustomPainting extends EntityHanging implements IEntityAdditionalSpawnData {
	
    public EntityCustomPainting.EnumCustomArt art;

    public EntityCustomPainting(World worldIn)
    {
        super(worldIn);
    }

    public EntityCustomPainting(World worldIn, BlockPos pos, EnumFacing facing)
    {
        super(worldIn, pos);
        List<EntityCustomPainting.EnumCustomArt> list = Lists.<EntityCustomPainting.EnumCustomArt>newArrayList();
        int i = 0;

        for (EntityCustomPainting.EnumCustomArt entitypainting$enumart : EntityCustomPainting.EnumCustomArt.values())
        {
            this.art = entitypainting$enumart;
            this.updateFacingWithBoundingBox(facing);

            if (this.onValidSurface())
            {
                list.add(entitypainting$enumart);
                int j = entitypainting$enumart.sizeX * entitypainting$enumart.sizeY;

                if (j > i)
                {
                    i = j;
                }
            }
        }

        if (!list.isEmpty())
        {
            Iterator<EntityCustomPainting.EnumCustomArt> iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityCustomPainting.EnumCustomArt entitypainting$enumart1 = iterator.next();

                if (entitypainting$enumart1.sizeX * entitypainting$enumart1.sizeY < i)
                {
                    iterator.remove();
                }
            }

            this.art = list.get(this.rand.nextInt(list.size()));
        }

        this.updateFacingWithBoundingBox(facing);
    }

    @SideOnly(Side.CLIENT)
    public EntityCustomPainting(World worldIn, BlockPos pos, EnumFacing facing, String title)
    {
        this(worldIn, pos, facing);

        for (EntityCustomPainting.EnumCustomArt entitypainting$enumart : EntityCustomPainting.EnumCustomArt.values())
        {
            if (entitypainting$enumart.title.equals(title))
            {
                this.art = entitypainting$enumart;
                break;
            }
        }

        this.updateFacingWithBoundingBox(facing);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setString("Motive", this.art.title);
        super.writeEntityToNBT(compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        String s = compound.getString("Motive");

        for (EntityCustomPainting.EnumCustomArt entitypainting$enumart : EntityCustomPainting.EnumCustomArt.values())
        {
            if (entitypainting$enumart.title.equals(s))
            {
                this.art = entitypainting$enumart;
            }
        }

        if (this.art == null)
        {
            this.art = EntityCustomPainting.EnumCustomArt.BEEHIVE;
        }

        super.readEntityFromNBT(compound);
    }

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		Main.getLogger().debug("[SERVER] writeSpawnData called, title: {}", this.art.title);
	    ByteBufUtils.writeUTF8String(buffer, this.art.title);
	    buffer.writeByte(this.facingDirection.getIndex());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		String title = ByteBufUtils.readUTF8String(additionalData);
		EnumFacing facing = EnumFacing.getFront(additionalData.readByte());
	    Main.getLogger().debug("[CLIENT] readSpawnData called, title: {}", title);
	    
	    for (EnumCustomArt art : EnumCustomArt.values()) {
	        if (art.title.equals(title)) {
	            this.art = art;
	            Main.getLogger().debug("[CLIENT] art set to: {}", art.title);
	            break;
	        }
	    }

	    this.updateFacingWithBoundingBox(facing);
	}


    public int getWidthPixels()
    {
        return this.art.sizeX;
    }

    public int getHeightPixels()
    {
        return this.art.sizeY;
    }

    /**
     * Called when this entity is broken. Entity parameter may be null.
     */
    public void onBroken(@Nullable Entity brokenEntity)
    {
        if (this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);

            if (brokenEntity instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)brokenEntity;

                if (entityplayer.capabilities.isCreativeMode)
                {
                    return;
                }
            }

            this.entityDropItem(new ItemStack(Items.PAINTING), 0.0F);
        }
    }

    public void playPlaceSound()
    {
        this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0F, 1.0F);
    }

    /**
     * Sets the location and Yaw/Pitch of an entity in the world
     */
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
    {
        this.setPosition(x, y, z);
    }

    /**
     * Set the position and rotation values directly without any clamping.
     */
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
        this.setPosition((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
    }

    public static enum EnumCustomArt
    {
        BEEHIVE("Beehive", 16, 16, 0, 0),
        QUEENBEE("QueenBoss", 32, 16, 0, 16),
        HIVETELEPORT("HiveTeleport", 32, 16, 32, 16);

        public static final int MAX_NAME_LENGTH = "SkullAndRoses".length();
        /** Painting Title. */
        public final String title;
        public final int sizeX;
        public final int sizeY;
        public final int offsetX;
        public final int offsetY;

        private EnumCustomArt(String titleIn, int width, int height, int textureU, int textureV)
        {
            this.title = titleIn;
            this.sizeX = width;
            this.sizeY = height;
            this.offsetX = textureU;
            this.offsetY = textureV;
        }
    }
}