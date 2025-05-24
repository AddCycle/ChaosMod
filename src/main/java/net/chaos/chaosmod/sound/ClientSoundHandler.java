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

    public static void playMusic(SoundEvent soundEvent) {
        if (!isPlaying) {
            currentMusic = PositionedSoundRecord.getMusicRecord(soundEvent);
            Minecraft.getMinecraft().getSoundHandler().playSound(currentMusic);
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

    public static void toggleMusic(SoundEvent soundEvent) {
        if (isPlaying) stopMusic();
        else playMusic(soundEvent);
    }
}
