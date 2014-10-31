package lotr.common.block;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenSimpleTrees;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockFruitSapling extends LOTRBlockSaplingBase
{
    public LOTRBlockFruitSapling()
    {
        super();
		setSaplingNames("apple", "pear", "cherry", "mango");
    }

	@Override
    public void growTree(World world, int i, int j, int k, Random random)
    {
        int meta = world.getBlockMetadata(i, j, k) & 3;
        WorldGenerator treeGen = null;
		
		if (meta == 0)
		{
			treeGen = LOTRTreeType.APPLE.create(true);
		}
		else if (meta == 1)
		{
			treeGen = LOTRTreeType.PEAR.create(true);
		}
		else if (meta == 2)
		{
			treeGen = LOTRTreeType.CHERRY.create(true);
		}
		else if (meta == 3)
		{
			treeGen = LOTRTreeType.MANGO.create(true);
		}
		
		world.setBlock(i, j, k, Blocks.air, 0, 4);
		
		if (treeGen != null && !treeGen.generate(world, random, i, j, k))
		{
			world.setBlock(i, j, k, this, meta, 4);
		}
    }
}
