package lotr.common.block;

import java.util.Arrays;

import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBlockOreStorage extends LOTRBlockOreStorageBase
{
	@SideOnly(Side.CLIENT)
	private IIcon orcSteelSideIcon;
	@SideOnly(Side.CLIENT)
	private IIcon morgulSteelSideIcon;
	@SideOnly(Side.CLIENT)
	private IIcon[] mithrilCTMIcons;

	public LOTRBlockOreStorage()
	{
		super();
		setOreStorageNames("copper", "tin", "bronze", "silver", "mithril", "orcSteel", "quendite", "dwarfSteel", "galvorn", "urukSteel", "naurite", "gulduril", "morgulSteel", "sulfur", "saltpeter", "blueDwarfSteel");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side)
	{
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 4)
		{
			int[] mithril = surroundingMithril(world, i, j, k, side);
			if (Arrays.equals(mithril, new int[]{0, 0, 0, 0}))
			{
				return getIcon(side, meta);
			}
			if (Arrays.equals(mithril, new int[]{0, 1, 1, 0}))
			{
				return mithrilCTMIcons[0];
			}
			if (Arrays.equals(mithril, new int[]{0, 1, 1, 1}))
			{
				return mithrilCTMIcons[1];
			}
			if (Arrays.equals(mithril, new int[]{0, 0, 1, 1}))
			{
				return mithrilCTMIcons[2];
			}
			if (Arrays.equals(mithril, new int[]{0, 0, 1, 0}))
			{
				return mithrilCTMIcons[3];
			}
			if (Arrays.equals(mithril, new int[]{1, 1, 1, 0}))
			{
				return mithrilCTMIcons[4];
			}
			if (Arrays.equals(mithril, new int[]{1, 1, 1, 1}))
			{
				return mithrilCTMIcons[5];
			}
			if (Arrays.equals(mithril, new int[]{1, 0, 1, 1}))
			{
				return mithrilCTMIcons[6];
			}
			if (Arrays.equals(mithril, new int[]{1, 0, 1, 0}))
			{
				return mithrilCTMIcons[7];
			}
			if (Arrays.equals(mithril, new int[]{1, 1, 0, 0}))
			{
				return mithrilCTMIcons[8];
			}
			if (Arrays.equals(mithril, new int[]{1, 1, 0, 1}))
			{
				return mithrilCTMIcons[9];
			}
			if (Arrays.equals(mithril, new int[]{1, 0, 0, 1}))
			{
				return mithrilCTMIcons[10];
			}
			if (Arrays.equals(mithril, new int[]{1, 0, 0, 0}))
			{
				return mithrilCTMIcons[11];
			}
			if (Arrays.equals(mithril, new int[]{0, 1, 0, 0}))
			{
				return mithrilCTMIcons[12];
			}
			if (Arrays.equals(mithril, new int[]{0, 1, 0, 1}))
			{
				return mithrilCTMIcons[13];
			}
			if (Arrays.equals(mithril, new int[]{0, 0, 0, 1}))
			{
				return mithrilCTMIcons[14];
			}
		}
		return super.getIcon(world, i, j, k, side);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j)
	{
		if (j == 5 && i > 1)
		{
			return orcSteelSideIcon;
		}
		
		if (j == 12 && i > 1)
		{
			return morgulSteelSideIcon;
		}
		
		return super.getIcon(i, j);
	}
	
	@Override
    public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side)
    {
		return world.getBlockMetadata(i, j, k) == 13 && side == ForgeDirection.UP;
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k)
	{
		if (world.getBlockMetadata(i, j, k) == 11)
		{
			return LOTRMod.oreGulduril.getLightValue();
		}
		return 0;
	}
	
	@Override
    public boolean hasTileEntity(int metadata)
    {
        return metadata == 11;
    }

	@Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        if (hasTileEntity(metadata))
        {
            return new LOTRTileEntityGulduril();
        }
        return null;
    }

	private int[] surroundingMithril(IBlockAccess world, int i, int j, int k, int side)
	{
		int[] mithril = new int[4];
		if (side == 0)
		{
			mithril[0] = isMithril(world, i, j, k - 1);
			mithril[1] = isMithril(world, i + 1, j, k);
			mithril[2] = isMithril(world, i, j, k + 1);
			mithril[3] = isMithril(world, i - 1, j, k);
		}
		if (side == 1)
		{
			mithril[0] = isMithril(world, i, j, k - 1);
			mithril[1] = isMithril(world, i + 1, j, k);
			mithril[2] = isMithril(world, i, j, k + 1);
			mithril[3] = isMithril(world, i - 1, j, k);
		}
		if (side == 2)
		{
			mithril[0] = isMithril(world, i, j + 1, k);
			mithril[1] = isMithril(world, i - 1, j, k);
			mithril[2] = isMithril(world, i, j - 1, k);
			mithril[3] = isMithril(world, i + 1, j, k);
		}
		if (side == 3)
		{
			mithril[0] = isMithril(world, i, j + 1, k);
			mithril[1] = isMithril(world, i + 1, j, k);
			mithril[2] = isMithril(world, i, j - 1, k);
			mithril[3] = isMithril(world, i - 1, j, k);
		}
		if (side == 4)
		{
			mithril[0] = isMithril(world, i, j + 1, k);
			mithril[1] = isMithril(world, i, j, k + 1);
			mithril[2] = isMithril(world, i, j - 1, k);
			mithril[3] = isMithril(world, i, j, k - 1);
		}
		if (side == 5)
		{
			mithril[0] = isMithril(world, i, j + 1, k);
			mithril[1] = isMithril(world, i, j, k - 1);
			mithril[2] = isMithril(world, i, j - 1, k);
			mithril[3] = isMithril(world, i, j, k + 1);
		}
		return mithril;
	}
	
	private int isMithril(IBlockAccess world, int i, int j, int k)
	{
		return world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == 4 ? 1 : 0;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister)
    {
        super.registerBlockIcons(iconregister);
		
		orcSteelSideIcon = iconregister.registerIcon(getTextureName() + "_orcSteel_side");
		morgulSteelSideIcon = iconregister.registerIcon(getTextureName() + "_morgulSteel_side");
		
		mithrilCTMIcons = new IIcon[15];
        for (int i = 0; i < 15; i++)
        {
            mithrilCTMIcons[i] = iconregister.registerIcon(getTextureName() + "_mithril_ctm_" + i);
        }
    }
}
