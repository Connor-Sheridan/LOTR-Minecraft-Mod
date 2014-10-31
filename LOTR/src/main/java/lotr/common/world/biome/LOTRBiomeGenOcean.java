package lotr.common.world.biome;

import java.awt.Color;
import java.util.*;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRWaypoint;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenUnderwaterElvenRuin;
import lotr.common.world.structure2.LOTRWorldGenNumenorRuin;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenOcean extends LOTRBiome
{
	private static Random iceRand = new Random();
	
	private static int iceLimitSouth = -30000;
	private static int iceLimitNorth = -60000;
	
	public LOTRBiomeGenOcean(int i)
	{
		super(i);
		
		spawnableEvilList.clear();
		
		decorator.treesPerChunk = 1;
		decorator.flowersPerChunk = 2;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 1;
		
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
		decorator.addTree(LOTRTreeType.BIRCH, 100);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
		decorator.addTree(LOTRTreeType.BEECH, 50);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 5);
		decorator.addTree(LOTRTreeType.APPLE, 3);
		decorator.addTree(LOTRTreeType.PEAR, 3);
		
		decorator.addRandomStructure(new LOTRWorldGenNumenorRuin(false), 500);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.OCEAN;
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterOcean;
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
		
		if (i < LOTRWaypoint.MITHLOND.getXCoord() && k > LOTRWaypoint.SOUTH_FOROCHEL.getZCoord() && k < LOTRWaypoint.ERYN_VORN.getZCoord())
		{
			if (random.nextInt(200) == 0)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
				new LOTRWorldGenUnderwaterElvenRuin(false).generate(world, random, i1, j1, k1);
			}
		}
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.25F;
	}

	public static boolean isFrozen(int i, int k)
	{
		if (k > iceLimitSouth)
		{
			return false;
		}
		else
		{
			int l = iceLimitNorth - k;
			l *= -1;
			if (l < 1)
			{
				return true;
			}
			else
			{
				iceRand.setSeed((long)i * 341873128712L + (long)k * 132897987541L);
				l -= Math.abs(iceLimitNorth - iceLimitSouth) / 2;
				if (l < 0)
				{
					l *= -1;
					l = (int)Math.sqrt(l);
					if (l < 2)
					{
						l = 2;
					}
					return iceRand.nextInt(l) != 0;
				}
				else
				{
					l = (int)Math.sqrt(l);
					if (l < 2)
					{
						l = 2;
					}
					return iceRand.nextInt(l) == 0;
				}
			}
		}
	}
}
