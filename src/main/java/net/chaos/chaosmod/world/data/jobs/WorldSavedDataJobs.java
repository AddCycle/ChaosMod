package net.chaos.chaosmod.world.data.jobs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import util.Reference;

/**
 * Player rankings have to be saved here inside world storage
 */
public class WorldSavedDataJobs extends WorldSavedData {
	public static final String DATA_JOBS = Reference.PREFIX + "_jobsRankData";

	public WorldSavedDataJobs() {
		super(DATA_JOBS);
	}
	
	public WorldSavedDataJobs(String s) {
		super(s);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		return null;
	}

}
