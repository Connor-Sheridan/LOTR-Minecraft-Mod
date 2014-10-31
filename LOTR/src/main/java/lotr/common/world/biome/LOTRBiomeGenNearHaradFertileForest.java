package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenCedar;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeGenNearHaradFertileForest extends LOTRBiomeGenNearHaradFertile
{
	public LOTRBiomeGenNearHaradFertileForest(int i)
	{
		super(i);
		
		decorator.treesPerChunk = 6;
		
		decorator.addTree(LOTRTreeType.CEDAR, 6000);
		decorator.addTree(LOTRTreeType.CEDAR_LARGE, 1500);
		
		decorator.clearRandomStructures();
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 1F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return super.spawnCountMultiplier() * 2;
	}
}
