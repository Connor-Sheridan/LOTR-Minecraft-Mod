package lotr.client.render.entity;

import java.util.List;

import lotr.client.model.LOTRModelOrc;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.npc.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class LOTRRenderOrc extends LOTRRenderBiped
{
	private static List orcSkins;
	private static List urukSkins;
	
	public LOTRRenderOrc()
	{
		super(new LOTRModelOrc(), 0.5F);
		orcSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/orc");
		urukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/urukHai");
	}
	
	@Override
	protected void func_82421_b()
    {
        field_82423_g = new LOTRModelOrc(1F);
        field_82425_h = new LOTRModelOrc(0.5F);
    }
	
	@Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
		LOTREntityOrc orc = (LOTREntityOrc)entity;
        if (entity instanceof LOTREntityUrukHai)
		{
			return LOTRRandomSkins.getRandomSkin(urukSkins, orc);
		}
        return LOTRRandomSkins.getRandomSkin(orcSkins, orc);
    }
	
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float f)
	{
		if (!((LOTREntityOrc)entity).isWeakOrc)
		{
			return;
		}
		GL11.glScalef(0.85F, 0.85F, 0.85F);
	}
}
