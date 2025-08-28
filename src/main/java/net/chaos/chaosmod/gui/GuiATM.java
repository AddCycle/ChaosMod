package net.chaos.chaosmod.gui;

import net.chaos.chaosmod.inventory.ATMContainer;
import net.chaos.chaosmod.tileentity.TileEntityATM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class GuiATM extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/container/atm_gui.png");

	public GuiATM(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 176;
        this.ySize = 166;
	}

	public GuiATM(InventoryPlayer inventory, TileEntityATM te, EntityPlayer player) {
		super(new ATMContainer(inventory, te, player));
		this.xSize = 176;
        this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		textureManager.bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	@Override
	public void drawBackground(int tint) {
		// TODO Auto-generated method stub
		super.drawBackground(tint);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
