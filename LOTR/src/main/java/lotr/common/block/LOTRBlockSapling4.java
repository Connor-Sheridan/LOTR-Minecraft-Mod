package lotr.common.block;

import java.util.Random;

import lotr.common.world.feature.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class LOTRBlockSapling4 extends LOTRBlockSaplingBase
{
    public LOTRBlockSapling4()
    {
        super();
		setSaplingNames("chestnut", "baobab", "cedar");
    }
    
    @Override
    public void incrementGrowth(World world, int i, int j, int k, Random random)
    {
        int meta = world.getBlockMetadata(i, j, k) & 3;
        if (meta == 1 && random.nextInt(4) > 0)
        {
        	return;
        }
        
        super.incrementGrowth(world, i, j, k, random);
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
				treeGen = LOTRTreeType.CHESTNUT_LARGE.create(true);
			}
			else
			{
				treeGen = LOTRTreeType.CHESTNUT.create(true);
			}
		}
		
		if (meta == 1)
		{
			treeGen = LOTRTreeType.BAOBAB.create(true);
		}
		
		if (meta == 2)
		{
			treeGen = LOTRTreeType.CEDAR.create(true);
		}
		
		world.setBlock(i, j, k, Blocks.air, 0, 4);
		
		if (treeGen != null && !treeGen.generate(world, random, i, j, k))
		{
			world.setBlock(i, j, k, this, meta, 4);
		}
    }
}
