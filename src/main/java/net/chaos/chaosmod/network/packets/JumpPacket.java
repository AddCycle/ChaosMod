package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class JumpPacket implements IMessage {
	public JumpPacket() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	public static class Handler implements IMessageHandler<JumpPacket, IMessage> {

		@Override
		public IMessage onMessage(JumpPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;

//			player.jump();
//			player.motionY = 0.42D;
//			player.fallDistance = 0;
//			player.velocityChanged = true;
			
			float upwardsMotion = 0.6f;
	        player.motionY = upwardsMotion;

	        if (player.isPotionActive(MobEffects.JUMP_BOOST))
	        {
	            player.motionY += (double)((float)(player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
	        }

//	        if (player.isSprinting())
	        {
	            float f = player.rotationYaw * 0.017453292F;
	            float leap = 0.8f;
	            player.motionX -= (double)(MathHelper.sin(f) * leap);
	            player.motionZ += (double)(MathHelper.cos(f) * leap);
	        }

//	        player.isAirBorne = true;
	        player.fallDistance = 0;
	        player.velocityChanged = true;
			return null;
		}
	}
}