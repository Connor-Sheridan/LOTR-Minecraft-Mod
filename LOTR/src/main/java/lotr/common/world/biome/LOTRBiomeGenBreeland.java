package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.*;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class LOTRBiomeGenBreeland extends LOTRBiome
{
	public LOTRBiomeGenBreeland(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(LOTREntityHorse.class, 8, 2, 6));
		
		spawnableEvilList.clear();
		
		decorator.flowersPerChunk = 2;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 1;
		decorator.generateAthelas = true;
		
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
		decorator.addTree(LOTRTreeType.BEECH, 300);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 75);
		decorator.addTree(LOTRTreeType.MAPLE, 200);
		decorator.addTree(LOTRTreeType.MAPLE_LARGE, 50);
		decorator.addTree(LOTRTreeType.CHESTNUT, 300);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 75);
		decorator.addTree(LOTRTreeType.BIRCH, 50);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
		decorator.addTree(LOTRTreeType.APPLE, 3);
		decorator.addTree(LOTRTreeType.PEAR, 3);
		
        registerPlainsFlowers();
        
        registerTravellingTrader(LOTREntityElvenTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		registerTravellingTrader(LOTREntityNearHaradMerchant.class);
		
		setBanditChance(LOTRBanditSpawner.RARE);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GUNDABAD, LOTRInvasionSpawner.RARE));
	}
	
	@Override
	public boolean hasDomesticAnimals()
	{
		return true;
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterBreeland;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.BREE_LAND;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.05F;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.2F;
	}
}
