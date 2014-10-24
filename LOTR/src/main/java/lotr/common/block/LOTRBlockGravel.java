package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;

public class LOTRBlockGravel extends BlockGravel
{
	public LOTRBlockGravel()
    {
        super();
        setCreativeTab(LOTRCreativeTabs.tabBlock);
        setHardness(0.6F);
        setStepSound(Block.soundTypeGravel);
    }
}