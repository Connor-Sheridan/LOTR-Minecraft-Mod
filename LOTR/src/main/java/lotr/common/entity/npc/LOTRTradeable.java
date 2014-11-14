package lotr.common.entity.npc;

import lotr.common.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface LOTRTradeable
{
	public String getNPCName();
	
	public LOTRFaction getFaction();
	
	public boolean canTradeWith(EntityPlayer entityplayer);
	
	public void onPlayerBuyItem(EntityPlayer entityplayer, ItemStack itemstack);
	
	public void onPlayerSellItem(EntityPlayer entityplayer, ItemStack itemstack);
	
	public boolean shouldTraderRespawn();
}
