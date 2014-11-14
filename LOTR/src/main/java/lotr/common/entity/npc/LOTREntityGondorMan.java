package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenGondor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTREntityGondorMan extends LOTREntityNPC
{
	public LOTREntityGondorMan(World world)
	{
		super(world);
		setSize(0.6F, 1.8F);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
        tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, createGondorAttackAI());
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
        tasks.addTask(5, new EntityAIWander(this, 1D));
        tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.GONDOR, 8000));
        tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.GONDOR_DRINK, 8000));
        tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8F, 0.1F));
        tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5F, 0.05F));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8F, 0.02F));
        tasks.addTask(9, new EntityAILookIdle(this));
	}
	
	public abstract EntityAIBase createGondorAttackAI();
	
	@Override
	public LOTRNPCMount createMountToRide()
	{
		LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
		horse.setMountArmor(new ItemStack(LOTRMod.horseArmorGondor));
		return horse;
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, LOTRNames.getRandomGondorName(rand));
	}
	
	public String getGondorianName()
	{
		return dataWatcher.getWatchableObjectString(16);
	}
	
	public void setGondorianName(String name)
	{
		dataWatcher.updateObject(16, name);
	}
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.GONDOR;
	}
	
	@Override
	public String getNPCName()
	{
		return getGondorianName();
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		super.dropFewItems(flag, i);
		
		int j = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.bone, 1);
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setString("GondorName", getGondorianName());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("GondorName"))
		{
			setGondorianName(nbt.getString("GondorName"));
		}
	}
	
	@Override
	public float getBlockPathWeight(int i, int j, int k)
	{
		float f = 0F;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenGondor)
		{
			f += 20F;
		}
		return f;
	}
	
	@Override
	public LOTRMiniQuest createMiniQuest(EntityPlayer entityplayer)
	{
		return LOTRMiniQuestFactory.GONDOR.createQuest(entityplayer);
	}
}
