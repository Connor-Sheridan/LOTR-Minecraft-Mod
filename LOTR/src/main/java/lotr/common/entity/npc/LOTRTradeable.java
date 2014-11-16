package lotr.common.entity.npc;

import lotr.common.LOTRFaction;
import lotr.common.entity.npc.LOTRTraderNPCInfo.Trade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface LOTRTradeable
{
	public String getNPCName();
	
	public LOTRFaction getFaction();
	
	public boolean canTradeWith(EntityPlayer entityplayer);
	
	public void onPlayerTrade(EntityPlayer entityplayer, Trade type, ItemStack itemstack);
	
	public boolean shouldTraderRespawn();
}
