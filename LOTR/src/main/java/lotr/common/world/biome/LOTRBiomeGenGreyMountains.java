package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.npc.LOTREntityGundabadWarg;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenLarch;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class LOTRBiomeGenGreyMountains extends LOTRBiome
{
	public LOTRBiomeGenGreyMountains(int i)
	{
		super(i);
		
		spawnableCreatureList.clear();
		
		spawnableEvilList.add(new SpawnListEntry(LOTREntityGundabadWarg.class, 10, 4, 4));
		
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		
		decorator.addTree(LOTRTreeType.SPRUCE, 1000);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 500);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 50);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA_TALL, 10);
		decorator.addTree(LOTRTreeType.LARCH, 500);
		
		registerMountainsFlowers();
		
		biomeColors.setSky(0xA5C0CE);
		
		decorator.generateOrcDungeon = true;
		
		setBanditChance(LOTRBanditSpawner.RARE);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GUNDABAD, LOTRInvasionSpawner.RARE));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.DWARF, LOTRInvasionSpawner.RARE));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterGreyMountains;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.GREY_MOUNTAINS;
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
		
		for (int count = 0; count < 3; count++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			if (j1 < 100)
			{
				func_150567_a(random).generate(world, random, i1, j1, k1);
			}
		}
		
		for (int count = 0; count < 2; count++)
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
