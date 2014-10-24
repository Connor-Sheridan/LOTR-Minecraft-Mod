package lotr.common.item;

import java.util.List;

import lotr.common.*;
import lotr.common.world.LOTRChunkProviderUtumno.UtumnoLevel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRItemUtumnoKey extends Item
{
	@SideOnly(Side.CLIENT)
	private IIcon[] keyIcons;
	private String[] keyTypes = {"ice", "obsidian"};
	
	public LOTRItemUtumnoKey()
	{
		super();
		setCreativeTab(LOTRCreativeTabs.tabMisc);
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2)
	{
		if (LOTRDimension.getCurrentDimension(world) == LOTRDimension.UTUMNO)
		{
			if (side == 1)
			{
				Block block = world.getBlock(i, j, k);
				int meta = world.getBlockMetadata(i, j, k);
				UtumnoLevel utumnoLevel = UtumnoLevel.forY(j);
				
				UtumnoLevel keyUsageLevel = null;
				if (itemstack.getItemDamage() == 0)
				{
					keyUsageLevel = UtumnoLevel.ICE;
				}
				else if (itemstack.getItemDamage() == 1)
				{
					keyUsageLevel = UtumnoLevel.OBSIDIAN;
				}

				if (utumnoLevel == keyUsageLevel)
				{
					if (j < utumnoLevel.corridorBaseLevels[0] && block == LOTRMod.utumnoBrick && (meta == utumnoLevel.brickMeta || meta == utumnoLevel.brickMetaGlow))
					{
						if (!world.isRemote)
						{
							world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.explode", 2F, 0.2F + world.rand.nextFloat() * 0.2F);
						}
						
						for (int l = 0; l < 60; l++)
						{
							world.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + meta, i + 0.5D + world.rand.nextGaussian() * 1D, j + 1.5D, k + 0.5D + world.rand.nextGaussian() * 1D, 0D, 0D, 0D);
						}
						
						if (!world.isRemote)
						{
							UtumnoLevel targetLevel = UtumnoLevel.values()[keyUsageLevel.ordinal() + 1];
							int stair = 0;
							int stairX = i;
							int stairZ = k;
							int stairY = j - 1;
							while (true)
							{
								if (stairY < targetLevel.corridorBaseLevels[0])
								{
									break;
								}
								if (UtumnoLevel.forY(stairY) == targetLevel && world.isAirBlock(stairX, stairY + 1, stairZ) && World.doesBlockHaveSolidTopSurface(world, stairX, stairY, stairZ))
								{
									break;
								}
								
								world.setBlock(stairX, stairY, stairZ, LOTRMod.utumnoBrick, keyUsageLevel.brickMetaGlow, 3);
								world.setBlock(stairX, stairY + 1, stairZ, Blocks.air, 0, 3);
								world.setBlock(stairX, stairY + 2, stairZ, Blocks.air, 0, 3);
								world.setBlock(stairX, stairY + 3, stairZ, Blocks.air, 0, 3);
								
								stair++;
								stair = stair % 4;
								if (stair == 1)
								{
									stairX++;
								}
								if (stair == 2)
								{
									stairZ++;
								}
								if (stair == 3)
								{
									stairX--;
								}
								if (stair == 0)
								{
									stairZ--;
								}
								stairY--;
							}
						}
						
						itemstack.stackSize--;
						return true;
					}
				}

				for (int l = 0; l < 8; l++)
				{
					double d = i + (double)world.rand.nextFloat();
					double d1 = j + 1D;
					double d2 = k + (double)world.rand.nextFloat();
					world.spawnParticle("smoke", d, d1, d2, 0D, 0D, 0D);
				}
				return false;
			}
		}
		return false;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        if (i >= keyIcons.length)
		{
			i = 0;
		}
		return keyIcons[i];
    }
	
	@Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconregister)
    {
		keyIcons = new IIcon[keyTypes.length];
        for (int i = 0; i < keyTypes.length; i++)
        {
        	keyIcons[i] = iconregister.registerIcon(getIconString() + "_" + keyTypes[i]);
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int j = 0; j < keyTypes.length; j++)
        {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
