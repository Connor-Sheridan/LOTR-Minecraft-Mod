package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.world.biome.LOTRBiomeGenBarrowDowns;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBDBarrow extends LOTRWorldGenStructureBase2
{
	public LOTRWorldGenBDBarrow(boolean flag)
	{
		super(flag);
	}
	
	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation)
	{
		if (restrictions)
		{
			if (world.getBlock(i, j - 1, k) != Blocks.grass || !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenBarrowDowns))
			{
				return false;
			}
		}
		
		j--;
		
		int radius = 9;
		int height = 6;
		
		if (!restrictions && usingPlayer != null)
		{
			rotation = usingPlayerRotation();
			switch (rotation)
			{
				case 0:
					k += radius;
					break;
				case 1:
					i -= radius;
					break;
				case 2:
					k -= radius;
					break;
				case 3:
					i += radius;
					break;
			}
		}
		
		setOrigin(i, j, k);
		setRotationMode(rotation);
		
		if (restrictions)
		{
			int minHeight = 0;
			int maxHeight = 0;
			
			for (int i1 = -radius; i1 <= radius; i1++)
			{
				for (int k1 = -radius; k1 <= radius; k1++)
				{
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (getBlock(world, i1, j1, k1) != Blocks.grass)
					{
						return false;
					}

					if (j1 < minHeight)
					{
						minHeight = j1;
					}
					if (j1 > maxHeight)
					{
						maxHeight = j1;
					}
				}
			}
			
			if (maxHeight - minHeight > 5)
			{
				return false;
			}
		}
		
		for (int i1 = -radius; i1 <= radius; i1++)
		{
			for (int j1 = height; j1 >= 0; j1--)
			{
				for (int k1 = -radius; k1 <= radius; k1++)
				{
					if (i1 * i1 + j1 * j1 + k1 * k1 > radius * radius)
					{
						continue;
					}
					boolean grass = !isOpaque(world, i1, j1 + 1, k1);
					setBlockAndMetadata(world, i1, j1, k1, grass ? Blocks.grass : Blocks.dirt, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		
		for (int i1 = -radius; i1 <= radius; i1++)
		{
			for (int k1 = -radius; k1 <= radius; k1++)
			{
				for (int j1 = -1; !isOpaque(world, i1, j1, k1) && getY(j1) >= 0; j1--)
				{
					if (i1 * i1 + k1 * k1 > radius * radius)
					{
						continue;
					}
					setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		
		for (int i1 = -3; i1 <= 3; i1++)
		{
			for (int k1 = -3; k1 <= 3; k1++)
			{
				for (int j1 = 1; j1 <= 3; j1++)
				{
					setAir(world, i1, j1, k1);
				}
			}
		}
		
		for (int i1 = -2; i1 <= 2; i1++)
		{
			for (int k1 = -2; k1 <= 2; k1++)
			{
				setBlockAndMetadata(world, i1, 1, k1, Blocks.dirt, 0);
			}
		}

		placeChest(world, random, 0, 0, 0, 0, LOTRChestContents.BARROW_DOWNS);
		
		setBlockAndMetadata(world, -3, 2, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 2, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 2, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 2, 3, Blocks.torch, 4);

		return true;
	}
}
