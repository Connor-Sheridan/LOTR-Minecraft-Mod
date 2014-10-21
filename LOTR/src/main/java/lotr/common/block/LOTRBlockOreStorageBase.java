package lotr.common.block;

import java.util.List;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class LOTRBlockOreStorageBase extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon[] oreStorageIcons;
	private String[] oreStorageNames;

	public LOTRBlockOreStorageBase()
	{
		super(Material.iron);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(5F);
		setResistance(10F);
		setStepSound(Block.soundTypeMetal);
	}
	
	protected void setOreStorageNames(String... names)
	{
		oreStorageNames = names;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j)
	{
		if (j >= oreStorageNames.length)
		{
			j = 0;
		}
		
		return oreStorageIcons[j];
	}
	
	@Override
	public int damageDropped(int i)
	{
		return i;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
		for (int j = 0; j < oreStorageNames.length; j++)
		{
			list.add(new ItemStack(item, 1, j));
		}
    }
	
	@Override
    public boolean isBeaconBase(IBlockAccess world, int i, int j, int k, int beaconX, int beaconY, int beaconZ)
    {
		return true;
	}
	
	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister)
    {
        oreStorageIcons = new IIcon[oreStorageNames.length];
        for (int i = 0; i < oreStorageNames.length; i++)
        {
            oreStorageIcons[i] = iconregister.registerIcon(getTextureName() + "_" + oreStorageNames[i]);
        }
    }
}
