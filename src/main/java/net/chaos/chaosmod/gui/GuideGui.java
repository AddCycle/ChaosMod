package net.chaos.chaosmod.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.text.format.colors.ColorEnum;
import util.text.format.style.StyleEnum;

@SideOnly(Side.CLIENT)
public class GuideGui extends GuiScreen {

	protected int xSize = 178;
	protected int ySize = 78;
	private Item item;
	protected int guiLeft;
	protected int guiTop;

	private int pageIndex;
	private float currentScroll;
	
	// new variables
	private TextComponentTranslation translator = new TextComponentTranslation("text.guide.title");
	private ColorEnum current_color = ColorEnum.AQUA;


	public GuideGui(int index)
	{
		pageIndex = index;
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
		int k = 240;
		int l = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		RenderHelper.disableStandardItemLighting();
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		RenderHelper.enableGUIStandardItemLighting();
		InventoryPlayer inventoryplayer = this.mc.player.inventory;

		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Random rand = new Random();
		//GlStateManager.color(rand.nextInt(10), rand.nextInt(10), rand.nextInt(10));
		// FIXME : each page can have it style so make sure to have one texture per page
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/guide/guide_book.png"));
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, 158);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		int droite = 390; // idem
		int haut = 200; // suffit d'incrementer la pos
		int x = (this.width - droite) / 2;
		int y = (this.height - haut) / 2;
		int x_ = (this.width - droite + 64) / 2;
		/*this.buttonList.add(new GuiButton(this.buttonList.size(), x + 2, y - 17, 18, 18, "<-")
		{
			public void mouseReleased(int mouseX, int mouseY)
			{
				mc.displayGuiScreen(new GuideGui(MathHelper.clamp(GuideGui.this.pageIndex - 1, 0, GuideGui.this.getSize())));
			}
		});

		this.buttonList.add(new GuiButton(this.buttonList.size(), x + 20, y - 17, 18, 18, "->")
		{
			public void mouseReleased(int mouseX, int mouseY)
			{
				mc.displayGuiScreen(new GuideGui(MathHelper.clamp(net.chaos.chaosmod.gui.GuideGui.this.pageIndex + 1, 0, net.chaos.chaosmod.gui.GuideGui.this.getSize())));
			}
		});*/
			this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/blocks/allemanite_ore.png"));
			Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 32, 32, 32, 32);
			this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/blocks/oxonium_ore.png"));
			Gui.drawModalRectWithCustomSizedTexture(x_, y, 0, 0, 32, 32, 32, 32);

	    	this.drawCenteredString(fontRenderer, current_color + "" + StyleEnum.BOLD + translator.getUnformattedComponentText(), 88, 8, new Color(0, 0, 255).getRGB());
			// this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	    	// debug : System.out.println("Title1 : " + translator.getUnformattedText());
	    	// debug : System.out.println("Key : " + translator.getKey());

	    	/*for(int i = 1; i <= 5; ++i)
	    	{
	    		this.drawCenteredString(fontRenderer, I18n.translateToLocal("ib.line" + i + ".p" + this.pageIndex), 88, 9 + i * 18, new Color(120, 120, 120).getRGB());
	    	}*/
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
            break;
        case 2:
        	current_color = ColorEnum.RED;
        	break;
        case 3:
        	current_color = ColorEnum.LIGHT_PURPLE;
        	break;
        case 18:
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null)
            {
                this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
            }
            break;
        case 49:
        	current_color = ColorEnum.values()[rand.nextInt(15)];
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

	private int getSize()
	{
		return 9;
	}
}