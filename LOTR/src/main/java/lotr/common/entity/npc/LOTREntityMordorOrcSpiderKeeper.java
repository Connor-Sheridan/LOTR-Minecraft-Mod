package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMordorOrcSpiderKeeper extends LOTREntityMordorOrc implements LOTRUnitTradeable
{
	public LOTREntityMordorOrcSpiderKeeper(World world)
	{
		super(world);
		setSize(0.6F, 1.8F);
		addTargetTasks(false);
		isWeakOrc = false;
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
    }
	
	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.orcSkullStaff));
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsOrc));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsOrc));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyOrc));
		setCurrentItemOrArmor(4, null);
		
		if (!worldObj.isRemote)
		{
			LOTREntityMordorSpider spider = new LOTREntityMordorSpider(worldObj);
			spider.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0F);
			spider.setSpiderScale(3);
			if (worldObj.func_147461_a(spider.boundingBox).isEmpty() || liftSpawnRestrictions)
			{
				spider.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(spider);
				mountEntity(spider);
			}
		}
		
		return data;
	}

	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.MORDOR_ORC_SPIDER_KEEPER;
	}
	
	@Override
	public LOTRUnitTradeEntry[] getUnits()
	{
		return LOTRUnitTradeEntry.MORDOR_ORC_SPIDER_KEEPER;
	}
	
	@Override
	public boolean canTradeWith(EntityPlayer entityplayer)
	{
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= LOTRAlignmentValues.Levels.MORDOR_ORC_SPIDER_KEEPER_TRADE && isFriendly(entityplayer);
	}
	
	@Override
	public void onUnitTrade(EntityPlayer entityplayer)
	{
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeOrcSpiderKeeper);
	}
	
	@Override
	public boolean shouldTraderRespawn()
	{
		return true;
	}
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		if (isFriendly(entityplayer))
		{
			if (canTradeWith(entityplayer))
			{
				return "mordor/chieftain/friendly";
			}
			else
			{
				return "mordor/chieftain/neutral";
			}
		}
		else
		{
			return "mordor/orc/hostile";
		}
	}
}
