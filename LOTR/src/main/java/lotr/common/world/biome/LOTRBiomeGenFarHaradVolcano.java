package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradVolcano extends LOTRBiomeGenFarHarad
{
	public LOTRBiomeGenFarHaradVolcano(int i)
	{
		super(i);
		
		topBlock = Blocks.stone;
		fillerBlock = Blocks.stone;

		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableLOTRAmbientList.clear();
		spawnableGoodList.clear();
		spawnableEvilList.clear();
		
		decorator.treesPerChunk = 0;
		decorator.grassPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		decorator.flowersPerChunk = 0;
		
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
		decorator.addTree(LOTRTreeType.ACACIA_DEAD, 200);
		decorator.addTree(LOTRTreeType.CHARRED, 500);
		
		biomeColors.setSky(0x5B5A58);
		biomeColors.setClouds(0x333333);
		biomeColors.setFog(0x666666);
		biomeColors.setWater(0x3D2F1F);
	}
	
	@Override
	public boolean getEnableRiver()
	{
		return false;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
		super.decorate(world, random, i, k);

		WorldGenerator lavaGen = new LOTRWorldGenStreams(Blocks.flowing_lava);
		for (int l = 0; l < 60; l++)
		{
			int i1 = i + random.nextInt(16) + 8;
			int j1 = 60 + random.nextInt(100);
			int k1 = k + random.nextInt(16) + 8;
			lavaGen.generate(world, random, i1, j1, k1);
		}
		
		for (int l = 0; l < 30; l++)
		{
			int i1 = i + random.nextInt(16) + 8;
			int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			new LOTRWorldGenVolcanoCrater().generate(world, random, i1, j1, k1);
		}
    }
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.5F;
	}
	
	@Override
	public float getChanceToSpawnLavaLakes()
	{
		return 1F;
	}
}
