package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenLarch;
import lotr.common.world.structure.LOTRWorldGenStoneRuin;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenMistyMountains extends LOTRBiome
{
	private WorldGenerator mithrilGen = new WorldGenMinable(LOTRMod.oreMithril, 6);
	
	public LOTRBiomeGenMistyMountains(int i)
	{
		super(i);

		spawnableCreatureList.clear();
		
		spawnableEvilList.add(new SpawnListEntry(LOTREntityGundabadWarg.class, 20, 4, 4));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHai.class, 20, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiCrossbower.class, 10, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiSapper.class, 3, 1, 2));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiBerserker.class, 5, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukWarg.class, 20, 4, 4));
		
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		decorator.generateWater = false;
		
		decorator.addTree(LOTRTreeType.SPRUCE, 1000);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 500);
		decorator.addTree(LOTRTreeType.LARCH, 300);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
		
		registerMountainsFlowers();
		
		biomeColors.setSky(0xBACBD1);
		
		decorator.generateOrcDungeon = true;
		
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 500);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterMistyMountains;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.MISTY_MOUNTAINS;
	}
	
	@Override
	public boolean getEnableRiver()
	{
		return false;
	}
	
	public boolean isSnowCovered()
	{
		return true;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
		if (random.nextInt(4) == 0)
		{
			genStandardOre(world, random, i, k, 1, mithrilGen, 0, 16);
		}
		
        super.decorate(world, random, i, k);
		
		for (int count = 0; count < 6; count++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			if (j1 < 100)
			{
				func_150567_a(random).generate(world, random, i1, j1, k1);
			}
		}
		
		for (int count = 0; count < 3; count++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
			if (j1 < 100)
			{
				getRandomWorldGenForGrass(random).generate(world, random, i1, j1, k1);
			}
		}
    }
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0F;
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0F;
	}
}
