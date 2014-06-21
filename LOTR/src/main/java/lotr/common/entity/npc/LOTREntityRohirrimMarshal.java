package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRAlignmentValues;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREntityRohirrimMarshal extends LOTREntityRohirrim implements LOTRUnitTradeable
{
	public LOTREntityRohirrimMarshal(World world)
	{
		super(world);
		targetTasks.taskEntries.clear();
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
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
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.swordRohan));
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohan));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohan));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohan));
		setCurrentItemOrArmor(4, null);
		return data;
    }
	
	@Override
    public String getCommandSenderName()
    {
		return StatCollector.translateToLocalFormatted("entity.lotr.RohirrimMarshal.entityName", new Object[] {getNPCName()});
    }
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.ROHIRRIM_MARSHAL_BONUS;
	}
	
	@Override
	public LOTRUnitTradeEntry[] getUnits()
	{
		return LOTRUnitTradeEntry.ROHIRRIM_MARSHAL;
	}
	
	@Override
	public boolean canTradeWith(EntityPlayer entityplayer)
	{
		return LOTRLevelData.getAlignment(entityplayer, getFaction()) >= LOTRAlignmentValues.ROHIRRIM_MARSHAL_TRADE && isFriendly(entityplayer);
	}
	
	@Override
	public void onUnitTrade(EntityPlayer entityplayer)
	{
		LOTRLevelData.addAchievement(entityplayer, LOTRAchievement.tradeRohirrimMarshal);
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
				return "rohirrimMarshal_friendly";
			}
			else
			{
				return "rohirrimMarshal_neutral";
			}
		}
		else
		{
			return "rohirrim_hostile";
		}
	}
}
