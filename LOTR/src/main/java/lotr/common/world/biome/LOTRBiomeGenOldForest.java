package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.npc.LOTREntityDarkHuorn;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenOldForest extends LOTRBiome
{
	public LOTRBiomeGenOldForest(int i)
	{
		super(i);
		
		spawnableEvilList.clear();
		spawnableEvilList.add(new SpawnListEntry(LOTREntityDarkHuorn.class, 10, 4, 4));
		
		hasPodzol = true;
		decorator.treesPerChunk = 16;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 5;
		decorator.enableFern = true;
		decorator.mushroomsPerChunk = 2;
		
		decorator.addTree(LOTRTreeType.OAK, 500);
		decorator.addTree(LOTRTreeType.OAK_TALL, 1000);
		decorator.addTree(LOTRTreeType.OAK_TALLER, 200);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 500);
		decorator.addTree(LOTRTreeType.DARK_OAK, 1000);
		
		registerForestFlowers();
		
		biomeColors.setGrass(0x47823E);
		biomeColors.setFoliage(0x30682A);
		biomeColors.setFog(0x193219);
		biomeColors.setFoggy(true);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterOldForest;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.OLD_FOREST;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0F;
	}
}
