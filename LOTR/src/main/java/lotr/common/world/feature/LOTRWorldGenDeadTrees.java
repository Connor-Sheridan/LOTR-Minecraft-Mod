package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenDeadTrees extends WorldGenAbstractTree
{
	private Block woodBlock;
	private int woodMeta;
	
	public LOTRWorldGenDeadTrees(Block block, int i)
	{
		super(false);
		woodBlock = block;
		woodMeta = i;
	}
	
	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		Block id = world.getBlock(i, j - 1, k);
		if (id != Blocks.grass && id != Blocks.dirt && id != Blocks.stone && id != Blocks.sand && id != Blocks.gravel)
		{
			return false;
		}

		if (id == Blocks.grass)
		{
			setBlockAndNotifyAdequately(world, i, j - 1, k, Blocks.dirt, 0);
		}
		
		int height = 3 + random.nextInt(4);
		for (int j1 = j; j1 < j + height; j1++)
		{
			setBlockAndNotifyAdequately(world, i, j1, k, woodBlock, woodMeta);
		}
		
		for (int branch = 0; branch < 4; branch++)
		{
			int branchLength = 3 + random.nextInt(5);
			int branchHorizontalPos = 0;
			int branchVerticalPos = j + height - 1 - random.nextInt(2);
			for (int l = 0; l < branchLength; l++)
			{
				if (random.nextInt(4) == 0)
				{
					branchHorizontalPos++;
				}
				if (random.nextInt(3) != 0)
				{
					branchVerticalPos++;
				}
				
				switch (branch)
				{
					case 0:
						setBlockAndNotifyAdequately(world, i - branchHorizontalPos, branchVerticalPos, k, woodBlock, woodMeta | 12);
						break;
					case 1:
						setBlockAndNotifyAdequately(world, i, branchVerticalPos, k + branchHorizontalPos, woodBlock, woodMeta | 12);
						break;
					case 2:
						setBlockAndNotifyAdequately(world, i + branchHorizontalPos, branchVerticalPos, k, woodBlock, woodMeta | 12);
						break;
					case 3:
						setBlockAndNotifyAdequately(world, i, branchVerticalPos, k - branchHorizontalPos, woodBlock, woodMeta | 12);
						break;
				}
			}
		}
		
		return true;
	}
}
