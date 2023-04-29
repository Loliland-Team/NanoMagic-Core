package lolimods.adds.lolicore.client.gui;

import lolimods.adds.lolicore.client.gui.IScreenDrawable;
import lolimods.adds.lolicore.client.gui.component.GuiComponent;
import lolimods.adds.lolicore.client.gui.component.GuiComponentManager;
import lolimods.adds.lolicore.gui.LCContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class LCGuiContainer extends GuiContainer implements IScreenDrawable {
	protected final int sizeX;
	protected final int sizeY;
	@Nullable
	private final ResourceLocation bg;
	private final GuiComponentManager components;
	private int posX;
	private int posY;
	private float partialTicks;

	public LCGuiContainer(LCContainer container, @Nullable ResourceLocation bg, int sizeX, int sizeY) {
		super(container);
		this.bg = bg;
		this.sizeX = this.xSize = sizeX;
		this.sizeY = this.ySize = sizeY;
		this.components = new GuiComponentManager(this);
	}

	public LCGuiContainer(LCContainer container, @Nullable ResourceLocation bg) {
		this(container, bg, 176, 166);
	}

	public LCGuiContainer(LCContainer container) {
		this(container, null);
	}

	@Override
	public void initGui() {
		super.initGui();
		posX = (this.width - this.sizeX) / 2;
		posY = (this.height - this.sizeY) / 2;
	}

	public int getOffsetX() {
		return posX;
	}

	public int getOffsetY() {
		return posY;
	}

	@Override
	public void addComponent(GuiComponent comp) {
		components.register(comp);
	}

	@Override
	public void drawScreen(int mX, int mY, float partialTicks) {
		super.drawScreen(mX, mY, partialTicks);
		renderHoveredToolTip(mX, mY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mX, int mY) {
		this.partialTicks = partialTicks;
		drawBackground(partialTicks, mX - posX, mY - posY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mX, int mY) {
		drawForeground(partialTicks, mX - posX, mY - posY);
		components.draw(partialTicks, mX - posX, mY - posY);
		drawOverlay(partialTicks, mX - posX, mY - posY);
	}

	@Override
	public void drawBackground(float partialTicks, int mX, int mY) {
		drawDefaultBackground();
		if (bg != null) {
			GlStateManager.color(1F, 1F, 1F, 1F);
			mc.renderEngine.bindTexture(bg);
			drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
		}
	}

	@Override
	public void drawForeground(float partialTicks, int mX, int mY) {
		drawPlayerInventoryName();
	}

	@Override
	public void drawOverlay(float partialTicks, int mX, int mY) {
		// NO-OP
	}

	protected void drawContainerName(String name) {
		fontRenderer.drawString(name, 8, 6, DEF_TEXT_COL);
		GlStateManager.color(1F, 1F, 1F);
	}

	@SuppressWarnings("deprecation")
	protected void drawPlayerInventoryName() {
		fontRenderer.drawString(I18n.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, DEF_TEXT_COL);
	}

	protected void drawString(String string, int x, int y, int colour, boolean shadow) {
		Minecraft.getMinecraft().fontRenderer.drawString(string, x, y, colour, shadow);
	}

	protected void drawString(String string, int x, int y, int colour) {
		drawString(string, x, y, colour, false);
	}

	protected void drawTooltip(String string, int x, int y) {
		drawHoveringText(string, x, y);
		RenderHelper.enableGUIStandardItemLighting();
	}

	protected void drawTooltip(List<String> lines, int x, int y) {
		drawHoveringText(lines, x, y);
		RenderHelper.enableGUIStandardItemLighting();
	}

	@Override
	protected void mouseClicked(int mX, int mY, int button) throws IOException {
		if (components.handleMouseClick(mX - posX, mY - posY, button)) super.mouseClicked(mX, mY, button);
	}

	@Override
	protected void mouseClickMove(int mX, int mY, int button, long dragTime) {
		if (components.handleMouseDrag(mX - posX, mY - posY, button, dragTime)) {
			super.mouseClickMove(mX, mY, button, dragTime);
		}
	}

	@Override
	protected void keyTyped(char typed, int keyCode) throws IOException {
		if (components.handleKeyTyped(typed, keyCode)) super.keyTyped(typed, keyCode);
	}
}
