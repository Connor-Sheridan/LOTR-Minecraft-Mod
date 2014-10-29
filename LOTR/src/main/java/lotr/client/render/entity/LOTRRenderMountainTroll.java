package lotr.client.render.entity;

import java.util.List;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class LOTRRenderMountainTroll extends LOTRRenderTroll
{
	private static List mountainTrollSkins;
	
	private LOTREntityThrownRock heldRock;
	
    public LOTRRenderMountainTroll()
    {
        super();
        mountainTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mountainTroll");
    }
	
	@Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
		return LOTRRandomSkins.getRandomSkin(mountainTrollSkins, (LOTREntityTroll)entity);
    }
	
	@Override
	protected void renderTrollWeapon(EntityLivingBase entity, float f)
	{
		LOTREntityMountainTroll troll = (LOTREntityMountainTroll)entity;
		if (troll.isThrowingRocks())
		{
			if (((LOTRModelTroll)mainModel).onGround <= 0F)
			{
				if (heldRock == null)
				{
					heldRock = new LOTREntityThrownRock(troll.worldObj);
				}
				heldRock.setWorld(troll.worldObj);
				heldRock.setPosition(troll.posX, troll.posY, troll.posZ);
				((LOTRModelTroll)mainModel).rightArm.postRender(0.0625F);
				GL11.glTranslatef(0.375F, 1.5F, 0F);
				GL11.glRotatef(45F, 0F, 1F, 0F);
				scaleTroll(troll, true);
				renderManager.renderEntityWithPosYaw(heldRock, 0D, 0D, 0D, 0F, f);
			}
		}
		else
		{
			((LOTRModelTroll)mainModel).renderWoodenClubWithSpikes(0.0625F);
		}
	}
}
