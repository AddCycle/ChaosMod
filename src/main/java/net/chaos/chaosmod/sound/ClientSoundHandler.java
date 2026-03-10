package net.chaos.chaosmod.sound;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// FIXME : playlist issue
// I think I will have to rewrite a sound system myself instead of using Paulscode one, RIP (later)
@SideOnly(Side.CLIENT)
public class ClientSoundHandler {
	public static int index = 0;

	private static List<SoundEvent> sounds = Arrays.asList(ModSounds.FIRE_FORCE_OP, ModSounds.BLACK_CATCHER,
			ModSounds.BLACK_ROVER, ModSounds.CHAINSAWMAN_OP, ModSounds.GRANDEUR, ModSounds.ZANKYOU_SANKYA,
			ModSounds.HOLLOW_PURPLE, ModSounds.PUTIN_WIDE_WALK);

	public static ISound currentMusic;
	private static boolean isPlaying = false;
	private static boolean isPaused = false;

	public static void playMusic(SoundEvent soundEvent) {
		stopMusic();

		currentMusic = PositionedSoundRecord.getMasterRecord(soundEvent, 1.0f);
		Minecraft.getMinecraft().getSoundHandler().playSound(currentMusic);
		isPlaying = true;
		isPaused = false;
	}

	public static void forcePauseMusic() {
		Minecraft.getMinecraft().getSoundHandler().pauseSounds();
	}

	public static void pauseMusic() {
		if (isPaused || currentMusic == null)
			return;

		Minecraft.getMinecraft().getSoundHandler().pauseSounds();
		isPaused = true;
		isPlaying = false;
	}

	public static void resumeMusic() {
		if (!isPaused || currentMusic == null)
			return;

		Minecraft.getMinecraft().getSoundHandler().resumeSounds();
		isPaused = false;
		isPlaying = true;
	}

	public static void stopMusic() {
		if (currentMusic == null)
			return;

		Minecraft.getMinecraft().getSoundHandler().stopSound(currentMusic);
		currentMusic = null;
		isPlaying = false;
	}

	public static boolean isMusicPlaying() {
		return isPlaying;
	}

	public static boolean isMusicPaused() {
		return isPaused;
	}

	public static void nextSound() {
		playMusic(sounds.get(index));

		index++;
		index %= sounds.size();
	}
	
	@Nullable
	public static String getSoundName() {
		if (currentMusic == null)
			return "";

		return currentMusic.getSoundLocation().getResourcePath();
	}
}