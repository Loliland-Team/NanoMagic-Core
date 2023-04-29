package lolimods.adds.lolicore.util.data;

import net.minecraft.nbt.NBTTagCompound;

public interface INbtSerializable {
	void serNBT(NBTTagCompound tag);

	void deserNBT(NBTTagCompound tag);
}
