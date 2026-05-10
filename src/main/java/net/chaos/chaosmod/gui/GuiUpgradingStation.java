package net.chaos.chaosmod.gui;

import net.chaos.chaosmod.inventory.ContainerUpgradingStation;
import net.chaos.chaosmod.tileentity.TileEntityUpgradingStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class GuiUpgradingStation extends GuiContainer {
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID, "textures/gui/container/upgrading_station.png");
	private TileEntityUpgradingStation station;
	private InventoryPlayer playerInventory;

	public GuiUpgradingStation(EntityPlayer player, TileEntityUpgradingStation tileEntity) {
		super(new ContainerUpgradingStation(player.inventory, tileEntity, player.world, tileEntity.getPos()));
		this.playerInventory = player.inventory;
		station = tileEntity;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = "Upgrade Gear"; // FIXME : minor but should be read from tileentity's name + add localization
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
}