package lotr.client.gui;

import java.util.List;

import lotr.client.LOTRClientProxy;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.*;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiAlignment extends LOTRGuiMenu
{
	private static LOTRDimension currentDimension;
	private static LOTRDimension prevDimension;
	
	private static int currentFactionIndex = 0;
	private LOTRFaction currentFaction;
	
	private static final int maxAlignmentsDisplayed = 5;
	
	@Override
    public void initGui()
    {
		xSize = 220;
		super.initGui();
    }
	
	@Override
    public void updateScreen()
    {
        super.updateScreen();
        
        currentDimension = LOTRDimension.getCurrentDimension(mc.theWorld);
        if (currentDimension != prevDimension)
        {
        	currentFactionIndex = 0;
        }
        prevDimension = currentDimension;
        
        updateCurrentFaction();
    }
	
	private void updateCurrentFaction()
	{
		currentFaction = currentDimension.factionList.get(currentFactionIndex);
	}
	
	@Override
	public void drawScreen(int i, int j, float f)
	{
		drawDefaultBackground();
		GL11.glColor4f(1F, 1F, 1F, 1F);

		String s = StatCollector.translateToLocalFormatted("lotr.gui.alignment.title", new Object[] {currentDimension.getDimensionName()});
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop - 30, 0xFFFFFF);

		super.drawScreen(i, j, f);
		
		int x = guiLeft + xSize / 2;
		int y = guiTop;
		
		List<LOTRFaction> factions = currentDimension.factionList;
		for (int index = currentFactionIndex; index < currentFactionIndex + maxAlignmentsDisplayed; index++)
		{
			if (index >= factions.size())
			{
				break;
			}
			
			LOTRFaction faction = factions.get(index);
			if (!faction.allowPlayer)
			{
				continue;
			}
			
			int alignment = LOTRLevelData.getData(mc.thePlayer).getAlignment(faction);
			
			GL11.glColor4f(1F, 1F, 1F, 1F);
			mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
			
			float[] factionColors = faction.factionColor.getColorComponents(null);
	        GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], 1F);
	        drawTexturedModalRect(x - 116, y, 0, 18, 232, 18);
			
	        GL11.glColor4f(1F, 1F, 1F, 1F);
	        drawTexturedModalRect(x - 116, y, 0, 0, 232, 18);

			drawTexturedModalRect(x - 8 + LOTRTickHandlerClient.calculateAlignmentDisplay(alignment), y + 1, 0, 36, 16, 16);
			
			y += 22;
			s = faction.factionName();
			LOTRTickHandlerClient.drawTextWithShadow(fontRendererObj, x - fontRendererObj.getStringWidth(s) / 2, y, s, 1F);
			
			int max = LOTRTickHandlerClient.calculateMaxDisplayValue(alignment);
			String sMax = "+" + String.valueOf(max);
			String sMin = "-" + String.valueOf(max);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			int xMax = (x * 2) - 220 - fontRendererObj.getStringWidth(sMax) / 2;
			int xMin = (x * 2) + 220 - fontRendererObj.getStringWidth(sMax) / 2;
			y *= 2;
			LOTRTickHandlerClient.drawTextWithShadow(fontRendererObj, xMax, y, sMax, 1F);
			LOTRTickHandlerClient.drawTextWithShadow(fontRendererObj, xMin, y, sMin, 1F);
			GL11.glScalef(2F, 2F, 2F);
			y /= 2;
			
			y += fontRendererObj.FONT_HEIGHT + 4;
			s = String.valueOf(alignment);
			if (alignment > 0)
			{
				s = "+" + s;
			}
			LOTRTickHandlerClient.drawTextWithShadow(fontRendererObj, x - fontRendererObj.getStringWidth(s) / 2, y, s, 1F);
				
			y += 20;
		}
	}
	
	@Override
    protected void keyTyped(char c, int i)
    {
		if (i == Keyboard.KEY_DOWN)
		{
			increaseFactionIndex();
			return;
		}
		
		if (i == Keyboard.KEY_UP)
		{
			decreaseFactionIndex();
			return;
		}
		
		super.keyTyped(c, i);
	}
	
	@Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
        
        int i = Mouse.getEventDWheel();
        if (i != 0)
        {
            if (i < 0)
            {
            	increaseFactionIndex();
            }

            if (i > 0)
            {
            	decreaseFactionIndex();
            }
        }
    }
	
	private void decreaseFactionIndex()
	{
		currentFactionIndex = Math.max(currentFactionIndex - 1, 0);
	}
	
	private void increaseFactionIndex()
	{
		currentFactionIndex = Math.min(currentFactionIndex + 1, Math.max(0, currentDimension.factionList.size() - maxAlignmentsDisplayed));
	}
}
