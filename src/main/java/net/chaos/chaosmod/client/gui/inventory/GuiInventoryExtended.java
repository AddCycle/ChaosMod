package net.chaos.chaosmod.client.gui.inventory;

import java.io.IOException;
import java.util.Collection;

import net.chaos.chaosmod.client.inventory.ContainerAccessory;
import net.chaos.chaosmod.client.inventory.SlotAccessory;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInventoryExtended extends GuiInventory {
	public static final ResourceLocation INV_TEXTURE = new ResourceLocation("chaosmod:textures/gui/slot_icon2.png");
	private boolean showAccessorySlot = true;
    private GuiButton toggleButton;
	private Slot hoveredSlot;

	public GuiInventoryExtended(EntityPlayer player) {
        super(player);
        this.allowUserInput = true;
        this.inventorySlots = new ContainerAccessory(player.inventory, player.world.isRemote, player);
    }
	
	@Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(toggleButton = new GuiButton(99, this.guiLeft + 30, this.guiTop + 5, 20, 20, ""));
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
    protected void actionPerformed(GuiButton button) throws IOException {
    	if (button.id == 99) {
            showAccessorySlot = !showAccessorySlot;
        } else {
            super.actionPerformed(button);
        }
    }

    private void drawCustomActivePotionEffects() {
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
    }

}
