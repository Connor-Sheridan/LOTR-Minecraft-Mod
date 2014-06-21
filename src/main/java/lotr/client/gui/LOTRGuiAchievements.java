package lotr.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRAchievement.Category;
import lotr.common.LOTRLevelData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LOTRGuiAchievements extends LOTRGui
{
	private static ResourceLocation pageTexture = new ResourceLocation("lotr:gui/achievements/page.png");
	private static ResourceLocation iconsTexture = new ResourceLocation("lotr:gui/achievements/icons.png");

	private static int currentCategoryIndex = 0;
	private Category currentCategory = Category.values()[currentCategoryIndex];
	private ArrayList currentCategoryTakenAchievements = new ArrayList();
	private ArrayList currentCategoryUntakenAchievements = new ArrayList();
	private int currentCategoryTakenCount;
	private int currentCategoryUntakenCount;
	private int totalTakenCount;
	private int totalAvailableCount;
    private float currentScroll = 0F;
    private boolean isScrolling = false;
	private boolean wasClicking;
	
	@Override
    public void initGui()
    {
		xSize = 220;
		super.initGui();
		buttonList.add(new LOTRGuiButtonAchievements(2, true, guiLeft + 13, guiTop + 13));
		buttonList.add(new LOTRGuiButtonAchievements(3, false, guiLeft + 191, guiTop + 13));
	}
	
	@Override
    public void updateScreen()
    {
        super.updateScreen();
		
		currentCategory = Category.values()[currentCategoryIndex];
		updateAchievementLists();
    }
	
	@Override
	public void drawScreen(int i, int j, float f)
	{
        boolean isMouseDown = Mouse.isButtonDown(0);
        int i1 = guiLeft + 201;
        int j1 = guiTop + 43;
        int k1 = i1 + 12;
        int l1 = j1 + 200;

        if (!wasClicking && isMouseDown && i >= i1 && j >= j1 && i < k1 && j < l1)
        {
            isScrolling = hasScrollBar();
        }

        if (!isMouseDown)
        {
            isScrolling = false;
        }

        wasClicking = isMouseDown;

        if (isScrolling)
        {
            currentScroll = ((float)(j - j1) - 8.5F) / ((float)(l1 - j1) - 17F);

            if (currentScroll < 0F)
            {
                currentScroll = 0F;
            }

            if (currentScroll > 1F)
            {
                currentScroll = 1F;
            }
        }
		
		drawDefaultBackground();
		GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        String s = StatCollector.translateToLocal("lotr.gui.achievements.title");
		s = s + ": (" + totalTakenCount + "/" + totalAvailableCount + ")";
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop - 30, 0xFFFFFF);
		
		String categoryName = currentCategory.getDisplayName();
		categoryName = categoryName + " (" + currentCategoryTakenCount + "/" + (currentCategoryTakenCount + currentCategoryUntakenCount) + ")";
		fontRendererObj.drawString(categoryName, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(categoryName) / 2, guiTop + 18, 0x7A5D43);

		super.drawScreen(i, j, f);
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(iconsTexture);
		if (hasScrollBar())
		{
			int offset = (int)(currentScroll * (198 - 17));
			drawTexturedModalRect(guiLeft + 201, guiTop + 43 + offset, 190, 0, 10, 17);
		}
		else
		{
			drawTexturedModalRect(guiLeft + 201, guiTop + 43, 200, 0, 10, 17);
		}
		
		drawAchievements();
	}
	
	private void drawAchievements()
	{
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		
		int size = currentCategoryTakenCount + currentCategoryUntakenCount;
		int min = 0 + Math.round(currentScroll * (size - 4));
		int max = 3 + Math.round(currentScroll * (size - 4));
		if (max > size - 1)
		{
			max = size - 1;
		}
		
		for (int i = min; i <= max; i++)
		{
			LOTRAchievement achievement;
			boolean hasAchievement;
			if (i < currentCategoryTakenCount)
			{
				achievement = (LOTRAchievement)currentCategoryTakenAchievements.get(i);
				hasAchievement = true;
			}
			else
			{
				achievement = (LOTRAchievement)currentCategoryUntakenAchievements.get(i - currentCategoryTakenCount);
				hasAchievement = false;
			}
			
			int offset = 42 + 50 * (i - min);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			mc.getTextureManager().bindTexture(iconsTexture);
			drawTexturedModalRect(guiLeft + 9, guiTop + offset, 0, hasAchievement ? 0 : 50, 190, 50);
			
			if (!hasAchievement)
			{
				float shading = 0.25F;
				GL11.glColor4f(shading, shading, shading, 1F);
				renderItem.renderWithColor = false;
			}
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), achievement.icon, guiLeft + 12, guiTop + offset + 3);
			GL11.glDisable(GL11.GL_LIGHTING);
			
			if (!hasAchievement)
			{
				renderItem.renderWithColor = true;
			}
			
			GL11.glColor4f(1F, 1F, 1F, 1F);
			int textColour = hasAchievement ? 0x7A5D43 : 0x56412F;
			mc.fontRenderer.drawString(achievement.getTitle(), guiLeft + 33, guiTop + offset + 3, textColour);
			mc.fontRenderer.drawSplitString(achievement.getDescription(), guiLeft + 12, guiTop + offset + 23, 184, textColour);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			
			if (hasAchievement)
			{
				mc.getTextureManager().bindTexture(iconsTexture);
				drawTexturedModalRect(guiLeft + 181, guiTop + offset + 2, 190, 17, 16, 16);
			}
		}
		
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1F, 1F, 1F, 1F);
	}
	
	@Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
			if (button.id == 2)
			{
				if (currentCategoryIndex == 0)
				{
					currentCategoryIndex = Category.values().length - 1;
				}
				else
				{
					currentCategoryIndex--;
				}
				currentScroll = 0F;
			}
			
			else if (button.id == 3)
			{
				if (currentCategoryIndex == Category.values().length - 1)
				{
					currentCategoryIndex = 0;
				}
				else
				{
					currentCategoryIndex++;
				}
				currentScroll = 0F;
			}
			
			else
			{
				super.actionPerformed(button);
			}
		}
	}
	
	@Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0 && hasScrollBar())
        {
            int j = currentCategoryTakenCount + currentCategoryUntakenCount - 4;

            if (i > 0)
            {
                i = 1;
            }

            if (i < 0)
            {
                i = -1;
            }

            currentScroll = (float)((double)currentScroll - (double)i / (double)j);

            if (currentScroll < 0F)
            {
                currentScroll = 0F;
            }

            if (currentScroll > 1F)
            {
                currentScroll = 1F;
            }
        }
    }
	
	private boolean hasScrollBar()
	{
		return currentCategoryTakenCount + currentCategoryUntakenCount > 4;
	}
	
	private void updateAchievementLists()
	{
		currentCategoryTakenAchievements.clear();
		currentCategoryUntakenAchievements.clear();
		Iterator i = currentCategory.list.iterator();
		while (i.hasNext())
		{
			LOTRAchievement achievement = (LOTRAchievement)i.next();
			if (achievement.canPlayerEarn(mc.thePlayer))
			{
				if (LOTRLevelData.hasAchievement(mc.thePlayer, achievement))
				{
					currentCategoryTakenAchievements.add(achievement);
				}
				else
				{
					currentCategoryUntakenAchievements.add(achievement);
				}
			}
		}
		currentCategoryTakenCount = currentCategoryTakenAchievements.size();
		currentCategoryUntakenCount = currentCategoryUntakenAchievements.size();
		
		totalTakenCount = 0;
		i = LOTRLevelData.getPlayerAchievements(mc.thePlayer).iterator();
		while (i.hasNext())
		{
			LOTRAchievement achievement = (LOTRAchievement)i.next();
			if (achievement.canPlayerEarn(mc.thePlayer))
			{
				totalTakenCount++;
			}
		}
		
		totalAvailableCount = 0;
		i = LOTRAchievement.allAchievements.iterator();
		while (i.hasNext())
		{
			LOTRAchievement achievement = (LOTRAchievement)i.next();
			if (achievement.canPlayerEarn(mc.thePlayer))
			{
				totalAvailableCount++;
			}
		}
		
		Collections.sort(currentCategoryTakenAchievements);
		Collections.sort(currentCategoryUntakenAchievements);
	}
}
