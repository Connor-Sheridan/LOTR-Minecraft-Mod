package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.biome.LOTRBiome.GrassBlockAndMeta;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenNurnWheatFarm;
import lotr.common.world.structure.LOTRWorldGenOrcSlaverTower;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenNurn extends LOTRBiomeGenMordor
{
	public LOTRBiomeGenNurn(int i)
	{
		super(i);
		
		topBlock = Blocks.grass;
		fillerBlock = Blocks.dirt;
		
		enableRain = true;

		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 8;
		decorator.generateWater = true;
		
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenNurnWheatFarm(false), 40);
		decorator.addRandomStructure(new LOTRWorldGenOrcSlaverTower(false), 200);
		
		decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
		decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
		decorator.addTree(LOTRTreeType.CHARRED, 500);
		
		biomeColors.setSky(0x564637);
		biomeColors.resetClouds();
		biomeColors.resetFog();
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterNurn;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.NURN;
	}
	
	@Override
	public boolean isGorgoroth()
	{
		return false;
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.2F;
	}
	
	@Override
	public GrassBlockAndMeta getRandomGrass(Random random)
	{
		if (random.nextInt(3) == 0)
		{
			return new GrassBlockAndMeta(Blocks.tallgrass, 1);
		}
		else
		{
			return new GrassBlockAndMeta(LOTRMod.tallGrass, 0);
		}
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.5F;
	}
	
	@Override
	public float getChanceToSpawnLavaLakes()
	{
		return 0.2F;
	}

	@Override
	public int spawnCountMultiplier()
	{
		return 3;
	}
}
