package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRFaction;
import lotr.common.entity.npc.*;
import lotr.common.world.*;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeGenFangornWasteland extends LOTRBiome
{
	public LOTRBiomeGenFangornWasteland(int i)
	{
		super(i);
		
		spawnableCreatureList.clear();
		
		spawnableEvilList.clear();
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHai.class, 20, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiCrossbower.class, 10, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiSapper.class, 3, 1, 2));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiBerserker.class, 5, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukWarg.class, 5, 4, 4));
		
		setGoodEvilWeight(0, 100);
		
		decorator.treesPerChunk = 1;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 5;
		decorator.doubleGrassPerChunk = 3;
		decorator.enableFern = true;
		
		decorator.addTree(LOTRTreeType.CHARRED, 500);
		decorator.addTree(LOTRTreeType.OAK_DEAD, 300);
		decorator.addTree(LOTRTreeType.BEECH_DEAD, 100);
		decorator.addTree(LOTRTreeType.BIRCH_DEAD, 20);
		decorator.addTree(LOTRTreeType.CHARRED_FANGORN, 50);
		decorator.addTree(LOTRTreeType.OAK_FANGORN_DEAD, 30);
		decorator.addTree(LOTRTreeType.BEECH_FANGORN_DEAD, 10);
		
		setBanditChance(LOTRBanditSpawner.UNCOMMON);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.URUK_HAI, LOTRInvasionSpawner.UNCOMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.ROHAN, LOTRInvasionSpawner.UNCOMMON));
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		if (random.nextInt(60) == 0)
		{
			int i1 = i + random.nextInt(16) + 8;
			int k1 = k + random.nextInt(16) + 8;
			new LOTRWorldGenBlastedLand().generate(world, random, i1, world.getHeightValue(i1, k1), k1);
		}
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.25F;
	}
	
	@Override
	public boolean canSpawnHostilesInDay()
	{
		return true;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return 3;
	}
}
