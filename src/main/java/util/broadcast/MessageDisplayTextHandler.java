package util.broadcast;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDisplayTextHandler implements IMessageHandler<MessageDisplayText, IMessage>{
	@Override
    public IMessage onMessage(MessageDisplayText message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            ClientMessageHandler.displayMessage(message.getText());
        });
        return null;
    }

}
