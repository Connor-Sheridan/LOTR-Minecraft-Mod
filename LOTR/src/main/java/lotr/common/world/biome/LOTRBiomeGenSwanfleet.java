package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class LOTRBiomeGenSwanfleet extends LOTRBiomeGenEriador
{
	public LOTRBiomeGenSwanfleet(int i)
	{
		super(i);
		
		spawnableGoodList.clear();
		
		setGoodEvilWeight(0, 100);

		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 1;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 4;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 6;
		decorator.enableFern = true;
		decorator.waterlilyPerChunk = 2;
		decorator.reedsPerChunk = 10;
		
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.OAK_TALL, 500);
		decorator.addTree(LOTRTreeType.OAK_SWAMP, 500);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
		decorator.addTree(LOTRTreeType.BIRCH, 50);
		
		registerSwampFlowers();
		
		setBanditChance(LOTRBanditSpawner.RARE);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GUNDABAD, LOTRInvasionSpawner.RARE));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterSwanfleet;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		for (int l = 0; l < 6; l++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			new WorldGenLakes(Blocks.water).generate(world, random, i1, j1, k1);
		}
	}
}
