package lotr.common.entity.npc;

import net.minecraft.world.World;

public class LOTREntityUtumnoObsidianWarg extends LOTREntityUtumnoWarg
{
	public LOTREntityUtumnoObsidianWarg(World world)
	{
		super(world);
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		
		if (rand.nextInt(3) > 0)
		{
			setWargType(WargType.BLACK);
		}
	}
}
