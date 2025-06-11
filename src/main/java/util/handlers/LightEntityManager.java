package util.handlers;

import net.chaos.chaosmod.entity.EntityFakeLight;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LightEntityManager {
	private static EntityFakeLight currentLight = null;

    public static void updateLightEntity(EntityPlayer player) {
        World world = player.world;

        if (world.isRemote) {
            if (currentLight == null || currentLight.isDead) {
                currentLight = new EntityFakeLight(world);
                currentLight.setPosition(player.posX, player.posY + 0.5, player.posZ);
                world.spawnEntity(currentLight);
                System.out.println("spawned");
            }
        }
    }

    public static void removeLightEntity() {
        if (currentLight != null) {
            currentLight.setDead();
            System.out.println("Killed");
            currentLight = null;
        }
    }

}
