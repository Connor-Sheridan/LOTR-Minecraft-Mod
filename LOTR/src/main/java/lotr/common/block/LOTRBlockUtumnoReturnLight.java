package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBlockUtumnoReturnLight extends Block
{
	public LOTRBlockUtumnoReturnLight()
	{
		super(Material.circuits);
		setLightLevel(1F);
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int i, int j, int k)
    {
        return true;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {}
	
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }
	
	@Override
    public boolean isCollidable()
    {
        return false;
    }

	@Override
	public int getRenderType()
	{
		return -1;
	}
}
