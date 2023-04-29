package lolimods.adds.lolicore.gui;

import net.minecraft.inventory.Container;

import javax.annotation.Nullable;

public class GuiIdentity<S extends Container, C> {
	private final String name;
	@Nullable
	private final Class<? extends LCContainer> containerClass;

	public GuiIdentity(String name, @Nullable Class<? extends LCContainer> containerClass) {
		this.name = name;
		this.containerClass = containerClass;
	}

	public String getName() {
		return name;
	}

	@Nullable
	public Class<? extends LCContainer> getContainerClass() {
		return containerClass;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		return o instanceof GuiIdentity && ((GuiIdentity)o).name.equals(name);
	}
}
