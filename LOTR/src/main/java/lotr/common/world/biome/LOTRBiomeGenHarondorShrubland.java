package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;

public class LOTRBiomeGenHarondorShrubland extends LOTRBiomeGenHarondor
{
	public LOTRBiomeGenHarondorShrubland(int i)
	{
		super(i);

		decorator.treesPerChunk = 8;
		
		decorator.addTree(LOTRTreeType.OAK_SHRUB, 10000);
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
		genStandardOre(world, random, i, k, 6, harondorDirtPatchGen, 60, 80);
		
		super.decorate(world, random, i, k);
	}
}
