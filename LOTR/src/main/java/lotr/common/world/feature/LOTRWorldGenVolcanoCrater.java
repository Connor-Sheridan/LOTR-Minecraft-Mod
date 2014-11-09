package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenVolcanoCrater extends WorldGenerator
{
	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		if (world.getBlock(i, j - 1, k) != Blocks.stone || world.isAirBlock(i, j, k))
		{
			return false;
		}

		int width = 6 + random.nextInt(8);
		int depth = 4 + random.nextInt(8);
		int lavaDepth = depth / 2;
		
		j -= depth + (3 + random.nextInt(4));
		if (j < 70)
		{
			return false;
		}
		
		int fullHeight = depth;
		int widthCheck = width + 1;
		
		for (int i1 = -widthCheck; i1 <= widthCheck; i1++)
		{
			for (int k1 = -widthCheck; k1 <= widthCheck; k1++)
			{
				for (int j1 = 0; j1 <= fullHeight; j1++)
				{
					int i2 = i + i1;
					int j2 = j + j1;
					int k2 = k + k1;
					
					double d = i1 * i1 + k1 * k1;
					if (d < widthCheck * widthCheck)
					{
						if (j1 <= depth && !world.getBlock(i2, j2, k2).isOpaqueCube())
						{
							return false;
						}
						
						if (j1 >= depth && world.getBlock(i2, j2 + 1, k2).isOpaqueCube())
						{
							fullHeight++;
						}
					}
				}
			}
		}
		
		for (int i1 = -widthCheck; i1 <= widthCheck; i1++)
		{
			for (int k1 = -widthCheck; k1 <= widthCheck; k1++)
			{
				for (int j1 = 0; j1 <= fullHeight; j1++)
				{
					int i2 = i + i1;
					int j2 = j + j1;
					int k2 = k + k1;
					
					double d = i1 * i1 + k1 * k1;
					if (d < width * width)
					{
						if (j1 <= lavaDepth)
						{
							world.setBlock(i2, j2, k2, Blocks.lava, 0, 2);
						}
						else
						{
							world.setBlock(i2, j2, k2, Blocks.air, 0, 2);
						}
					}
					else if (d < widthCheck * widthCheck)
					{
						int obsidianDepth = lavaDepth + (depth - lavaDepth) / 2 + random.nextInt(3);
								
						if (j1 <= obsidianDepth)
						{
							world.setBlock(i2, j2, k2, Blocks.obsidian, 0, 2);
						}
					}
				}
			}
		}
		
		return true;
	}
}
