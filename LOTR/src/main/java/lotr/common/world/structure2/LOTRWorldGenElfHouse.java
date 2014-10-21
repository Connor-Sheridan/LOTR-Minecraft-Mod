package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityGaladhrimElf;
import lotr.common.world.feature.LOTRWorldGenMallornLarge;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenElfHouse extends LOTRWorldGenStructureBase2
{
	public LOTRWorldGenElfHouse(boolean flag)
	{
		super(flag);
	}
	
	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation)
	{
		if (!restrictions && usingPlayer != null)
		{
			rotation = usingPlayerRotation();
			switch (rotation)
			{
				case 0:
					k += 2;
					break;
				case 1:
					i -= 2;
					break;
				case 2:
					k -= 2;
					break;
				case 3:
					i += 2;
					break;
			}
			
			j--;
			
			LOTRWorldGenMallornLarge treeGen = new LOTRWorldGenMallornLarge(true);
			int j1 = treeGen.generateAndReturnHeight(world, random, i, j, k, true);
			j += MathHelper.floor_double(j1 * MathHelper.randomFloatClamp(random, LOTRWorldGenMallornLarge.HOUSE_HEIGHT_MIN, LOTRWorldGenMallornLarge.HOUSE_HEIGHT_MAX));
		}
		
		setOrigin(i, j, k);
		setRotationMode(rotation);
		
		if (restrictions)
		{
			for (int i1 = -8; i1 <= 8; i1++)
			{
				for (int j1 = -3; j1 <= 6; j1++)
				{
					for (int k1 = -8; k1 <= 8; k1++)
					{
						if (Math.abs(i1) <= 2 && Math.abs(k1) <= 2)
						{
							continue;
						}
						else if (!isAir(world, i1, j1, k1))
						{
							return false;
						}
					}
				}
			}
		}
		else if (usingPlayer != null)
		{
			for (int i1 = -2; i1 <= 2; i1++)
			{
				for (int k1 = -2; k1 <= 2; k1++)
				{
					for (int j1 = 0; !isOpaque(world, i1, j1, k1) && getY(j1) >= 0; j1--)
					{
						setBlockAndMetadata(world, i1, j1, k1, LOTRMod.wood, 1);
					}
				}
			}
		}
		
		for (int i1 = -7; i1 <= 7; i1++)
		{
			for (int j1 = 1; j1 <= 4; j1++)
			{
				for (int k1 = -7; k1 <= 7; k1++)
				{
					setBlockAndMetadata(world, i1, j1, k1, Blocks.air, 0);
				}
			}
		}
		
		for (int i1 = -2; i1 <= 2; i1++)
		{
			for (int j1 = -1; j1 <= 5; j1++)
			{
				for (int k1 = -2; k1 <= 2; k1++)
				{
					setBlockAndMetadata(world, i1, j1, k1, LOTRMod.wood, 1);
					
					if (j1 >= 1 && j1 <= 2 && Math.abs(i1) == 2 && Math.abs(k1) == 2)
					{
						setBlockAndMetadata(world, i1, j1, k1, LOTRMod.fence, 1);
					}
				}
			}
		}
		
		for (int i1 = -6; i1 <= 6; i1++)
		{
			for (int k1 = -6; k1 <= 6; k1++)
			{
				if (Math.abs(i1) <= 2 && Math.abs(k1) <= 2)
				{
					continue;
				}
				if (Math.abs(i1) == 6 || Math.abs(k1) == 6)
				{
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k1, LOTRMod.planks, 1);
			}
		}
		
		for (int i1 = -5; i1 <= 5; i1++)
		{
			setBlockAndMetadata(world, i1, 0, -6, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 0, 6, LOTRMod.planks, 1);
		}
		
		for (int k1 = -5; k1 <= 5; k1++)
		{
			setBlockAndMetadata(world, -6, 0, k1, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 6, 0, k1, LOTRMod.planks, 1);
		}
		
		for (int i1 = -3; i1 <= 3; i1++)
		{
			setBlockAndMetadata(world, i1, 0, -7, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 0, 7, LOTRMod.planks, 1);
		}
		
		for (int k1 = -3; k1 <= 3; k1++)
		{
			setBlockAndMetadata(world, -7, 0, k1, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 7, 0, k1, LOTRMod.planks, 1);
		}
		
		for (int j1 = 1; j1 <= 4; j1++)
		{
			setBlockAndMetadata(world, -5, j1, -5, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 5, j1, -5, LOTRMod.planks, 1);
			setBlockAndMetadata(world, -5, j1, 5, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 5, j1, 5, LOTRMod.planks, 1);
			
			setBlockAndMetadata(world, -6, j1, -3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, -6, j1, 3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 6, j1, -3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 6, j1, 3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, -3, j1, -6, LOTRMod.wood, 1);
			setBlockAndMetadata(world, -3, j1, 6, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 3, j1, -6, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 3, j1, 6, LOTRMod.wood, 1);
		}
		
		setBlockAndMetadata(world, -4, 2, -5, LOTRMod.mallornTorch, 2);
		setBlockAndMetadata(world, -5, 2, -4, LOTRMod.mallornTorch, 3);
		setBlockAndMetadata(world, 4, 2, -5, LOTRMod.mallornTorch, 1);
		setBlockAndMetadata(world, 5, 2, -4, LOTRMod.mallornTorch, 3);
		setBlockAndMetadata(world, -4, 2, 5, LOTRMod.mallornTorch, 2);
		setBlockAndMetadata(world, -5, 2, 4, LOTRMod.mallornTorch, 4);
		setBlockAndMetadata(world, 4, 2, 5, LOTRMod.mallornTorch, 1);
		setBlockAndMetadata(world, 5, 2, 4, LOTRMod.mallornTorch, 4);
		
		for (int i1 = -3; i1 <= 3; i1++)
		{
			setBlockAndMetadata(world, i1, 1, -7, LOTRMod.fence, 1);
			setBlockAndMetadata(world, i1, 1, 7, LOTRMod.fence, 1);
		}
		
		for (int k1 = -3; k1 <= 3; k1++)
		{
			setBlockAndMetadata(world, -7, 1, k1, LOTRMod.fence, 1);
			setBlockAndMetadata(world, 7, 1, k1, LOTRMod.fence, 1);
		}
		
		setBlockAndMetadata(world, -4, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -5, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -4, 1, 6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -5, 1, 6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 4, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 5, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 4, 1, 6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 5, 1, 6, LOTRMod.fence, 1);
		
		setBlockAndMetadata(world, -6, 1, -4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 1, -5, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, -4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, -5, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 1, 4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 1, 5, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, 4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, 5, LOTRMod.fence, 1);
		
		setBlockAndMetadata(world, -6, 4, -2, LOTRMod.stairsMallorn, 7);
		setBlockAndMetadata(world, -6, 4, -1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, -6, 4, 0, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, -6, 4, 1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, -6, 4, 2, LOTRMod.stairsMallorn, 6);
		
		setBlockAndMetadata(world, 6, 4, -2, LOTRMod.stairsMallorn, 7);
		setBlockAndMetadata(world, 6, 4, -1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 6, 4, 0, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 6, 4, 1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 6, 4, 2, LOTRMod.stairsMallorn, 6);
		
		setBlockAndMetadata(world, -2, 4, -6, LOTRMod.stairsMallorn, 4);
		setBlockAndMetadata(world, -1, 4, -6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 0, 4, -6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 1, 4, -6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 2, 4, -6, LOTRMod.stairsMallorn, 5);
		
		setBlockAndMetadata(world, -2, 4, 6, LOTRMod.stairsMallorn, 4);
		setBlockAndMetadata(world, -1, 4, 6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 0, 4, 6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 1, 4, 6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 2, 4, 6, LOTRMod.stairsMallorn, 5);
		
		for (int i1 = -6; i1 <= -4; i1++)
		{
			setBlockAndMetadata(world, i1, 4, -6, LOTRMod.stairsMallorn, 6);
			setBlockAndMetadata(world, i1, 4, 6, LOTRMod.stairsMallorn, 7);
		}
		
		for (int i1 = 4; i1 <= 6; i1++)
		{
			setBlockAndMetadata(world, i1, 4, -6, LOTRMod.stairsMallorn, 6);
			setBlockAndMetadata(world, i1, 4, 6, LOTRMod.stairsMallorn, 7);
		}
		
		for (int k1 = -6; k1 <= -4; k1++)
		{
			setBlockAndMetadata(world, -6, 4, k1, LOTRMod.stairsMallorn, 5);
			setBlockAndMetadata(world, 6, 4, k1, LOTRMod.stairsMallorn, 4);
		}
		
		for (int k1 = 4; k1 <= 6; k1++)
		{
			setBlockAndMetadata(world, -6, 4, k1, LOTRMod.stairsMallorn, 5);
			setBlockAndMetadata(world, 6, 4, k1, LOTRMod.stairsMallorn, 4);
		}
		
		for (int i1 = -4; i1 <= 4; i1++)
		{
			if (Math.abs(i1) <= 1)
			{
				continue;
			}
			setBlockAndMetadata(world, i1, 4, -5, LOTRMod.stairsMallorn, 7);
			setBlockAndMetadata(world, i1, 4, 5, LOTRMod.stairsMallorn, 6);
		}
		
		for (int k1 = -4; k1 <= 4; k1++)
		{
			if (Math.abs(k1) <= 1)
			{
				continue;
			}
			setBlockAndMetadata(world, -5, 4, k1, LOTRMod.stairsMallorn, 4);
			setBlockAndMetadata(world, 5, 4, k1, LOTRMod.stairsMallorn, 5);
		}
		
		for (int i1 = -6; i1 <= 6; i1++)
		{
			for (int k1 = -6; k1 <= 6; k1++)
			{
				if (restrictions && i1 >= -2 && i1 <= 2 && k1 >= -2 && k1 <= 2)
				{
					continue;
				}
				if ((i1 == -6 || i1 == 6) && (k1 == -6 || k1 == 6))
				{
					continue;
				}
				setBlockAndMetadata(world, i1, 5, k1, LOTRMod.planks, 1);
			}
		}
		
		for (int i1 = -3; i1 <= 3; i1++)
		{
			setBlockAndMetadata(world, i1, 5, -7, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 5, 7, LOTRMod.planks, 1);
		}
		
		for (int k1 = -3; k1 <= 3; k1++)
		{
			setBlockAndMetadata(world, -7, 5, k1, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 7, 5, k1, LOTRMod.planks, 1);
		}
		
		for (int i1 = -5; i1 <= 5; i1++)
		{
			for (int k1 = -5; k1 <= 5; k1++)
			{
				if (restrictions && i1 >= -2 && i1 <= 2 && k1 >= -2 && k1 <= 2)
				{
					continue;
				}
				if ((i1 == -5 || i1 == 5) && (k1 == -5 || k1 == 5))
				{
					continue;
				}
				setBlockAndMetadata(world, i1, 6, k1, LOTRMod.planks, 1);
			}
		}
		
		for (int i1 = -2; i1 <= 2; i1++)
		{
			setBlockAndMetadata(world, i1, 6, -6, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 6, 6, LOTRMod.planks, 1);
		}
		
		for (int k1 = -2; k1 <= 2; k1++)
		{
			setBlockAndMetadata(world, -6, 6, k1, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 6, 6, k1, LOTRMod.planks, 1);
		}
		
		for (int i1 = -8; i1 <= 8; i1++)
		{
			int stairZ = 0;
			int stairX = i1;
			
			int i2 = Math.abs(i1);
			if (i2 <= 3)
			{
				stairZ = 8;
			}
			else if (i2 <= 5)
			{
				stairZ = 7;
			}
			else if (i2 <= 7)
			{
				stairZ = 6;
			}
			else
			{
				stairZ = 4;
			}
			
			setBlockAndMetadata(world, stairX, 5, -stairZ, LOTRMod.stairsMallorn, 2);
			setBlockAndMetadata(world, stairX, 5, stairZ, LOTRMod.stairsMallorn, 3);
			
			stairZ--;
			stairX = Integer.signum(stairX) * (Math.abs(stairX) - 1);
			
			setBlockAndMetadata(world, stairX, 6, -stairZ, LOTRMod.stairsMallorn, 2);
			setBlockAndMetadata(world, stairX, 6, stairZ, LOTRMod.stairsMallorn, 3);
		}
		
		for (int k1 = -8; k1 <= 8; k1++)
		{
			int stairX = 0;
			int stairZ = k1;
			
			int k2 = Math.abs(k1);
			if (k2 <= 3)
			{
				stairX = 8;
			}
			else if (k2 <= 5)
			{
				stairX = 7;
			}
			else if (k2 <= 7)
			{
				stairX = 6;
			}
			else
			{
				stairX = 4;
			}
			
			setBlockAndMetadata(world, -stairX, 5, stairZ, LOTRMod.stairsMallorn, 1);
			setBlockAndMetadata(world, stairX, 5, stairZ, LOTRMod.stairsMallorn, 0);
			
			stairX--;
			stairZ = Integer.signum(stairZ) * (Math.abs(stairZ) - 1);
			
			setBlockAndMetadata(world, -stairX, 6, stairZ, LOTRMod.stairsMallorn, 1);
			setBlockAndMetadata(world, stairX, 6, stairZ, LOTRMod.stairsMallorn, 0);
		}
		
		for (int i1 = -3; i1 <= 3; i1++)
		{
			setBlockAndMetadata(world, i1, 4, -3, LOTRMod.stairsMallorn, 6);
			setBlockAndMetadata(world, i1, 4, 3, LOTRMod.stairsMallorn, 7);
		}
		
		for (int k1 = -2; k1 <= 2; k1++)
		{
			setBlockAndMetadata(world, -3, 4, k1, LOTRMod.stairsMallorn, 5);
			setBlockAndMetadata(world, 3, 4, k1, LOTRMod.stairsMallorn, 4);
		}
		
		for (int bough = 0; bough <= 2; bough++)
		{
			int j1 = -3 + bough;
			int i1 = 0 + bough;
			int k1 = 3 + bough;
			
			for (int i2 = -i1; i2 <= i1; i2++)
			{
				for (int k2 = -k1; k2 <= k1; k2++)
				{
					setBlockAndMetadata(world, i2, j1, k2, LOTRMod.wood, 13);
					setBlockAndMetadata(world, k2, j1, i2, LOTRMod.wood, 13);
				}
			}
		}

		for (int j1 = 3; j1 >= -3 || (!isOpaque(world, 0, j1, -3) && getY(j1) >= 0); j1--)
		{
			setBlockAndMetadata(world, 0, j1, -3, LOTRMod.mallornLadder, 2);
		}
		
		setBlockAndMetadata(world, -2, 1, 0, LOTRMod.elvenTable, 0);
		setBlockAndMetadata(world, -2, 2, 0, Blocks.air, 0);
		setBlockAndMetadata(world, -2, 3, 0, Blocks.air, 0);
		setBlockAndMetadata(world, -2, 4, 0, LOTRMod.wood, 5);
		
		setBlockAndMetadata(world, 2, 1, 0, LOTRMod.elvenTable, 0);
		setBlockAndMetadata(world, 2, 2, 0, Blocks.air, 0);
		setBlockAndMetadata(world, 2, 3, 0, Blocks.air, 0);
		setBlockAndMetadata(world, 2, 4, 0, LOTRMod.wood, 5);
		
		placeChest(world, random, 0, 1, 2, 0, LOTRChestContents.ELF_HOUSE);
		setBlockAndMetadata(world, 0, 2, 2, Blocks.air, 0);
		setBlockAndMetadata(world, 0, 3, 2, Blocks.air, 0);
		setBlockAndMetadata(world, 0, 4, 2, LOTRMod.wood, 9);
		
		tryPlaceLight(world, -7, -1, -3, random);
		tryPlaceLight(world, -7, -1, 3, random);
		tryPlaceLight(world, 7, -1, -3, random);
		tryPlaceLight(world, 7, -1, 3, random);
		tryPlaceLight(world, -3, -1, -7, random);
		tryPlaceLight(world, 3, -1, -7, random);
		tryPlaceLight(world, -3, -1, 7, random);
		tryPlaceLight(world, 3, -1, 7, random);
		
		placeFlowerPot(world, -4, 1, -5, getRandomPlant(random));
		placeFlowerPot(world, -5, 1, -4, getRandomPlant(random));
		placeFlowerPot(world, -5, 1, 4, getRandomPlant(random));
		placeFlowerPot(world, -4, 1, 5, getRandomPlant(random));
		placeFlowerPot(world, 4, 1, -5, getRandomPlant(random));
		placeFlowerPot(world, 5, 1, -4, getRandomPlant(random));
		placeFlowerPot(world, 5, 1, 4, getRandomPlant(random));
		placeFlowerPot(world, 4, 1, 5, getRandomPlant(random));
		
		setBlockAndMetadata(world, -2, 1, 5, LOTRMod.elvenBed, 3);
		setBlockAndMetadata(world, -3, 1, 5, LOTRMod.elvenBed, 11);

		LOTREntityElf elf = new LOTREntityGaladhrimElf(world);
		elf.spawnRidingHorse = false;
		spawnNPCAndSetHome(elf, world, 0, 1, 4, 8);
		
		return true;
	}
	
	private void tryPlaceLight(World world, int i, int j, int k, Random random)
	{
		int height = 2 + random.nextInt(6);
		for (int j1 = j; j1 >= -height; j1--)
		{
			if (restrictions)
			{
				if (!isAir(world, i, j1, k))
				{
					return;
				}
				if (j1 == -height)
				{
					if (!isAir(world, i, j1, k - 1) || !isAir(world, i, j1, k + 1) || !isAir(world, i - 1, j1, k) || !isAir(world, i + 1, j1, k))
					{
						return;
					}
				}
			}
		}
		
		for (int j1 = j; j1 >= j - height; j1--)
		{
			if (j1 == j - height)
			{
				setBlockAndMetadata(world, i, j1, k, LOTRMod.planks, 1);
				setBlockAndMetadata(world, i, j1, k - 1, LOTRMod.mallornTorch, 4);
				setBlockAndMetadata(world, i, j1, k + 1, LOTRMod.mallornTorch, 3);
				setBlockAndMetadata(world, i - 1, j1, k, LOTRMod.mallornTorch, 1);
				setBlockAndMetadata(world, i + 1, j1, k, LOTRMod.mallornTorch, 2);
			}
			else
			{
				setBlockAndMetadata(world, i, j1, k, LOTRMod.fence, 1);
			}
		}
	}
	
	private ItemStack getRandomPlant(Random random)
	{
		return random.nextBoolean() ? new ItemStack(LOTRMod.elanor) : new ItemStack(LOTRMod.niphredil);
	}
}
