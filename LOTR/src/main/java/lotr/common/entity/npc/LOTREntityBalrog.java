package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityBalrog extends LOTREntityNPC
{
	public LOTREntityBalrog(World world)
	{
		super(world);
		setSize(1.6F, 4F);
		getNavigator().setAvoidsWater(true);
        tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.6D, false, 0.8F));
		tasks.addTask(2, new LOTREntityAIFollowHiringPlayer(this));
        tasks.addTask(3, new EntityAIWander(this, 1D));
        tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 24F, 0.05F));
        tasks.addTask(4, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 16F, 0.05F));
        tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 12F, 0.01F));
        tasks.addTask(6, new EntityAILookIdle(this));
        addTargetTasks(true);
        spawnsInDarkness = true;
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200D);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24D);
        getEntityAttribute(npcAttackDamage).setBaseValue(10D);
    }
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.UTUMNO;
	}
	
	@Override
    public boolean isAIEnabled()
    {
        return true;
    }
	
	@Override
    public void knockBack(Entity entity, float f, double d, double d1)
    {
        super.knockBack(entity, f, d, d1);
		motionX /= 3D;
		motionY /= 3D;
		motionZ /= 3D;
    }
	
	@Override
    public boolean attackEntityAsMob(Entity entity)
	{
		if (super.attackEntityAsMob(entity))
		{
			float attackDamage = (float)getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
			float knockbackModifier = 0.25F * attackDamage;
			entity.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180F) * knockbackModifier * 0.5F), (double)knockbackModifier * 0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180F) * knockbackModifier * 0.5F));
			return true;
		}
		return false;
	}
	
	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killBalrog;
	}
	
	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer)
    {
        return 8 + rand.nextInt(5);
    }
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		int j = 3 + rand.nextInt(4) + rand.nextInt(i + 1);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.coal, 1);
		}
	}
	
	@Override
	public String getLivingSound()
	{
		return "lotr:troll.say";
	}
	
	@Override
	protected float getSoundVolume()
	{
		return 1.5F;
	}
	
	@Override
	protected void func_145780_a(int i, int j, int k, Block block)
	{
		playSound("lotr:troll.step", 0.75F, getSoundPitch());
	}
}
