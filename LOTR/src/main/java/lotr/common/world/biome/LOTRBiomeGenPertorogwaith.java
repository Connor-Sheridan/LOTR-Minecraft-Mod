package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.LOTRWaypoint;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.*;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenPertorogwaith extends LOTRBiome
{
	private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
	private WorldGenerator deadMoundGen = new LOTRWorldGenBoulder(Blocks.soul_sand, 0, 1, 3);
	
	public LOTRBiomeGenPertorogwaith(int i)
	{
		super(i);
		
		spawnableCreatureList.clear();

		spawnableEvilList.clear();
		
		setGoodEvilWeight(0, 100);
		
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 4;
		decorator.flowersPerChunk = 0;
		
		decorator.addTree(LOTRTreeType.OAK_DESERT, 50);
		decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
		decorator.addTree(LOTRTreeType.ACACIA, 100);
		decorator.addTree(LOTRTreeType.ACACIA_DEAD, 200);
		
		registerHaradFlowers();
		
		biomeColors.setSky(0x827C72);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
	}

	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterPertorogwaith;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.PERTOROGWAITH;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
		super.decorate(world, random, i, k);
		
		if (random.nextInt(4) == 0)
		{
			int boulders = 1 + random.nextInt(4);
			for (int l = 0; l < boulders; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
		
		if (random.nextInt(40) == 0)
		{
			for (int l = 0; l < 3; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				deadMoundGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
				for (int l1 = 0; l1 < 4; l1++)
				{
					int i2 = i1 - 4 + random.nextInt(9);
					int k2 = k1 - 4 + random.nextInt(9);
					int j2 = world.getHeightValue(i2, k2);
					if (LOTRMod.isOpaque(world, i2, j2 - 1, k2) && (world.isAirBlock(i2, j2, k2) || world.getBlock(i2, j2, k2) == Blocks.tallgrass))
					{
						world.setBlock(i2, j2, k2, Blocks.skull, 1, 2);
						TileEntity tileentity = world.getTileEntity(i2, j2, k2);
						if (tileentity != null && tileentity instanceof TileEntitySkull)
						{
							TileEntitySkull skull = (TileEntitySkull)tileentity;
							skull.func_145903_a(random.nextInt(16));
						}
					}
				}
			}
		}
    }
	
	@Override
	public float getTreeIncreaseChance()
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
		return 0.5F;
	}
}
