package lolimods.adds.lolicore.client;

import com.google.common.collect.Lists;
import lolimods.adds.lolicore.LCCommonProxy;
import lolimods.adds.lolicore.Registrar;
import lolimods.adds.lolicore.client.event.ClientTickHandler;
import lolimods.adds.lolicore.client.event.MultiBlockDebugRenderHandler;
import lolimods.adds.lolicore.client.model.LCModels;
import lolimods.adds.lolicore.tile.LCTileEntity;
import lolimods.adds.lolicore.util.render.shader.ShaderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class LCClientProxy extends LCCommonProxy {
	public LCClientProxy() {
		LCModels.registerModels();
	}

	@Override
	protected Registrar initRegistrar() {
		return new ClientRegistrar();
	}

	@Override
	public void dispatchTileUpdate(LCTileEntity tile) {
		if (!tile.getWorld().isRemote) super.dispatchTileUpdate(tile);
	}

	@Override
	public World getAnySidedWorld() {
		return FMLCommonHandler.instance().getEffectiveSide().isClient() ? Minecraft.getMinecraft().world : super.getAnySidedWorld();
	}

	@Nullable
	@Override
	public World getDimensionWorld(int dim) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			World world = Minecraft.getMinecraft().world;
			return world.provider.getDimension() == dim ? world : null;
		} else {
			return super.getDimensionWorld(dim);
		}
	}

	@Override
	protected void onPreInit(FMLPreInitializationEvent event) {
		ShaderUtils.registerReloadHook();
		super.onPreInit(event);
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new MultiBlockDebugRenderHandler());
	}

	@Override
	protected void onInit(FMLInitializationEvent event) {
		super.onInit(event);
		getRegistrar().onRegisterColourHandlers();
	}

	@Override
	protected void onPostInit(FMLPostInitializationEvent event) {
		super.onPostInit(event);
	}
}
