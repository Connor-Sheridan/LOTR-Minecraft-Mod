package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.entity.npc.LOTREntityWoodElfScout;
import lotr.common.entity.npc.LOTREntityWoodElfWarrior;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenRuinedWoodElfTower;
import lotr.common.world.structure.LOTRWorldGenWoodElfHouse;
import lotr.common.world.structure.LOTRWorldGenWoodElfTower;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenMirkwood extends LOTRBiome
{
	public final boolean corrupted;
	
	public LOTRBiomeGenMirkwood(int i, boolean flag)
	{
		super(i);
		corrupted = flag;

		spawnableEvilList.clear();
		
		registerForestFlowers();
		
		if (corrupted)
		{
			spawnableCreatureList.clear();
			spawnableWaterCreatureList.clear();
			
			spawnableEvilList.add(new SpawnListEntry(LOTREntityMirkwoodSpider.class, 10, 4, 4));
			
			hasPodzol = true;
			decorator.treesPerChunk = 8;
			decorator.vinesPerChunk = 20;
			decorator.logsPerChunk = 3;
			decorator.flowersPerChunk = 0;
			decorator.grassPerChunk = 12;
			decorator.doubleGrassPerChunk = 6;
			decorator.enableFern = true;
			decorator.mushroomsPerChunk = 4;
			decorator.generateCobwebs = false;
			
			decorator.addTree(LOTRTreeType.MIRK_OAK_LARGE, 1000);
			
			biomeColors.setGrass(0x2B5B25);
			biomeColors.setFoliage(0x263325);
			biomeColors.setFog(0x32647D);
			biomeColors.setFoggy(true);
			biomeColors.setWater(0x473668);
			
			decorator.addRandomStructure(new LOTRWorldGenRuinedWoodElfTower(false), 500);
			
			setBanditChance(LOTRBanditSpawner.NEVER);
			
			invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.WOOD_ELF, LOTRInvasionSpawner.UNCOMMON));
		}
		else
		{
			spawnableCreatureList.add(new SpawnListEntry(LOTREntityElk.class, 30, 4, 6));
			
			spawnableCaveCreatureList.clear();
			
			spawnableGoodList.add(new SpawnListEntry(LOTREntityWoodElf.class, 10, 4, 4));
			spawnableGoodList.add(new SpawnListEntry(LOTREntityWoodElfScout.class, 2, 4, 4));
			spawnableGoodList.add(new SpawnListEntry(LOTREntityWoodElfWarrior.class, 1, 4, 4));
			
			spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
			
			setGoodEvilWeight(100, 0);
			
			decorator.treesPerChunk = 2;
			decorator.flowersPerChunk = 3;
			decorator.doubleFlowersPerChunk = 1;
			decorator.grassPerChunk = 4;
			decorator.enableFern = true;
			decorator.generateLava = false;
			decorator.generateCobwebs = false;
			
			decorator.addTree(LOTRTreeType.OAK, 200);
			decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
			decorator.addTree(LOTRTreeType.CHESTNUT, 50);
			decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 20);
			decorator.addTree(LOTRTreeType.LARCH, 100);
			decorator.addTree(LOTRTreeType.MIRK_OAK, 200);
			decorator.addTree(LOTRTreeType.MIRK_OAK_MID, 200);
			decorator.addTree(LOTRTreeType.MIRK_OAK_HUGE, 50);
			decorator.addTree(LOTRTreeType.RED_MIRK_OAK_LARGE, 15);
			
			decorator.addRandomStructure(new LOTRWorldGenWoodElfHouse(false), 6);
			decorator.addRandomStructure(new LOTRWorldGenWoodElfTower(false), 100);
			
			setBanditChance(LOTRBanditSpawner.NEVER);
			
			invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.DOL_GULDUR, LOTRInvasionSpawner.UNCOMMON));
		}
		
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		if (corrupted)
		{
			return LOTRAchievement.enterMirkwoodCorrupted;
		}
		else
		{
			return LOTRAchievement.enterMirkwood;
		}
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		if (corrupted)
		{
			return LOTRWaypoint.Region.MIRKWOOD_CORRUPTED;
		}
		else
		{
			return LOTRWaypoint.Region.MIRKWOOD;
		}
	}
	
	@Override
	public void decorate(World world, Random random, int i, int k)
	{
		super.decorate(world, random, i, k);
		
		if (corrupted)
		{
			if (decorator.treesPerChunk > 2)
			{
				for (int l = 0; l < decorator.treesPerChunk / 2; l++)
				{
					int i1 = i + random.nextInt(16) + 8;
					int k1 = k + random.nextInt(16) + 8;
					int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
					LOTRTreeType.MIRK_OAK_MID.create(false).generate(world, random, i1, j1, k1);
				}
			}
			
			for (int l = 0; l < 6; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int j1 = random.nextInt(128);
				int k1 = k + random.nextInt(16) + 8;
				new LOTRWorldGenWebOfUngoliant(false, 64).generate(world, random, i1, j1, k1);
			}
		}
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.5F;
	}
	
	@Override
	public float getChanceToSpawnLavaLakes()
	{
		return corrupted ? super.getChanceToSpawnLavaLakes() : 0F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return corrupted ? 1 : 3;
	}
}
