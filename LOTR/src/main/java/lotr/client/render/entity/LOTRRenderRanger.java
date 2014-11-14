package lotr.client.render.entity;

import java.util.List;

import lotr.client.model.LOTRModelBiped;
import lotr.common.entity.npc.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class LOTRRenderRanger extends LOTRRenderBiped
{
	private static List rangerSkins;
	private static List ithilienSkins;
	
	public LOTRRenderRanger()
	{
		super(new LOTRModelBiped(), 0.5F);
		rangerSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/ranger/ranger");
		ithilienSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/ranger");
	}
	
	@Override
	public ResourceLocation getEntityTexture(Entity entity)
    {
		LOTREntityRanger ranger = (LOTREntityRanger)entity;
		if (ranger instanceof LOTREntityRangerIthilien)
		{
			return LOTRRandomSkins.getRandomSkin(ithilienSkins, ranger);
		}
		return LOTRRandomSkins.getRandomSkin(rangerSkins, ranger);
    }
	
	private void doRangerInvisibility()
	{
		GL11.glColor4f(1F, 1F, 1F, 0.15F);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
	}
	
	private void undoRangerInvisibility()
	{
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDepthMask(true);
	}
	
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float f)
	{
		if (((LOTREntityRanger)entity).isRangerSneaking())
		{
			doRangerInvisibility();
		}
	}
	
	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f)
	{
		int j = super.shouldRenderPass(entity, pass, f);
		if (j > 0 && ((LOTREntityRanger)entity).isRangerSneaking())
		{
			doRangerInvisibility();
		}
		return j;
	}
	
	@Override
	protected void renderEquippedItems(EntityLivingBase entity, float f)
	{
		if (((LOTREntityRanger)entity).isRangerSneaking())
		{
			doRangerInvisibility();
		}
		
		super.renderEquippedItems(entity, f);
		
		if (((LOTREntityRanger)entity).isRangerSneaking())
		{
			undoRangerInvisibility();
		}
	}
	
	@Override
	protected void renderNPCCape(LOTREntityNPC entity)
	{
		if (((LOTREntityRanger)entity).isRangerSneaking())
		{
			doRangerInvisibility();
		}
		
		super.renderNPCCape(entity);
		
		if (((LOTREntityRanger)entity).isRangerSneaking())
		{
			undoRangerInvisibility();
		}
	}
}
