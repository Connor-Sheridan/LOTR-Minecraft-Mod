package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenHugeTrees;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;

public class LOTRBiomeGenWilderlandForestDense extends LOTRBiomeGenWilderlandForest
{
	public LOTRBiomeGenWilderlandForestDense(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 16, 4, 8));
		
		spawnableLOTRAmbientList.clear();
		
		hasPodzol = true;
		decorator.treesPerChunk = 12;
		decorator.logsPerChunk = 1;
		
		decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
		decorator.addTree(LOTRTreeType.OAK_HUGE, 200);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 300);
	}
}
