package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityHalfTroll extends LOTREntityNPC
{
	public LOTREntityHalfTroll(World world)
	{
		super(world);
		setSize(1F, 2.4F);
		getNavigator().setAvoidsWater(true);
        tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, createHalfTrollAttackAI());
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
        tasks.addTask(4, new EntityAIWander(this, 1D));
        tasks.addTask(5, new LOTREntityAIEat(this, LOTRFoods.HALF_TROLL, 6000));
        tasks.addTask(5, new LOTREntityAIDrink(this, LOTRFoods.HALF_TROLL_DRINK, 6000));
        tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8F, 0.1F));
        tasks.addTask(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5F, 0.05F));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 8F, 0.02F));
        tasks.addTask(8, new EntityAILookIdle(this));

        addTargetTasks(true);
		
		spawnsInDarkness = true;
	}
	
	public EntityAIBase createHalfTrollAttackAI()
	{
		return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, LOTRNames.getRandomOrcName(rand));
	}
	
	public String getHalfTrollName()
	{
		return dataWatcher.getWatchableObjectString(16);
	}
	
	public void setHalfTrollName(String name)
	{
		dataWatcher.updateObject(16, name);
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
    }
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		return data;
    }

	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.HALF_TROLL;
	}
	
	@Override
	public String getNPCName()
	{
		return getHalfTrollName();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setString("HalfTrollName", getHalfTrollName());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("HalfTrollName"))
		{
			setHalfTrollName(nbt.getString("HalfTrollName"));
		}
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		super.dropFewItems(flag, i);
		
		int j = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.rotten_flesh, 1);
		}
		
		j = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int k = 0; k < j; k++)
		{
			dropItem(LOTRMod.trollBone, 1);
		}
	}
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.HALF_TROLL;
	}
	
	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killHalfTroll;
	}
	
	@Override
    protected String getLivingSound()
    {
        return "lotr:orc.say";
    }
	
	@Override
    protected String getHurtSound()
    {
        return "lotr:orc.hurt";
    }
	
	@Override
    protected String getDeathSound()
    {
        return "lotr:orc.death";
    }
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		if (isFriendly(entityplayer))
		{
			if (hiredNPCInfo.getHiringPlayer() == entityplayer)
			{
				return "halfTroll/halfTroll/hired";
			}
			else
			{
				return "halfTroll/halfTroll/friendly";
			}
		}
		else
		{
			return "halfTroll/halfTroll/hostile";
		}
	}
}
