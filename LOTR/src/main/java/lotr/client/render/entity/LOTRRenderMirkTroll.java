package lotr.client.render.entity;

import java.util.List;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMirkTroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMirkTroll extends LOTRRenderTroll
{
	private static List mirkSkins;
	private static List mirkArmorSkins;
	
    public LOTRRenderMirkTroll()
    {
        super();
        mirkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll");
        mirkArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll_armor");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
		return LOTRRandomSkins.getRandomSkin(mirkSkins, (LOTREntityMirkTroll)entity);
    }
	
	@Override
	protected void renderTrollWeapon(EntityLivingBase entity, float f)
	{
		((LOTRModelTroll)mainModel).renderBattleaxe(0.0625F);
	}

	@Override
	protected void bindTrollOutfitTexture(EntityLivingBase entity)
	{
		bindTexture(LOTRRandomSkins.getRandomSkin(mirkArmorSkins, (LOTREntityMirkTroll)entity));
	}
}
