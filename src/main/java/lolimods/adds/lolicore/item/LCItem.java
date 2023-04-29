package lolimods.adds.lolicore.item;

import lolimods.adds.lolicore.LoliCore;
import net.minecraft.item.Item;

public class LCItem extends Item {
	private final String internalName;

	public LCItem(String name) {
		this.internalName = name;
		initName();
		initRegistration();
	}

	public void postInit() {
		initModel();
		initCreativeTab();
	}

	protected void initName() {
		setTranslationKey(LoliCore.PROXY.getRegistrar().getBound().prefix(getInternalName()));
	}

	protected void initRegistration() {
		setRegistryName(LoliCore.PROXY.getRegistrar().getBound().newResourceLocation(getInternalName()));
		LoliCore.PROXY.getRegistrar().queueItemReg(this);
	}

	protected void initModel() {
		LoliCore.PROXY.getRegistrar().queueItemModelReg(this, getInternalName());
	}

	protected void initCreativeTab() {
		LoliCore.PROXY.getRegistrar().getBound().setCreativeTabFor(this);
	}

	public String getInternalName() {
		return internalName;
	}
}
