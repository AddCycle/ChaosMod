package util.broadcast;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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

}
