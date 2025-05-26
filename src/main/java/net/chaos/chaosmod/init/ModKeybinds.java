package net.chaos.chaosmod.init;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModKeybinds {
	public static KeyBinding playMusicKey;
	public static KeyBinding pauseMusicKey;
	public static KeyBinding stopMusicKey;

    public static void init() {
    	// TODO : fix that : one key to play/pause and one to stop and one to go to previous/next (with shift combine)
    	String category = "key.categories.sounds";
    	helper(playMusicKey, Keyboard.KEY_K, "key.sound_handler.play", category); // play
    	helper(stopMusicKey, Keyboard.KEY_P, "key.sound_handler.stop", category); // play
    	helper(pauseMusicKey, Keyboard.KEY_J, "key.sound_handler.pause", category); // play
    }
    
    public static void helper(KeyBinding bind, int key, String name, String category) {
        bind = new KeyBinding(name, key, category);
        ClientRegistry.registerKeyBinding(bind);
    }

}
