package lotr.common.block;

import java.util.Random;

import lotr.common.world.feature.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockSapling2 extends LOTRBlockSaplingBase
{
    public LOTRBlockSapling2()
    {
        super();
		setSaplingNames("lebethron", "beech", "holly", "banana");
    }

	@Override
    public void growTree(World world, int i, int j, int k, Random random)
    {
        int metadata = world.getBlockMetadata(i, j, k) & 3;
        WorldGenerator treeGen = null;
		int extraTrunkWidth = 0;
		int xOffset = 0;
		int zOffset = 0;
		
		if (metadata == 0 || metadata == 2)
		{
            for (int i1 = 0; i1 >= -1; i1--)
            {
                for (int k1 = 0; k1 >= -1; k1--)
                {
                    if (isSameSapling(world, i + i1, j, k + k1, metadata) && isSameSapling(world, i + i1 + 1, j, k + k1, metadata) && isSameSapling(world, i + i1, j, k + k1 + 1, metadata) && isSameSapling(world, i + i1 + 1, j, k + k1 + 1, metadata))
                    {
						if (metadata == 0)
						{
							treeGen = LOTRTreeType.LEBETHRON_LARGE.create(true);
						}
						else if (metadata == 2)
						{
							treeGen = LOTRTreeType.HOLLY_LARGE.create(true);
						}
                        extraTrunkWidth = 1;
						xOffset = i1;
						zOffset = k1;
                        break;
                    }
                }

                if (treeGen != null)
                {
                    break;
                }
            }

            if (treeGen == null)
            {
				xOffset = 0;
				zOffset = 0;
				if (metadata == 0)
				{
					treeGen = LOTRTreeType.LEBETHRON.create(true);
				}
				else if (metadata == 2)
				{
					treeGen = LOTRTreeType.HOLLY.create(true);
				}
            }
		}
		else if (metadata == 1)
		{
			if (random.nextInt(10) == 0)
			{
				treeGen = LOTRTreeType.BEECH_LARGE.create(true);
			}
			else
			{
				treeGen = LOTRTreeType.BEECH.create(true);
			}
		}
		else if (metadata == 3)
		{
			treeGen = LOTRTreeType.BANANA.create(true);
		}
		
		for (int i1 = 0; i1 <= extraTrunkWidth; i1++)
		{
			for (int k1 = 0; k1 <= extraTrunkWidth; k1++)
			{
				world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.air, 0, 4);
			}
		}
		
		if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset))
		{
			for (int i1 = 0; i1 <= extraTrunkWidth; i1++)
			{
				for (int k1 = 0; k1 <= extraTrunkWidth; k1++)
				{
					world.setBlock(i + xOffset + i1, j, k + zOffset + k1, this, metadata, 4);
				}
			}
		}
    }
}
