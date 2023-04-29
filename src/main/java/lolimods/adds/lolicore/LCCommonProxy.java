package lolimods.adds.lolicore;

import lolimods.adds.lolicore.InitMe;
import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.Registrar;
import lolimods.adds.lolicore.Virtue;
import lolimods.adds.lolicore.event.ModDependentEventBusSubscriber;
import lolimods.adds.lolicore.event.TileEntityDispatchHandler;
import lolimods.adds.lolicore.network.PacketServerSyncTileEntity;
import lolimods.adds.lolicore.recipe.IRecipeManager;
import lolimods.adds.lolicore.recipe.RecipeManager;
import lolimods.adds.lolicore.tile.LCTileEntity;
import lolimods.adds.lolicore.tile.RegisterTile;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.asm.ModAnnotation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class LCCommonProxy {
	private Registrar registrar;
	private RecipeManager recipeManager;
	private TileEntityDispatchHandler teDispatcher = new TileEntityDispatchHandler();

	public LCCommonProxy() {
		registrar = initRegistrar();
		recipeManager = initRecipeManager();
	}

	protected Registrar initRegistrar() {
		return new Registrar();
	}

	@SuppressWarnings("WeakerAccess")
	protected RecipeManager initRecipeManager() {
		return new RecipeManager();
	}

	public Registrar getRegistrar() {
		return registrar;
	}

	public IRecipeManager getRecipeManager() {
		return recipeManager;
	}

	public TileEntityDispatchHandler getTileEntityDispatcher() {
		return teDispatcher;
	}

	public void dispatchTileUpdate(LCTileEntity tile) {
		BlockPos pos = tile.getPos();
		getRegistrar().lookUpTileVirtue(tile.getClass()).getNetworkHandler()
				.sendToAllAround(new PacketServerSyncTileEntity(tile), new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64D));
	}

	public World getAnySidedWorld() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
	}

	@Nullable
	public World getDimensionWorld(int dim) {
		return DimensionManager.getWorld(dim);
	}

	public World getDimensionWorldStrict(int dim) {
		World world = getDimensionWorld(dim);
		if (world == null) {
			throw new IllegalStateException("Nonexistent dimension: " + dim);
		}
		return world;
	}

	protected void onPreInit(FMLPreInitializationEvent event) {
		registrar.hookEvents();
		MinecraftForge.EVENT_BUS.register(teDispatcher);
		processAnnotations(event.getAsmData());
	}

	@SuppressWarnings("unchecked")
	private void processAnnotations(ASMDataTable asmData) {
		Side actualSide = FMLCommonHandler.instance().getSide();

		tile_iter:
		for (ASMDataTable.ASMData target : asmData.getAll(RegisterTile.class.getName())) {
			List<String> deps = (List<String>)target.getAnnotationInfo().get("deps");
			if (deps != null) {
				for (String dep : deps) {
					if (!Loader.isModLoaded(dep)) {
						continue tile_iter;
					}
				}
			}
			getRegistrar().queueTileEntityReg((String)target.getAnnotationInfo().get("value"), target.getClassName());
		}

		mod_dep_sub_iter:
		for (ASMDataTable.ASMData annot : asmData.getAll(ModDependentEventBusSubscriber.class.getName())) {
			Map<String, Object> data = annot.getAnnotationInfo();
			List<Side> acceptedSides = (List<Side>)data.get("side");
			if (acceptedSides != null && !acceptedSides.contains(actualSide)) {
				continue;
			}
			for (String depModId : (List<String>)data.get("dependencies")) {
				if (!Loader.isModLoaded(depModId)) {
					continue mod_dep_sub_iter;
				}
			}
			try {
				MinecraftForge.EVENT_BUS.register(Class.forName(annot.getClassName()));
			} catch (ClassNotFoundException e) {
				LoliCore.LOGGER.warn("Failed to register mod-dependent event subscriber: " + annot.getClassName(), e);
			}
		}
		for (ASMDataTable.ASMData target : asmData.getAll(InitMe.class.getName())) {
			System.out.println("InitMe: " + InitMe.class.getName() + " | Target: " + target);
			List<ModAnnotation.EnumHolder> sides = (List<ModAnnotation.EnumHolder>)target.getAnnotationInfo().get("sides");
			boolean shouldContinue = true;
			if (sides != null) {
				shouldContinue = false;
				for (ModAnnotation.EnumHolder side : sides) {
					if (actualSide == Side.valueOf(side.getValue())) {
						shouldContinue = true;
					}
				}
			}
			if (shouldContinue) {
				String modId = (String)target.getAnnotationInfo().get("value");
				boolean bind = modId != null && !modId.isEmpty();
				if (bind) getRegistrar().begin(Virtue.forMod(modId));
				try {
					String methodName = target.getObjectName();
					methodName = methodName.substring(0, methodName.lastIndexOf('('));
					Class.forName(target.getClassName()).getDeclaredMethod(methodName).invoke(null);
				} catch (Exception e) {
					LoliCore.LOGGER.error("Failed to run initializer {}::{} for virtue {}", target.getClassName(), target.getObjectName(), modId);
					LoliCore.LOGGER.error("", e);
				}
				if (bind) getRegistrar().end();
			}
		}
	}

	protected void onInit(FMLInitializationEvent event) {
	}

	protected void onPostInit(FMLPostInitializationEvent event) {
	}
}
