package lotr.common.entity.npc;

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
		
		if (rand.nextInt(3) > 0)
		{
			setWargType(WargType.WHITE);
		}
	}
}
