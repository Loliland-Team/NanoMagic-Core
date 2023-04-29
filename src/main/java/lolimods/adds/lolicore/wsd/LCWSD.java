package lolimods.adds.lolicore.wsd;

import lolimods.adds.lolicore.Virtue;
import lolimods.adds.lolicore.util.data.INbtSerializable;
import lolimods.adds.lolicore.wsd.IWSDIdentity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public abstract class LCWSD extends WorldSavedData implements INbtSerializable {
	public LCWSD(Virtue virtue, IWSDIdentity<?> identity) {
		super(String.format("%s:%s_%s", virtue.getModId(), identity.getPrefix(), identity.getIdentifier()));
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		deserNBT(data);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		serNBT(data);
		return data;
	}
}
