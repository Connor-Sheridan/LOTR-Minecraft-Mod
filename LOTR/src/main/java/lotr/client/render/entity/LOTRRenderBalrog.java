package lotr.client.render.entity;

import java.util.List;

import lotr.client.model.LOTRModelBalrog;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class LOTRRenderBalrog extends RenderLiving
{
	private static List balrogSkins;
	
	private static ResourceLocation fireTexture = new ResourceLocation("lotr:mob/balrog/fire.png");
	private LOTRModelBalrog balrogModel;
	private LOTRModelBalrog fireModel = new LOTRModelBalrog(0F);
	
    public LOTRRenderBalrog()
    {
        super(new LOTRModelBalrog(), 0.5F);
        balrogModel = (LOTRModelBalrog)mainModel;
        balrogSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog");
        
        fireModel.setFireModel();
    }
	
	@Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
		return LOTRRandomSkins.getRandomSkin(balrogSkins, (LOTREntityBalrog)entity);
    }
	
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
	{
		LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
		ItemStack heldItem = balrog.getHeldItem();
		balrogModel.heldItemRight = fireModel.heldItemRight = (heldItem == null ? 0 : 2);
		
		super.doRender(balrog, d, d1, d2, f, f1);
	}
	
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float f)
	{
		LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
		
		float scale = 2F;
		GL11.glScalef(scale, scale, scale);
		
		if (balrog.isWreathedInFlame())
		{
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		}
	}
	
	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int pass, float f)
    {
		LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
		if (balrog.isWreathedInFlame())
		{
	        if (pass == 1)
	        {
	        	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
	            float f1 = (float)entity.ticksExisted + f;
	            bindTexture(fireTexture);
	            GL11.glMatrixMode(GL11.GL_TEXTURE);
	            GL11.glLoadIdentity();
	            float f2 = f1 * 0.01F;
	            float f3 = f1 * 0.01F;
	            GL11.glTranslatef(f2, f3, 0F);
	            setRenderPassModel(fireModel);
	            GL11.glMatrixMode(GL11.GL_MODELVIEW);
	            GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
	            GL11.glEnable(GL11.GL_BLEND);
	    		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
	            float alpha = 0.3F + MathHelper.sin(f1 * 0.05F) * 0.15F;
	            GL11.glColor4f(alpha, alpha, alpha, 1F);
	            GL11.glDisable(GL11.GL_LIGHTING);
	            GL11.glDepthMask(false);
	            return 1;
	        }
	
	        if (pass == 2)
	        {
	            GL11.glMatrixMode(GL11.GL_TEXTURE);
	            GL11.glLoadIdentity();
	            GL11.glMatrixMode(GL11.GL_MODELVIEW);
	            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	            GL11.glDisable(GL11.GL_BLEND);
	            GL11.glEnable(GL11.GL_LIGHTING);
	            GL11.glDepthMask(true);
	        }
		}

        return -1;
    }
	
	@Override
	protected void renderEquippedItems(EntityLivingBase entity, float f)
	{
        GL11.glColor3f(1F, 1F, 1F);

        ItemStack heldItem = entity.getHeldItem();
        if (heldItem != null)
        {
            GL11.glPushMatrix();

            balrogModel.body.postRender(0.0625F);
            balrogModel.rightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.25F, 1.5F, -0.125F);
            float scale = 1.25F;
            GL11.glScalef(scale, -scale, scale);
            GL11.glRotatef(-100F, 1F, 0F, 0F);
            GL11.glRotatef(45F, 0F, 1F, 0F);

            renderManager.itemRenderer.renderItem(entity, heldItem, 0);

            if (heldItem.getItem().requiresMultipleRenderPasses())
            {
                for (int x = 1; x < heldItem.getItem().getRenderPasses(heldItem.getItemDamage()); x++)
                {
                    renderManager.itemRenderer.renderItem(entity, heldItem, x);
                }
            }

            GL11.glPopMatrix();
        }
	}
}
