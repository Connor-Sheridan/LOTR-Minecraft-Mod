package lotr.common.entity.npc;

import lotr.common.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface LOTRUnitTradeable
{
	public String getNPCName();
	
	public LOTRFaction getFaction();
	
	public LOTRUnitTradeEntry[] getUnits();
	
	public boolean canTradeWith(EntityPlayer entityplayer);
	
	public void onUnitTrade(EntityPlayer entityplayer);
	
	public boolean shouldTraderRespawn();
	
	public ItemStack createAlignmentReward();
}
