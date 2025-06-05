package net.chaos.chaosmod.gui;

import net.chaos.chaosmod.inventory.TrophyContainerBase;
import net.chaos.chaosmod.tileentity.TileEntityTrophyBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class TrophyGui extends GuiContainer {
	private static final ResourceLocation TROPHY_GUI_TEXTURES = new ResourceLocation(Reference.MODID, "textures/gui/container/trophy.png");
    private final InventoryPlayer playerInventory;
    private final IInventory tileTrophy;

	public TrophyGui(InventoryPlayer inventory, TileEntityTrophyBase te) {
		super(new TrophyContainerBase(inventory, te));
		this.playerInventory = inventory;
		this.tileTrophy = te;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TROPHY_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int initial = 4210752;
    	// int color1 = 40703; // light blue
    	int color1 = 0x0000ff;
    	// int color2 = 16751616; // orange
    	// int color2 = 0x0000ff;
    	int color2 = initial;
        String s = this.tileTrophy.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, color1); // furnace label
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, color2); // inventory label
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

}
