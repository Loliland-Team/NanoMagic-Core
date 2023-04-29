package lolimods.adds.lolicore.util.format;

import net.minecraft.util.text.translation.I18n;

public interface ILocalizable {
	String getTranslationKey();

	@SuppressWarnings("deprecation")
	default String getLocalizedName() {
		return I18n.translateToLocal(getTranslationKey());
	}
}
