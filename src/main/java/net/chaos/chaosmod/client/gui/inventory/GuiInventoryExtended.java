package net.chaos.chaosmod.client.gui.inventory;

import java.io.IOException;
import java.util.Collection;

import net.chaos.chaosmod.client.inventory.ContainerAccessory;
import net.chaos.chaosmod.client.inventory.SlotAccessory;
import net.chaos.chaosmod.init.ModSounds;
import net.chaos.chaosmod.sound.ClientSoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInventoryExtended extends GuiInventory {
	public static final ResourceLocation INV_TEXTURE = new ResourceLocation("chaosmod:textures/gui/slot_icon2.png");
	public static final ResourceLocation BUTTON_TEXTURE_ = new ResourceLocation("chaosmod:textures/gui/container/button_icons.png");
	private boolean showAccessorySlot = false;
    private GuiButton toggleButton;
	private Slot hoveredSlot;

	// ############# Music management ################
	// private ISound currentMusic = null;
	// private boolean isMusicPlaying = false;
	// Handled see : sound.ClientSoundHandler.java

	public GuiInventoryExtended(EntityPlayer player) {
        super(player);
        this.allowUserInput = true;
        this.inventorySlots = new ContainerAccessory(player.inventory, player.world.isRemote, player);
    }
	
	@Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(toggleButton = new GuiButton(99, this.guiLeft + 59, this.guiTop + 8, 15, 12, "") {
        	@Override
        	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

        		if (this.visible)
        		{
        			FontRenderer fontrenderer = mc.fontRenderer;
        			mc.getTextureManager().bindTexture(BUTTON_TEXTURE_);
        			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        			int i = this.getHoverState(this.hovered);
        			GlStateManager.enableBlend();
        			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        			this.drawTexturedModalRect(this.x, this.y, 182, 24, this.width, this.height);
        			if (this.hovered) {
        				this.drawTexturedModalRect(this.x, this.y, 166, 24, this.width, this.height);
        			}
        			this.mouseDragged(mc, mouseX, mouseY);
        			int j = 14737632;

        			if (packedFGColour != 0)
        			{
        				j = packedFGColour;
        			}
        			else
        				if (!this.enabled)
        				{
        					j = 10526880;
        				}
        				else if (this.hovered)
        				{
        					j = 16777120;
        				}

        			this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        		}
        		// super.drawButton(mc, mouseX, mouseY, partialTicks);

        	}
        });
        this.addButton(new GuiButton(100, this.guiLeft + 140, this.guiTop + 5, 30, 20, "P")); // TODO : texture the button
        this.addButton(new GuiButton(101, this.guiLeft + 140, this.guiTop + 5 + 20 * 2, 30, 20, "C")); // TODO : texture the button
        this.addButton(new GuiButton(102, this.guiLeft + 140, this.guiTop + 5 + 20 * 3, 30, 20, "F")); // TODO : texture the button
        this.addButton(new GuiButton(103, this.guiLeft + 140, this.guiTop + 5 + 20 * 4, 30, 20, "H")); // TODO : texture the button
        this.addButton(new GuiButton(104, this.guiLeft + 140, this.guiTop + 5 + 20 * 6, 30, 20, "Z")); // TODO : texture the button
        this.addButton(new GuiButton(105, this.guiLeft + 140, this.guiTop + 5 + 20 * 7, 30, 20, "BR")); // TODO : texture the button
        this.addButton(new GuiButton(106, this.guiLeft + 140, this.guiTop + 5 + 20 * 8, 30, 20, "BC")); // TODO : texture the button
        this.addButton(new GuiButton(107, this.guiLeft + 140, this.guiTop + 5 + 20 * 9, 30, 20, "G")); // TODO : texture the button
        this.addButton(new GuiButton(108, this.guiLeft + 140, this.guiTop + 5 + 20 * 10, 30, 20, "HP")); // TODO : texture the button
    }
	
	private void resetGuiLeft()
	{
		this.guiLeft = (this.width - this.xSize) / 2;
	}
	
	@Override 
	public void updateScreen()
	{
		updateActivePotionEffects();
		resetGuiLeft();
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // Optional: Draw custom icon behind your slot
        if (showAccessorySlot) {
        	this.mc.getTextureManager().bindTexture(INV_TEXTURE);
        	drawModalRectWithCustomSizedTexture(
        	    this.guiLeft,  // x
        	    this.guiTop - 24,   // y
        	    0, 0,                    // u, v
        	    64, 24,                  // width, height
        	    64, 24                   // actual texture size
        	);
        }
        // this.drawTexturedModalRect(this.guiLeft - 12 - (32 / 2), this.guiTop - 16 - (32/2), 0, 0, 32, 32);
    }
    
    @Override
    protected void updateActivePotionEffects() {
        Collection<PotionEffect> effects = this.mc.player.getActivePotionEffects();
        this.hasActivePotionEffects = false;  // prevent vanilla drawing

        if (!effects.isEmpty()) {
            this.guiLeft = (this.width - this.xSize) / 2;  // shift GUI right to make room
        } else {
            this.guiLeft = (this.width - this.xSize) / 2;
        }
    }

    @Override
    protected void renderHoveredToolTip(int p_191948_1_, int p_191948_2_)
    {
    	super.renderHoveredToolTip(p_191948_1_, p_191948_2_);
    }
    
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	// Set visibility before drawing
        for (Slot slot : this.inventorySlots.inventorySlots) {
            if (slot instanceof SlotAccessory) {
                ((SlotAccessory) slot).visible = this.showAccessorySlot; // your toggle
            }
        }

        // Prevent interaction with hidden slots
        Slot hoveredSlot = this.getSlotUnderMouse();
        if (hoveredSlot instanceof SlotAccessory && !((SlotAccessory) hoveredSlot).visible) {
            this.hoveredSlot = null;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        // Skip tooltip for hidden accessory slot
        if (!(hoveredSlot instanceof SlotAccessory && !((SlotAccessory) hoveredSlot).visible)) {
            renderHoveredToolTip(mouseX, mouseY);
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Slot slot = this.getSlotUnderMouse();
        if (slot instanceof SlotAccessory && !((SlotAccessory) slot).visible) {
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    // TODO : add a switch statement or something else based on a registry
    protected void actionPerformed(GuiButton button) throws IOException {
    	if (button.id == 99) {
            showAccessorySlot = !showAccessorySlot;
        } else if (button.id == 100) {
        	System.out.println("PLAYING putin MUSIC");
        	ClientSoundHandler.toggleMusic(ModSounds.PUTIN_WIDE_WALK); // FIXME : do it client-side only
        } else if (button.id == 101) {
        	System.out.println("PLAYING chainsawman op");
        	ClientSoundHandler.toggleMusic(ModSounds.CHAINSAWMAN_OP); // FIXME : do it client-side only
        } else if (button.id == 102) {
        	System.out.println("PLAYING fire force");
        	ClientSoundHandler.toggleMusic(ModSounds.FIRE_FORCE_OP); // FIXME : do it client-side only
        } else if (button.id == 103) {
        	System.out.println("PLAYING highest op");
        	ClientSoundHandler.toggleMusic(ModSounds.HIGHEST_OP); // FIXME : do it client-side only
        } else if (button.id == 104) {
        	System.out.println("Zankyou Sankya op");
        	ClientSoundHandler.toggleMusic(ModSounds.ZANKYOU_SANKYA); // FIXME : do it client-side only
        } else if (button.id == 105) {
        	System.out.println("Black rover op");
        	ClientSoundHandler.toggleMusic(ModSounds.BLACK_ROVER); // FIXME : do it client-side only
        } else if (button.id == 106) {
        	System.out.println("Black catcher op");
        	ClientSoundHandler.toggleMusic(ModSounds.BLACK_CATCHER); // FIXME : do it client-side only
        } else if (button.id == 107) {
        	System.out.println("Grandeur op");
        	ClientSoundHandler.toggleMusic(ModSounds.GRANDEUR); // FIXME : do it client-side only
        } else if (button.id == 108) {
        	System.out.println("Hollow purple op");
        	ClientSoundHandler.toggleMusic(ModSounds.HOLLOW_PURPLE); // FIXME : do it client-side only
        } else {
            super.actionPerformed(button);
        }
    }

    /*private void drawCustomActivePotionEffects() {
        // You can copy and customize the logic from the original drawActivePotionEffects()
        // Here is a simplified example that draws potion icons and duration:

        // Get player's active potion effects
        Collection<PotionEffect> effects = this.mc.player.getActivePotionEffects();

        if (effects.isEmpty()) {
            return;
        }

        int x = this.guiLeft - 120;  // Draw left of the inventory GUI
        int y = this.guiTop + 32;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        for (PotionEffect effect : effects) {
            Potion potion = effect.getPotion();

            // Draw the potion icon background
            this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
            this.drawTexturedModalRect(x, y, 0, 166, 140, 32);

            // Draw potion icon if available
            if (potion.hasStatusIcon()) {
                int iconIndex = potion.getStatusIconIndex();
                this.drawTexturedModalRect(x + 6, y + 7, 18 + (iconIndex % 8) * 18, 198 + (iconIndex / 8) * 18, 18, 18);
            }

            // Draw potion name
            String potionName = I18n.format(potion.getName());
            if (effect.getAmplifier() > 0) {
                potionName = potionName + " " + I18n.format("enchantment.level." + (effect.getAmplifier() + 1));
            }
            this.fontRenderer.drawStringWithShadow(potionName, x + 10 + 18, y + 6, 0xFFFFFF);

            // Draw potion duration
            String duration = Potion.getPotionDurationString(effect, 1.0F);
            this.fontRenderer.drawStringWithShadow(duration, x + 10 + 18, y + 6 + 10, 0x7F7F7F);

            y += 32;  // Move down for next effect
        }
    }*/

}
