package net.chaos.chaosmod.init;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModKeybinds {
	public static KeyBinding playMusicKey;
	public static KeyBinding pauseMusicKey;
	public static KeyBinding stopMusicKey;
	public static KeyBinding nextMusicKey;
	public static KeyBinding previousMusicKey;
	public static KeyBinding displayJobsKey;

	public static void init() {
        String category_sounds = "key.categories.sounds";
        String category_guis = "key.categories.guis";

        playMusicKey = new KeyBinding("key.sound_handler.play", Keyboard.KEY_K, category_sounds);
        pauseMusicKey = new KeyBinding("key.sound_handler.pause", Keyboard.KEY_J, category_sounds);
        stopMusicKey = new KeyBinding("key.sound_handler.stop", Keyboard.KEY_P, category_sounds);
        nextMusicKey = new KeyBinding("key.sound_handler.next", Keyboard.KEY_N, category_sounds);
        previousMusicKey = new KeyBinding("key.sound_handler.previous", Keyboard.KEY_M, category_sounds);
        displayJobsKey = new KeyBinding("key.job_handler.open", Keyboard.KEY_SEMICOLON, category_guis);

        ClientRegistry.registerKeyBinding(playMusicKey);
        ClientRegistry.registerKeyBinding(pauseMusicKey);
        ClientRegistry.registerKeyBinding(stopMusicKey);
        ClientRegistry.registerKeyBinding(nextMusicKey);
        ClientRegistry.registerKeyBinding(previousMusicKey);
        ClientRegistry.registerKeyBinding(displayJobsKey);
    }
}