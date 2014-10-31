package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenShirePine;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenForest;

public class LOTRBiomeGenShireWoodlands extends LOTRBiomeGenShire
{
	public LOTRBiomeGenShireWoodlands(int i)
	{
		super(i);
		
		decorator.treesPerChunk = 9;
		decorator.flowersPerChunk = 6;
		decorator.doubleFlowersPerChunk = 2;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 2;
		decorator.enableFern = true;
		
		decorator.addTree(LOTRTreeType.BIRCH, 250);
		decorator.addTree(LOTRTreeType.SHIRE_PINE, 2500);
		
		addFlower(LOTRMod.shireHeather, 0, 20);
		
		biomeColors.resetGrass();
	}
	
	@Override
	public boolean hasDomesticAnimals()
	{
		return false;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.3F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return super.spawnCountMultiplier() * 2;
	}
}
