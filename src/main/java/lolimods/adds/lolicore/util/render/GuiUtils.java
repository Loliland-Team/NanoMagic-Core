package lolimods.adds.lolicore.util.render;

import lolimods.adds.lolicore.util.math.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiUtils {
	public static final ResourceLocation BUTTON_SOUND = new ResourceLocation("gui.button.press");

	public static boolean isMouseOver(int x, int y, int width, int height, int mX, int mY) {
		return mX >= x && mX < x + width && mY >= y && mY < y + height;
	}

	public static boolean isMouseWithin(int x, int y, Vec2i... convexVertices) {
		boolean xgt = false, xlt = false, ygt = false, ylt = false;
		for (Vec2i vertex : convexVertices) {
			if (vertex.getX() <= x) {
				xlt = true;
			} else if (vertex.getX() >= x) {
				xgt = true;
			}
			if (vertex.getY() <= y) {
				ylt = true;
			} else if (vertex.getY() >= y) {
				ygt = true;
			}
			if (xlt && xgt && ylt && ygt) return true;
		}
		return false;
	}

	public static int getFontHeight() {
		return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
	}

	public static int getStringWidth(String string) {
		return Minecraft.getMinecraft().fontRenderer.getStringWidth(string);
	}

	public static void drawRect(int x, int y, int width, int height, int colour) {
		Gui.drawRect(x, y, x + width, y + height, colour);
		GlStateManager.color(1F, 1F, 1F, 1F);
	}
}
