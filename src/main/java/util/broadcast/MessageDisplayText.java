package util.broadcast;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDisplayText implements IMessage {
	private String text;

	public MessageDisplayText() {}

	public MessageDisplayText(String text) {
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

	public static class MessageDisplayTextHandler implements IMessageHandler<MessageDisplayText, IMessage> {
	    @Override
	    public IMessage onMessage(MessageDisplayText message, MessageContext ctx) {
	        Minecraft.getMinecraft().addScheduledTask(() -> {
	            String text = message.getText();

	            if (text.startsWith("NAME:")) {
	                // Already the display name
	                String displayName = text.substring(5);
	                String formatted = TextFormatting.RESET + "Now entering: " +
	                                    TextFormatting.RED + "" + TextFormatting.BOLD + displayName;
	                ClientMessageHandler.displayMessage(formatted);
	            }
	            else if (text.startsWith("REG:")) {
	                // Resolve registry name on client
	                String registryName = text.substring(4);
	                Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(registryName));
	                if (biome != null) {
	                    String displayName = biome.getBiomeName();
	                    String formatted = TextFormatting.RESET + "Now entering: " +
	                                        TextFormatting.RED + "" + TextFormatting.BOLD + displayName;
	                    ClientMessageHandler.displayMessage(formatted);
	                }
	            }
	            else {
	                // Fallback: treat as raw text
	                ClientMessageHandler.displayMessage(text);
	            }
	        });
	        return null;
	    }
	}

}