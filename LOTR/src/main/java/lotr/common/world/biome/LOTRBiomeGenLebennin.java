package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenLebennin extends LOTRBiomeGenGondor
{
	private WorldGenerator boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 1, 2, 5);
	
	public LOTRBiomeGenLebennin(int i)
	{
		super(i);
		
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		
		setGoodEvilWeight(90, 10);
		
		decorator.treesPerChunk = 1;
		decorator.flowersPerChunk = 5;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 6;
		
		decorator.addTree(LOTRTreeType.BIRCH, 300);
		decorator.addTree(LOTRTreeType.BEECH, 150);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 15);
		decorator.addTree(LOTRTreeType.MAPLE, 100);
		
		setBanditChance(LOTRBanditSpawner.RARE);
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterLebennin;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		if (random.nextInt(24) == 0)
		{
			for (int l = 0; l < 3; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}
}
