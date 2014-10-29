package lotr.common.entity.npc;

import lotr.common.world.biome.LOTRBiomeGenForodwaith;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceWarg extends LOTREntityUtumnoWarg
{
	public LOTREntityUtumnoIceWarg(World world)
	{
		super(world);
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		setWargType(WargType.ICE);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
    {
        if (super.attackEntityAsMob(entity))
        {
        	entity.attackEntityFrom(LOTRBiomeGenForodwaith.frost, 0F);
        	
        	if (entity instanceof EntityLivingBase)
            {
        		int difficulty = worldObj.difficultySetting.getDifficultyId();
                int duration = difficulty * (difficulty + 5) / 2;

                if (duration > 0)
                {
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
                }
            }
        	
        	return true;
        }
        return false;
    }
}
