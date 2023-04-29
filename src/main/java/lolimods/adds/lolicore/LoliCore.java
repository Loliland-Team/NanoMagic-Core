package lolimods.adds.lolicore;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = LCConst.MOD_ID, version = "rc-v1.0.0", useMetadata = true, dependencies = "after:lolimagically;")
public class LoliCore {
	@Mod.Instance(LCConst.MOD_ID)
	@SuppressWarnings("NotNullFieldNotInitialized")
	public static LoliCore instance;
	@SidedProxy(clientSide = "lolimods.adds.lolicore.client.LCClientProxy", serverSide = "lolimods.adds.lolicore.LCCommonProxy")
	@SuppressWarnings("NotNullFieldNotInitialized")
	public static LCCommonProxy PROXY;
	@SuppressWarnings("NotNullFieldNotInitialized")
	public static Logger LOGGER;

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		LOGGER = event.getModLog();
		PROXY.onPreInit(event);
	}

	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event) {
		PROXY.onInit(event);
	}

	@Mod.EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		PROXY.onPostInit(event);
	}
}
