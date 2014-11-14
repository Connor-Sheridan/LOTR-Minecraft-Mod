package lotr.client.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.*;

import lotr.client.*;
import lotr.common.*;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.EmptyChunk;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Charsets;

public class LOTRGuiMap extends LOTRGuiMenu
{
	public static ResourceLocation overlayTexture = new ResourceLocation("lotr:map/mapOverlay.png");
	private static ResourceLocation borderTexture = new ResourceLocation("lotr:map/mapScreen.png");
	
	public static Map playerLocations = new HashMap();
	private static Map playerSkins = new HashMap();
	
	private static ItemStack questBookIcon = new ItemStack(LOTRMod.redBook);
	
	private static final int MIN_ZOOM = -3;
	private static final int MAX_ZOOM = 4;
	private static final int mapXSize = 256;
	private static final int mapYSize = 200;
	private static final int mapBorder = 4;
	
	private static final int addWPButtonX = mapXSize - 16;
	private static final int addWPButtonY = 6;
	private static final int addWPButtonWidth = 10;
	
	private static final int delWPButtonX = mapXSize - 16;
	private static final int delWPButtonY = 18;
	private static final int delWPButtonWidth = 10;
	
	private static final int waypointToggleX = 6;
	private static final int waypointToggleY = 6;
	private static final int waypointToggleWidth = 10;
	
	private static int zoomPower = 0;
	private static int maxZoomTicks = 5;
	
	private int prevZoomPower = zoomPower;
	private int zoomTicks;
	private float zoomScale;
	
	public boolean isPlayerOp = false;
	
	private float posX;
	private float posY;
	private int isMouseButtonDown;
	private int prevMouseX;
	private int prevMouseY;
	private int mouseXCoord;
	private int mouseZCoord;
	private boolean isMouseWithinMap;
	
	private LOTRAbstractWaypoint selectedWaypoint;
	private boolean canCreateWaypoint;
	private boolean creatingWaypoint;
	private GuiTextField nameWaypointTextField;
	private boolean deletingWaypoint;
	
	private boolean hasOverlay;
	private String overlayDisplayString[];
	
	public static enum WaypointMode
	{
		ALL,
		MAP,
		CUSTOM,
		NONE;
		
		public boolean canDisplayWaypoint(LOTRAbstractWaypoint waypoint)
		{
			if (this == ALL)
			{
				return true;
			}
			if (this == NONE)
			{
				return false;
			}
			return waypoint instanceof LOTRWaypoint.Custom ? this == CUSTOM : this == MAP;
		}
		
		public void toggle()
		{
			int i = ordinal();
			i++;
			if (i >= values().length)
			{
				i = 0;
			}
			waypointMode = values()[i];
			LOTRClientProxy.sendClientInfoPacket();
		}
		
		public static void setWaypointMode(int i)
		{
			for (WaypointMode mode : values())
			{
				if (mode.ordinal() == i)
				{
					waypointMode = mode;
					return;
				}
			}
			waypointMode = WaypointMode.ALL;
		}
	}
	
	public static WaypointMode waypointMode = WaypointMode.ALL;
	
	public LOTRGuiMap()
	{
		if (LOTRGenLayerWorld.biomeImageData == null)
		{
			new LOTRGenLayerWorld();
		}
	}
	
	@Override
    public void initGui()
    {
		xSize = 256;
		ySize = 256;
		super.initGui();
		posX = ((int)mc.thePlayer.posX / LOTRGenLayerWorld.scale) + LOTRGenLayerWorld.originX;
		posY = ((int)mc.thePlayer.posZ / LOTRGenLayerWorld.scale) + LOTRGenLayerWorld.originZ;
		nameWaypointTextField = new GuiTextField(fontRendererObj, guiLeft + mapXSize / 2 - 80, guiTop + 40, 160, 20);
	}
	
	@Override
    public void updateScreen()
    {
		super.updateScreen();
		
		if (zoomTicks > 0)
		{
			zoomTicks--;
		}
		
		if (creatingWaypoint)
		{
			nameWaypointTextField.updateCursorCounter();
		}
		
		if (hasOverlay && (canCreateWaypoint && !creatingWaypoint) || (creatingWaypoint))
		{
			IChatComponent[] protectionMessage = new IChatComponent[1];
			if (LOTRBannerProtection.isProtectedByBanner(mc.theWorld, mc.thePlayer, LOTRBannerProtection.forPlayer_returnMessage(mc.thePlayer, protectionMessage), true))
			{
				hasOverlay = true;
				canCreateWaypoint = false;
				creatingWaypoint = false;
				overlayDisplayString = new String[]
				{
					StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.protected.1", protectionMessage[0].getFormattedText()),
					StatCollector.translateToLocal("lotr.gui.map.customWaypoint.protected.2")
				};
			}
		}
    }
	
