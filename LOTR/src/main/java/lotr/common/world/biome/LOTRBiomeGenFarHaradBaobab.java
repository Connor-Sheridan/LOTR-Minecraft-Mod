package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBaobab;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeGenFarHaradBaobab extends LOTRBiomeGenFarHarad
{
	public LOTRBiomeGenFarHaradBaobab(int i)
	{
		super(i);
		
		decorator.treesPerChunk = 0;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 6;
		
		decorator.addTree(LOTRTreeType.BAOBAB, 2500);
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.75F;
	}
}
