package lotr.client.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.UUID;

import lotr.common.LOTRLevelData;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Charsets;
import com.mojang.authlib.*;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

public class LOTRGuiBanner extends LOTRGuiScreenBase
{
	private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/banner_edit.png");

	private LOTREntityBanner theBanner;
	public String[] usernamesReceived = new String[LOTREntityBanner.MAX_PLAYERS];
	
	public int xSize = 200;
    public int ySize = 240;
    private int guiLeft;
    private int guiTop;
    private boolean firstInit = true;
    
    private GuiButton modeButton;
    private LOTRGuiButtonOptions selfProtectionButton;
    
    private LOTRGuiSlider alignmentSlider;
    
    private static final int displayedPlayers = 5;
    private GuiTextField[] allowedPlayers = new GuiTextField[LOTREntityBanner.MAX_PLAYERS];
    private boolean[] invalidUsernames = new boolean[LOTREntityBanner.MAX_PLAYERS];
    
    private float currentScroll = 0F;
    private boolean isScrolling = false;
	private boolean wasMouseDown;
    
    private int scrollBarWidth = 14;
	private int scrollBarHeight = 120;
	private int scrollBarX = 180;
	private int scrollBarY = 68;
	private int scrollBarBorder = 1;
	private int scrollWidgetWidth = 12;
	private int scrollWidgetHeight = 17;

	public LOTRGuiBanner(LOTREntityBanner banner)
	{
		theBanner = banner;
	}