	@Override
	public void drawScreen(int i, int j, float f)
	{
		zLevel = 0F;
		
		drawDefaultBackground();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		drawCenteredString(StatCollector.translateToLocal("lotr.gui.map.title"), guiLeft + mapXSize / 2, guiTop - 30, 0xFFFFFF);
		
		float zoomExp = (float)zoomPower;
		if (zoomTicks > 0)
		{
			float progress = (zoomPower - prevZoomPower) * (maxZoomTicks - (zoomTicks - f)) / (float)maxZoomTicks;
			zoomExp = (float)prevZoomPower + progress;
		}
		zoomScale = (float)Math.pow(2, zoomExp);
		int zoomScaleX = Math.round(mapXSize / zoomScale);
		int zoomScaleY = Math.round(mapYSize / zoomScale);
		
		int i1 = guiLeft + mapBorder;
		int i2 = guiLeft + mapXSize - mapBorder;
		int j1 = guiTop + mapBorder;
		int j2 = guiTop + mapYSize - mapBorder;
		isMouseWithinMap = i >= i1 && i < i2 && j >= j1 && j < j2;
		
        if (!hasOverlay && Mouse.isButtonDown(0))
        {
            if ((isMouseButtonDown == 0 || isMouseButtonDown == 1) && isMouseWithinMap)
            {
                if (isMouseButtonDown == 0)
                {
                    isMouseButtonDown = 1;
                }
                else
                {
                	float x = (float)(i - prevMouseX) / zoomScale;
                	float y = (float)(j - prevMouseY) / zoomScale;
                    posX -= x;
					posY -= y;
                    if (x != 0F || y != 0F)
					{
						selectedWaypoint = null;
					}
                }

                prevMouseX = i;
                prevMouseY = j;
            }
        }
        else
        {
            isMouseButtonDown = 0;
        }
		
		float minX = posX - zoomScaleX / 2;
		float maxX = posX + zoomScaleX / 2;
		if (minX < 0)
		{
			posX = 0 + zoomScaleX / 2;
		}
		if (maxX >= LOTRGenLayerWorld.imageWidth)
		{
			posX = LOTRGenLayerWorld.imageWidth - zoomScaleX / 2;
		}
		
		float minY = posY - zoomScaleY / 2;
		float maxY = posY + zoomScaleY / 2;
		if (minY < 0)
		{
			posY = 0 + zoomScaleY / 2;
		}
		if (maxY >= LOTRGenLayerWorld.imageHeight)
		{
			posY = LOTRGenLayerWorld.imageHeight - zoomScaleY / 2;
		}
		
		double minU = (double)(posX - zoomScaleX / 2) / (double)LOTRGenLayerWorld.imageWidth;
		double maxU = (double)(posX + zoomScaleX / 2) / (double)LOTRGenLayerWorld.imageWidth;
		double minV = (double)(posY - zoomScaleY / 2) / (double)LOTRGenLayerWorld.imageHeight;
		double maxV = (double)(posY + zoomScaleY / 2) / (double)LOTRGenLayerWorld.imageHeight;
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(LOTRTextures.mapTexture);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(guiLeft, guiTop + mapYSize, zLevel, minU, maxV);
		tessellator.addVertexWithUV(guiLeft + mapXSize, guiTop + mapYSize, zLevel, maxU, maxV);
		tessellator.addVertexWithUV(guiLeft + mapXSize, guiTop, zLevel, maxU, minV);
		tessellator.addVertexWithUV(guiLeft, guiTop, zLevel, minU, minV);
		tessellator.draw();
		
		zLevel += 150F;
		mc.getTextureManager().bindTexture(borderTexture);
		int compassWidth = 32;
		int compassHeight = 32;
		int compassU = 224;
		int compassV = 200;
		int compassX = guiLeft + mapBorder + 6;
		int compassY = guiTop + mapYSize - mapBorder - 6 - compassHeight;
		drawTexturedModalRect(compassX, compassY, compassU, compassV, compassWidth, compassHeight);
		zLevel -= 150F;
		
        mc.getTextureManager().bindTexture(overlayTexture);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glColor4f(1F, 1F, 1F, 0.2F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, mapXSize, mapYSize);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL11.GL_BLEND);
		
		if (!waypointMode.canDisplayWaypoint(selectedWaypoint))
		{
			selectedWaypoint = null;
		}
		
