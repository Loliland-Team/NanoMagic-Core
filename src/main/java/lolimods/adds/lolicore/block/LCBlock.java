package lolimods.adds.lolicore.block;

import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.item.LCItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class LCBlock extends Block implements ITileEntityProvider {
	private final String internalName;
	private final LCItemBlock itemBlock;
	@Nullable
	private BiFunction<World, Integer, ? extends TileEntity> tileFactory;

	public LCBlock(String name, Material material) {
		super(material);
		this.internalName = name;
		initName();
		this.itemBlock = initItemBlock();
		initRegistration();
	}

	public void postInit() {
		initModel();
		initCreativeTab();
	}

	protected void initName() {
		setTranslationKey(LoliCore.PROXY.getRegistrar().getBound().prefix(getInternalName()));
	}

	protected LCItemBlock initItemBlock() {
		return new LCItemBlock(this);
	}

	protected void initRegistration() {
		ResourceLocation registryName = LoliCore.PROXY.getRegistrar().getBound().newResourceLocation(getInternalName());
		setRegistryName(registryName);
		getItemBlock().setRegistryName(registryName);
		LoliCore.PROXY.getRegistrar().queueBlockReg(this);
		LoliCore.PROXY.getRegistrar().queueItemReg(getItemBlock());
	}

	protected void initModel() {
		LoliCore.PROXY.getRegistrar().queueItemBlockModelReg(this, getInternalName());
	}

	protected void initCreativeTab() {
		LoliCore.PROXY.getRegistrar().getBound().setCreativeTabFor(this);
	}

	protected void setTileFactory(BiFunction<World, Integer, ? extends TileEntity> tileFactory) {
		this.tileFactory = tileFactory;
	}

	public LCItemBlock getItemBlock() {
		return itemBlock;
	}

	public String getInternalName() {
		return internalName;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return tileFactory != null;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return createNewTileEntity(world, getMetaFromState(state));
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return tileFactory != null ? tileFactory.apply(world, meta) : null;
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public <T extends TileEntity> T getTileEntity(IBlockAccess w, BlockPos pos) {
		return (T)w.getTileEntity(pos);
	}
}
