package net.chaos.chaosmod.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketSyncJigsaw;
import net.chaos.chaosmod.tileentity.TileEntityJigsaw;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITabCompleter;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiJigsaw extends GuiScreen implements ITabCompleter {
    private GuiTextField poolField;
    private GuiTextField attachementTypeField;
    private GuiTextField turnsIntoField;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    private TileEntityJigsaw tileJigsaw;

	public GuiJigsaw(TileEntityJigsaw tileentity) {
		this.tileJigsaw = tileentity;
	}
	
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
        this.doneBtn = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done")));
        this.cancelBtn = this.addButton(new GuiButton(1, this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel")));

		int y = 50;
        this.poolField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 150, y, 300, 20);
        this.poolField.setMaxStringLength(256);
        poolField.setText(this.tileJigsaw.getPool());

        this.attachementTypeField = new GuiTextField(3, this.fontRenderer, this.width / 2 - 150, y + 20 * 2, 300, 20);
        this.attachementTypeField.setMaxStringLength(256);
        this.attachementTypeField.setText(this.tileJigsaw.getAttachementType());
        
        this.turnsIntoField = new GuiTextField(4, this.fontRenderer, this.width / 2 - 150, y + 20 * 3 + 10 * 2, 300, 20);
        this.turnsIntoField.setMaxStringLength(256);
        this.turnsIntoField.setText(this.tileJigsaw.getTurnsInto());
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (!button.enabled) return;
		
		if (button.id == 0) {
			this.tileJigsaw.setPool(this.poolField.getText());
			this.tileJigsaw.setAttachementType(this.attachementTypeField.getText());
			this.tileJigsaw.setTurnsInto(this.turnsIntoField.getText());

			this.sendToServer(1);
			this.mc.displayGuiScreen(null);
		}
		
		if (button.id == 1) {
			this.mc.displayGuiScreen(null);
		}
	}
	
	private <T extends IMessage> boolean sendToServer(int id) {
		if (id == 1) {
			NBTTagCompound tag = tileJigsaw.getUpdateTag();
			String pool = tag.getString("pool");
			Main.getLogger().info("pool: {}", pool);
			PacketManager.network.sendToServer(new PacketSyncJigsaw(tag));
		}
		return true;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();

		this.drawString(this.fontRenderer, "Target Pool:", this.width / 2 - 150, 40, 0xa0a0a0);

		this.drawString(this.fontRenderer, "Attachement type:", this.width / 2 - 150, 40 + 20 * 2, 0xa0a0a0);

		this.drawString(this.fontRenderer, "Turns into:", this.width / 2 - 150, 40 + 80, 0xa0a0a0);

		this.poolField.drawTextBox();
		this.attachementTypeField.drawTextBox();
		this.turnsIntoField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void setCompletions(String... newCompletions) {
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.poolField.getVisible())
        {
            this.poolField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.attachementTypeField.getVisible())
        {
            this.attachementTypeField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.turnsIntoField.getVisible())
        {
            this.turnsIntoField.mouseClicked(mouseX, mouseY, mouseButton);
        }
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {

        if (this.poolField.getVisible())
        {
            this.poolField.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.attachementTypeField.getVisible())
        {
            this.attachementTypeField.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.turnsIntoField.getVisible())
        {
            this.turnsIntoField.textboxKeyTyped(typedChar, keyCode);
        }

        if (keyCode != Keyboard.KEY_RETURN && keyCode != Keyboard.KEY_NUMPADENTER)
        {
            if (keyCode == Keyboard.KEY_ESCAPE)
            {
                this.actionPerformed(this.cancelBtn);
            }
        }
        else
        {
            this.actionPerformed(this.doneBtn);
        }
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}