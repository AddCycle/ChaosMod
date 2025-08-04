package net.chaos.chaosmod.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import net.chaos.chaosmod.commands.GuideCommand;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class GuideGui extends GuiScreen implements IInteractionObject {
	protected int xSize = 400; // LARGE/HUGE
	protected int ySize = 256;
	protected int guiLeft;
	protected int guiTop;
	protected int currentScroll;

	// new variables
	private TextComponentTranslation translator = new TextComponentTranslation("text.guide.title");
	private TextFormatting current_color = TextFormatting.AQUA;


	public GuideGui(int index)
	{
	}

	public void initGui()
	{
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		super.drawScreen(mouseX, mouseY, partialTicks);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)i, (float)j, 0.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableRescaleNormal();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		RenderHelper.disableStandardItemLighting();
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		RenderHelper.enableGUIStandardItemLighting();

		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		// GlStateManager.color(rand.nextInt(10), rand.nextInt(10), rand.nextInt(10));
		// FIXME : each page can have it style so make sure to have one texture per page
		// this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/guide/guide_book_large.png"));
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/guide/ultimate_guide_book.png"));
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize + 5) / 2;
		// this.drawTexturedModalRect(i, j, 0, 0, this.xSize, 158); MEDIUM/NORMAL
		// this.drawTexturedModalRect(i, j, 0, 0, this.xSize, 256); // LARGE
		Gui.drawModalRectWithCustomSizedTexture(i, j, 0, 0, 400, 250, 400, 250);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		int droite = 355; // idem
		int haut = 190; // suffit d'incrementer la pos
		int x = (this.width - droite) / 2;
		int y = (this.height - haut) / 2;
		int x_1 = (this.width - droite + 64) / 2;
		int x_2 = (this.width - droite + 64 + 32) / 2;
		// IDEE : dimension solaire -> minerais SOLARITE avec des crafts a partir du soleil un peu comme dans les cites d'or
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/blocks/oxonium_ore.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 32, 32, 32, 32);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/blocks/allemanite_ore.png"));
		Gui.drawModalRectWithCustomSizedTexture(x_1, y, 0, 0, 32, 32, 32, 32);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/blocks/enderite_ore.png"));
		Gui.drawModalRectWithCustomSizedTexture(x_2, y, 0, 0, 32, 32, 32, 32);
		GlStateManager.pushMatrix();
		GlStateManager.scale(2.0F, 2.0F, 1.0F); // scale by 2x
		GlStateManager.popMatrix();

		int origin_x = 49;
		int origin_y = 27;
		if ((mouseX >= origin_x && mouseX <= origin_x + 32) && (mouseY >= origin_y && mouseY <= origin_y + 32))
			this.renderToolTip(new ItemStack(ModBlocks.OXONIUM_ORE), mouseX + 5, mouseY + 10);
		if ((mouseX >= origin_x + 32 && mouseX <= origin_x + 64) && (mouseY >= origin_y && mouseY <= origin_y + 32))
			this.renderToolTip(new ItemStack(ModBlocks.ALLEMANITE_ORE), mouseX + 5, mouseY + 10);
		if ((mouseX >= origin_x + 64 && mouseX <= origin_x + 64 + 32) && (mouseY >= origin_y && mouseY <= origin_y + 32))
			this.renderToolTip(new ItemStack(ModBlocks.OXONIUM_BLOCK), mouseX + 5, mouseY + 10);
		this.drawCenteredString(fontRenderer, TextFormatting.RESET + "" + current_color + "" + TextFormatting.BOLD + translator.getUnformattedComponentText(), 190, 13, new Color(0, 0, 255).getRGB());
	}

	@Override
	public void drawBackground(int tint) {
		super.drawBackground(tint);
	}

	@Override
	public void onResize(Minecraft mcIn, int w, int h)
	{
		this.setWorldAndResolution(mcIn, w, h);
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		Random rand = new Random();
		switch (keyCode)
		{
		case 1:
			this.mc.displayGuiScreen((GuiScreen)null);
			if (this.mc.currentScreen == null)
			{
				this.mc.setIngameFocus();
			}
			GuideCommand.is_open = false;
			break;
		case 2:
			current_color = TextFormatting.RED;
			break;
		case 3:
			current_color = TextFormatting.LIGHT_PURPLE;
			break;
		case 18:
			this.mc.displayGuiScreen((GuiScreen)null);
			if (this.mc.currentScreen == null)
			{
				this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
			}
			break;
		case 49:
			current_color = TextFormatting.values()[rand.nextInt(15)];
			break;
		default:
			System.out.println("No such key");
			break;
		}
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}

	public void updateScreen()
	{
		super.updateScreen();

		if (!this.mc.player.isEntityAlive() || this.mc.player.isDead)
		{
			this.mc.player.closeScreen();
		}
	}

	@Override
	public String getName() {
		return "chaosmod:guide_gui";
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("Guide Gui");
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return "chaosmod:guide_gui";
	}
}