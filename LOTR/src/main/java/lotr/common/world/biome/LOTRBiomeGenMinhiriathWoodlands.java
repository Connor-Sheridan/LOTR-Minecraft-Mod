package lotr.common.world.biome;

import java.util.Random;

import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class LOTRBiomeGenMinhiriathWoodlands extends LOTRBiomeGenMinhiriath
{
	public LOTRBiomeGenMinhiriathWoodlands(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 10, 4, 8));
		
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 2, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityRabbit.class, 6, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 12, 4, 4));

		decorator.treesPerChunk = 8;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 3;
		
		decorator.addTree(LOTRTreeType.OAK, 10000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 1000);
		decorator.addTree(LOTRTreeType.SPRUCE, 2000);
		
		registerForestFlowers();
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}
}
