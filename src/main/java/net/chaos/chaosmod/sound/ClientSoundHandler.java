package net.chaos.chaosmod.sound;

import java.util.Arrays;
import java.util.List;

import net.chaos.chaosmod.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientSoundHandler {
	public static int index = 0; // from 0 to sounds.size() - 1
	private static List<SoundEvent> sounds = Arrays.asList(
			ModSounds.FIRE_FORCE_OP,
			ModSounds.BLACK_CATCHER,
			ModSounds.BLACK_ROVER,
			ModSounds.CHAINSAWMAN_OP,
			ModSounds.GRANDEUR,
			ModSounds.ZANKYOU_SANKYA,
			ModSounds.HOLLOW_PURPLE,
			ModSounds.PUTIN_WIDE_WALK
		);
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
    
    public static void launchPlaylist() {
    	if (index > sounds.size() - 1) index = 0;
    	if (index < 0) index = sounds.size() - 1;
    	playMusic(sounds.get(index));
    	index++;
    }
}
