package lotr.common.world.biome;

import java.util.Random;

import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenHugeTrees;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;

public class LOTRBiomeGenRhunForest extends LOTRBiomeGenRhun
{
	public LOTRBiomeGenRhunForest(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 16, 4, 8));
		
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityRabbit.class, 6, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 12, 4, 4));

		decorator.treesPerChunk = 8;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 1;
		decorator.doubleGrassPerChunk = 2;
		
		decorator.addTree(LOTRTreeType.OAK_LARGE, 2000);
		decorator.addTree(LOTRTreeType.OAK_HUGE, 100);
		
		registerForestFlowers();
		
		decorator.treesPerChunk = 9;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.5F;
	}
}
