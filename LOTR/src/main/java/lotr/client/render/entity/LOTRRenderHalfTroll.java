package lotr.client.render.entity;

import java.util.List;

import lotr.client.model.LOTRModelHalfTroll;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHalfTroll extends LOTRRenderBiped
{
	private static List halfTrollSkins;
	
	public LOTRRenderHalfTroll()
	{
		super(new LOTRModelHalfTroll(), 0.5F);
		halfTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/halfTroll/halfTroll");
	}
	
	@Override
	protected void func_82421_b()
    {
        field_82423_g = new LOTRModelHalfTroll(1F);
        field_82425_h = new LOTRModelHalfTroll(0.5F);
    }
	
	@Override
	public ResourceLocation getEntityTexture(Entity entity)
    {
		LOTREntityHalfTroll halfTroll = (LOTREntityHalfTroll)entity;
        return LOTRRandomSkins.getRandomSkin(halfTrollSkins, halfTroll);
    }
}
