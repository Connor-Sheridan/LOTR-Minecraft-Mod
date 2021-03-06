package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.npc.LOTRTraderNPCInfo.Trade;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDunlendingBartender extends LOTREntityDunlending implements LOTRTradeable
{
	public LOTREntityDunlendingBartender(World world)
	{
		super(world);
		addTargetTasks(false);

		npcLocationName = "entity.lotr.DunlendingBartender.locationName";
		
		if (!worldObj.isRemote)
		{
			traderNPCInfo.setBuyTrades(LOTRTradeEntry.getRandomTrades(LOTRTradeEntry.DUNLENDING_BARTENDER_BUY, rand, true));
			traderNPCInfo.setSellTrades(LOTRTradeEntry.getRandomTrades(LOTRTradeEntry.DUNLENDING_BARTENDER_SELL, rand, false));
		}
	}
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, null);
		return data;
	}
	
	@Override
	public void dropDunlendingItems(boolean flag, int i)
	{
		int j = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int k = 0; k < j; k++)
		{
			int l = rand.nextInt(7);
			switch(l)
			{
				case 0: case 1: case 2:
					Item food = LOTRFoods.DUNLENDING.getRandomFood(rand).getItem();
					entityDropItem(new ItemStack(food), 0F);
					break;
				case 3:
					entityDropItem(new ItemStack(Items.gold_nugget, 2 + rand.nextInt(3)), 0F);
					break;
				case 4: case 5:
					entityDropItem(new ItemStack(LOTRMod.mug), 0F);
					break;
				case 6:
					Item drink = LOTRFoods.DUNLENDING_DRINK.getRandomFood(rand).getItem();
					entityDropItem(new ItemStack(drink, 1, 1 + rand.nextInt(3)), 0F);
					break;
			}
		}
	}
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.DUNLENDING_BARTENDER;
	}
	
	@Override
	public boolean canTradeWith(EntityPlayer entityplayer)
	{
		return isFriendly(entityplayer);
	}
	
	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, Trade type, ItemStack itemstack)
	{
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDunlendingBartender);
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
			return "dunlending/bartender/friendly";
		}
		else
		{
			return "dunlending/dunlending/hostile";
		}
	}
}
