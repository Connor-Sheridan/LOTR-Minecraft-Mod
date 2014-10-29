package lotr.common.entity.npc;

import lotr.common.entity.npc.LOTREntityWarg.WargType;
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
		setWargType(WargType.OBSIDIAN);
	}
}
