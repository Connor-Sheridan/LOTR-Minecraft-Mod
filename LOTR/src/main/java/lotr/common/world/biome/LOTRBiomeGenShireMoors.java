package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenHobbitWindmill;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenShireMoors extends LOTRBiomeGenShire
{
	private WorldGenerator boulderSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
	private WorldGenerator boulderLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 3, 5);
	
	public LOTRBiomeGenShireMoors(int i)
	{
		super(i);
		
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 16;
		decorator.doubleFlowersPerChunk = 0;
		decorator.grassPerChunk = 16;
		decorator.doubleGrassPerChunk = 1;
		
		decorator.addTree(LOTRTreeType.OAK_LARGE, 8000);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 2000);
		
		addFlower(LOTRMod.shireHeather, 0, 100);
		
		biomeColors.resetGrass();
		
		decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 1000);
		
		setBanditChance(LOTRBanditSpawner.RARE);
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);

		if (random.nextInt(8) == 0)
		{
			for (int l = 0; l < 4; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderSmall.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
		
		if (random.nextInt(30) == 0)
		{
			for (int l = 0; l < 4; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderLarge.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.1F;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.2F;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return super.spawnCountMultiplier() * 2;
	}
}
