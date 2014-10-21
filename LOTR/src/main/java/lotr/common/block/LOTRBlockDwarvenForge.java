package lotr.common.block;

import lotr.common.tileentity.LOTRTileEntityDwarvenForge;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockDwarvenForge extends LOTRBlockAlloyForge
{
	public LOTRBlockDwarvenForge()
	{
		super();
	}
	
	@Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new LOTRTileEntityDwarvenForge();
    }
}
