package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIHobbitChildFollowGoodPlayer;
import lotr.common.entity.ai.LOTREntityAIHobbitSmoke;
import lotr.common.entity.ai.LOTREntityAINPCAvoidEvilPlayer;
import lotr.common.entity.ai.LOTREntityAINPCFollowParent;
import lotr.common.entity.ai.LOTREntityAINPCFollowSpouse;
import lotr.common.entity.ai.LOTREntityAINPCMarry;
import lotr.common.entity.ai.LOTREntityAINPCMate;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenShire;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityHobbit extends LOTREntityNPC
{
	public LOTREntityHobbit(World world)
	{
		super(world);
        setSize(0.45F, 0.9F);
        getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
        tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityOrc.class, 12F, 1.5D, 1.8D));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityWarg.class, 12F, 1.5D, 1.8D));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityTroll.class, 12F, 1.5D, 1.8D));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntitySpiderBase.class, 12F, 1.5D, 1.8D));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityDarkHuorn.class, 12F, 1.5D, 1.8D));
		tasks.addTask(2, new EntityAIPanic(this, 1.6D));
		tasks.addTask(3, new LOTREntityAINPCAvoidEvilPlayer(this, 8F, 1.5D, 1.8D));
		tasks.addTask(4, new LOTREntityAIHobbitChildFollowGoodPlayer(this, 12F, 1.5D));
		tasks.addTask(5, new LOTREntityAINPCMarry(this, 1.3D));
		tasks.addTask(6, new LOTREntityAINPCMate(this, 1.3D));
		tasks.addTask(7, new LOTREntityAINPCFollowParent(this, 1.4D));
		tasks.addTask(8, new LOTREntityAINPCFollowSpouse(this, 1.1D));
		tasks.addTask(9, new EntityAIOpenDoor(this, true));
		tasks.addTask(10, new EntityAIWander(this, 1.1D));
		tasks.addTask(11, new LOTREntityAIEat(this, LOTRFoods.HOBBIT, 3000));
		tasks.addTask(11, new LOTREntityAIDrink(this, LOTRFoods.HOBBIT_DRINK, 3000));
        tasks.addTask(11, new LOTREntityAIHobbitSmoke(this, 4000));
        tasks.addTask(12, new EntityAIWatchClosest2(this, EntityPlayer.class, 8F, 0.2F));
        tasks.addTask(12, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5F, 0.1F));
        tasks.addTask(13, new EntityAIWatchClosest(this, EntityLiving.class, 8F, 0.02F));
        tasks.addTask(14, new EntityAILookIdle(this));
        
        familyInfo.marriageEntityClass = LOTREntityHobbit.class;
        familyInfo.marriageRing = LOTRMod.hobbitRing;
        familyInfo.marriageAlignmentRequired = LOTRAlignmentValues.Levels.HOBBIT_MARRY;
        familyInfo.marriageAchievement = LOTRAchievement.marryHobbit;
        familyInfo.potentialMaxChildren = 4;
        familyInfo.timeToMature = 48000;
        familyInfo.breedingDelay = 24000;
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		familyInfo.setNPCMale(rand.nextBoolean());
		dataWatcher.addObject(19, LOTRNames.getRandomHobbitName(familyInfo.isNPCMale(), rand));
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
    }
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.HOBBIT;
	}
	
	@Override
	public String getNPCName()
	{
		return getHobbitName();
	}

	public String getHobbitName()
	{
		return dataWatcher.getWatchableObjectString(19);
	}
	
	public void setHobbitName(String name)
	{
		dataWatcher.updateObject(19, name);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setString("HobbitName", getHobbitName());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		if (nbt.hasKey("HobbitName"))
		{
			setHobbitName(nbt.getString("HobbitName"));
		}
		
		if (nbt.hasKey("HobbitGender"))
		{
			familyInfo.setNPCMale(nbt.getBoolean("HobbitGender"));
		}
		if (nbt.hasKey("HobbitAge"))
		{
			familyInfo.setNPCAge(nbt.getInteger("HobbitAge"));
		}
	}
	
	@Override
	public void changeNPCNameForMarriage(LOTREntityNPC spouse)
	{
		if (familyInfo.isNPCMale())
		{
			LOTRNames.changeHobbitSurnameForMarriage(this, (LOTREntityHobbit)spouse);
		}
		else if (spouse.familyInfo.isNPCMale())
		{
			LOTRNames.changeHobbitSurnameForMarriage((LOTREntityHobbit)spouse, this);
		}
	}
	
	@Override
	public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent)
	{
		setHobbitName(LOTRNames.getRandomHobbitChildNameForParent(familyInfo.isNPCMale(), rand, (LOTREntityHobbit)maleParent));
	}
	
	@Override
	public boolean interact(EntityPlayer entityplayer)
	{
		if (familyInfo.interact(entityplayer))
		{
			return true;
		}
		return super.interact(entityplayer);
	}
	
	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killHobbit;
	}
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.HOBBIT;
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		int j = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int k = 0; k < j; k++)
		{
			dropItem(LOTRMod.hobbitBone, 1);
		}
		
		dropHobbitItems(flag, i);
	}
		
	public void dropHobbitItems(boolean flag, int i)
	{
		if (rand.nextBoolean())
		{
			dropChestContents(LOTRChestContents.HOBBIT_HOLE_STUDY, 0, 1 + i);
		}
		
		if (rand.nextBoolean())
		{
			dropChestContents(LOTRChestContents.HOBBIT_HOLE_LARDER, 0, 2 + i);
		}
	}
	
	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer)
    {
        return 1 + rand.nextInt(3);
    }
	
	@Override
	public boolean getCanSpawnHere()
	{
		if (super.getCanSpawnHere())
		{
			if (liftSpawnRestrictions)
			{
				return true;
			}
			else
			{
				int i = MathHelper.floor_double(posX);
				int j = MathHelper.floor_double(boundingBox.minY);
				int k = MathHelper.floor_double(posZ);
				return j > 62 && worldObj.getBlock(i, j - 1, k) == Blocks.grass;
			}
		}
		return false;
	}
	
	@Override
	public float getBlockPathWeight(int i, int j, int k)
	{
		float f = 0F;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenShire)
		{
			f += 20F;
		}
		return f;
	}
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		if (isFriendly(entityplayer))
		{
			return isChild() ? "hobbit/child/friendly" : "hobbit/hobbit/friendly";
		}
		else
		{
			return isChild() ? "hobbit/child/hostile" : "hobbit/hobbit/hostile";
		}
	}
	
	@Override
	public LOTRMiniQuest createMiniQuest(EntityPlayer entityplayer)
	{
		return LOTRMiniQuestFactory.HOBBIT.createQuest(entityplayer);
	}
	
	@Override
	public void onArtificalSpawn()
	{
		if (getClass() == familyInfo.marriageEntityClass && rand.nextInt(10) == 0)
		{
			familyInfo.setChild();
		}
	}
}
