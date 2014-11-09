package lotr.common.world;

import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import cpw.mods.fml.common.FMLLog;

public class LOTRDerivedWorldInfo extends DerivedWorldInfo
{
	private WorldInfo parentWorldInfo;
	
	public LOTRDerivedWorldInfo(WorldInfo worldinfo)
	{
		super(worldinfo);
		parentWorldInfo = worldinfo;
	}
	
	@Override
	public void setWorldTime(long time)
	{
		if (time - getWorldTime() < 20)
		{
			return;
		}
		parentWorldInfo.setWorldTime(time);
		FMLLog.info("Set LOTR world time to " + time);
	}
}
