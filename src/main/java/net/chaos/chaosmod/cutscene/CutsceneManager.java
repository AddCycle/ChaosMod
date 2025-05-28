package net.chaos.chaosmod.cutscene;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CutsceneManager {
    private static int ticks;
    private static boolean playing;
    private static final List<Runnable> actions = new ArrayList<>();
    private static EntityCamera camera = null;

    public static void startCutscene(BlockPos pos) {
    	ticks = 0;
        playing = true;
        actions.clear();
        Minecraft mc = Minecraft.getMinecraft();
        WorldClient world = mc.world;
        EntityPlayer player = mc.player;

        EntityCamera cam = new EntityCamera(world);
        // cam.setPositionAndRotation(player.posX, player.posY, player.posZ, 0, 0);
        cam.setPositionAndRotation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        world.spawnEntity(cam);

        camera = cam; // store it in a static field for access in tick/update

        queue(() -> {
        }, 0);

        queue(() -> {
        	Minecraft.getMinecraft().setRenderViewEntity(cam);
            Minecraft.getMinecraft().setIngameNotInFocus(); // optional
            // lookAt(camera, bossSpawn.getX() + 0.5, bossSpawn.getY() + 0.5, bossSpawn.getZ() + 0.5);
        }, 30);

        queue(() -> {
            // Spawn boss
        }, 100);

        queue(() -> {
            Minecraft.getMinecraft().setIngameFocus();
            Minecraft.getMinecraft().setRenderViewEntity(mc.player);
            camera.setDead();
            camera = null;
            playing = false;
        }, 140);
    }
    
    public static void lookAt(Entity entity, double targetX, double targetY, double targetZ) {
        double dx = targetX - entity.posX;
        double dy = targetY - (entity.posY + entity.getEyeHeight());
        double dz = targetZ - entity.posZ;

        double distXZ = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90);
        float pitch = (float)-Math.toDegrees(Math.atan2(dy, distXZ));

        entity.rotationYaw = yaw;
        // entity.rotationYawHead = yaw;
        // entity.renderYawOffset = yaw;
        entity.rotationPitch = pitch;
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
