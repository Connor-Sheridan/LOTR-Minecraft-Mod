package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import lotr.common.entity.npc.LOTREntityRangerIthilienCaptain;
import lotr.common.world.biome.LOTRBiomeGenIthilien;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenIthilienHideout extends LOTRWorldGenStructureBase2
{
	public LOTRWorldGenIthilienHideout(boolean flag)
	{
		super(flag);
	}
	
	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation)
	{
		if (restrictions)
		{
			if (world.getBlock(i, j - 1, k) != Blocks.grass || !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenIthilien))
			{
				return false;
			}
		}
		
		j--;
		
		setOrigin(i, j, k);
		setRotationMode(rotation);

		int width = 5;
		int height = 4;
		int baseY = -(height + 2 + random.nextInt(4));
		
		if (restrictions)
		{
			for (int i1 = -width; i1 <= width; i1++)
			{
				for (int k1 = -width; k1 <= width; k1++)
				{
					for (int j1 = baseY; j1 <= baseY + height + 2; j1++)
					{
						if (!isOpaque(world, i1, j1, k1))
						{
							return false;
						}
					}
				}
			}
		}

		for (int i1 = -width - 1; i1 <= width + 1; i1++)
		{
			for (int k1 = -width - 1; k1 <= width + 1; k1++)
			{
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				boolean withinWalls = i2 <= width && k2 <= width;
				
				setBlockAndMetadata(world, i1, baseY, k1, Blocks.stone, 0);
				setBlockAndMetadata(world, i1, baseY + height + 1, k1, Blocks.stone, 0);
				
				for (int j1 = baseY + 1; j1 <= baseY + height; j1++)
				{
					if (withinWalls)
					{
						setAir(world, i1, j1, k1);
					}
					else
					{
						setBlockAndMetadata(world, i1, j1, k1, Blocks.stone, 0);
					}
				}
				
				if (withinWalls)
				{
					if ((i2 <= 2 && k2 <= 2) || random.nextInt(3) == 0)
					{
						setBlockAndMetadata(world, i1, baseY + 1, k1, LOTRMod.thatchFloor, 0);
					}
					
					if (i2 == width || k2 == width)
					{
						setBlockAndMetadata(world, i1, baseY + 1, k1, LOTRMod.planks, 8);
					}
				}
			}
		}
		
		int ladderY = baseY + 1;
		while (true)
		{
			if (ladderY > baseY + height && !isOpaque(world, 0, ladderY, 0))
			{
				if (!isOpaque(world, -1, ladderY, 0) || !isOpaque(world, 1, ladderY, 0) || !isOpaque(world, 0, ladderY, -1) || !isOpaque(world, 0, ladderY, 1))
				{
					break;
				}
			}
			
			if (!isOpaque(world, 0, ladderY, -1))
			{
				setBlockAndMetadata(world, 0, ladderY, -1, Blocks.stone, 0);
			}
			setBlockAndMetadata(world, 0, ladderY, 0, Blocks.ladder, 3);
			
			ladderY++;
		}
		
		for (int pass = 0; pass <= 1; pass++)
		{
			for (int i1 = -1; i1 <= 1; i1++)
			{
				for (int k1 = -1; k1 <= 1; k1++)
				{
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					
					if (i1 == 0 && k1 == 0)
					{
						continue;
					}
					
					if (pass == 0)
					{
						if (i1 == 0 && k1 == 1)
						{
							treeAttempt:
							for (int j1 = 0; j1 <= 3; j1++)
							{
								int j2 = ladderY + j1;
								if (LOTRTreeType.OAK_ITHILIEN_HIDEOUT.create(notifyChanges).generate(world, random, getX(i1, k1), getY(j2), getZ(i1, k1)))
								{
									break treeAttempt;
								}
							}
						}
					}
					
					if (pass == 1)
					{
						boolean doublegrass = i2 != k2;
						
						grassAttempt:
						for (int j1 = -3; j1 <= 3; j1++)
						{
							int j2 = ladderY + j1;
							Block below = getBlock(world, i1, j2 - 1, k1);
							if ((below == Blocks.grass || below == Blocks.dirt) && !isOpaque(world, i1, j2, k1) && !isOpaque(world, i1, j2 + 1, k1))
							{
								if (doublegrass)
								{
									setBlockAndMetadata(world, i1, j2, k1, Blocks.double_plant, 2);
									setBlockAndMetadata(world, i1, j2 + 1, k1, Blocks.double_plant, 8);
								}
								else
								{
									setBlockAndMetadata(world, i1, j2, k1, Blocks.tallgrass, 1);
								}
								break grassAttempt;
							}
						}
					}
				}
			}
		}
		
		setBlockAndMetadata(world, -width, baseY + 3, -width, Blocks.torch, 2);
		setBlockAndMetadata(world, -width, baseY + 3, width, Blocks.torch, 2);
		setBlockAndMetadata(world, width, baseY + 3, -width, Blocks.torch, 1);
		setBlockAndMetadata(world, width, baseY + 3, width, Blocks.torch, 1);
		
		placeWallBanner(world, -width - 1, baseY + 4, 0, LOTRFaction.GONDOR, 1);
		placeWallBanner(world, 0, baseY + 4, width + 1, LOTRFaction.GONDOR, 2);
		placeWallBanner(world, width + 1, baseY + 4, 0, LOTRFaction.GONDOR, 3);
		placeWallBanner(world, 0, baseY + 4, -width - 1, LOTRFaction.GONDOR, 0);
		
		setBlockAndMetadata(world, -2, baseY + 1, width, LOTRMod.gondorianTable, 0);
		setBlockAndMetadata(world, 0, baseY + 1, width, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 2, baseY + 1, width, Blocks.furnace, 2);
		
		placeChest(world, random, width, baseY + 1, 0, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
		
		Item drink = LOTRFoods.GONDOR_DRINK.getRandomFood(random).getItem();
		placeBarrel(world, random, width, baseY + 2, -3, 5, drink);
		placeBarrel(world, random, width, baseY + 2, -2, 5, drink);
		placeBarrel(world, random, width, baseY + 2, 2, 5, drink);
		placeBarrel(world, random, width, baseY + 2, 3, 5, drink);
		
		for (int i1 = -3; i1 <= 3; i1 += 2)
		{
			setBlockAndMetadata(world, i1, baseY + 1, -width + 1, LOTRMod.strawBed, 2);
			setBlockAndMetadata(world, i1, baseY + 1, -width, LOTRMod.strawBed, 10);
		}
		
		placeChest(world, random, -width, baseY + 1, 0, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
		
		ItemStack[] rangerArmor = new ItemStack[] {new ItemStack(LOTRMod.helmetRanger), new ItemStack(LOTRMod.bodyRanger), new ItemStack(LOTRMod.legsRanger), new ItemStack(LOTRMod.bootsRanger)};
		placeArmorStand(world, -width, baseY + 2, -2, 3, rangerArmor);
		placeArmorStand(world, -width, baseY + 2, 2, 3, rangerArmor);
		
		int rangers = 2 + random.nextInt(3);
		for (int l = 0; l < rangers; l++)
		{
			LOTREntityRangerIthilien ranger = new LOTREntityRangerIthilien(world);
			spawnNPCAndSetHome(ranger, world, -2, baseY + 1, -2, 16);
		}
		
		spawnNPCAndSetHome(new LOTREntityRangerIthilienCaptain(world), world, -2, baseY + 1, -2, 16);

		return true;
	}
}