		if (!hasOverlay && isMiddleEarth() && selectedWaypoint != null)
		{
			boolean hasUnlocked = selectedWaypoint.hasPlayerUnlocked(mc.thePlayer);
			int fastTravel = LOTRLevelData.getData(mc.thePlayer).getFTTimer();
			boolean canFastTravel = fastTravel <= 0 && hasUnlocked;
			
			int border = 3;
			int stringHeight = fontRendererObj.FONT_HEIGHT;
				
			int x = guiLeft;
			int y = guiTop + mapYSize + 10;
			int rectWidth = 256;
			int rectHeight = border * 2 + stringHeight;
			drawRect(x, y, x + rectWidth, y + rectHeight, 0xC0000000);
			
			if (!hasUnlocked)
			{
				String s = "If you can read this, something has gone hideously wrong";
				
				if (!selectedWaypoint.isUnlockable(mc.thePlayer))
				{
					s = StatCollector.translateToLocal("lotr.gui.map.waypointUnavailableEnemy");
				}
				else
				{
					s = StatCollector.translateToLocal("lotr.gui.map.waypointUnavailableTravel");
				}
				
				drawCenteredString(s, guiLeft + mapXSize / 2, y + border, 0xFFFFFF);
			}
			else if (canFastTravel)
			{
				String s = StatCollector.translateToLocalFormatted("lotr.gui.map.fastTravel", new Object[] {GameSettings.getKeyDisplayString(LOTRKeyHandler.keyBindingFastTravel.getKeyCode())});
				drawCenteredString(s, guiLeft + mapXSize / 2, y + border, 0xFFFFFF);
			}
			else
			{
				String s = StatCollector.translateToLocalFormatted("lotr.gui.map.fastTravelTimeRemaining", new Object[] {LOTRLevelData.getHMSTime(fastTravel)});
				drawCenteredString(s, guiLeft + mapXSize / 2, y + border, 0xFFFFFF);
			}
		}
		else if (!hasOverlay && isMouseWithinMap)
		{
			float biomePosX = posX + (((i - guiLeft) - mapXSize / 2) / zoomScale);
			float biomePosZ = posY + (((j - guiTop) - mapYSize / 2) / zoomScale);
			
			int biomePosX_int = Math.round(biomePosX);
			int biomePosZ_int = Math.round(biomePosZ);
			
			int biomeID = LOTRGenLayerWorld.biomeImageData[biomePosZ_int * LOTRGenLayerWorld.imageWidth + biomePosX_int];
			BiomeGenBase biome = LOTRDimension.MIDDLE_EARTH.biomeList[biomeID];
			if (biome instanceof LOTRBiome)
			{
				String biomeName = ((LOTRBiome)biome).getBiomeDisplayName();
				int border = 3;
				int stringHeight = fontRendererObj.FONT_HEIGHT;
				
				int x = guiLeft;
				int y = guiTop + mapYSize + 10;
				int rectWidth = 256;
				int rectHeight = border * 3 + stringHeight * 2;
				if (canTeleport())
				{
					rectHeight += (stringHeight + border) * 2;
				}
				drawRect(x, y, x + rectWidth, y + rectHeight, 0xC0000000);
				drawCenteredString(biomeName, guiLeft + mapXSize / 2, y + border, 0xFFFFFF);
				
				mouseXCoord = Math.round((biomePosX - LOTRGenLayerWorld.originX) * LOTRGenLayerWorld.scale);
				mouseZCoord = Math.round((biomePosZ - LOTRGenLayerWorld.originZ) * LOTRGenLayerWorld.scale);
				String coords = StatCollector.translateToLocalFormatted("lotr.gui.map.coords", new Object[] {mouseXCoord, mouseZCoord});
				int stringX = guiLeft + mapXSize / 2;
				int stringY = y + border * 2 + stringHeight;
				drawCenteredString(coords, stringX, stringY, 0xFFFFFF);
				
				if (canTeleport())
				{
					String teleport = StatCollector.translateToLocalFormatted("lotr.gui.map.tp", new Object[] {GameSettings.getKeyDisplayString(LOTRKeyHandler.keyBindingMapTeleport.getKeyCode())});
					drawCenteredString(teleport, stringX, stringY + (stringHeight + border) * 2, 0xFFFFFF);
				}
			}
		}

		Iterator it = playerLocations.keySet().iterator();
		while (it.hasNext())
		{
			UUID player = (UUID)it.next();
			PlayerLocationInfo info = (PlayerLocationInfo)playerLocations.get(player);
			renderPlayer(player, info.name, info.posX, info.posZ, i, j);
		}
		
