package lolimods.adds.lolicore.wsd;

import lolimods.adds.lolicore.Virtue;
import lolimods.adds.lolicore.util.data.INbtSerializable;
import lolimods.adds.lolicore.wsd.IWSDIdentity;
import lolimods.adds.lolicore.wsd.LCWSD;
import net.minecraft.nbt.NBTTagCompound;

public class WSDSerializableWrapper<T extends INbtSerializable> extends LCWSD {
	private T value;

	public WSDSerializableWrapper(Virtue virtue, IWSDIdentity<?> identity, T initialValue) {
		super(virtue, identity);
		this.value = initialValue;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
		markDirty();
	}

	@Override
	public void serNBT(NBTTagCompound tag) {
		value.serNBT(tag);
	}

	@Override
	public void deserNBT(NBTTagCompound tag) {
		value.deserNBT(tag);
	}
}
