package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRAlignmentValues;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTRTraderNPCInfo.Trade;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorBlacksmith extends LOTREntityGondorMan implements LOTRTradeable
{
	public LOTREntityGondorBlacksmith(World world)
	{
		super(world);
		addTargetTasks(false);
		
		if (!worldObj.isRemote)
		{
			traderNPCInfo.setBuyTrades(LOTRTradeEntry.getRandomTrades(LOTRTradeEntry.GONDOR_BLACKSMITH_BUY, rand, true));
			traderNPCInfo.setSellTrades(LOTRTradeEntry.getRandomTrades(LOTRTradeEntry.GONDOR_BLACKSMITH_SELL, rand, false));
		}
	}
	
	@Override
	public EntityAIBase createGondorAttackAI()
	{
		return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
    }
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.blacksmithHammer));
		return data;
    }

	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.GONDOR_BLACKSMITH;
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		super.dropFewItems(flag, i);

		dropItem(Items.iron_ingot, 1 + rand.nextInt(3) + rand.nextInt(i + 1));
	}
	
	@Override
	public boolean canTradeWith(EntityPlayer entityplayer)
	{
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= LOTRAlignmentValues.Levels.GONDOR_BLACKSMITH_TRADE && isFriendly(entityplayer);
	}
	
	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, Trade type, ItemStack itemstack)
	{
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeGondorBlacksmith);
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
				return "gondor/blacksmith/friendly";
			}
			else
			{
				return "gondor/blacksmith/neutral";
			}
		}
		else
		{
			return "gondor/blacksmith/hostile";
		}
	}
}
