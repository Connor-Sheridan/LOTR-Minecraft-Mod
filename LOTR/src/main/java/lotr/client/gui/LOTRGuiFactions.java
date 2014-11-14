package lotr.client.gui;

import java.util.Map.Entry;

import lotr.client.*;
import lotr.common.*;
import lotr.common.LOTRFaction.FactionMapInfo;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiFactions extends LOTRGuiMenu
{
	private static LOTRDimension currentDimension;
	private static LOTRDimension prevDimension;
	
	private static int currentFactionIndex = 0;
	private LOTRFaction currentFaction;
	
	private static final int maxAlignmentsDisplayed = 1;
	
	{
		xSize = 220;
	}
	
	private float currentScroll = 0F;
    private boolean isScrolling = false;
	private boolean wasMouseDown;
    
    private int scrollBarWidth = 14;
	private int scrollBarHeight = 200;
	private int scrollBarX = xSize + 10;
	private int scrollBarY = ySize / 2 - scrollBarHeight / 2;
	private int scrollBarBorder = 1;
	private int scrollWidgetWidth = 12;
	private int scrollWidgetHeight = 17;
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		updateCurrentDimensionAndFaction();
		currentFaction = LOTRTickHandlerClient.currentAlignmentFaction;
		currentFactionIndex = currentDimension.factionList.indexOf(currentFaction);
				
		currentScroll = (float)currentFactionIndex / (currentDimension.factionList.size() - maxAlignmentsDisplayed);
	}
	
	@Override
    public void updateScreen()
    {
        super.updateScreen();
        updateCurrentDimensionAndFaction();
    }
	
	private void updateCurrentDimensionAndFaction()
	{
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
		setupScrollBar(i, j);
		
		drawDefaultBackground();
		GL11.glColor4f(1F, 1F, 1F, 1F);

		String s = StatCollector.translateToLocalFormatted("lotr.gui.factions.title", currentDimension.getDimensionName());
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop - 30, 0xFFFFFF);

		super.drawScreen(i, j, f);
		
		if (currentFaction != null)
		{
			LOTRFaction faction = currentFaction;
			
			int x = guiLeft + xSize / 2;
			int y = guiTop;
			
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
			int xDisplayMax = (x * 2) - 220 - fontRendererObj.getStringWidth(sMax) / 2;
			int xDisplayMin = (x * 2) + 220 - fontRendererObj.getStringWidth(sMax) / 2;
			y *= 2;
			LOTRTickHandlerClient.drawTextWithShadow(fontRendererObj, xDisplayMax, y, sMax, 1F);
			LOTRTickHandlerClient.drawTextWithShadow(fontRendererObj, xDisplayMin, y, sMin, 1F);
			GL11.glScalef(2F, 2F, 2F);
			y /= 2;
			
			y += fontRendererObj.FONT_HEIGHT + 5;
			s = faction.factionSubtitle();
			drawCenteredString(s, x, y, 0xFFFFFF);
			y += fontRendererObj.FONT_HEIGHT * 3;
			
			if (faction.factionMapInfo != null)
			{
				FactionMapInfo mapInfo = faction.factionMapInfo;
				
				int mapX = mapInfo.posX;
				int mapY = mapInfo.posY;
				int mapR = mapInfo.radius;
				
				double minU = (double)(mapX - mapR) / (double)LOTRGenLayerWorld.imageWidth;
				double maxU = (double)(mapX + mapR) / (double)LOTRGenLayerWorld.imageWidth;
				double minV = (double)(mapY - mapR) / (double)LOTRGenLayerWorld.imageHeight;
				double maxV = (double)(mapY + mapR) / (double)LOTRGenLayerWorld.imageHeight;
				
				int mapSize = 80;
				int xMax = guiLeft + xSize;
				int xMin = xMax - mapSize;
				int yMin = y;
				int yMax = yMin + mapSize;
				
				GL11.glColor4f(1F, 1F, 1F, 1F);
				mc.getTextureManager().bindTexture(LOTRTextures.mapTexture);
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(xMin, yMax, zLevel, minU, maxV);
				tessellator.addVertexWithUV(xMax, yMax, zLevel, maxU, maxV);
				tessellator.addVertexWithUV(xMax, yMin, zLevel, maxU, minV);
				tessellator.addVertexWithUV(xMin, yMin, zLevel, minU, minV);
				tessellator.draw();
				
				mc.getTextureManager().bindTexture(LOTRGuiMap.overlayTexture);
		        GL11.glEnable(GL11.GL_BLEND);
		        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		        GL11.glColor4f(1F, 1F, 1F, 0.2F);
				drawTexturedModalRect(xMin, yMin, 0, 0, mapSize, mapSize);
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_BLEND);
			}
			
			String alignmentInfo = StatCollector.translateToLocal("lotr.gui.factions.alignment");
			x = guiLeft;
			fontRendererObj.drawString(alignmentInfo, x, y, 0xFFFFFF);
			x += fontRendererObj.getStringWidth(alignmentInfo) + 5;
			
			String alignmentString = String.valueOf(alignment);
			if (alignment > 0)
			{
				alignmentString = "+" + alignmentString;
			}
			LOTRTickHandlerClient.drawTextWithShadow(fontRendererObj, x, y, alignmentString, 1F);
			y += fontRendererObj.FONT_HEIGHT;
			
			x = guiLeft;
			alignmentInfo = StatCollector.translateToLocal("lotr.gui.factions.alignment.neutral");
			if (alignment < 0)
			{
				alignmentInfo = StatCollector.translateToLocal("lotr.gui.factions.alignment.enemy");
			}
			else if (alignment > 0)
			{
				alignmentInfo = StatCollector.translateToLocal("lotr.gui.factions.alignment.ally");
				
				int highestAlignmentAchievement = 0;
				for (Entry<Integer, LOTRAchievement> entry : faction.getAlignmentAchievements().entrySet())
				{
					int achievementAlignment = entry.getKey();
					LOTRAchievement achievement = entry.getValue();
					if (achievementAlignment >= highestAlignmentAchievement && alignment >= achievementAlignment)
					{
						highestAlignmentAchievement = achievementAlignment;
						alignmentInfo = achievement.getTitle();
					}
				}
			}
			
			alignmentInfo = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.state", alignmentInfo);
			fontRendererObj.drawString(alignmentInfo, x, y, 0xFFFFFF);
			y += fontRendererObj.FONT_HEIGHT * 2;
			
			LOTRFactionData factionData = LOTRLevelData.getData(mc.thePlayer).getFactionData(faction);
			if (alignment >= 0)
			{
				s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.enemiesKilled", factionData.getEnemiesKilled());
				fontRendererObj.drawString(s, x, y, 0xFFFFFF);
				y += fontRendererObj.FONT_HEIGHT;
				
				s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.trades", factionData.getTradeCount());
				fontRendererObj.drawString(s, x, y, 0xFFFFFF);
				y += fontRendererObj.FONT_HEIGHT;
				
				s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.hires", factionData.getHireCount());
				fontRendererObj.drawString(s, x, y, 0xFFFFFF);
				y += fontRendererObj.FONT_HEIGHT;
				
				s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.miniquests", factionData.getMiniQuestsCompleted());
				fontRendererObj.drawString(s, x, y, 0xFFFFFF);
				y += fontRendererObj.FONT_HEIGHT;
			}
			if (alignment <= 0)
			{
				s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.npcsKilled", factionData.getNPCsKilled());
				fontRendererObj.drawString(s, x, y, 0xFFFFFF);
				y += fontRendererObj.FONT_HEIGHT;
			}
		}
		
		if (hasScrollBar())
		{
			mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 0, 52, scrollBarWidth, scrollBarHeight);

			for (int index = 0; index < currentDimension.factionList.size(); index++)
			{
				LOTRFaction faction = currentDimension.factionList.get(index);
				
				float[] factionColors = faction.factionColor.getColorComponents(null);
				float shade = 0.6F;
		        GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1F);
		        
		        float xMin = guiLeft + scrollBarX + scrollBarBorder;
		        float xMax = guiLeft + scrollBarX + scrollBarWidth - scrollBarBorder;
		        float yMin = (float)index / (float)currentDimension.factionList.size();
		        float yMax = (float)(index + 1) / (float)currentDimension.factionList.size();
		        yMin = guiTop + scrollBarY + scrollBarBorder + (yMin * (scrollBarHeight - scrollBarBorder * 2));
		        yMax = guiTop + scrollBarY + scrollBarBorder + (yMax * (scrollBarHeight - scrollBarBorder * 2));

		        float minU = (0 + scrollBarBorder) / 256F;
		        float maxU = (0 + scrollBarWidth - scrollBarBorder) / 256F;
		        float minV = (52 + scrollBarBorder) / 256F;
		        float maxV = (52 + scrollBarBorder + 1) / 256F;
		        
		        Tessellator tessellator = Tessellator.instance;
		        tessellator.startDrawingQuads();
		        tessellator.addVertexWithUV(xMin, yMax, zLevel, minU, maxV);
		        tessellator.addVertexWithUV(xMax, yMax, zLevel, maxU, maxV);
		        tessellator.addVertexWithUV(xMax, yMin, zLevel, maxU, minV);
		        tessellator.addVertexWithUV(xMin, yMin, zLevel, minU, minV);
		        tessellator.draw();
			}
			
			GL11.glColor4f(1F, 1F, 1F, 1F);

			if (canScroll())
			{
				int scroll = (int)(currentScroll * (scrollBarHeight - (scrollBarBorder) * 2 - scrollWidgetHeight));
				drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder, guiTop + scrollBarY + scrollBarBorder + scroll, 14, 52, scrollWidgetWidth, scrollWidgetHeight);
			}
		}
	}
	
	private boolean hasScrollBar()
	{
		return currentDimension.factionList.size() > maxAlignmentsDisplayed;
	}
	
	private boolean canScroll()
	{
		return true;
	}
	
	private void setupScrollBar(int i, int j)
	{
        boolean isMouseDown = Mouse.isButtonDown(0);
        
        int i1 = guiLeft + scrollBarX;
        int j1 = guiTop + scrollBarY;
        int i2 = i1 + scrollBarWidth;
        int j2 = j1 + scrollBarHeight;

        if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2)
        {
            isScrolling = canScroll();
        }

        if (!isMouseDown)
        {
            isScrolling = false;
        }

        wasMouseDown = isMouseDown;

        if (isScrolling)
        {
            currentScroll = ((float)(j - j1) - ((float)scrollWidgetHeight / 2F)) / ((float)(j2 - j1) - (float)scrollWidgetHeight);

            if (currentScroll < 0F)
            {
                currentScroll = 0F;
            }

            if (currentScroll > 1F)
            {
                currentScroll = 1F;
            }
            
            currentFactionIndex = Math.round(currentScroll * (currentDimension.factionList.size() - maxAlignmentsDisplayed));
        }
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
            	currentFactionIndex = Math.min(currentFactionIndex + 1, Math.max(0, currentDimension.factionList.size() - maxAlignmentsDisplayed));
            }

            if (i > 0)
            {
            	currentFactionIndex = Math.max(currentFactionIndex - 1, 0);
            }
            
            currentScroll = (float)currentFactionIndex / (currentDimension.factionList.size() - maxAlignmentsDisplayed);
        }
    }
}
