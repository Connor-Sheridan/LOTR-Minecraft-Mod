package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.world.*;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure.*;
import lotr.common.world.structure2.LOTRWorldGenGondorTurret;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenGondor extends LOTRBiome
{
	private WorldGenerator gondorRockVein = new WorldGenMinable(LOTRMod.rock, 1, 60, Blocks.stone);
	
	public LOTRBiomeGenGondor(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
		
		spawnableGoodList.add(new SpawnListEntry(LOTREntityGondorSoldier.class, 20, 4, 6));
		spawnableGoodList.add(new SpawnListEntry(LOTREntityGondorArcher.class, 10, 4, 6));
		
		spawnableEvilList.clear();
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMordorOrc.class, 20, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMordorOrcArcher.class, 10, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMordorOrcBombardier.class, 3, 1, 2));
		
		setGoodEvilWeight(70, 30);
		
		decorator.setTreeCluster(10, 30);
		decorator.flowersPerChunk = 1;
        decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 1;
		decorator.generateAthelas = true;
		
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 150);
		decorator.addTree(LOTRTreeType.APPLE, 2);
		decorator.addTree(LOTRTreeType.PEAR, 2);
		
        registerPlainsFlowers();
		
		decorator.addRandomStructure(new LOTRWorldGenGondorFortress(false), 800);
		decorator.addRandomStructure(new LOTRWorldGenGondorSmithy(false), 800);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 600);
		decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenGondorTurret(false), 1000);
		
		registerTravellingTrader(LOTREntityElvenTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		
		setBanditChance(LOTRBanditSpawner.RARE);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.MORDOR, LOTRInvasionSpawner.UNCOMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.NEAR_HARAD, LOTRInvasionSpawner.RARE));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterGondor;
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
    }
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.1F;
	}
	
	@Override
	public float getChanceToSpawnLavaLakes()
	{
		return 0.05F;
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.02F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return 4;
	}
}
