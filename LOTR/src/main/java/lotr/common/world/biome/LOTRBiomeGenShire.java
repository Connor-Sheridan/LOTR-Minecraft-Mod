package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRFaction;
import lotr.common.LOTRMod;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityElvenTrader;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityHobbitShirriff;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.biome.LOTRBiome.GrassBlockAndMeta;
import lotr.common.world.feature.*;
import lotr.common.world.structure.*;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenShire extends LOTRBiome
{
	public LOTRBiomeGenShire(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(LOTREntityShirePony.class, 8, 2, 6));
		
		spawnableGoodList.add(new SpawnListEntry(LOTREntityHobbit.class, 40, 1, 4));
		spawnableGoodList.add(new SpawnListEntry(LOTREntityHobbitShirriff.class, 1, 1, 3));
		
		spawnableEvilList.clear();
		
		setGoodEvilWeight(100, 0);
		
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityRabbit.class, 6, 4, 4));
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 6;
		decorator.generateLava = false;
		
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
		decorator.addTree(LOTRTreeType.OAK_HUGE, 10);
		decorator.addTree(LOTRTreeType.CHESTNUT, 250);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 100);
		decorator.addTree(LOTRTreeType.BIRCH, 25);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
		decorator.addTree(LOTRTreeType.APPLE, 5);
		decorator.addTree(LOTRTreeType.PEAR, 5);
		decorator.addTree(LOTRTreeType.CHERRY, 2);
		
		registerForestFlowers();
		addFlower(LOTRMod.bluebell, 0, 5);
		
		biomeColors.setGrass(0x16B716);
		
		if (hasShireStructures())
		{
			if (getClass() == LOTRBiomeGenShire.class)
			{
				decorator.addRandomStructure(new LOTRWorldGenHobbitHole(false), 10);
				decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 160);
				decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 600);
				decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 500);
			}
			
			decorator.addRandomStructure(new LOTRWorldGenHobbitPicnicBench(false), 40);
			decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 1500);
			decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 1500);
		}
		
		registerTravellingTrader(LOTREntityElvenTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.SHIRE;
	}
	
	@Override
	public boolean hasDomesticAnimals()
	{
		return true;
	}
	
	public boolean hasShireStructures()
	{
		return true;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
        
        for (int l = 0; l < decorator.grassPerChunk / 2; l++)
        {
        	int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
            new LOTRWorldGenClover().generate(world, random, i1, j1, k1);
        }
		
		if (random.nextInt(6) == 0)
		{
            int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(LOTRMod.pipeweedPlant).generate(world, random, i1, j1, k1);
		}
		
        if (decorator.doubleFlowersPerChunk > 0 && random.nextInt(6) == 0)
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
	public float getTreeIncreaseChance()
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
		return 0F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return 3;
	}
}
