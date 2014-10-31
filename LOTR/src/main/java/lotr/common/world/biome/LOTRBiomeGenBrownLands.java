package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.*;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.biome.LOTRBiome.GrassBlockAndMeta;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenBrownLands extends LOTRBiome
{
	private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
	
	public LOTRBiomeGenBrownLands(int i)
	{
		super(i);
		
		setDisableRain();
		
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		
		spawnableEvilList.clear();
		
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 2;
		
		decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
		
		biomeColors.setGrass(0xAD875F);
		biomeColors.setSky(0x877962);
		
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 3), 2000);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.MORDOR, LOTRInvasionSpawner.UNCOMMON));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterBrownLands;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.BROWN_LANDS;
	}

	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		if (random.nextInt(8) == 0)
		{
			int boulders = 1 + random.nextInt(6);
			for (int l = 0; l < boulders; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
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
	public float getTreeIncreaseChance()
	{
		return 0.1F;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0F;
	}
}
