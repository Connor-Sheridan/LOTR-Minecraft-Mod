package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenBaobab extends WorldGenAbstractTree
{
	private Block woodBlock;
	private int woodMeta;
	private Block leafBlock;
	private int leafMeta;
	
    public LOTRWorldGenBaobab(boolean flag)
    {
		super(flag);
		woodBlock = LOTRMod.wood4;
		woodMeta = 1;
		leafBlock = LOTRMod.leaves4;
		leafMeta = 1;
	}

	@Override
    public boolean generate(World world, Random random, int i, int j, int k)
    {
		int trunkCircleWidth = 4;
        int height = 16 + random.nextInt(10);
        int xSlope = 5 + random.nextInt(10);
		if (random.nextBoolean())
		{
			xSlope *= -1;
		}
		int zSlope = 5 + random.nextInt(10);
		if (random.nextBoolean())
		{
			zSlope *= -1;
		}
        
        boolean flag = true;

        if (j >= 1 && j + height + 5 <= 256)
        {
			for (int i1 = i - trunkCircleWidth - 1; i1 <= i + trunkCircleWidth + 1 && flag; i1++)
			{
				for (int k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1 && flag; k1++)
				{
					int i2 = Math.abs(i1 - i);
					int k2 = Math.abs(k1 - k);
					if (i2 * i2 + k2 * k2 <= trunkCircleWidth * trunkCircleWidth)
					{
						for (int j1 = j; j1 <= j + 1 + height; j1++)
						{
							if (j1 >= 0 && j1 < 256)
							{
								Block block = world.getBlock(i1, j1, k1);
								if (!isReplaceable(world, i1, j1, k1) && !block.isReplaceable(world, i1, j1, k1))
	                            {
	                                flag = false;
	                            }
							}
							else
							{
								flag = false;
							}
						}
						
						Block below = world.getBlock(i1, j - 1, k1);
				        boolean isSoil = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling);
				        if (!isSoil)
				        {
				        	flag = false;
				        }
					}
				}
			}
        }
        else
        {
        	flag = false;
        }

		if (!flag)
		{
			return false;
		}
		else
		{
			for (int j1 = 0; j1 < height; j1++)
			{
				for (int i1 = i - trunkCircleWidth - 1; i1 <= i + trunkCircleWidth + 1; i1++)
				{
					for (int k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1; k1++)
					{
						int i2 = Math.abs(i1 - i);
						int k2 = Math.abs(k1 - k);
						if (i2 * i2 + k2 * k2 <= trunkCircleWidth * trunkCircleWidth)
						{
							if (j1 == 0)
							{
								setBlockAndNotifyAdequately(world, i1, j - 1, k1, Blocks.dirt, 0);
							}

							setBlockAndNotifyAdequately(world, i1, j + j1, k1, woodBlock, woodMeta);
						}
					}
				}
							
				if (j1 % xSlope == 0)
				{
					if (xSlope > 0)
					{
						i++;
					}
					else if (xSlope < 0)
					{
						i--;
					}
				}
				if (j1 % zSlope == 0)
				{
					if (zSlope > 0)
					{
						k++;
					}
					else if (zSlope < 0)
					{
						k--;
					}
				}
			}

            for (int j1 = j + height - 1; j1 > j + (int)(height * 0.75F); j1 -= 1)
            {
            	int branches = 2 + random.nextInt(3);
            	for (int l = 0; l < branches; l++)
            	{
	                float angle = random.nextFloat() * (float)Math.PI * 2F;
	                int i1 = i;
	                int k1 = k;
	                int j2 = j1;
	
	                int length = MathHelper.getRandomIntegerInRange(random, 4, 6);
	                for (int l1 = trunkCircleWidth; l1 < trunkCircleWidth + length; l1++)
	                {
	                	i1 = i + (int)(1.5F + MathHelper.cos(angle) * (float)l1);
	                    k1 = k + (int)(1.5F + MathHelper.sin(angle) * (float)l1);
	                    j2 = j1 - 3 + l1 / 2;
	                    if (isReplaceable(world, i1, j2, k1))
	                    {
	                    	setBlockAndNotifyAdequately(world, i1, j2, k1, woodBlock, woodMeta);
	                    }
	                    else
	                    {
	                    	break;
	                    }
	                }
	
	                int leafMin = 1 + random.nextInt(2);
	                for (int j3 = j2 - leafMin; j3 <= j2; j3++)
	                {
	                    int leafRange = 1 - (j3 - j2);
	                    spawnLeaves(world, i1, j3, k1, leafRange);
	                }
            	}
            }
            
			for (int i1 = i - trunkCircleWidth - 1; i1 <= i + trunkCircleWidth + 1; i1++)
			{
				for (int k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1; k1++)
				{
					int i2 = Math.abs(i1 - i);
					int k2 = Math.abs(k1 - k);
					if (i2 * i2 + k2 * k2 <= trunkCircleWidth * trunkCircleWidth)
					{
						if (random.nextInt(5) == 0)
						{
							int j1 = j + height;
							int topHeight = 2 + random.nextInt(3);
							for (int l = 0; l < topHeight; l++)
							{
								setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta);
								j1++;
							}
							
							int leafMin = 2;
			                for (int j2 = j1 - leafMin; j2 <= j1; j2++)
			                {
			                    int leafRange = 1 - (j2 - j1);
			                    spawnLeaves(world, i1, j2, k1, leafRange);
			                }
						}
					}
				}
			}
			
			return true;
        }
    }
	
	private void spawnLeaves(World world, int i, int j, int k, int leafRange)
	{
        int leafRangeSq = leafRange * leafRange;

        for (int i1 = i - leafRange; i1 <= i + leafRange; i1++)
        {
            for (int k1 = k - leafRange; k1 <= k + leafRange; k1++)
            {
            	int i2 = i1 - i;
                int k2 = k1 - k;

                if (i2 * i2 + k2 * k2 <= leafRangeSq)
                {
                	Block block = world.getBlock(i1, j, k1);
                    if (block.isReplaceable(world, i1, j, k1) || block.isLeaves(world, i1, j, k1))
                    {
                        setBlockAndNotifyAdequately(world, i1, j, k1, leafBlock, leafMeta);
                    }
                }
            }
        }
	}
}
