package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class LOTRBlockMordorDirt extends Block
{
	public LOTRBlockMordorDirt()
    {
        super(Material.ground);
        setHardness(0.5F);
        setStepSound(Block.soundTypeGravel);
        setCreativeTab(LOTRCreativeTabs.tabBlock);
    }
}
