package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenMinhiriath extends LOTRBiomeGenEriador
{
	protected WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
	
	public LOTRBiomeGenMinhiriath(int i)
	{
		super(i);

		decorator.grassPerChunk = 5;
		decorator.doubleGrassPerChunk = 3;
		
		decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
		decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 300);
		decorator.addTree(LOTRTreeType.BEECH_DEAD, 100);
		decorator.addTree(LOTRTreeType.BIRCH_DEAD, 50);
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterMinhiriath;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		if (random.nextInt(16) == 0)
		{
			for (int l = 0; l < 3; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.1F;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.05F;
	}
}
