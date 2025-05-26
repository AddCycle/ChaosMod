package net.chaos.chaosmod.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientSoundHandler {
	private static ISound currentMusic;
    private static boolean isPlaying = false;
    private static boolean isPaused = false;

    public static void playMusic(SoundEvent soundEvent) {
        if (!isPlaying) {
            currentMusic = PositionedSoundRecord.getMusicRecord(soundEvent);
            Minecraft.getMinecraft().getSoundHandler().playSound(currentMusic);
            isPlaying = true;
        }
    }
    
    public static void forcePause() {
    	Minecraft.getMinecraft().getSoundHandler().pauseSounds();
    	isPaused = true;
    	isPlaying = false;
    }

    public static void pauseMusic() {
        if (!isPaused && currentMusic != null) {
            Minecraft.getMinecraft().getSoundHandler().pauseSounds();
            isPaused = true;
            isPlaying = false;
        }
    }

    public static void resumeMusic() {
        if (isPaused && currentMusic != null) {
            Minecraft.getMinecraft().getSoundHandler().resumeSounds();
            isPaused = false;
            isPlaying = true;
        }
    }

    public static void stopMusic() {
        if (isPlaying && currentMusic != null) {
        	Minecraft.getMinecraft().getSoundHandler().stopSounds();
            Minecraft.getMinecraft().getSoundHandler().stopSound(currentMusic);
            currentMusic = null;
            isPlaying = false;
        }
    }

    public static boolean isMusicPlaying() {
        return isPlaying;
    }

    public static boolean isMusicPaused() {
        return isPaused;
    }

    public static void toggleMusic(SoundEvent soundEvent) {
        if (isPlaying && !isPaused) stopMusic();
        else if (!isPlaying && !isPaused) playMusic(soundEvent);
        else ;
    }
}
