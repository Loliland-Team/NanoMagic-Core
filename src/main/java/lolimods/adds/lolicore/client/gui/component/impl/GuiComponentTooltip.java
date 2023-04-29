package lolimods.adds.lolicore.client.gui.component.impl;

import lolimods.adds.lolicore.client.gui.component.GuiComponent;
import lolimods.adds.lolicore.util.render.TextureRegion;

import java.util.function.Supplier;

public class GuiComponentTooltip extends GuiComponent {
	private final TextureRegion texture;
	private final Supplier<String> src;

	public GuiComponentTooltip(int x, int y, TextureRegion texture, Supplier<String> src) {
		super(x, y, texture.getWidth(), texture.getHeight());
		this.texture = texture;
		this.src = src;
	}

	@Override
	public void render(float partialTicks, int mX, int mY, boolean mouseOver) {
		texture.draw(x, y, width, height);
	}

	@Override
	public void renderTooltip(float partialTicks, int mX, int mY) {
		drawTooltip(src.get(), mX, mY);
	}
}
