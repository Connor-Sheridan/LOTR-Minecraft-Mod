package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTREntityUtumnoTroll extends LOTREntityTroll
{
	public LOTREntityUtumnoTroll(World world)
	{
		super(world);
	}
	
	@Override
	public float getTrollScale()
	{
		return 1.5F;
	}
	
	@Override
	public EntityAIBase getTrollAttackAI()
	{
		return new LOTREntityAIAttackOnCollide(this, 2D, false, 0.8F);
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
        getEntityAttribute(npcAttackDamage).setBaseValue(7D);
    }
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.UTUMNO;
	}
	
	@Override
	protected boolean hasTrollName()
	{
		return false;
	}
	
	@Override
	protected boolean canTrollBeTickled(EntityPlayer entityplayer)
	{
		return false;
	}
	
	@Override
	public void onTrollDeathBySun()
	{
		worldObj.playSoundAtEntity(this, "lotr:troll.transform", getSoundVolume(), getSoundPitch());
		worldObj.setEntityState(this, (byte)15);
		setDead();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte b)
	{
		if (b == 15)
		{
			super.handleHealthUpdate(b);
			for (int l = 0; l < 64; l++)
			{
				LOTRMod.proxy.spawnParticle("largeStone", posX + rand.nextGaussian() * (double)width * 0.5D, posY + rand.nextDouble() * (double)height, posZ + rand.nextGaussian() * (double)width * 0.5D, 0D, 0D, 0D);
			}
		}
		else
		{
			super.handleHealthUpdate(b);
		}
	}
	
	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killUtumnoTroll;
	}
	
	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer)
    {
        return 5 + rand.nextInt(6);
    }
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		return null;
	}
}
