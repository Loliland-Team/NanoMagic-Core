package lolimods.adds.lolicore.client.gui;

import lolimods.adds.lolicore.client.gui.component.GuiComponent;

public interface IScreenDrawable {
	int DEF_TEXT_COL = 4210752;

	void addComponent(GuiComponent comp);

	void drawBackground(float partialTicks, int mX, int mY);

	void drawForeground(float partialTicks, int mX, int mY);

	void drawOverlay(float partialTicks, int mX, int mY);
}
