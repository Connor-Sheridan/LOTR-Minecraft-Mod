package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenDeadTrees;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenMinhiriathWasteland extends LOTRBiomeGenMinhiriath
{
	public LOTRBiomeGenMinhiriathWasteland(int i)
	{
		super(i);

		decorator.grassPerChunk = 2;
		decorator.doubleGrassPerChunk = 0;
		decorator.flowersPerChunk = 0;
		
		decorator.addTree(LOTRTreeType.OAK_DEAD, 5000);
		decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 1000);
		decorator.addTree(LOTRTreeType.BEECH_DEAD, 500);
		decorator.addTree(LOTRTreeType.BIRCH_DEAD, 100);
		
		biomeColors.setGrass(0xAEB269);
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		if (random.nextInt(4) == 0)
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
		return 0.25F;
	}
}
