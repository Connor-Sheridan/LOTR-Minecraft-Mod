package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.gen.feature.*;

public enum LOTRTreeType
{
	OAK(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 4, 6, Blocks.log, 0, Blocks.leaves, 0);
		}
	}),
	OAK_TALL(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 8, 12, Blocks.log, 0, Blocks.leaves, 0);
		}
	}),
	OAK_TALLER(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 16, 24, Blocks.log, 0, Blocks.leaves, 0);
		}
	}),
	OAK_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenBigTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
		}
	}),
	OAK_HUGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenHugeTrees(Blocks.log, 0, Blocks.leaves, 0);
		}
	}),
	OAK_FANGORN(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
		}
	}),
	OAK_FANGORN_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, Blocks.leaves, 0).setNoLeaves();
		}
	}),
	OAK_SWAMP(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenSwamp();
		}
	}),
	OAK_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDeadTrees(Blocks.log, 0);
		}
	}),
	OAK_DESERT(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDesertTrees();
		}
	}),
	OAK_SHRUB(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenShrub(3, 0);
		}
	}),
	
	BIRCH(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 5, 7, Blocks.log, 2, Blocks.leaves, 2);
		}
	}),
	BIRCH_TALL(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 8, 11, Blocks.log, 2, Blocks.leaves, 2);
		}
	}),
	BIRCH_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenBigTrees(flag, Blocks.log, 2, Blocks.leaves, 2);
		}
	}),
	BIRCH_HUGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenHugeTrees(Blocks.log, 2, Blocks.leaves, 2);
		}
	}),
	BIRCH_FANGORN(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenFangornTrees(flag, Blocks.log, 2, Blocks.leaves, 2);
		}
	}),
	BIRCH_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDeadTrees(Blocks.log, 2);
		}
	}),
	
	SPRUCE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenTaiga2(flag);
		}
	}),
	SPRUCE_THIN(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenTaiga1();
		}
	}),
	SPRUCE_MEGA(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenMegaPineTree(flag, false);
		}
	}),
	SPRUCE_MEGA_TALL(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenMegaPineTree(flag, true);
		}
	}),
	SPRUCE_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDeadTrees(Blocks.log, 1);
		}
	}),
	
	JUNGLE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenTrees(flag, 7, 3, 3, true);
		}
	}),
	JUNGLE_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenMegaJungle(flag, 10, 20, 3, 3);
		}
	}),
	JUNGLE_CLOUD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenMegaJungle(flag, 30, 30, 3, 3);
		}
	}),
	
	ACACIA(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenSavannaTree(flag);
		}
	}),
	ACACIA_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDeadTrees(Blocks.log2, 0);
		}
	}),
	
	DARK_OAK(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new WorldGenCanopyTree(flag);
		}
	}),
	
	SHIRE_PINE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenShirePine(flag);
		}
	}),
	
	MALLORN(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 8, 15, LOTRMod.wood, 1, LOTRMod.leaves, 1);
		}
	}),
	MALLORN_HUGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMallornLarge(flag);
		}
	}),
	MALLORN_HUGE_SAPLING(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMallornLarge(flag).setSaplingGrowth();
		}
	}),
	
	MIRK_OAK(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMirkOak(flag, 6, 4, 0, 2);
		}
	}),
	MIRK_OAK_MID(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMirkOak(flag, 8, 4, 0, 3);
		}
	}),
	MIRK_OAK_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMirkOak(flag, 16, 4, 1, 7).disableDecay().setHasVines();
		}
	}),
	MIRK_OAK_HUGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMirkOak(flag, 20, 30, 2, 12);
		}
	}),
	MIRK_OAK_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDeadTrees(LOTRMod.wood, 2);
		}
	}),
	
	RED_MIRK_OAK(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMirkOak(flag, 6, 4, 0, 2).setRed();
		}
	}),
	RED_MIRK_OAK_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMirkOak(flag, 12, 6, 1, 6).setRed().disableDecay();
		}
	}),
	
	CHARRED(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenCharredTrees();
		}
	}),
	CHARRED_FANGORN(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood, 3, Blocks.air, 0).setNoLeaves();
		}
	}),
	
	APPLE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 4, 7, LOTRMod.fruitWood, 0, LOTRMod.fruitLeaves, 0);
		}
	}),
	
	PEAR(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 4, 5, LOTRMod.fruitWood, 1, LOTRMod.fruitLeaves, 1);
		}
	}),
	
	CHERRY(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2);
		}
	}),
	CHERRY_MORDOR(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenHugeTrees(LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2).disableRestrictions();
		}
	}),
	
	MANGO(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 4, 7, LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3);
		}
	}),
	
	LEBETHRON(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
		}
	}),
	LEBETHRON_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 11, 18, LOTRMod.wood2, 0, LOTRMod.leaves2, 0).setTrunkWidth(2);
		}
	}),
	LEBETHRON_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 0);
		}
	}),
	
	BEECH(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
		}
	}),
	BEECH_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
		}
	}),
	BEECH_FANGORN(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
		}
	}),
	BEECH_FANGORN_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1).setNoLeaves();
		}
	}),
	BEECH_DEAD(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 1);
		}
	}),
	
	HOLLY(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenHolly(flag);
		}
	}),
	HOLLY_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenHolly(flag).setLarge();
		}
	}),
	
	BANANA(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenBanana(flag);
		}
	}),
	
	MAPLE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
		}
	}),
	MAPLE_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenBigTrees(flag, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
		}
	}),
	
	LARCH(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenLarch(flag);
		}
	}),
	
	DATE_PALM(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenDatePalm(flag);
		}
	}),
	
	MANGROVE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenMangrove(flag);
		}
	}),
	
	CHESTNUT(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenSimpleTrees(flag, 5, 7, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
		}
	}),
	CHESTNUT_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenBigTrees(flag, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
		}
	}),
	
	BAOBAB(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenBaobab(flag);
		}
	}),
	
	CEDAR(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenCedar(flag);
		}
	}),
	CEDAR_LARGE(new ITreeFactory()
	{
		@Override
		public WorldGenAbstractTree createTree(boolean flag)
		{
			return new LOTRWorldGenCedar(flag).setMinMaxHeight(15, 30);
		}
	}),
	
	NULL(null);
	
	private static interface ITreeFactory
	{
		public abstract WorldGenAbstractTree createTree(boolean flag);
	}
	
	private ITreeFactory treeFactory;
	
	private LOTRTreeType(ITreeFactory factory)
	{
		treeFactory = factory;
	}
	
	public WorldGenAbstractTree create(boolean flag)
	{
		return treeFactory.createTree(flag);
	}
	
	public static class WeightedTreeType extends WeightedRandom.Item
	{
		public final LOTRTreeType treeType;

		public WeightedTreeType(LOTRTreeType tree, int i)
		{
			super(i);
			treeType = tree;
		}
	}
}
