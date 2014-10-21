package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityPlate extends TileEntity
{
	private ItemStack foodItem;
	
	public static boolean isValidFoodItem(ItemStack itemstack)
	{
		return itemstack != null && itemstack.getItem() instanceof ItemFood && !itemstack.getItem().hasContainerItem(itemstack);
	}
	
	public ItemStack getFoodItem()
	{
		return foodItem;
	}
	
	public void setFoodItem(ItemStack item)
	{
		if (item != null && item.stackSize <= 0)
		{
			item = null;
		}
		foodItem = item;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("PlateEmpty", foodItem == null);
        if (foodItem != null)
        {
        	nbt.setTag("FoodItem", foodItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("FoodID"))
		{
			Item item = Item.getItemById(nbt.getInteger("FoodID"));
			if (item != null)
			{
				int damage = nbt.getInteger("FoodDamage");
				foodItem = new ItemStack(item, 1, damage);
			}
		}
		else
		{
			boolean empty = nbt.getBoolean("PlateEmpty");
			if (empty)
			{
				foodItem = null;
			}
			else
			{
				foodItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("FoodItem"));
			}
		}
	}
	
	@Override
    public Packet getDescriptionPacket()
    {
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
    }
	
	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet)
	{
		NBTTagCompound data = packet.func_148857_g();
		readFromNBT(data);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        AxisAlignedBB bb = super.getRenderBoundingBox();
        if (foodItem != null)
        {
        	bb = bb.addCoord(0D, foodItem.stackSize * 0.03125F, 0D);
        }
        return bb;
    }
}
