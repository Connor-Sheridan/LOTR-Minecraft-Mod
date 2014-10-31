package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenHolly;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeGenEregionForest extends LOTRBiomeGenEregion
{
	public LOTRBiomeGenEregionForest(int i)
	{
		super(i);
		
		hasPodzol = true;
		decorator.treesPerChunk = 8;
        decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 2;
        decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 3;
		
		decorator.addTree(LOTRTreeType.HOLLY_LARGE, 400);
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.5F;
	}
}
