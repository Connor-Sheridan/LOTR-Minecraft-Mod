package lotr.client.render.item;

import java.util.HashMap;

import lotr.client.LOTRClientProxy;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class LOTRRenderLargeItem implements IItemRenderer
{
	private static HashMap largeItemTextures = new HashMap();
	
	public static ResourceLocation getLargeItemTexture(Item item)
	{
		String prefix = LOTRMod.getModID() + ":";
		String itemName = item.getUnlocalizedName();
		String s = prefix + "textures/items/large/" + itemName.substring(itemName.indexOf(prefix) + prefix.length()) + ".png";
		return new ResourceLocation(s);
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
		
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemSpear && data[1] instanceof EntityPlayer && ((EntityPlayer)data[1]).getItemInUse() == itemstack)
		{
			GL11.glRotatef(260F, 0F, 0F, 1F);
			GL11.glTranslatef(-1F, 0F, 0F);
		}
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
		GL11.glScalef(2F, 2F, 1F);
		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		ResourceLocation texture = null;
		
		
		if (largeItemTextures.get(item) != null)
		{
			texture = (ResourceLocation)largeItemTextures.get(item);
		}
		else
		{
			texture = getLargeItemTexture(item);
			largeItemTextures.put(item, texture);
		}

		textureManager.bindTexture(texture);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Tessellator tessellator = Tessellator.instance;
		ItemRenderer.renderItemIn2D(tessellator, 1F, 0F, 0F, 1F, 32, 32, 0.0625F);

		if (itemstack != null && itemstack.hasEffect(0))
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			textureManager.bindTexture(LOTRClientProxy.enchantmentTexture);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			float f = 0.76F;
			GL11.glColor4f(0.5F * f, 0.25F * f, 0.8F * f, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float f1 = 0.125F;
			GL11.glScalef(f1, f1, f1);
			float f2 = (float)(System.currentTimeMillis() % 3000L) / 3000.0F * 8.0F;
			GL11.glTranslatef(f2, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(f1, f1, f1);
			f2 = (float)(System.currentTimeMillis() % 4873L) / 4873.0F * 8.0F;
			GL11.glTranslatef(-f2, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
		
		GL11.glPopMatrix();
	}
}