	@Override
    public void initGui()
    {
        guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonList.add((modeButton = new GuiButton(0, guiLeft + xSize / 2 - 80, guiTop + 20, 160, 20, "")));
		buttonList.add((alignmentSlider = new LOTRGuiSlider(1, guiLeft + xSize / 2 - 80, guiTop + 80, 160, 20, StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction.alignment"), 0F)));
		buttonList.add((selfProtectionButton = new LOTRGuiButtonOptions(1, guiLeft + xSize / 2 - 80, guiTop + 200, 160, 20, StatCollector.translateToLocal("lotr.gui.bannerEdit.selfProtection"))));
		alignmentSlider.setSliderValue(theBanner.getAlignmentProtection(), LOTREntityBanner.ALIGNMENT_PROTECTION_MIN, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
		
		for (int i = 0; i < allowedPlayers.length; i++)
		{
			GuiTextField textBox = new GuiTextField(fontRendererObj, 0, 0, 140, 20);
			
			if (firstInit)
			{
				if (usernamesReceived[i] != null)
				{
					textBox.setText(usernamesReceived[i]);
				}
			}
			else
			{
				GuiTextField prevTextBox = allowedPlayers[i];
				if (prevTextBox != null)
				{
					String prevText = prevTextBox.getText();
					if (prevText != null)
					{
						textBox.setText(prevText);
					}
				}
			}
			
			allowedPlayers[i] = textBox;
		}
		
		allowedPlayers[0].setEnabled(false);
		
		if (firstInit)
		{
			firstInit = false;
		}
	}

	@Override
	public void drawScreen(int i, int j, float f)
	{
		setupScrollBar(i, j);
		
		for (int l = 0; l < allowedPlayers.length; l++)
		{
			GuiTextField textBox = allowedPlayers[l];
			textBox.setVisible(false);
			textBox.setEnabled(false);
		}
		
		drawDefaultBackground();

		mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		String title = StatCollector.translateToLocal("lotr.gui.bannerEdit.title");
		fontRendererObj.drawString(title, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(title) / 2, guiTop + 6, 0x404040);

		if (theBanner.isPlayerSpecificProtection())
		{
			modeButton.displayString = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific");

			String s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.1");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46, 0x404040);

			s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.2");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT, 0x404040);
			
			int start = 0 + Math.round(currentScroll * (allowedPlayers.length - displayedPlayers));
			int end = start + displayedPlayers - 1;
			start = Math.max(start, 0);
			end = Math.min(end, allowedPlayers.length - 1);
			
			for (int index = start; index <= end; index++)
			{
				int displayIndex = index - start;
				
				GuiTextField textBox = allowedPlayers[index];
				textBox.setVisible(true);
				textBox.setEnabled(index != 0);
				
				textBox.xPosition = guiLeft + xSize / 2 - 70;
				textBox.yPosition = guiTop + 70 + (displayIndex * (textBox.height + 4));
				textBox.drawTextBox();
				
				String number = (index + 1) + ".";
				fontRendererObj.drawString(number, guiLeft + 24 - fontRendererObj.getStringWidth(number), textBox.yPosition + 6, 0x404040);
			}
			
			if (hasScrollBar())
			{
				mc.getTextureManager().bindTexture(guiTexture);
				GL11.glColor4f(1F, 1F, 1F, 1F);
				drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 200, 0, scrollBarWidth, scrollBarHeight);
				
				if (canScroll())
				{
					int scroll = (int)(currentScroll * (scrollBarHeight - (scrollBarBorder) * 2 - scrollWidgetHeight));
					drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder, guiTop + scrollBarY + scrollBarBorder + scroll, 214, 0, scrollWidgetWidth, scrollWidgetHeight);
				}
			}
		}
		else
		{
			modeButton.displayString = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction");

			String s = StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.protectionMode.faction.desc.1", new Object[] {theBanner.getAlignmentProtection(), theBanner.getBannerFaction().factionName()});
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46, 0x404040);

			s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction.desc.2");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT, 0x404040);
		}
		
		super.drawScreen(i, j, f);
	}

	@Override
    public void updateScreen()
    {
		super.updateScreen();
		
		selfProtectionButton.setState(theBanner.isSelfProtection());
		
		if (alignmentSlider.enabled)
		{
			int playerAlignment = LOTRLevelData.getData(mc.thePlayer).getAlignment(theBanner.getBannerFaction());
			int alignment = alignmentSlider.getSliderValue(LOTREntityBanner.ALIGNMENT_PROTECTION_MIN, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
			if (alignment > playerAlignment)
			{
				alignment = playerAlignment;
				alignmentSlider.setSliderValue(alignment, LOTREntityBanner.ALIGNMENT_PROTECTION_MIN, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
			}
		}

		alignmentSlider.visible = !theBanner.isPlayerSpecificProtection();
		
		if (alignmentSlider.dragging)
		{
			int prevAlignment = theBanner.getAlignmentProtection();
			int alignment = alignmentSlider.getSliderValue(LOTREntityBanner.ALIGNMENT_PROTECTION_MIN, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
			
			theBanner.setAlignmentProtection(alignment);
			alignmentSlider.setState(String.valueOf(alignment));
			
			if (alignment != prevAlignment)
			{
				sendBannerData();
			}
		}
		
		for (int l = 0; l < allowedPlayers.length; l++)
		{
			GuiTextField textBox = allowedPlayers[l];
			textBox.updateCursorCounter();
		}
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
        }
	}
	
	private boolean hasScrollBar()
	{
		return theBanner.isPlayerSpecificProtection();
	}
	
	private boolean canScroll()
	{
		return true;
	}
	
	@Override
    protected void keyTyped(char c, int i)
    {
		for (int l = 1; l < allowedPlayers.length; l++)
		{
			GuiTextField textBox = allowedPlayers[l];
			if (textBox.getVisible() && textBox.textboxKeyTyped(c, i))
			{
				return;
			}
		}

        super.keyTyped(c, i);
    }

	@Override
	protected void mouseClicked(int i, int j, int k)
    {
		super.mouseClicked(i, j, k);

		for (int l = 1; l < allowedPlayers.length; l++)
		{
			GuiTextField textBox = allowedPlayers[l];
			if (textBox.getVisible())
			{
				textBox.mouseClicked(i, j, k);

				String text = textBox.getText();
				if (!textBox.isFocused() && !StringUtils.isEmpty(text) && !invalidUsernames[l])
				{
					UUID uuid = UUIDFromUsername(text);
					if (uuid == null)
					{
						invalidUsernames[l] = true;
						textBox.setTextColor(0xFF0000);
						textBox.setText(StatCollector.translateToLocal("lotr.gui.bannerEdit.invalidUsername"));
					}
				}

				if (textBox.isFocused() && invalidUsernames[l])
				{
					invalidUsernames[l] = false;
					textBox.setTextColor(0xFFFFFF);
					textBox.setText("");
				}
			}
		}
	}
	
	@Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
        
        int i = Mouse.getEventDWheel();
        if (i != 0 && canScroll())
        {
            int j = allowedPlayers.length - displayedPlayers;

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

	@Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
			if (button == modeButton)
			{
				theBanner.setPlayerSpecificProtection(!theBanner.isPlayerSpecificProtection());
			}
			
			if (button == selfProtectionButton)
			{
				theBanner.setSelfProtection(!theBanner.isSelfProtection());
			}
		}
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		sendBannerData();
	}
	
	private void sendBannerData()
	{
		ByteBuf data = Unpooled.buffer();

		data.writeInt(theBanner.getEntityId());
		data.writeByte(theBanner.worldObj.provider.dimensionId);
		data.writeBoolean(theBanner.isPlayerSpecificProtection());
		data.writeBoolean(theBanner.isSelfProtection());
		data.writeInt(theBanner.getAlignmentProtection());

		for (int index = 1; index < allowedPlayers.length; index++)
		{
			if (!invalidUsernames[index])
			{
				data.writeInt(index);
				
				String text = allowedPlayers[index].getText();
				if (StringUtils.isEmpty(text))
				{
					data.writeByte(-1);
				}
				else
				{
					data.writeByte(text.length());
					data.writeBytes(text.getBytes(Charsets.UTF_8));
				}
			}
		}
		data.writeInt(-1);

		Packet packet = new C17PacketCustomPayload("lotr.editBanner", data);
		mc.thePlayer.sendQueue.addToSendQueue(packet);
	}

	private UUID UUIDFromUsername(String name)
	{
		GameProfile profile = GameProfileLookup.getProfileByName(name);
		return profile == null ? null : profile.getId();
	}
	
	private static class GameProfileLookup
	{
		private static Minecraft theMC = Minecraft.getMinecraft();
		private static YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(theMC.getProxy(), UUID.randomUUID().toString());
		private static GameProfileRepository profileRepo = authService.createProfileRepository();
		
		public static GameProfile getProfileByName(String name)
		{
			final GameProfile[] foundProfiles = new GameProfile[1];
			
	        ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback()
	        {
	            public void onProfileLookupSucceeded(GameProfile profile)
	            {
	            	foundProfiles[0] = profile;
	            }
	            public void onProfileLookupFailed(GameProfile profile, Exception exception)
	            {
	            	foundProfiles[0] = null;
	            }
	        };
	        
	        profileRepo.findProfilesByNames(new String[] {name}, Agent.MINECRAFT, profilelookupcallback);

	        return foundProfiles[0];
		}
	}
}