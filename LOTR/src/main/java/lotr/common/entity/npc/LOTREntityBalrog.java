package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityBalrog extends LOTREntityNPC
{
	public LOTREntityBalrog(World world)
	{
		super(world);
		setSize(2.4F, 5F);
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
        isImmuneToFire = true;
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
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300D);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        getEntityAttribute(npcAttackDamage).setBaseValue(10D);
    }
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);

		if (rand.nextBoolean())
		{
			if (rand.nextBoolean())
			{
				setCurrentItemOrArmor(0, new ItemStack(LOTRMod.balrogWhip));
			}
			else
			{
				int i = rand.nextInt(3);
				
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
					setCurrentItemOrArmor(0, new ItemStack(LOTRMod.hammerUtumno));
				}
			}
		}
		
		return data;
    }
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.UTUMNO;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (getHealth() < getMaxHealth() && worldObj.getWorldTime() % 10 == 0)
		{
			heal(1F);
		}
		
		if (isWreathedInFlame())
		{
			if (!worldObj.isRemote && rand.nextInt(80) == 0)
			{
				for (int l = 0; l < 24; l++)
				{
					int i = MathHelper.floor_double(posX);
					int j = MathHelper.floor_double(boundingBox.minY);
					int k = MathHelper.floor_double(posZ);
					
					i += MathHelper.getRandomIntegerInRange(rand, -8, 8);
					j += MathHelper.getRandomIntegerInRange(rand, -4, 8);
					k += MathHelper.getRandomIntegerInRange(rand, -8, 8);
					
					Block block = worldObj.getBlock(i, j, k);
					float maxResistance = Blocks.stone.getExplosionResistance(this);
					if ((block.isReplaceable(worldObj, i, j, k) || block.getExplosionResistance(this) <= maxResistance) && Blocks.fire.canPlaceBlockAt(worldObj, i, j, k))
					{
						worldObj.setBlock(i, j, k, Blocks.fire, 0, 3);
					}
				}
			}
	
			for (int l = 0; l < 3; l++)
			{
				String s = rand.nextInt(3) == 0 ? "flame" : "largesmoke";
				worldObj.spawnParticle(s, posX + (rand.nextDouble() - 0.5D) * (double)width * 2D, posY + 0.5D + rand.nextDouble() * (double)height * 1.5D, posZ + (rand.nextDouble() - 0.5D) * (double)width * 2D, 0D, 0D, 0D);
			}
		}
	}
	
	public boolean isWreathedInFlame()
	{
		return isEntityAlive() && !isWet();
	}
	
	@Override
    public void knockBack(Entity entity, float f, double d, double d1)
    {
        super.knockBack(entity, f, d, d1);
		motionX /= 4D;
		motionY /= 4D;
		motionZ /= 4D;
    }
	
	@Override
    public boolean attackEntityAsMob(Entity entity)
	{
		if (super.attackEntityAsMob(entity))
		{
			if (getHeldItem() == null)
			{
				entity.setFire(5);
			}
			
			float attackDamage = (float)getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
			float knockbackModifier = 0.25F * attackDamage;
			entity.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180F) * knockbackModifier * 0.5F), (double)knockbackModifier * 0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180F) * knockbackModifier * 0.5F));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f)
	{
		if (damagesource == DamageSource.fall)
		{
			return false;
		}
		
		return super.attackEntityFrom(damagesource, f);
	}
	
	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killBalrog;
	}
	
	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer)
    {
        return 15 + rand.nextInt(10);
    }
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		int j = 3 + rand.nextInt(4) + rand.nextInt(i + 1);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.coal, 1);
		}
		
		super.dropFewItems(flag, i);
	}
	
	@Override
	public void dropNPCEquipment(boolean flag, int i)
	{
		if (flag && rand.nextInt(5) == 0)
		{
			ItemStack heldItem = getHeldItem();
			if (heldItem != null)
			{
				entityDropItem(heldItem, 0F);
				setCurrentItemOrArmor(0, null);
			}
		}
		
		super.dropNPCEquipment(flag, i);
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
