package lotr.client.sound;

import java.util.Random;

import lotr.common.block.LOTRBlockGrass;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRAmbience
{
	private static int tallGrassCount;
	private static int leafCount;
	
	public static void update(World world, EntityPlayer entityplayer)
	{
		/*world.theProfiler.startSection("lotrAmbience");
		
		double x = entityplayer.posX;
		double y = entityplayer.boundingBox.minY;
		double z = entityplayer.posZ;
		
		int i = MathHelper.floor_double(x);
		int j = MathHelper.floor_double(y);
		int k = MathHelper.floor_double(z);
		
		Random rand = world.rand;
		
		if (world.getWorldTime() % 60 == 0)
		{
			tallGrassCount = 0;
			leafCount = 0;
			
			int range = 16;
			for (int i1 = i - range; i1 <= i + range; i1++)
			{
				for (int j1 = j - range / 2; j1 <= j + range / 2; j1++)
				{
					for (int k1 = k - range; k1 <= k + range; k1++)
					{
						Block block = world.getBlock(i1, j1, k1);
						if (block instanceof BlockTallGrass || block instanceof LOTRBlockGrass)
						{
							tallGrassCount++;
						}
						
						if (block instanceof BlockLeavesBase)
						{
							leafCount++;
						}
					}
				}
			}
		}
		
		BiomeGenBase biomegenbase = world.getBiomeGenForCoords(i, k);
		if (biomegenbase instanceof LOTRBiome)
		{
			LOTRBiome biome = (LOTRBiome)biomegenbase;
			
			float cricketChance = (float)tallGrassCount / 300F;
			cricketChance /= 20F;
			if (rand.nextFloat() < cricketChance)
			{
				double d = x + MathHelper.getRandomDoubleInRange(rand, -8D, 8D);
				double d1 = y + MathHelper.getRandomDoubleInRange(rand, -4D, 4D);
				double d2 = z + MathHelper.getRandomDoubleInRange(rand, -8D, 8D);
				
				world.playSoundEffect(d, d1, d2, "lotr:ambience.crickets", 0.5F, 0.8F + rand.nextFloat() * 0.4F);
			}
		}
		
		world.theProfiler.endSection();*/
	}
}
