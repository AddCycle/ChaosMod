package net.chaos.chaosmod.client.particles;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ClientEffectRender {
	public static void spawnCustomParticle(String type, double x, double y, double z) {
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;
        EntityPlayer player = mc.player;
        Random rand = new Random();

        if (world == null || player == null) return;

        double dx = -MathHelper.sin(player.rotationYaw * 0.017453292F);
        double dz = MathHelper.cos(player.rotationYaw * 0.017453292F);
        double px = x + dx;
        double py = y + player.height * 0.5;
        double pz = z + dz;

        switch (type) {
            case "sweep":
                mc.effectRenderer.addEffect(
                    new ColoredSweepParticle(mc.getTextureManager(), world, px, py, pz, dx, 0.0D, dz, rand.nextInt(0xffffff + 1))
                );
                break;
            case "coin":
                mc.effectRenderer.addEffect(
                    new CoinParticles(mc.getTextureManager(), world, px, py, pz, dx, 0.0D, dz)
                );
                break;
        }
    }

}
