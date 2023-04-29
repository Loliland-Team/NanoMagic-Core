package lolimods.adds.lolicore.block;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.block.LCBlock;
import lolimods.adds.lolicore.block.state.VirtualState;
import lolimods.adds.lolicore.item.LCItemBlock;
import lolimods.adds.lolicore.item.LCItemBlockStated;
import lolimods.adds.lolicore.util.collection.Accrue;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LCBlockStated extends LCBlock {
	@SuppressWarnings("NotNullFieldNotInitialized")
	private List<IProperty<?>> props;
	@SuppressWarnings("NotNullFieldNotInitialized")
	private List<VirtualState> states;
	@SuppressWarnings("NotNullFieldNotInitialized")
	private TObjectIntMap<IBlockState> statesInv;
	@SuppressWarnings("NotNullFieldNotInitialized")
	private List<PropApplicator<?>> volatilePropDefaults;

	public LCBlockStated(String name, Material material) {
		super(name, material);
		setDefaultState(initDefaultState(getBlockState().getBaseState()));
	}

	@SuppressWarnings("WeakerAccess")
	protected void accrueProperties(Accrue<IProperty<?>> props) {
	}

	@SuppressWarnings("WeakerAccess")
	protected void accrueVolatileProperties(Accrue<IProperty<?>> props) {
	}

	@SuppressWarnings("WeakerAccess")
	protected void accrueExtendedProperties(Accrue<IUnlistedProperty<?>> props) {
	}

	@Override
	protected BlockStateContainer createBlockState() {
		List<IProperty<?>> propsPersistent = new ArrayList<>(), propsVolatile = new ArrayList<>();
		List<IUnlistedProperty<?>> propsExt = new ArrayList<>();
		accrueProperties(new Accrue<>(propsPersistent));
		accrueVolatileProperties(new Accrue<>(propsVolatile));
		accrueExtendedProperties(new Accrue<>(propsExt));
		states = Collections.unmodifiableList(VirtualState.cartesian(propsPersistent));
		props = new ArrayList<>(propsPersistent);
		props.addAll(propsVolatile);
		props = Collections.unmodifiableList(props);
		BlockStateContainer container = propsExt.isEmpty() ? new BlockStateContainer(this, props.toArray(new IProperty[0])) : new ExtendedBlockState(this, props.toArray(new IProperty[0]), propsExt.toArray(new IUnlistedProperty[0]));
		statesInv = new TObjectIntHashMap<>();
		for (int i = 0; i < states.size(); i++) {
			statesInv.put(states.get(i).synthesize(container), i);
		}
		volatilePropDefaults = new ArrayList<>();
		IBlockState baseState = container.getBaseState();
		for (IProperty<?> prop : propsVolatile) {
			volatilePropDefaults.add(new PropApplicator<>(prop, baseState));
		}
		return container;
	}

	@SuppressWarnings("WeakerAccess")
	protected IBlockState initDefaultState(IBlockState state) {
		return state;
	}

	@Override
	protected LCItemBlock initItemBlock() {
		return new LCItemBlockStated(this);
	}

	@Override
	protected void initModel() {
		for (int i = 0; i < states.size(); i++) {
			LoliCore.PROXY.getRegistrar().queueItemBlockModelReg(this, i, getItemBlock().getModelName(states.get(i)));
		}
	}

	public List<IProperty<?>> getProperties() {
		return props;
	}

	public List<VirtualState> getStates() {
		return states;
	}

	@Override
	public LCItemBlockStated getItemBlock() {
		return (LCItemBlockStated)super.getItemBlock();
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int i = 0; i < states.size(); i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		for (PropApplicator<?> applicator : volatilePropDefaults) {
			state = applicator.apply(state);
		}
		return statesInv.get(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return (meta >= 0 && meta < states.size()) ? states.get(meta).synthesize(getBlockState()) : getDefaultState();
	}

	private static class PropApplicator<T extends Comparable<T>> {
		private final IProperty<T> property;
		private final T value;

		PropApplicator(IProperty<T> property, IBlockState sourceState) {
			this.property = property;
			this.value = sourceState.getValue(property);
		}

		IBlockState apply(IBlockState state) {
			return state.withProperty(property, value);
		}
	}
}
