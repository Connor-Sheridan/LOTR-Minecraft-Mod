package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockUtumnoReturnPortalBase extends Block
{
	public static int MAX_SACRIFICE = 15;
	
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	
	public LOTRBlockUtumnoReturnPortalBase()
    {
        super(Material.rock);
        setHardness(-1F);
        setResistance(Float.MAX_VALUE);
        setStepSound(Block.soundTypeStone);
    }
		
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j)
	{
		if (i == 1)
		{
			return topIcon;
		}
		return super.getIcon(i, j);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister)
    {
        super.registerBlockIcons(iconregister);
        topIcon = iconregister.registerIcon(getTextureName() + "_top");
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		
		float f = (float)meta / (float)MAX_SACRIFICE;
		float f1 = 1F / 16F;
		float f2 = f1 + (1F - f1) * f;
		setBlockBounds(0F, 0F, 0F, 1F, f2, 1F);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k)
    {
		int meta = world.getBlockMetadata(i, j, k);
		
		float f = (float)meta / (float)MAX_SACRIFICE;
		float f1 = 0.5F;
		float f2 = f1 + (1F - f1) * f;
		f2 *= 16F;
		return (int)f2;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}
