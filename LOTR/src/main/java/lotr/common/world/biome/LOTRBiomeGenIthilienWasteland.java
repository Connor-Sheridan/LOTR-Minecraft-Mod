package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenIthilienWasteland extends LOTRBiomeGenIthilien
{
	public LOTRBiomeGenIthilienWasteland(int i)
	{
		super(i);
		
		spawnableCreatureList.clear();
		
		spawnableGoodList.clear();
		
		setGoodEvilWeight(0, 100);
		
		decorator.treesPerChunk = 4;
		decorator.logsPerChunk = 2;
        decorator.flowersPerChunk = 1;
        decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 2;
		
		decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
		decorator.addTree(LOTRTreeType.LEBETHRON_DEAD, 200);
		decorator.addTree(LOTRTreeType.BIRCH_DEAD, 50);
	}
}
