package lotr.common.block;

import java.util.List;
import java.util.Random;

import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityUtumnoReturnPortal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBlockUtumnoReturnPortal extends BlockContainer
{
	public LOTRBlockUtumnoReturnPortal()
    {
        super(Material.portal);
        setHardness(-1F);
        setResistance(Float.MAX_VALUE);
        setStepSound(Block.soundTypeStone);
        setLightLevel(1F);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new LOTRTileEntityUtumnoReturnPortal();
	}
	
    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity) {}

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
	
    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
		if (world.provider.dimensionId != LOTRDimension.UTUMNO.dimensionID)
		{
			world.setBlockToAir(i, j, k);
		}
    }
    
    @Override
    public void breakBlock(World world, int i, int j, int k, Block block, int meta)
    {
        super.breakBlock(world, i, j, k, block, meta);
        
        if (!world.isRemote)
        {
        	for (int j1 = j; j1 <= world.getHeight(); j1++)
        	{
        		if (world.getBlock(i, j1, k) == LOTRMod.utumnoReturnLight)
        		{
        			world.setBlockToAir(i, j1, k);
        		}
        	}
        }
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int i, int j, int k)
    {
        return Item.getItemById(0);
    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int i, int j)
    {
        return Blocks.portal.getIcon(i, j);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {}
}
