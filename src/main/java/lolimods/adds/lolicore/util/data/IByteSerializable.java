package lolimods.adds.lolicore.util.data;

import lolimods.adds.lolicore.util.data.ByteUtils;

public interface IByteSerializable {
	void serBytes(ByteUtils.Writer data);

	void deserBytes(ByteUtils.Reader data);
}
