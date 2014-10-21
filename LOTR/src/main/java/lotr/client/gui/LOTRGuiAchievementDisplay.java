package lotr.client.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lotr.common.LOTRAchievement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LOTRGuiAchievementDisplay extends Gui
{
	private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/achievements/icons.png");
	private static int guiXSize = 190;
	private static int guiYSize = 32;
	
    private Minecraft mc;
    private RenderItem itemRenderer;
    
    private int achievementWindowWidth;
    private int achievementWindowHeight;
    private String achievementGetLocalText;
    private Map<LOTRAchievement, Long> achievements = new HashMap();
    private Set<LOTRAchievement> achievementsToRemove = new HashSet();

    public LOTRGuiAchievementDisplay()
    {
        mc = Minecraft.getMinecraft();
        itemRenderer = new RenderItem();
    }

    public void queueAchievement(LOTRAchievement achievement)
    {
		achievements.put(achievement, Minecraft.getSystemTime());
        achievementGetLocalText = StatCollector.translateToLocal("achievement.get");
    }

    private void updateAchievementWindowScale()
    {
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        achievementWindowWidth = mc.displayWidth;
        achievementWindowHeight = mc.displayHeight;
        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        achievementWindowWidth = scaledresolution.getScaledWidth();
        achievementWindowHeight = scaledresolution.getScaledHeight();
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0D, (double)achievementWindowWidth, (double)achievementWindowHeight, 0D, 1000D, 3000D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0F, 0F, -2000F);
    }

    public void updateAchievementWindow()
    {
        if (!achievements.isEmpty())
        {
			int index = 0;
			for (LOTRAchievement achievement : achievements.keySet())
			{
				long achievementTime = achievements.get(achievement);
				double d0 = (double)(Minecraft.getSystemTime() - achievementTime) / 3000D;
				
				if (d0 < 0D || d0 > 1D)
				{
					achievementsToRemove.add(achievement);
				}
				else
				{
					updateAchievementWindowScale();
					
					if (Minecraft.isGuiEnabled())
					{
						GL11.glEnable(GL11.GL_ALPHA_TEST);
						GL11.glDisable(GL11.GL_DEPTH_TEST);
						GL11.glDepthMask(false);
						double d1 = d0 * 2D;
	
						if (d1 > 1D)
						{
							d1 = 2D - d1;
						}
	
						d1 *= 4D;
						d1 = 1D - d1;
	
						if (d1 < 0D)
						{
							d1 = 0D;
						}
	
						d1 *= d1;
						d1 *= d1;
						int i = achievementWindowWidth - guiXSize;
						int j = 0 - (int)(d1 * 36D);
						j += index * (guiYSize + 8);
						GL11.glColor4f(1F, 1F, 1F, 1F);
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						mc.getTextureManager().bindTexture(guiTexture);
						GL11.glDisable(GL11.GL_LIGHTING);
						drawTexturedModalRect(i, j, 0, 148, guiXSize, guiYSize);
	
						mc.fontRenderer.drawString(achievementGetLocalText, i + 30, j + 7, 0x7A5D43);
						mc.fontRenderer.drawString(achievement.getTitle(), i + 30, j + 18, 0x7A5D43);
	
						RenderHelper.enableGUIStandardItemLighting();
						GL11.glDisable(GL11.GL_LIGHTING);
						GL11.glEnable(GL12.GL_RESCALE_NORMAL);
						GL11.glEnable(GL11.GL_COLOR_MATERIAL);
						GL11.glEnable(GL11.GL_LIGHTING);
						itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), achievement.icon, i + 8, j + 8);
						GL11.glDisable(GL11.GL_LIGHTING);
						GL11.glDepthMask(true);
						GL11.glEnable(GL11.GL_DEPTH_TEST);
						GL11.glDisable(GL11.GL_ALPHA_TEST);
					}
				}
				
				index++;
			}
		}
		
		if (!achievementsToRemove.isEmpty())
		{
			achievements.keySet().removeAll(achievementsToRemove);
		}
    }
}
