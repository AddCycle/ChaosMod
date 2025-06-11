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

	public static void init() {
        String category = "key.categories.sounds";

        playMusicKey = new KeyBinding("key.sound_handler.play", Keyboard.KEY_K, category);
        pauseMusicKey = new KeyBinding("key.sound_handler.pause", Keyboard.KEY_J, category);
        stopMusicKey = new KeyBinding("key.sound_handler.stop", Keyboard.KEY_P, category);
        nextMusicKey = new KeyBinding("key.sound_handler.next", Keyboard.KEY_N, category);
        previousMusicKey = new KeyBinding("key.sound_handler.previous", Keyboard.KEY_M, category);

        ClientRegistry.registerKeyBinding(playMusicKey);
        ClientRegistry.registerKeyBinding(pauseMusicKey);
        ClientRegistry.registerKeyBinding(stopMusicKey);
        ClientRegistry.registerKeyBinding(nextMusicKey);
        ClientRegistry.registerKeyBinding(previousMusicKey);
    }

}
    	// TODO : fix that : one key to play/pause and one to stop and one to go to previous/next (with shift combine)