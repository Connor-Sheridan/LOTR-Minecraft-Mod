package lotr.common.entity.npc;

import lotr.common.LOTRAlignmentValues;
import lotr.common.LOTRFaction;
import lotr.common.entity.npc.LOTREntityWarg.WargType;
import net.minecraft.world.World;

public class LOTREntityMordorWargBombardier extends LOTREntityWargBombardier
{
	public LOTREntityMordorWargBombardier(World world)
	{
		super(world);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		setWargType(WargType.BLACK);
	}

	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.MORDOR;
	}
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.MORDOR_WARG;
	}
}
