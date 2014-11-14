package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfMerchant extends LOTREntityBlueDwarf implements LOTRTradeable, LOTRTravellingTrader
{
	public LOTREntityBlueDwarfMerchant(World world)
	{
		super(world);
		addTargetTasks(false);
		
		if (!worldObj.isRemote)
		{
			traderNPCInfo.setBuyTrades(LOTRTradeEntry.getRandomTrades(LOTRTradeEntry.BLUE_DWARF_MERCHANT_BUY, rand, true));
			traderNPCInfo.setSellTrades(LOTRTradeEntry.getRandomTrades(LOTRTradeEntry.BLUE_DWARF_MERCHANT_SELL, rand, false));
		}
	}

	@Override
	public LOTREntityNPC createTravellingEscort()
	{
		return new LOTREntityBlueDwarf(worldObj);
	}
	
	@Override
	public String getDepartureSpeech()
	{
		return "blueDwarf/merchant/departure";
	}

	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.BLUE_DWARF_MERCHANT;
	}
	
	@Override
	public boolean canTradeWith(EntityPlayer entityplayer)
	{
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= LOTRAlignmentValues.Levels.BLUE_DWARF_MERCHANT_TRADE && isFriendly(entityplayer);
	}
	
	@Override
	public void onPlayerBuyItem(EntityPlayer entityplayer, ItemStack itemstack)
	{
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlueDwarfMerchant);
	}
	
	@Override
	public void onPlayerSellItem(EntityPlayer entityplayer, ItemStack itemstack)
	{
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlueDwarfMerchant);
	}
	
	@Override
	public boolean shouldTraderRespawn()
	{
		return false;
	}
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		if (isFriendly(entityplayer))
		{
			return "blueDwarf/merchant/friendly";
		}
		else
		{
			return "blueDwarf/dwarf/hostile";
		}
	}
}
