package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUtumnoOrc extends LOTREntityOrc
{
	public LOTREntityUtumnoOrc(World world)
	{
		super(world);
		setSize(0.6F, 1.8F);
		isWeakOrc = false;
	}
	
	@Override
	public EntityAIBase createOrcAttackAI()
	{
		return new LOTREntityAIAttackOnCollide(this, 1.5D, true);
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		
		if (rand.nextInt(4) == 0)
		{
			setOrcName(LOTRNames.getRandomElfName(rand.nextBoolean(), rand));
		}
		else
		{
			setOrcName(LOTRNames.getRandomOrcName(rand));
		}
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24D);
    }
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		
		int i = rand.nextInt(6);
		if (i == 0)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.swordUtumno));
		}
		else if (i == 1)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.battleaxeUtumno));
		}
		else if (i == 2)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.daggerUtumno));
		}
		else if (i == 3)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.daggerUtumnoPoisoned));
		}
		else if (i == 4)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.spearUtumno));
		}
		else if (i == 5)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.hammerUtumno));
		}
		
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUtumno));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUtumno));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUtumno));
		
		if (rand.nextInt(10) != 0)
		{
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUtumno));
		}
		
		return data;
    }
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.UTUMNO;
	}
	
	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killUtumnoOrc;
	}
	
	@Override
	protected float getSoundPitch()
	{
		return super.getSoundPitch() * 0.65F;
	}
	
	@Override
	public boolean canOrcSkirmish()
	{
		return false;
	}
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		return "utumnoOrc_hostile";
	}
}
