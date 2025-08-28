package net.chaos.chaosmod.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomParticleSpawnManager {
	public static void spawnMoneyParticle(World worldIn, BlockPos pos, double speedX, double speedY, double speedZ) {
		Minecraft.getMinecraft().effectRenderer.addEffect(
			new ParticleMoneyBill(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), speedX, speedY, speedZ));
	}
}
