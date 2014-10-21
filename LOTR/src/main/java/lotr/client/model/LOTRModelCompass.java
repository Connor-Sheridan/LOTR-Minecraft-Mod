package lotr.client.model;

import lotr.client.LOTRClientProxy;
import lotr.client.render.entity.LOTRRenderPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LOTRModelCompass extends ModelBase
{
	public static LOTRModelCompass compassModel = new LOTRModelCompass();
	private static ResourceLocation compassTexture = new ResourceLocation("lotr:misc/compass.png");
			
	private ModelRenderer compass;
	private ModelBase ringModel = new LOTRModelPortal(0);
	private ModelBase writingModelOuter = new LOTRModelPortal(1);
	private ModelBase writingModelInner = new LOTRModelPortal(1);
	
	private LOTRModelCompass()
	{
		textureWidth = 32;
		textureHeight = 32;
		
		compass = new ModelRenderer(this, 0, 0);
		compass.setRotationPoint(0F, 0F, 0F);
		compass.addBox(-16F, 0F, -16F, 32, 0, 32);
	}
	
	public void render(float scale, float rotation)
	{
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glNormal3f(0F, 0F, 0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(1F, 1F, -1F);

		GL11.glRotatef(40F, 1F, 0F, 0F);
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		
		texturemanager.bindTexture(compassTexture);
		compass.render(scale * 2F);
		
		texturemanager.bindTexture(LOTRRenderPortal.ringTexture);
		ringModel.render(null, 0F, 0F, 0F, 0F, 0F, scale);

		texturemanager.bindTexture(LOTRRenderPortal.writingTexture);
		writingModelOuter.render(null, 0F, 0F, 0F, 0F, 0F, scale * 1.05F);
		texturemanager.bindTexture(LOTRRenderPortal.writingTexture);
		writingModelInner.render(null, 0F, 0F, 0F, 0F, 0F, scale * 0.85F);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
