package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.LOTRBanditSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenShireMarshes extends LOTRBiomeGenShire
{
	public LOTRBiomeGenShireMarshes(int i)
	{
		super(i);

		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 0;
		decorator.logsPerChunk = 2;
		decorator.flowersPerChunk = 4;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 6;
		decorator.enableFern = true;
		decorator.waterlilyPerChunk = 2;
		decorator.reedsPerChunk = 10;
		
		registerSwampFlowers();
		
		biomeColors.resetGrass();
		
		setBanditChance(LOTRBanditSpawner.RARE);
	}
	
	@Override
    public WorldGenAbstractTree func_150567_a(Random random)
    {
		if (random.nextInt(3) > 0)
		{
			return new WorldGenSwamp();
		}
		return super.func_150567_a(random);
    }
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.75F;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		for (int l = 0; l < 1; l++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			new WorldGenLakes(Blocks.water).generate(world, random, i1, j1, k1);
		}
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.1F;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return super.spawnCountMultiplier() * 3;
	}
}
