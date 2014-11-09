package lotr.client.render.tileentity;

import lotr.common.tileentity.LOTRTileEntityUtumnoReturnPortal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class LOTRRenderUtumnoReturnPortal extends TileEntitySpecialRenderer
{
	private static ResourceLocation lightCircle = new ResourceLocation("lotr:misc/utumnoPortal_lightCircle.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	{
		LOTRTileEntityUtumnoReturnPortal portal = (LOTRTileEntityUtumnoReturnPortal)tileentity;
		World world = portal.getWorldObj();
		world.theProfiler.startSection("utumnoReturnPortal");
		float renderTime = portal.ticksExisted + f;
		
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
		
		float alpha = 0.2F + 0.15F * MathHelper.sin(renderTime * 0.1F);
		int passes = 12;
		for (int i = 0; i < passes; i++)
		{
			GL11.glPushMatrix();

			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1F, 1F, 1F, alpha);
			double width = (float)(i + 1) / (float)passes * 0.99F;
			width /= 2D;

			double bottom = d1;
			double top = bottom + LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP;
			
			tessellator.addVertexWithUV(width, bottom, width, 0D, 0D);
			tessellator.addVertexWithUV(width, top, width, 0D, 0D);
			tessellator.addVertexWithUV(-width, top, width, 0D, 0D);
			tessellator.addVertexWithUV(-width, bottom, width, 0D, 0D);
			
			tessellator.addVertexWithUV(width, bottom, -width, 0D, 0D);
			tessellator.addVertexWithUV(width, top, -width, 0D, 0D);
			tessellator.addVertexWithUV(-width, top, -width, 0D, 0D);
			tessellator.addVertexWithUV(-width, bottom, -width, 0D, 0D);
			
			tessellator.addVertexWithUV(width, bottom, width, 0D, 0D);
			tessellator.addVertexWithUV(width, top, width, 0D, 0D);
			tessellator.addVertexWithUV(width, top, -width, 0D, 0D);
			tessellator.addVertexWithUV(width, bottom, -width, 0D, 0D);
			
			tessellator.addVertexWithUV(-width, bottom, width, 0D, 0D);
			tessellator.addVertexWithUV(-width, top, width, 0D, 0D);
			tessellator.addVertexWithUV(-width, top, -width, 0D, 0D);
			tessellator.addVertexWithUV(-width, bottom, -width, 0D, 0D);
			
			tessellator.draw();
			
			GL11.glPopMatrix();
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		bindTexture(lightCircle);
		
		int circles = 12;
		for (int i = 0; i < circles; i++)
		{
			alpha = 0.15F + 0.1F * MathHelper.sin(renderTime * 0.1F + i);
			GL11.glColor4f(1F, 1F, 1F, alpha);
			
			double width = 1.5F + 1.5F * MathHelper.sin(renderTime * 0.05F + i);
			double height = 0.01D + i * 0.01D;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(width, height, width, 1D, 1D);
			tessellator.addVertexWithUV(width, height, -width, 1D, 0D);
			tessellator.addVertexWithUV(-width, height, -width, 0D, 0D);
			tessellator.addVertexWithUV(-width, height, width, 0D, 1D);
			tessellator.draw();
		}
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		
		world.theProfiler.endSection();
	}
}
