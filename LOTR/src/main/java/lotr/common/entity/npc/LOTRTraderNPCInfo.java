package lotr.common.entity.npc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.Constants;

public class LOTRTraderNPCInfo
{
	public enum Trade
	{
		BUY,
		SELL;
	}
	
	private LOTREntityNPC theEntity;
	private LOTRTradeEntry[] buyTrades;
	private LOTRTradeEntry[] sellTrades;
	private int timeUntilAdvertisement;
	private int timeSinceTrade;
	
	public LOTRTraderNPCInfo(LOTREntityNPC npc)
	{
		theEntity = npc;
	}
	
	public LOTRTradeEntry[] getBuyTrades()
	{
		return buyTrades;
	}
	
	public LOTRTradeEntry[] getSellTrades()
	{
		return sellTrades;
	}
	
	public void setBuyTrades(LOTRTradeEntry[] trades)
	{
		buyTrades = trades;
	}
	
	public void setSellTrades(LOTRTradeEntry[] trades)
	{
		sellTrades = trades;
	}
	
	public void onTrade(EntityPlayer entityplayer, Trade type, ItemStack itemstack)
	{
		((LOTRTradeable)theEntity).onPlayerTrade(entityplayer, type, itemstack);
		LOTRLevelData.getData(entityplayer).getFactionData(theEntity.getFaction()).addTrade();
		timeSinceTrade = 0;
	}
	
	public void onUpdate()
	{
		if (timeUntilAdvertisement > 0)
		{
			timeUntilAdvertisement--;
		}
		
		timeSinceTrade++;
		
		if (!theEntity.worldObj.isRemote && theEntity.isEntityAlive() && theEntity.getAttackTarget() == null && timeUntilAdvertisement == 0 && timeSinceTrade > 600)
		{
			double range = 10D;
			List players = theEntity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theEntity.boundingBox.expand(range, range, range));
			for (Object obj : players)
			{
				EntityPlayer entityplayer = (EntityPlayer)obj;
				if (entityplayer.isEntityAlive() && (entityplayer.openContainer == null || entityplayer.openContainer == entityplayer.inventoryContainer))
				{
					String speechBank = theEntity.getSpeechBank(entityplayer);
					if (speechBank != null && theEntity.getRNG().nextInt(3) == 0)
					{
						theEntity.sendSpeechBank(entityplayer, speechBank);
					}
				}
			}
			
			timeUntilAdvertisement = 20 * MathHelper.getRandomIntegerInRange(theEntity.getRNG(), 5, 20);
		}
	}
	
	public void writeToNBT(NBTTagCompound data)
	{
		if (buyTrades != null)
		{
			NBTTagList buyTradesTrags = new NBTTagList();
			for (LOTRTradeEntry trade : buyTrades)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				trade.item.writeToNBT(nbt);
				nbt.setInteger("Cost", trade.cost);
				buyTradesTrags.appendTag(nbt);
			}
			NBTTagCompound buyTradesData = new NBTTagCompound();
			buyTradesData.setTag("Trades", buyTradesTrags);
			data.setTag("LOTRBuyTrades", buyTradesData);
		}
		
		if (sellTrades != null)
		{
			NBTTagList sellTradesTrags = new NBTTagList();
			for (LOTRTradeEntry trade : sellTrades)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				trade.item.writeToNBT(nbt);
				nbt.setInteger("Cost", trade.cost);
				sellTradesTrags.appendTag(nbt);
			}
			NBTTagCompound sellTradesData = new NBTTagCompound();
			sellTradesData.setTag("Trades", sellTradesTrags);
			data.setTag("LOTRSellTrades", sellTradesData);
		}
		
		data.setInteger("TimeSinceTrade", timeSinceTrade);
	}
	
	public void readFromNBT(NBTTagCompound data)
	{
		if (data.hasKey("LOTRBuyTrades"))
		{
			NBTTagCompound buyTradesData = data.getCompoundTag("LOTRBuyTrades");
			if (buyTradesData.hasKey("Trades"))
			{
				NBTTagList buyTradesTrags = buyTradesData.getTagList("Trades", Constants.NBT.TAG_COMPOUND);
				buyTrades = new LOTRTradeEntry[buyTradesTrags.tagCount()];
				for (int i = 0; i < buyTradesTrags.tagCount(); i++)
				{
					NBTTagCompound nbt = (NBTTagCompound)buyTradesTrags.getCompoundTagAt(i);
					ItemStack tradeItem = ItemStack.loadItemStackFromNBT(nbt);
					int tradeCost = nbt.getInteger("Cost");
					buyTrades[i] = new LOTRTradeEntry(tradeItem, tradeCost);
				}
			}
		}
		
		if (data.hasKey("LOTRSellTrades"))
		{
			NBTTagCompound sellTradesData = data.getCompoundTag("LOTRSellTrades");
			if (sellTradesData.hasKey("Trades"))
			{
				NBTTagList sellTradesTrags = sellTradesData.getTagList("Trades", Constants.NBT.TAG_COMPOUND);
				sellTrades = new LOTRTradeEntry[sellTradesTrags.tagCount()];
				for (int i = 0; i < sellTradesTrags.tagCount(); i++)
				{
					NBTTagCompound nbt = (NBTTagCompound)sellTradesTrags.getCompoundTagAt(i);
					ItemStack tradeItem = ItemStack.loadItemStackFromNBT(nbt);
					int tradeCost = nbt.getInteger("Cost");
					sellTrades[i] = new LOTRTradeEntry(tradeItem, tradeCost);
				}
			}
		}
		
		timeSinceTrade = data.getInteger("TimeSinceTrade");
	}
	
	public void sendClientPacket(EntityPlayer entityplayer)
	{
		try
		{
			ByteBuf data = Unpooled.buffer();
			NBTTagCompound nbt = new NBTTagCompound();
			writeToNBT(nbt);
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(nbt);
			Packet packet = new S3FPacketCustomPayload("lotr.trades", data);
			
			((EntityPlayerMP)entityplayer).playerNetServerHandler.sendPacket(packet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void receiveClientPacket(S3FPacketCustomPayload packet)
	{
		try
		{
			ByteBuf data = Unpooled.wrappedBuffer(packet.func_149168_d());
			NBTTagCompound nbt = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
			readFromNBT(nbt);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
