package util.broadcast;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class MessageDisplayBiomeName implements IMessage {
	private String text;

	public MessageDisplayBiomeName() {
	}

	public MessageDisplayBiomeName(String text) {
		this.text = text;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int length = buf.readInt();
		byte[] bytes = new byte[length];
		buf.readBytes(bytes);
		text = new String(bytes);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] bytes = text.getBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
	}

	public String getText() {
		return text;
	}

	public static class MessageDisplayBiomeNameHandler implements IMessageHandler<MessageDisplayBiomeName, IMessage> {
		@Override
		public IMessage onMessage(MessageDisplayBiomeName message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				String registryName = message.getText();

				Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(registryName));
				if (biome == null)
					return;

				String displayName = biome.getBiomeName();
				String formatted = TextFormatting.RESET + "Now entering: " + TextFormatting.RED + ""
						+ TextFormatting.BOLD + displayName;
				ClientMessageHandler.displayMessage(formatted);
			});
			return null;
		}
	}
}