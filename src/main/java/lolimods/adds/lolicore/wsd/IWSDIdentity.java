package lolimods.adds.lolicore.wsd;

import lolimods.adds.lolicore.wsd.LCWSD;

public interface IWSDIdentity<T extends LCWSD> {
	String getIdentifier();

	Class<T> getType();

	String getPrefix();
}
