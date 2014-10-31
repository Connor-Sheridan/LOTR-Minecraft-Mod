package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenDeadTrees;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class LOTRBiomeGenTaiga extends LOTRBiomeGenTundra
{
	public LOTRBiomeGenTaiga(int i)
	{
		super(i);

		decorator.treesPerChunk = 2;
		decorator.flowersPerChunk = 2;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
		decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 50);
		
		registerTaigaFlowers();
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}
}
