package lotr.common.block;

import java.util.Random;

import lotr.common.world.feature.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockSapling3 extends LOTRBlockSaplingBase
{
    public LOTRBlockSapling3()
    {
        super();
		setSaplingNames("maple", "larch", "datePalm", "mangrove");
    }
	
	@Override
    public void growTree(World world, int i, int j, int k, Random random)
    {
        int meta = world.getBlockMetadata(i, j, k) & 3;
        WorldGenerator treeGen = null;
		
		if (meta == 0)
		{
			if (random.nextInt(10) == 0)
			{
				treeGen = LOTRTreeType.MAPLE_LARGE.create(true);
			}
			else
			{
				treeGen = LOTRTreeType.MAPLE.create(true);
			}
		}
		else if (meta == 1)
		{
			treeGen = LOTRTreeType.LARCH.create(true);
		}
		else if (meta == 2)
		{
			treeGen = LOTRTreeType.DATE_PALM.create(true);
		}
		else if (meta == 3)
		{
			treeGen = LOTRTreeType.MANGROVE.create(true);
		}
		
		world.setBlock(i, j, k, Blocks.air, 0, 4);
		
		if (treeGen != null && !treeGen.generate(world, random, i, j, k))
		{
			world.setBlock(i, j, k, this, meta, 4);
		}
    }
}
