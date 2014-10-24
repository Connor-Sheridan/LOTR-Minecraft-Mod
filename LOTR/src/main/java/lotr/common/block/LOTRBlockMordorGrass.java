package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class LOTRBlockMordorGrass extends BlockBush implements IShearable
{
    public LOTRBlockMordorGrass()
    {
        super(Material.vine);
        setBlockBounds(0.1F, 0F, 0.1F, 0.9F, 0.8F, 0.9F);
        setCreativeTab(LOTRCreativeTabs.tabDeco);
		setHardness(0F);
		setStepSound(Block.soundTypeGrass);
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k)
    {
        if (j >= 0 && j < 256)
        {
            return LOTRBiomeGenMordor.canPlantGrow(world, i, j - 1, k);
        }
        else
        {
            return false;
        }
    }
	
	@Override
    public boolean isReplaceable(IBlockAccess world, int i, int j, int k)
    {
	    return true;
    }
	
	@Override
    public int getRenderType()
    {
		return LOTRMod.proxy.getGrassRenderID();
    }
	
	@Override
    public Item getItemDropped(int i, Random random, int j)
	{
		return null;
	}

	@Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) 
    {
        return true;
	}

	@Override
    public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune)
    {
		ArrayList list = new ArrayList();
		list.add(new ItemStack(this, 1, world.getBlockMetadata(i, j, k)));
		return list;
    }
}
