package net.chaos.chaosmod.cutscene;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CutsceneManager {
    private static int ticks;
    private static boolean playing;
    private static final List<Runnable> actions = new ArrayList<>();

    public static void startCutscene() {
        ticks = 0;
        playing = true;
        actions.clear();

        queue(() -> {
            // Example action: freeze player
            Minecraft.getMinecraft().setIngameNotInFocus();
        }, 0);

        queue(() -> {
            // Play sound, show particles
        }, 40);

        queue(() -> {
            // Spawn boss
        }, 100);

        queue(() -> {
            // Unfreeze player
            Minecraft.getMinecraft().setIngameFocus();
            playing = false;
        }, 140);
    }

    public static void queue(Runnable action, int delay) {
        while (actions.size() <= delay) actions.add(null);
        actions.set(delay, action);
    }

    public static void onClientTick() {
        if (!playing) return;
        if (ticks < actions.size() && actions.get(ticks) != null) {
            actions.get(ticks).run();
        }
        ticks++;
    }

    public static boolean isPlaying() {
        return playing;
    }
}
