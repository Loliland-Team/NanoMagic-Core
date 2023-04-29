package lolimods.adds.lolicore.util.data.serialization;

import lolimods.adds.lolicore.util.data.ByteUtils;
import net.minecraft.nbt.NBTTagCompound;

public interface ISerializationProvider<T> {
	Class<T> getSerializationType();

	void serializeNBT(T obj, String key, NBTTagCompound tag);

	T deserializeNBT(String key, NBTTagCompound tag);

	void serializeBytes(T obj, ByteUtils.Writer data);

	T deserializeBytes(ByteUtils.Reader data);
}
