package lotr.common.block;

import lotr.common.tileentity.LOTRTileEntityOrcForge;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockOrcForge extends LOTRBlockAlloyForge
{
	public LOTRBlockOrcForge()
	{
		super();
	}
	
	@Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new LOTRTileEntityOrcForge();
    }
}
