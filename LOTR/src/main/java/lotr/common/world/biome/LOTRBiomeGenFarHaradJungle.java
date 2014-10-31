package lotr.common.world.biome;

import java.util.Random;

import lotr.common.entity.animal.*;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenSimpleTrees;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenVines;

public class LOTRBiomeGenFarHaradJungle extends LOTRBiomeGenFarHarad
{
    public LOTRBiomeGenFarHaradJungle(int i)
    {
        super(i);
        
        spawnableCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(LOTREntityFlamingo.class, 10, 4, 4));
        
        spawnableLOTRAmbientList.clear();
        spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
        
        spawnableMonsterList.add(new SpawnListEntry(LOTREntityJungleScorpion.class, 30, 4, 4));

        decorator.treesPerChunk = 50;
        decorator.vinesPerChunk = 50;
        decorator.flowersPerChunk = 4;
        decorator.doubleFlowersPerChunk = 4;
        decorator.grassPerChunk = 15;
		decorator.doubleGrassPerChunk = 10;
        decorator.enableFern = true;
        
        decorator.clearTrees();
        decorator.addTree(LOTRTreeType.JUNGLE, 1000);
        decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 500);
        decorator.addTree(LOTRTreeType.OAK_SHRUB, 1000);
        decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        decorator.addTree(LOTRTreeType.MANGO, 20);
		
		registerJungleFlowers();
    }

    @Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
        
        if (random.nextInt(3) == 0)
        {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			new WorldGenMelon().generate(world, random, i1, j1, k1);
        }
    }
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 1F;
	}
}
