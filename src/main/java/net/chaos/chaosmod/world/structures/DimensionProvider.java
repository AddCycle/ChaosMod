package net.chaos.chaosmod.world.structures;

import javax.annotation.Nullable;

import net.chaos.chaosmod.entity.boss.fightmanager.CMFightManager;
import net.chaos.chaosmod.init.ModDimensions;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionProvider extends WorldProvider {
	private CMFightManager dragonFightManager;

    /**
     * Creates a new {@link BiomeProvider} for the WorldProvider, and also sets the values of {@link #hasSkylight} and
     * {@link #hasNoSky} appropriately.
     *  
     * Note that subclasses generally override this method without calling the parent version.
     */
    public void init()
    {
        this.biomeProvider = new BiomeProviderSingle(Biomes.SKY);
        NBTTagCompound nbttagcompound = this.world.getWorldInfo().getDimensionData(this.world.provider.getDimension());
        this.dragonFightManager = this.world instanceof WorldServer ? new CMFightManager((WorldServer)this.world, nbttagcompound.getCompoundTag("DragonFight")) : null;
    }

	@Override
	public DimensionType getDimensionType() {
		return ModDimensions.CUSTOM;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new DimensionGenerator(world, world.getSeed(), true, world.getWorldInfo().getGeneratorOptions());
	}
	
	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return 2;
	}

	/**
     * Called when the world is performing a save. Only used to save the state of the Dragon Boss fight in
     * WorldProviderEnd in Vanilla.
     */
    public void onWorldSave()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        if (this.dragonFightManager != null)
        {
            nbttagcompound.setTag("DragonFight", this.dragonFightManager.getCompound());
        }

        this.world.getWorldInfo().setDimensionData(this.world.provider.getDimension(), nbttagcompound);
    }

    /**
     * Called when the world is updating entities. Only used in WorldProviderEnd to update the DragonFightManager in
     * Vanilla.
     */
    public void onWorldUpdateEntities()
    {
        if (this.dragonFightManager != null)
        {
            this.dragonFightManager.tick();
        }
    }

    @Nullable
    public CMFightManager getDragonFightManager()
    {
        return this.dragonFightManager;
    }

    /**
     * Called when a Player is added to the provider's world.
     */
    @Override
    public void onPlayerAdded(EntityPlayerMP player)
    {
        if (this.dragonFightManager != null)
        {
            this.dragonFightManager.addPlayer(player);
        }
    }

    /**
     * Called when a Player is removed from the provider's world.
     */
    @Override
    public void onPlayerRemoved(net.minecraft.entity.player.EntityPlayerMP player)
    {
        if (this.dragonFightManager != null)
        {
            this.dragonFightManager.removePlayer(player);
        }
    }
	
	

}
