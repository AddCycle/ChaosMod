package net.chaos.chaosmod.jobs.gui.fisherman;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.network.packets.PacketFishingResult;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.ui.components.GuiUtils;

@SideOnly(Side.CLIENT)
// TODO : handle fishingSpeedBonus to reduce the speed of the caret rn it's random
public class GuiFishingMinigame extends GuiScreen {
	private ItemStack icon;
	private Random rand;

	public int dark = 0xff24292f;
	public int light_dark = 0xff24292f;

	private int caretX, caretY, caretWidth, caretHeight, caretColor;
	private int rectX, rectY, rectWidth, rectHeight, rectColor;

	private int targetRectRandomPos;
	private int totalTargetRange;

	private int tickSpeed = 1; // the higher, the slower (maxTickSpeed at 1)

	private int caretTicks, caretSpeed;
	private int caretDir = 1;

	private boolean stoppedCaret;
	private int closingTicks, closingTime;

	private int score;

	public GuiFishingMinigame() {}

	@Override
	public void initGui() {
		rand = mc.world.rand;
		initRect();
		initCaret();
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Reference.MATHSMOD, "kurayum_rod"));
		icon = new ItemStack(item);
		totalTargetRange = 40;
		targetRectRandomPos = getRandomPosOnRange(rectX + totalTargetRange, rectWidth - totalTargetRange);

		closingTime = 60; // 3 sec
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!stoppedCaret) updateCaret();
		processGuiClosing();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawBackground(0);

		itemRender.renderItemIntoGUI(icon, width / 2, 40);

		drawCenteredString(fontRenderer, "Are you a good fisherman ?", width / 2, 20, 0xffffffff);

		drawRect(rectX, rectY, rectWidth, height / 2 + rectHeight, rectColor);
		drawThirdTargetRect();
		drawSecondaryTargetRect();
		drawPrimaryTargetRect();
		drawCaret();

		renderScore();

		if (closingTicks >= 1) {
			drawCenteredString(fontRenderer, String.format("You got a [%s]", icon.getDisplayName()), width / 2, 50,
					0xff00ff00);
		}
	}

	@Override
	public void drawBackground(int tint) {
		drawRect(0, 0, width, height, dark);
	}

	private void initRect() {
		rectX = 20;
		rectY = height / 2;
		rectWidth = width - 20;
		rectHeight = 20;
		rectColor = 0xffffffff;
	}

	private void initCaret() {
		caretX = rectX;
		caretY = rectY;
		caretWidth = 10;
		caretHeight = rectY + rectHeight;
		caretColor = 0xff0000ff;

		caretSpeed = 2 + rand.nextInt(5); // 2-6
	}

	private void updateCaret() {
		caretTicks++;
		if (caretTicks >= tickSpeed) {
			caretTicks = 0;
			caretX += caretSpeed * caretDir;
			if (caretX <= rectX) {
				caretX = rectX;
				caretDir *= -1;
			} else if (caretX >= rectWidth - caretWidth) {
				caretX = rectWidth - caretWidth;
				caretDir *= -1;
			}
		}
	}

	private void drawCaret() {
		drawRect(caretX, caretY, caretX + caretWidth, caretHeight, caretColor);
	}

	private void drawPrimaryTargetRect() {
		drawRect(targetRectRandomPos - 5, rectY, targetRectRandomPos + 5, rectY + rectHeight,
				0xffff0000);
	}

	private void drawSecondaryTargetRect() {
		drawRect(targetRectRandomPos - 10, rectY, targetRectRandomPos + 10, rectY + rectHeight,
				0xffff7f00);
	}

	private void drawThirdTargetRect() {
		drawRect(targetRectRandomPos - 20, rectY, targetRectRandomPos + 20, rectY + rectHeight,
				0xffffff19);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (!stoppedCaret) super.keyTyped(typedChar, keyCode);

		switch (keyCode) {
			case Keyboard.KEY_SPACE:
				stopCaret();
				break;
			default:
				break;
		}
	}

	private void stopCaret() {
		stoppedCaret = true;
	}

	private void renderScore() {
		if (!stoppedCaret)
			return;

		score = calculateScore();
		drawCenteredString(fontRenderer, String.format("Score: %d", score), width / 2, 30, 0xffffffff);
	}

	private int calculateScore() {
		if (isInsideTarget(5)) return 3;
		if (isInsideTarget(10)) return 2;
		if (isInsideTarget(20)) return 1;
		return 0;
	}

	private int getRandomPosOnRange(int min, int max) {
		assert max >= min;
		return min + rand.nextInt(max - min);
	}

	private void processGuiClosing() {
		if (!stoppedCaret)
			return;

		closingTicks++;
		if (closingTicks == 1) {
		}

		if (closingTicks >= closingTime) {
			GuiUtils.closeCurrentScreen(mc);
		}
	}

	@Override
	public void onGuiClosed() {
		if (!stoppedCaret) return;
		PacketManager.network.sendToServer(new PacketFishingResult(score, mc.player.fishEntity.getEntityId()));
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private boolean isInsideTarget(int range) {
		int caretCenter = caretX + caretWidth / 2;
		return targetRectRandomPos - range <= caretCenter && caretCenter <= targetRectRandomPos + range;
	}
}