package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUtumnoFireWarg extends LOTREntityUtumnoWarg
{
	public LOTREntityUtumnoFireWarg(World world)
	{
		super(world);
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
        if (rand.nextInt(3) > 0)
		{
			setWargType(WargType.RED);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
		}
    }
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (getWargType() == WargType.RED)
		{
			String s = rand.nextInt(3) > 0 ? "flame" : "smoke";
			worldObj.spawnParticle(s, posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0D, 0D, 0D);
		}
	}
	
	@Override
    public boolean attackEntityAsMob(Entity entity)
    {
        boolean flag = super.attackEntityAsMob(entity);
        if (!worldObj.isRemote && flag)
        {
        	entity.setFire(4);
        }
        return flag;
    }
}
