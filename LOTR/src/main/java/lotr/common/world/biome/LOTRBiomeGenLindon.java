package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRMod;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityElvenTrader;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.entity.npc.LOTREntityHighElfWarrior;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenHighElvenHall;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenLindon extends LOTRBiome
{
	private WorldGenerator quenditeGen = new WorldGenMinable(LOTRMod.oreQuendite, 6);
	
	public LOTRBiomeGenLindon(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
		
		spawnableGoodList.add(new SpawnListEntry(LOTREntityHighElf.class, 10, 4, 4));
		spawnableGoodList.add(new SpawnListEntry(LOTREntityHighElfWarrior.class, 2, 4, 4));
		
		spawnableEvilList.clear();
		
		setGoodEvilWeight(100, 0);
		
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 3;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 1;
		
		decorator.addTree(LOTRTreeType.OAK, 100);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 25);
		decorator.addTree(LOTRTreeType.BIRCH, 500);
		decorator.addTree(LOTRTreeType.BIRCH_TALL, 500);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 200);
		decorator.addTree(LOTRTreeType.BIRCH_HUGE, 50);
		decorator.addTree(LOTRTreeType.BEECH, 100);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 25);
		decorator.addTree(LOTRTreeType.CHESTNUT, 40);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
		decorator.addTree(LOTRTreeType.APPLE, 2);
		decorator.addTree(LOTRTreeType.PEAR, 2);
		
        registerPlainsFlowers();
		
		decorator.addRandomStructure(new LOTRWorldGenHighElvenHall(false), 1000);
		
		registerTravellingTrader(LOTREntityElvenTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GUNDABAD, LOTRInvasionSpawner.RARE));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterLindon;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.LINDON;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
		genStandardOre(world, random, i, k, 6, quenditeGen, 0, 48);
		
		super.decorate(world, random, i, k);
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.2F;
	}
	
	@Override
	public float getChanceToSpawnLavaLakes()
	{
		return 0F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return 5;
	}
}