		if (isMiddleEarth())
		{
			renderPlayer(mc.thePlayer.getUniqueID(), mc.thePlayer.getCommandSenderName(), mc.thePlayer.posX, mc.thePlayer.posZ, i, j);
		}

		renderMiniQuests(mc.thePlayer, i, j);
		
		renderWaypoints();

		zLevel = 100F;
        mc.getTextureManager().bindTexture(borderTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, mapXSize, mapYSize);
		
		if (!hasOverlay)
		{
			drawTexturedModalRect(guiLeft + waypointToggleX, guiTop + waypointToggleY, waypointToggleWidth, 204 + (waypointMode.ordinal() * waypointToggleWidth), waypointToggleWidth, waypointToggleWidth);
			
			if (isMiddleEarth())
			{
				drawTexturedModalRect(guiLeft + addWPButtonX, guiTop + addWPButtonY, 0, 204, addWPButtonWidth, addWPButtonWidth);
				
				if (selectedWaypoint instanceof LOTRWaypoint.Custom)
				{
					drawTexturedModalRect(guiLeft + delWPButtonX, guiTop + delWPButtonY, 0, 204 + addWPButtonWidth, delWPButtonWidth, delWPButtonWidth);
				}
			}
		}
		
		if (hasOverlay)
		{
			int x = guiLeft + mapBorder;
			int y = guiTop + mapBorder;
			int x1 = guiLeft + mapXSize - mapBorder;
			int y1 = guiTop + mapYSize - mapBorder;
			drawRect(x, y, x1, y1, 0xC0000000);
			
			if (overlayDisplayString != null)
			{
				int stringX = guiLeft + mapXSize / 2;
				int stringY = y + 3 + mc.fontRenderer.FONT_HEIGHT;
				for (String s : overlayDisplayString)
				{
					drawCenteredString(s, stringX, stringY, 0xFFFFFF);	
					stringY += mc.fontRenderer.FONT_HEIGHT;
				}
			}
			
			if (creatingWaypoint)
			{
		        GL11.glDisable(GL11.GL_LIGHTING);
		        nameWaypointTextField.drawTextBox();
				GL11.glEnable(GL11.GL_LIGHTING);
			}
		}
		
