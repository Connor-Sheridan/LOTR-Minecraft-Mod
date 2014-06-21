package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiButtonOptions extends GuiButton
{
	public String baseDisplayString;

    public LOTRGuiButtonOptions(int i, int j, int k, int l, int i1, String s)
    {
        super(i, j, k, l, i1, s);
		baseDisplayString = s;
    }
    
    private String getDescription()
    {
    	return StatCollector.translateToLocal(baseDisplayString + ".desc.on") + "\n\n" + StatCollector.translateToLocal(baseDisplayString + ".desc.off");
    }
	
    public void setState(String s)
	{
		displayString = StatCollector.translateToLocal(baseDisplayString) + ": " + s;
	}
    
    public void setState(boolean flag)
	{
		setState(flag ? StatCollector.translateToLocal("lotr.gui.button.on") : StatCollector.translateToLocal("lotr.gui.button.off"));
	}
	
	public void drawTooltip(Minecraft mc, int i, int j)
    {
		if (enabled && func_146115_a())
		{
			String s = getDescription();
			int border = 3;
			int stringWidth = 200;
			int stringHeight = mc.fontRenderer.listFormattedStringToWidth(s, stringWidth).size() * mc.fontRenderer.FONT_HEIGHT;
			int offset = 10;
			i += offset;
			j += offset;
			drawRect(i, j, i + stringWidth + border * 2, j + stringHeight + border * 2, 0xC0000000);
			mc.fontRenderer.drawSplitString(s, i + border, j + border, stringWidth, 0xFFFFFF);
		}
	}
}
