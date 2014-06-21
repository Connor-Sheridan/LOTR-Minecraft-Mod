package lotr.common.entity.ai;

import java.util.ArrayList;
import java.util.List;

import lotr.common.LOTRAlignmentValues;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class LOTREntityAINPCAvoidEvilPlayer extends EntityAIBase
{
    private LOTREntityNPC theNPC;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;

    public LOTREntityAINPCAvoidEvilPlayer(LOTREntityNPC npc, float f, double d, double d1)
    {
        theNPC = npc;
        distanceFromEntity = f;
        farSpeed = d;
        nearSpeed = d1;
        entityPathNavigate = npc.getNavigator();
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
		List validPlayers = new ArrayList();
		List list = theNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theNPC.boundingBox.expand((double)distanceFromEntity, (double)distanceFromEntity / 2D, (double)distanceFromEntity));
		if (list.isEmpty())
		{
			return false;
		}
		for (int i = 0; i < list.size(); i++)
		{
			EntityPlayer entityplayer = (EntityPlayer)list.get(i);
			if (entityplayer.capabilities.isCreativeMode)
			{
				continue;
			}
			int alignment = LOTRLevelData.getAlignment(entityplayer, theNPC.getFaction());
			if ((theNPC.familyInfo.getNPCAge() < 0 && alignment < 0) || (theNPC instanceof LOTREntityHobbit && alignment <= LOTRAlignmentValues.HOBBIT_FLEE))
			{
				validPlayers.add(entityplayer);
			}
		}

		if (validPlayers.isEmpty())
		{
			return false;
		}
		closestLivingEntity = (Entity)validPlayers.get(0);

        Vec3 fleePath = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theNPC, 16, 7, theNPC.worldObj.getWorldVec3Pool().getVecFromPool(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));
        if (fleePath == null)
        {
            return false;
        }
        else if (closestLivingEntity.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < closestLivingEntity.getDistanceSqToEntity(theNPC))
        {
            return false;
        }
        else
        {
            entityPathEntity = entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
            return entityPathEntity == null ? false : entityPathEntity.isDestinationSame(fleePath);
        }
    }

    @Override
    public boolean continueExecuting()
    {
        return !entityPathNavigate.noPath();
    }

    @Override
    public void startExecuting()
    {
        entityPathNavigate.setPath(entityPathEntity, farSpeed);
    }

    @Override
    public void resetTask()
    {
        closestLivingEntity = null;
    }

    @Override
    public void updateTask()
    {
        if (theNPC.getDistanceSqToEntity(closestLivingEntity) < 49.0D)
        {
            theNPC.getNavigator().setSpeed(nearSpeed);
        }
        else
        {
            theNPC.getNavigator().setSpeed(farSpeed);
        }
    }
}
