package lotr.client.gui.config;

import lotr.common.LOTRConfig;
import lotr.common.LOTRMod;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;

public class LOTRGuiConfig extends GuiConfig
{
	public LOTRGuiConfig(GuiScreen parent)
	{
	    super(parent, LOTRConfig.getConfigElements(), LOTRMod.getModID(), false, false, GuiConfig.getAbridgedConfigPath(LOTRConfig.config.toString()));
	}
}
