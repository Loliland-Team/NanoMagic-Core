package lolimods.adds.lolicore;

import lolimods.adds.lolicore.Virtue;
import lolimods.adds.lolicore.block.LCBlock;
import lolimods.adds.lolicore.block.LCBlockStated;
import lolimods.adds.lolicore.block.state.IBlockModelMapper;
import lolimods.adds.lolicore.gui.GuiIdentity;
import lolimods.adds.lolicore.gui.LCContainer;
import lolimods.adds.lolicore.gui.LCGuiHandler;
import lolimods.adds.lolicore.item.LCItem;
import lolimods.adds.lolicore.tile.LCTileEntity;
import lolimods.adds.lolicore.util.LazyConstant;
import lolimods.adds.lolicore.util.format.FormatUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Registrar {
	private final Map<Class<? extends LCTileEntity>, Virtue> tileVirtueTable;
	private final Map<Class<? extends LCContainer>, Virtue> containerVirtueTable;
	@Nullable
	private Virtue bound;
	private final List<LCBlock> virtueBlocks = new LinkedList<>();
	private final List<LCItem> virtueItems = new LinkedList<>();
	private final List<Item> rqItems = new LinkedList<>();
	private final List<LCBlock> rqBlocks = new LinkedList<>();
	private final List<TileRegistration> rqTileEntities = new LinkedList<>();
	private final List<SoundEvent> rqSounds = new LinkedList<>();


	public Registrar() {
		this.tileVirtueTable = new HashMap<>();
		this.containerVirtueTable = new HashMap<>();
	}

	public Virtue lookUpTileVirtue(Class<? extends LCTileEntity> clazz) {
		return tileVirtueTable.get(clazz);
	}

	public Virtue lookUpContainerVirtue(Class<? extends LCContainer> clazz) {
		return containerVirtueTable.get(clazz);
	}

	public void begin(Virtue virtue) {
		if (bound != null) {
			throw new IllegalStateException(String.format("Could not bind virtue %s because %s was already bound!", virtue.getModId(), getBound().getModId()));
		}
		this.bound = virtue;
	}

	public void end() {
		if (bound == null) throw new IllegalStateException("No virtue is bound!");
		virtueItems.forEach(LCItem::postInit);
		virtueItems.clear();
		virtueBlocks.forEach(LCBlock::postInit);
		virtueBlocks.clear();
		this.bound = null;
	}

	public Virtue getBound() {
		if (bound == null) throw new IllegalStateException("No virtue is bound!");
		return bound;
	}

	public void queueItemReg(Item item) {
		rqItems.add(item);
		if (item instanceof LCItem) virtueItems.add((LCItem)item);
	}

	public void queueBlockReg(LCBlock block) {
		rqBlocks.add(block);
		virtueBlocks.add(block);
	}

	void queueTileEntityReg(String modId, String className) {
		rqTileEntities.add(new TileRegistration(modId, className));
	}

	public void queueItemModelReg(LCItem item, int meta, String model, String variant) {
	}

	public void queueItemModelReg(LCItem item, int meta, String model) {
	}

	public void queueItemModelReg(LCItem item, String model) {
	}

	public void queueItemBlockModelReg(LCBlock block, int meta, String model, String variant) {
	}

	public void queueItemBlockModelReg(LCBlock block, int meta, String model) {
	}

	public void queueItemBlockModelReg(LCBlock block, String model) {
	}

	public void queueBlockStateMapperReg(LCBlockStated block, IBlockModelMapper mapper) {
	}

	public void queueItemColourHandlerReg(IItemColor handler, Item... items) {
	}

	public void queueBlockColourHandlerReg(IBlockColor handler, LCBlock... blocks) {
	}

	public void onRegisterColourHandlers() {
	}

	public void queueSoundEventReg(SoundEvent sound) {
		rqSounds.add(sound);
	}

	public <T extends TileEntity> void queueTESRReg(Class<T> clazz, TileEntitySpecialRenderer<T> renderer) {
	}

	public <T extends Container> void queueGuiServerReg(GuiIdentity<T, ?> identity, LCGuiHandler.IContainerFactory<T> factory) {
		getBound().markUsesContainers();
		getBound().getGuiHandler().registerServerGui(identity, factory);
		containerVirtueTable.put(identity.getContainerClass(), getBound());
	}

	public <S extends Container, C> void queueGuiClientReg(GuiIdentity<S, C> identity, LCGuiHandler.IGuiFactory<S, C> factory) {
	}

	protected void hookEvents() {
		MinecraftForge.EVENT_BUS.register(new RegistrarHook());
	}

	private class RegistrarHook {
		@SubscribeEvent
		public void onRegisterItems(RegistryEvent.Register<Item> event) {
			rqItems.forEach(event.getRegistry()::register);
		}

		@SubscribeEvent
		@SuppressWarnings("unchecked")
		public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
			rqBlocks.forEach(event.getRegistry()::register);
			rqTileEntities.forEach(t -> {
				t.register();
				tileVirtueTable.put(t.clazz.get(), t.virtue.get());
			});
		}

		@SubscribeEvent
		public void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
			rqSounds.forEach(event.getRegistry()::register);
		}
	}

	private static class TileRegistration {
		private final LazyConstant<Virtue> virtue;
		@SuppressWarnings("rawtypes")
		private final LazyConstant<Class> clazz;

		TileRegistration(String modId, String className) {
			this.virtue = new LazyConstant<>(() -> Virtue.forMod(modId));
			this.clazz = new LazyConstant<>(() -> {
				try {
					return Class.forName(className);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid tile entity class: " + className, e);
				}
			});
		}

		@SuppressWarnings("unchecked")
		void register() {
			GameRegistry.registerTileEntity((Class<? extends TileEntity>)clazz.get(), virtue.get().newResourceLocation(FormatUtils.formatClassName(clazz.get())));
			virtue.get().markUsesTileEntities();
		}
	}
}
