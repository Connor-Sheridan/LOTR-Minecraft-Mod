package lotr.client.render.entity;

import java.util.List;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderUtumnoTroll extends LOTRRenderTroll
{
	private static List utumnoTrollSkins;

    public LOTRRenderUtumnoTroll()
    {
        super();
        utumnoTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/utumno");
    }
	
	@Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
		return LOTRRandomSkins.getRandomSkin(utumnoTrollSkins, (LOTREntityTroll)entity);
    }
	
	@Override
	protected void renderTrollWeapon(EntityLivingBase entity, float f)
	{
		((LOTRModelTroll)mainModel).renderWoodenClubWithSpikes(0.0625F);
	}
}
