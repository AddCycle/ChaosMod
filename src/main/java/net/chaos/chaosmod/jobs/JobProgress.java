package net.chaos.chaosmod.jobs;

import net.minecraft.nbt.NBTTagCompound;

public class JobProgress {
    private int level;
    private int exp;

    public void addExp(int amount) {
        exp += amount;
        while (exp >= getExpToNextLevel()) {
            exp -= getExpToNextLevel();
            level++;
        }
    }

    private int getExpToNextLevel() {
        return 100 + (level * 50);
    }
    
    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("level", level);
        tag.setInteger("exp", exp);
        return tag;
    }

    public void fromNBT(NBTTagCompound tag) {
        this.level = tag.getInteger("level");
        this.exp = tag.getInteger("exp");
    }
}