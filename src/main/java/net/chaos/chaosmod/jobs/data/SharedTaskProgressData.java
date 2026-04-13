package net.chaos.chaosmod.jobs.data;

import net.chaos.chaosmod.jobs.task.SharedTaskProgress;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class SharedTaskProgressData extends WorldSavedData {
	public static final String NAME = "shared_task_progress";
	private SharedTaskProgress progress = new SharedTaskProgress();
	
	public SharedTaskProgressData() {
		super(NAME);
	}

	public SharedTaskProgressData(String name) {
		super(name);
	}
	
	public SharedTaskProgress getProgress() { return progress; }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		progress.fromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound data = progress.toNBT();
	    for (String key : data.getKeySet()) {
	        compound.setTag(key, data.getTag(key));
	    }
	    return compound;
	}
}
