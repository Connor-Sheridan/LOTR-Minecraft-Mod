package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenHugeTrees;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;

public class LOTRBiomeGenDunlandForest extends LOTRBiomeGenDunland
{
	public LOTRBiomeGenDunlandForest(int i)
	{
		super(i);
		
		hasPodzol = true;
		decorator.treesPerChunk = 8;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 4;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.mushroomsPerChunk = 1;
		
		decorator.addTree(LOTRTreeType.OAK_HUGE, 100);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 50);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
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