		super.drawScreen(i, j, f);
	}
    
    private void renderPlayer(UUID player, String playerName, double playerPosX, double playerPosZ, int mouseX, int mouseY)
    {
    	Tessellator tessellator = Tessellator.instance;
    	
    	int[] pos = transformCoords((float)playerPosX, (float)playerPosZ);
    	int playerX = pos[0];
    	int playerZ = pos[1];
		
		int iconWidthHalf = 4;
		int iconBorder = mapBorder + iconWidthHalf + 1;
		
		playerX = Math.max(guiLeft + iconBorder, playerX);
		playerX = Math.min(guiLeft + mapXSize - iconBorder - 1, playerX);
		playerZ = Math.max(guiTop + iconBorder, playerZ);
		playerZ = Math.min(guiTop + mapYSize - iconBorder - 1, playerZ);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		ResourceLocation texture = (ResourceLocation)playerSkins.get(player);
		if (texture == null)
		{
			texture = AbstractClientPlayer.getLocationSkin(playerName);
			AbstractClientPlayer.getDownloadImageSkin(texture, playerName);
			playerSkins.put(player, texture);
		}

		mc.getTextureManager().bindTexture(texture);
		
		double iconMinU = 8D / 64D;
		double iconMaxU = 16D / 64D;
		double iconMinV = 8D / 32D;
		double iconMaxV = 16D / 32D;
		
		double playerX_d = playerX + 0.5D;
		double playerZ_d = playerZ + 0.5D;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf, playerZ_d + iconWidthHalf, zLevel, iconMinU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf, playerZ_d + iconWidthHalf, zLevel, iconMaxU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf, playerZ_d - iconWidthHalf, zLevel, iconMaxU, iconMinV);
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf, playerZ_d - iconWidthHalf, zLevel, iconMinU, iconMinV);
		tessellator.draw();
		
		iconMinU = 40D / 64D;
		iconMaxU = 48D / 64D;
		iconMinV = 8D / 32D;
		iconMaxV = 16D / 32D;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf - 0.5D, playerZ_d + iconWidthHalf + 0.5D, zLevel, iconMinU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf + 0.5D, playerZ_d + iconWidthHalf + 0.5D, zLevel, iconMaxU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf + 0.5D, playerZ_d - iconWidthHalf - 0.5D, zLevel, iconMaxU, iconMinV);
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf - 0.5D, playerZ_d - iconWidthHalf - 0.5D, zLevel, iconMinU, iconMinV);
		tessellator.draw();
		
		if (!hasOverlay && mouseX >= playerX - iconWidthHalf && mouseX < playerX + iconWidthHalf && mouseY >= playerZ - iconWidthHalf && mouseY < playerZ + iconWidthHalf)
		{
			int stringWidth = mc.fontRenderer.getStringWidth(playerName);
			int stringHeight = mc.fontRenderer.FONT_HEIGHT;
			
			int x = playerX;
			int y = playerZ;
			
			y += iconWidthHalf + 3;
			
			int border = 3;
			int rectWidth = stringWidth + border * 2;
			x -= rectWidth / 2;
			int rectHeight = stringHeight + border * 2;
			
			int mapBorder2 = mapBorder + 2;
			x = Math.max(x, guiLeft + mapBorder2);
			x = Math.min(x, guiLeft + mapXSize - mapBorder2 - rectWidth);
			y = Math.max(y, guiTop + mapBorder2);
			y = Math.min(y, guiTop + mapYSize - mapBorder2 - rectHeight);
			
			GL11.glTranslatef(0F, 0F, 300F);
			drawRect(x, y, x + rectWidth, y + rectHeight, 0xC0000000);
			mc.fontRenderer.drawString(playerName, x + border, y + border, 0xFFFFFF);
			GL11.glTranslatef(0F, 0F, -300F);
		}
    }
    
    private void renderMiniQuests(EntityPlayer entityplayer, int mouseX, int mouseY)
    {
    	if (hasOverlay)
    	{
    		return;
    	}
    	
    	List<LOTRMiniQuest> quests = LOTRLevelData.getData(entityplayer).getActiveMiniQuests();
    	for (LOTRMiniQuest quest : quests)
    	{
    		ChunkCoordinates location = quest.getLastLocation();
    		if (location != null)
    		{
    	    	int[] pos = transformCoords(location.posX, location.posZ);
    	    	int questX = pos[0];
    	    	int questZ = pos[1];
    			
    			float scale = 0.5F;
    			float invScale = 1F / scale;
    			
    			IIcon icon = questBookIcon.getIconIndex();
    			int iconWidthHalf = icon.getIconWidth() / 2;
    			iconWidthHalf *= scale;
    			int iconBorder = mapBorder + iconWidthHalf + 1;
    			
    			questX = Math.max(guiLeft + iconBorder, questX);
    			questX = Math.min(guiLeft + mapXSize - iconBorder - 1, questX);
    			questZ = Math.max(guiTop + iconBorder, questZ);
    			questZ = Math.min(guiTop + mapYSize - iconBorder - 1, questZ);
    			
    			int iconX = Math.round((float)questX * invScale);
    			int iconZ = Math.round((float)questZ * invScale);
    			iconX -= iconWidthHalf;
    			iconZ -= iconWidthHalf;
    			
    			GL11.glScalef(scale, scale, scale);
    			GL11.glColor4f(1F, 1F, 1F, 1F);
    			GL11.glEnable(GL11.GL_LIGHTING);
    			GL11.glEnable(GL11.GL_CULL_FACE);
    			renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), questBookIcon, iconX, iconZ);
    			GL11.glDisable(GL11.GL_LIGHTING);
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    			GL11.glScalef(invScale, invScale, invScale);
    			
    			if (!hasOverlay && mouseX >= questX - iconWidthHalf && mouseX < questX + iconWidthHalf && mouseY >= questZ - iconWidthHalf && mouseY < questZ + iconWidthHalf)
    			{
    				String name = quest.entityName;
    				int stringWidth = mc.fontRenderer.getStringWidth(name);
    				int stringHeight = mc.fontRenderer.FONT_HEIGHT;
    				
    				int x = questX;
    				int y = questZ;
    				
    				y += iconWidthHalf + 3;
    				
    				int border = 3;
    				int rectWidth = stringWidth + border * 2;
    				x -= rectWidth / 2;
    				int rectHeight = stringHeight + border * 2;
    				
    				int mapBorder2 = mapBorder + 2;
    				x = Math.max(x, guiLeft + mapBorder2);
    				x = Math.min(x, guiLeft + mapXSize - mapBorder2 - rectWidth);
    				y = Math.max(y, guiTop + mapBorder2);
    				y = Math.min(y, guiTop + mapYSize - mapBorder2 - rectHeight);

    				GL11.glTranslatef(0F, 0F, 300F);
    				drawRect(x, y, x + rectWidth, y + rectHeight, 0xC0000000);
    				mc.fontRenderer.drawString(name, x + border, y + border, 0xFFFFFF);
    				GL11.glTranslatef(0F, 0F, -300F);
    			}
    		}
    	}
    }
    
    private void renderWaypoints()
    {
		if (waypointMode == WaypointMode.NONE)
		{
			return;
		}
		
		mc.getTextureManager().bindTexture(borderTexture);
		
		List waypoints = LOTRWaypoint.getListOfAllWaypoints(mc.thePlayer);
		for (int l = 0; l < waypoints.size(); l++)
		{
			LOTRAbstractWaypoint waypoint = (LOTRAbstractWaypoint)waypoints.get(l);
			if (waypointMode.canDisplayWaypoint(waypoint))
			{
    	    	int[] pos = transformCoords(waypoint.getXCoord(), waypoint.getZCoord());
    	    	int x = pos[0];
    	    	int y = pos[1];

				if (x - 2 >= guiLeft && x + 2 <= guiLeft + mapXSize && y - 2 >= guiTop && y + 2 <= guiTop + mapYSize)
				{
					boolean unlocked = waypoint.hasPlayerUnlocked(mc.thePlayer);
					boolean custom = waypoint instanceof LOTRWaypoint.Custom;
					drawTexturedModalRect(x - 2, y - 2, custom ? 8 : (unlocked ? 4 : 0), 200, 4, 4);
				}
			}
		}
		
		if (selectedWaypoint != null && waypointMode.canDisplayWaypoint(selectedWaypoint))
		{
			String name = selectedWaypoint.getDisplayName();
			String coords = "x: " + selectedWaypoint.getXCoord() + ", z: " + selectedWaypoint.getZCoord();
			
	    	int[] pos = transformCoords(selectedWaypoint.getXCoord(), selectedWaypoint.getZCoord());
	    	int x = pos[0];
	    	int y = pos[1];
	    	
			y += 5;
			
			int border = 3;
			int stringHeight = fontRendererObj.FONT_HEIGHT;
			int rectWidth = Math.max(fontRendererObj.getStringWidth(name), fontRendererObj.getStringWidth(coords)) + border * 2;
			x -= rectWidth / 2;
			int rectHeight = border * 3 + stringHeight * 2;
			
			int mapBorder2 = mapBorder + 2;
			x = Math.max(x, guiLeft + mapBorder2);
			x = Math.min(x, guiLeft + mapXSize - mapBorder2 - rectWidth);
			y = Math.max(y, guiTop + mapBorder2);
			y = Math.min(y, guiTop + mapYSize - mapBorder2 - rectHeight);
			
			int stringX = x + rectWidth / 2;
			int stringY = y + border;
			
			GL11.glTranslatef(0F, 0F, 300F);
			drawRect(x, y, x + rectWidth, y + rectHeight, 0xC0000000);
			drawCenteredString(name, stringX, stringY, 0xFFFFFF);
			drawCenteredString(coords, stringX, stringY + stringHeight + border, 0xFFFFFF);	
			GL11.glTranslatef(0F, 0F, -300F);
		}
    }
    
    private int[] transformCoords(float x, float z)
    {
		x = (x / LOTRGenLayerWorld.scale) + LOTRGenLayerWorld.originX;
		z = (z / LOTRGenLayerWorld.scale) + LOTRGenLayerWorld.originZ;
		x -= posX;
		z -= posY;
		x *= zoomScale;
		z *= zoomScale;
		x += guiLeft + mapXSize / 2;
		z += guiTop + mapYSize / 2;
		return new int[] {Math.round(x), Math.round(z)};
    }
    
	@Override
    protected void keyTyped(char c, int i)
    {
		if (hasOverlay)
		{
			if (canCreateWaypoint && i == Keyboard.KEY_RETURN)
			{
				canCreateWaypoint = false;
				creatingWaypoint = true;
				overlayDisplayString = new String[]
				{
					StatCollector.translateToLocal("lotr.gui.map.customWaypoint.create.1"),
					"",
					"",
					"",
					"",
					"",
					StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.create.2", new Object[] {GameSettings.getKeyDisplayString(Keyboard.KEY_RETURN)})
				};
				return;
			}
			else if (creatingWaypoint && !nameWaypointTextField.getText().isEmpty() && i == Keyboard.KEY_RETURN)
			{
				String waypointName = nameWaypointTextField.getText();
				
				ByteBuf data = Unpooled.buffer();
				
				data.writeInt(mc.thePlayer.getEntityId());
				data.writeByte((byte)mc.thePlayer.dimension);
				
				byte[] bytes = waypointName.getBytes(Charsets.UTF_8);
				data.writeShort(bytes.length);
				data.writeBytes(bytes);
				
				C17PacketCustomPayload packet = new C17PacketCustomPayload("lotr.createCWP", data);
				mc.thePlayer.sendQueue.addToSendQueue(packet);
				
				closeOverlay();
				return;
			}
			else if (creatingWaypoint && nameWaypointTextField.textboxKeyTyped(c, i))
			{
				return;
			}
			else if (deletingWaypoint && i == Keyboard.KEY_RETURN)
			{
				ByteBuf data = Unpooled.buffer();
				
				data.writeInt(mc.thePlayer.getEntityId());
				data.writeByte((byte)mc.thePlayer.dimension);
				
				data.writeInt(selectedWaypoint.getID());
				
				C17PacketCustomPayload packet = new C17PacketCustomPayload("lotr.deleteCWP", data);
				mc.thePlayer.sendQueue.addToSendQueue(packet);
				
				closeOverlay();
				selectedWaypoint = null;
				return;
			}
			else if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())
			{
				closeOverlay();
				return;
			}
		}
		else
		{	
			if (i == LOTRKeyHandler.keyBindingFastTravel.getKeyCode() && isMiddleEarth() && selectedWaypoint != null && selectedWaypoint.hasPlayerUnlocked(mc.thePlayer) && LOTRLevelData.getData(mc.thePlayer).getFTTimer() <= 0)
			{
	        	ByteBuf data = Unpooled.buffer();
	        	
	        	data.writeInt(mc.thePlayer.getEntityId());
	        	data.writeByte((byte)mc.thePlayer.dimension);
	        	data.writeBoolean(selectedWaypoint instanceof LOTRWaypoint.Custom);
	        	data.writeInt(selectedWaypoint.getID());
	        	
	        	C17PacketCustomPayload packet = new C17PacketCustomPayload("lotr.fastTravel", data);
	        	mc.thePlayer.sendQueue.addToSendQueue(packet);
	
				mc.thePlayer.closeScreen();
			}
			else if (selectedWaypoint == null && i == LOTRKeyHandler.keyBindingMapTeleport.getKeyCode() && isMouseWithinMap && canTeleport())
			{
	        	ByteBuf data = Unpooled.buffer();
	        	
	        	data.writeInt(mc.thePlayer.getEntityId());
	        	data.writeByte((byte)mc.thePlayer.dimension);
	        	data.writeInt(mouseXCoord);
	        	data.writeInt(mouseZCoord);
	        	
	        	C17PacketCustomPayload packet = new C17PacketCustomPayload("lotr.mapTp", data);
	        	mc.thePlayer.sendQueue.addToSendQueue(packet);
	
				mc.thePlayer.closeScreen();
			}
	        else
			{
				super.keyTyped(c, i);
			}
		}
	}
	
	private void closeOverlay()
	{
		hasOverlay = false;
		overlayDisplayString = null;
		canCreateWaypoint = false;
		creatingWaypoint = false;
		deletingWaypoint = false;
	}
	
	@Override
    protected void mouseClicked(int i, int j, int k)
    {
		if (hasOverlay && creatingWaypoint)
		{
			nameWaypointTextField.mouseClicked(i, j, k);
		}
		
		if (!hasOverlay && k == 0 && isMiddleEarth() && selectedWaypoint instanceof LOTRWaypoint.Custom)
		{
			if (i >= guiLeft + delWPButtonX && i < guiLeft + delWPButtonX + delWPButtonWidth && j >= guiTop + delWPButtonY && j < guiTop + delWPButtonY + delWPButtonWidth)
			{
				hasOverlay = true;
				overlayDisplayString = new String[]
				{
					StatCollector.translateToLocal("lotr.gui.map.customWaypoint.delete.1"),
					StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.delete.2", selectedWaypoint.getDisplayName()),
					"",
					StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.delete.3", new Object[] {GameSettings.getKeyDisplayString(Keyboard.KEY_RETURN)})
				};
				deletingWaypoint = true;
				return;
			}
		}
		
		if (!hasOverlay && k == 0 && isMiddleEarth())
		{
			if (i >= guiLeft + addWPButtonX && i < guiLeft + addWPButtonX + addWPButtonWidth && j >= guiTop + addWPButtonY && j < guiTop + addWPButtonY + addWPButtonWidth)
			{
				hasOverlay = true;
				
				int waypoints = LOTRWaypoint.Custom.getWaypointList(mc.thePlayer).size();
				int maxWaypoints = LOTRWaypoint.Custom.getMaxAvailableToPlayer(mc.thePlayer);
				int remaining = maxWaypoints - waypoints;
				if (remaining <= 0)
				{
					overlayDisplayString = new String[]
					{
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.allUsed.1", new Object[] {maxWaypoints}),
						"",
						StatCollector.translateToLocal("lotr.gui.map.customWaypoint.allUsed.2"),
						StatCollector.translateToLocal("lotr.gui.map.customWaypoint.allUsed.3"),
						"",
						"",
						"",
						"",
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.unlockMore.1"),
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.unlockMore.2")
					};
				}
				else
				{
					overlayDisplayString = new String[]
					{
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.canCreate.1", new Object[] {waypoints, maxWaypoints}),
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.canCreate.2", new Object[] {remaining}),
						"",
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.canCreate.3", new Object[] {GameSettings.getKeyDisplayString(Keyboard.KEY_RETURN)}),
						StatCollector.translateToLocal("lotr.gui.map.customWaypoint.canCreate.4"),
						"",
						"",
						"",
						"",
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.unlockMore.1"),
						StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.unlockMore.2")
					};
					canCreateWaypoint = true;
				}
				
				return;
			}
		}
		
		if (!hasOverlay && k == 0)
		{
			if (i >= guiLeft + waypointToggleX && i < guiLeft + waypointToggleX + waypointToggleWidth && j >= guiTop + waypointToggleY && j < guiTop + waypointToggleY + waypointToggleWidth)
			{
				waypointMode.toggle();
				return;
			}
		}
		
        if (!hasOverlay && k == 0 && isMouseWithinMap)
        {
    		List waypoints = LOTRWaypoint.getListOfAllWaypoints(mc.thePlayer);
    		for (int l = 0; l < waypoints.size(); l++)
    		{
    			LOTRAbstractWaypoint waypoint = (LOTRAbstractWaypoint)waypoints.get(l);
    			if (waypointMode.canDisplayWaypoint(waypoint))
    			{
        	    	int[] pos = transformCoords(waypoint.getXCoord(), waypoint.getZCoord());
        	    	int x = pos[0];
        	    	int y = pos[1];
        	    	
					if (Math.abs(x - i) <= 3 && Math.abs(y - j) <= 3)
					{
						selectedWaypoint = waypoint;
						return;
					}
    			}
			}
			selectedWaypoint = null;
        }
		
		super.mouseClicked(i, j, k);
    }
	
	@Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
        
        if (!hasOverlay)
        {
	        int i = Mouse.getEventDWheel();

	        if (zoomTicks == 0)
	        {
				if (i < 0 && zoomPower > MIN_ZOOM)
				{
					prevZoomPower = zoomPower;
					zoomPower--;
					zoomTicks += maxZoomTicks;
					selectedWaypoint = null;
				}
		
				if (i > 0 && zoomPower < MAX_ZOOM)
				{
					prevZoomPower = zoomPower;
					zoomPower++;
					zoomTicks += maxZoomTicks;
					selectedWaypoint = null;
				}
	        }
        }
    }
	
	private boolean canTeleport()
	{
		if (!isMiddleEarth())
		{
			return false;
		}
		
		int chunkX = MathHelper.floor_double(mc.thePlayer.posX) >> 4;
		int chunkZ = MathHelper.floor_double(mc.thePlayer.posZ) >> 4;
		if (mc.theWorld.getChunkProvider().provideChunk(chunkX, chunkZ) instanceof EmptyChunk)
		{
			return false;
		}
		
		requestIsOp();
		return isPlayerOp;
	}
	
    private void requestIsOp()
    {
		if (mc.isSingleplayer())
		{
			MinecraftServer server = mc.getIntegratedServer();
			isPlayerOp = server.worldServers[0].getWorldInfo().areCommandsAllowed() && server.getServerOwner().equalsIgnoreCase(mc.thePlayer.getGameProfile().getName());
		}
		else
		{
			ByteBuf data = Unpooled.buffer();
			
			data.writeInt(mc.thePlayer.getEntityId());
			data.writeByte((byte)mc.thePlayer.dimension);
			
			C17PacketCustomPayload packet = new C17PacketCustomPayload("lotr.isOpReq", data);
			mc.thePlayer.sendQueue.addToSendQueue(packet);
		}
	}
    
    private boolean isMiddleEarth()
    {
    	return mc.thePlayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID;
    }
    
    public static void addPlayerLocationInfo(UUID player, String name, double x, double z)
    {
    	playerLocations.put(player, new PlayerLocationInfo(name, x, z));
    }
    
    private static class PlayerLocationInfo
    {
    	public String name;
    	public double posX;
    	public double posZ;
    	
    	public PlayerLocationInfo(String s, double x, double z)
    	{
    		name = s;
    		posX = x;
    		posZ = z;
    	}
    }
}
