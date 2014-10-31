package lotr.common.dispenser;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class LOTRDispenseSpawnEgg extends BehaviorDefaultDispenseItem
{
    @Override
    public ItemStack dispenseStack(IBlockSource dispenser, ItemStack itemstack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
        double d = dispenser.getX() + (double)enumfacing.getFrontOffsetX();
        double d1 = (double)((float)dispenser.getYInt() + 0.2F);
        double d2 = dispenser.getZ() + (double)enumfacing.getFrontOffsetZ();
        Entity entity = LOTRItemSpawnEgg.spawnCreature(dispenser.getWorld(), itemstack.getItemDamage(), d, d1, d2);

        if (entity instanceof EntityLiving && itemstack.hasDisplayName())
        {
            ((EntityLiving)entity).setCustomNameTag(itemstack.getDisplayName());
        }
		
		if (entity instanceof LOTREntityNPC)
		{
			((LOTREntityNPC)entity).isNPCPersistent = true;
		}

        itemstack.splitStack(1);
        return itemstack;
    }
}