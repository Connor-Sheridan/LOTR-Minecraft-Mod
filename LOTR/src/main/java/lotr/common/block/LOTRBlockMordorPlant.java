package lotr.common.block;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.world.World;

public class LOTRBlockMordorPlant extends LOTRBlockFlower
{
    public LOTRBlockMordorPlant()
    {
        super();
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
}
