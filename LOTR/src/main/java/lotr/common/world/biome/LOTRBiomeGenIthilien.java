package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRMod;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.npc.*;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure.LOTRWorldGenRuinedBeaconTower;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenIthilien extends LOTRBiome
{
	private WorldGenerator gondorRockVein = new WorldGenMinable(LOTRMod.rock, 1, 60, Blocks.stone);
	
	public LOTRBiomeGenIthilien(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
		
		spawnableGoodList.add(new SpawnListEntry(LOTREntityRangerIthilien.class, 10, 4, 4));
		
		spawnableEvilList.clear();
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMordorOrc.class, 20, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMordorOrcArcher.class, 10, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMordorOrcBombardier.class, 3, 1, 2));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMordorWarg.class, 5, 1, 3));
		
		setGoodEvilWeight(5, 95);
		
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityRabbit.class, 6, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		
		decorator.treesPerChunk = 6;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 4;
        decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 4;
		decorator.waterlilyPerChunk = 2;
		decorator.generateAthelas = true;
		
		decorator.addTree(LOTRTreeType.OAK_TALL, 500);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
		decorator.addTree(LOTRTreeType.LEBETHRON, 150);
		decorator.addTree(LOTRTreeType.LEBETHRON_LARGE, 15);
		decorator.addTree(LOTRTreeType.BIRCH, 100);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
		decorator.addTree(LOTRTreeType.CEDAR, 200);
		decorator.addTree(LOTRTreeType.APPLE, 3);
		decorator.addTree(LOTRTreeType.PEAR, 3);
		
		registerForestFlowers();
		addFlower(LOTRMod.asphodel, 0, 10);
		addFlower(Blocks.red_flower, 2, 5);
		
		decorator.generateOrcDungeon = true;
		
		decorator.addRandomStructure(new LOTRWorldGenRuinedBeaconTower(false), 200);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 500);
		decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 800);
		
		registerTravellingTrader(LOTREntityElvenTrader.class);
		
		setBanditChance(LOTRBanditSpawner.UNCOMMON);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GONDOR, LOTRInvasionSpawner.COMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.MORDOR, LOTRInvasionSpawner.COMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.NEAR_HARAD, LOTRInvasionSpawner.UNCOMMON));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterIthilien;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.GONDOR;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
		genStandardOre(world, random, i, k, 2, gondorRockVein, 0, 64);
		
		super.decorate(world, random, i, k);
		
		if (random.nextInt(3) == 0)
        {
            int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
            WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
            doubleFlowerGen.func_150548_a(0);
            doubleFlowerGen.generate(world, random, i1, j1, k1);
        }
    }
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.5F;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.5F;
	}
	
	@Override
	public float getChanceToSpawnLavaLakes()
	{
		return 0.05F;
	}
}
