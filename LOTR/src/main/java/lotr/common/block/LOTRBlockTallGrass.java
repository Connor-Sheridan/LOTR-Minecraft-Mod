package lotr.common.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBlockTallGrass extends LOTRBlockGrass
{
	@SideOnly(Side.CLIENT)
	private IIcon[] grassIcons;
	@SideOnly(Side.CLIENT)
	private IIcon[] overlayIcons;
	
	public static String[] grassNames = {"short", "flower", "wheat", "thistle"};
	public static boolean[] grassOverlay = {false, true, true, true};
	
    public LOTRBlockTallGrass()
    {
        super();
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
    	int meta = world.getBlockMetadata(i, j, k);
    	if (meta == 3 && entity.isSprinting() && world.rand.nextInt(3) == 0)
    	{
    		entity.attackEntityFrom(DamageSource.cactus, 1F);
    	}
    }
    
    @Override
    public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune)
    {
       	if (meta == 3)
       	{
       		ArrayList thistles = new ArrayList();
       		thistles.add(new ItemStack(this, 1, 3));
       		return thistles;
       	}
       	return super.getDrops(world, i, j, k, meta, fortune);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return Blocks.tallgrass.getBlockColor();
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta)
	{
    	return getBlockColor();
	}

    @Override
    @SideOnly(Side.CLIENT)
   	public int colorMultiplier(IBlockAccess world, int i, int j, int k)
    {
    	return world.getBiomeGenForCoords(i, k).getBiomeGrassColor(i, j, k);
   	}
    
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int i, int j)
    {
		if (j >= grassNames.length)
		{
			j = 0;
		}
		
		if (i == -1)
		{
			return overlayIcons[j];
		}
		return grassIcons[j];
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister)
    {
		grassIcons = new IIcon[grassNames.length];
		overlayIcons = new IIcon[grassNames.length];
		
        for (int i = 0; i < grassNames.length; i++)
        {
        	grassIcons[i] = iconregister.registerIcon(getTextureName() + "_" + grassNames[i]);

        	if (grassOverlay[i])
        	{
        		overlayIcons[i] = iconregister.registerIcon(getTextureName() + "_" + grassNames[i] + "_overlay");
        	}
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
		for (int j = 0; j < grassNames.length; j++)
		{
			list.add(new ItemStack(item, 1, j));
		}
    }
}
