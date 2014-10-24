package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRWorldGenBaobab;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeGenFarHaradBaobab extends LOTRBiomeGenFarHarad
{
	public LOTRBiomeGenFarHaradBaobab(int i)
	{
		super(i);
		
		decorator.treesPerChunk = 0;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 6;
	}
	
	@Override
    public WorldGenAbstractTree func_150567_a(Random random)
    {
		if (random.nextInt(3) > 0)
		{
			return new LOTRWorldGenBaobab(false);
		}
		return super.func_150567_a(random);
    }
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.75F;
	}
}
