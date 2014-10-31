package lotr.common.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.npc.LOTREntityElvenTrader;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.entity.npc.LOTREntityHuorn;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class LOTRBiomeGenFangorn extends LOTRBiome
{
	private boolean isBirchFangorn = false;
	
	public LOTRBiomeGenFangorn(int i)
	{
		super(i);
		
		spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		
		spawnableGoodList.add(new SpawnListEntry(LOTREntityEnt.class, 10, 4, 4));
		spawnableGoodList.add(new SpawnListEntry(LOTREntityHuorn.class, 20, 4, 4));
		
		spawnableEvilList.clear();
		
		setGoodEvilWeight(100, 0);
		
		hasPodzol = true;
		decorator.treesPerChunk = 12;
		decorator.logsPerChunk = 5;
		decorator.flowersPerChunk = 6;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 6;
		decorator.enableFern = true;
		
		decorator.addTree(LOTRTreeType.OAK, 100);
		decorator.addTree(LOTRTreeType.OAK_TALL, 200);
		decorator.addTree(LOTRTreeType.OAK_TALLER, 200);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
		decorator.addTree(LOTRTreeType.BIRCH, 20);
		decorator.addTree(LOTRTreeType.BIRCH_TALL, 20);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
		decorator.addTree(LOTRTreeType.BEECH, 50);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
		decorator.addTree(LOTRTreeType.OAK_FANGORN, 50);
		decorator.addTree(LOTRTreeType.BEECH_FANGORN, 20);
		
		registerForestFlowers();
		addFlower(LOTRMod.fangornPlant, 0, 1);
		addFlower(LOTRMod.fangornPlant, 1, 1);
		addFlower(LOTRMod.fangornPlant, 2, 1);
		addFlower(LOTRMod.fangornPlant, 3, 1);
		addFlower(LOTRMod.fangornPlant, 4, 1);
		addFlower(LOTRMod.fangornPlant, 5, 1);
		
		biomeColors.setSky(0x76A072);
		biomeColors.setFog(0x327D4B);
		biomeColors.setFoggy(true);
		
		registerTravellingTrader(LOTREntityElvenTrader.class);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
	}
	
	public LOTRBiomeGenFangorn setBirchFangorn()
	{
		isBirchFangorn = true;
		decorator.addTree(LOTRTreeType.BIRCH, 2000);
		decorator.addTree(LOTRTreeType.BIRCH_TALL, 2000);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 2000);
		decorator.addTree(LOTRTreeType.BIRCH_FANGORN, 1000);
		return this;
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterFangorn;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.FANGORN;
	}

	@Override
	public void decorate(World world, Random random, int i, int k)
	{
		super.decorate(world, random, i, k);
		
		if (random.nextInt(2) == 0)
		{
			int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1;
			
            for (j1 = 64 + random.nextInt(64); j1 > 0 && world.getBlock(i1, j1 - 1, k1) == Blocks.air; j1--)
            {
                ;
            }
            
            new LOTRWorldGenWaterPlant(LOTRMod.fangornRiverweed).generate(world, random, i1, j1, k1);
		}
		
		if (random.nextInt(10) == 0)
		{
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			new LOTRWorldGenEntJars().generate(world, random, i1, j1, k1);
		}
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return 3;
	}
}
