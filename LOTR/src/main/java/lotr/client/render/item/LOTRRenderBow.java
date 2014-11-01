package lotr.client.render.item;

import java.util.HashMap;
import java.util.Map;

import lotr.client.LOTRClientProxy;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBow;
import lotr.common.item.LOTRItemBow.BowState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LOTRRenderBow implements IItemRenderer
{
	private static Map<Pair<LOTRItemBow, BowState>, ResourceLocation> largeBowTextures = new HashMap();
	
	private static ResourceLocation getLargeBowTexture(LOTRItemBow item, BowState bowState)
	{
		Pair<LOTRItemBow, BowState> pair = Pair.of(item, bowState);
		ResourceLocation texture = largeBowTextures.get(pair);
		if (texture == null)
		{
			String prefix = LOTRMod.getModID() + ":";
			String itemName = item.getUnlocalizedName();
			itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
			String s = prefix + "textures/items/large/" + itemName + bowState.iconName + ".png";
			texture = new ResourceLocation(s);
			largeBowTextures.put(pair, texture);
		}
		return texture;
	}
	
	private boolean isLargeBow;

	public LOTRRenderBow(boolean large)
	{
		isLargeBow = large;
	}
	
	@Override
    public boolean handleRenderType(ItemStack itemstack, ItemRenderType type)
	{
		return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}
    
	@Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper)
	{
		return false;
	}
    
	@Override
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data)
	{
		GL11.glPushMatrix();
		
		EntityLivingBase entity = (EntityLivingBase)data[1];
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 || entity != Minecraft.getMinecraft().thePlayer)
		{
			GL11.glTranslatef(0.9375F, 0.0625F, 0F);
			GL11.glRotatef(-335F, 0F, 0F, 1F);
			GL11.glRotatef(-50F, 0F, 1F, 0F);
			GL11.glScalef(1F / 1.5F, 1F / 1.5F, 1F / 1.5F);
			GL11.glTranslatef(0F, 0.3F, 0F);
			
			GL11.glRotatef(-20F, 0F, 0F, 1F);
			GL11.glRotatef(90F, 1F, 0F, 0F);
			GL11.glRotatef(-60F, 0F, 0F, 1F);
			GL11.glScalef(1F / 0.375F, 1F / 0.375F, 1F / 0.375F);
			GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
			
			GL11.glTranslatef(0F, 0.125F, 0.3125F);
			GL11.glRotatef(-20F, 0F, 1F, 0F);
			GL11.glScalef(0.625F, -0.625F, 0.625F);
			GL11.glRotatef(-100F, 1F, 0F, 0F);
			GL11.glRotatef(45F, 0F, 1F, 0F);
			
			GL11.glTranslatef(0F, -0.3F, 0F);
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glRotatef(50F, 0F, 1F, 0F);
			GL11.glRotatef(335F, 0F, 0F, 1F);
			GL11.glTranslatef(-0.9375F, -0.0625F, 0F);
		}
		
		if (isLargeBow)
		{
			GL11.glTranslatef(-0.5F, -0.5F, 0F);
			GL11.glScalef(2F, 2F, 1F);
		}
		
		Tessellator tessellator = Tessellator.instance;
		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		
		if (isLargeBow)
		{
			ResourceLocation texture = null;
	
			Item item = itemstack.getItem();
			if (item instanceof LOTRItemBow)
			{
				LOTRItemBow bow = (LOTRItemBow)item;
				BowState bowState = BowState.HELD;
				
				if (entity instanceof EntityPlayer)
				{
					EntityPlayer entityplayer = (EntityPlayer)entity;
					ItemStack usingItem = entityplayer.getItemInUse();
					int useCount = entityplayer.getItemInUseCount();
					bowState = bow.getBowState(entityplayer, usingItem, useCount);
				}
				else
				{
					bowState = bow.getBowState(entity, itemstack, 0);
				}

				texture = getLargeBowTexture(bow, bowState);
		
				textureManager.bindTexture(texture);
				GL11.glColor4f(1F, 1F, 1F, 1F);
				ItemRenderer.renderItemIn2D(tessellator, 1F, 0F, 0F, 1F, 32, 32, 0.0625F);
			}
			else
			{
				throw new RuntimeException("Attempting to render a large bow which is not a bow");
			}
		}
		else
		{
			IIcon icon = ((EntityLivingBase)data[1]).getItemIcon(itemstack, 0);
			icon = RenderBlocks.getInstance().getIconSafe(icon);
	
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			int width = icon.getIconWidth();
			int height = icon.getIconWidth();
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625F);
		}
		
		if (itemstack != null && itemstack.hasEffect(0))
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			textureManager.bindTexture(LOTRClientProxy.enchantmentTexture);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			float f = 0.76F;
			GL11.glColor4f(0.5F * f, 0.25F * f, 0.8F * f, 1F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float f1 = 0.125F;
			GL11.glScalef(f1, f1, f1);
			float f2 = (float)(System.currentTimeMillis() % 3000L) / 3000F * 8F;
			GL11.glTranslatef(f2, 0F, 0F);
			GL11.glRotatef(-50F, 0F, 0F, 1F);
			ItemRenderer.renderItemIn2D(tessellator, 0F, 0F, 1F, 1F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(f1, f1, f1);
			f2 = (float)(System.currentTimeMillis() % 4873L) / 4873F * 8F;
			GL11.glTranslatef(-f2, 0F, 0F);
			GL11.glRotatef(10F, 0F, 0F, 1F);
			ItemRenderer.renderItemIn2D(tessellator, 0F, 0F, 1F, 1F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
