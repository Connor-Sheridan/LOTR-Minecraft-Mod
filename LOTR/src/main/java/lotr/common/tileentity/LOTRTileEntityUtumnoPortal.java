package lotr.common.tileentity;

import java.util.List;

import lotr.common.*;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRTileEntityUtumnoPortal extends TileEntity
{
	public static int WIDTH = 3;
	public static int HEIGHT = 30;
	
	private static int TARGET_COORDINATE_RANGE = 1000000;
	private int targetX;
	private int targetZ;
	private int targetResetTick;
	private static int targetResetTick_max = 60 * 20;
	
	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			if (targetResetTick > 0)
			{
				targetResetTick--;
			}
			else
			{
				targetX = MathHelper.getRandomIntegerInRange(worldObj.rand, -TARGET_COORDINATE_RANGE, TARGET_COORDINATE_RANGE);
				targetZ = MathHelper.getRandomIntegerInRange(worldObj.rand, -TARGET_COORDINATE_RANGE, TARGET_COORDINATE_RANGE);
				targetResetTick = targetResetTick_max;
			}
		}
		
		if (!worldObj.isRemote)
		{
			List players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 8, yCoord, zCoord - 8, xCoord + 9, yCoord + 60, zCoord + 9));
			for (Object obj : players)
			{
				EntityPlayer entityplayer = (EntityPlayer)obj;
				LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.UTUMNO_WARN);
			}
			
			List nearbyEntities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - (WIDTH - 2), yCoord, zCoord - (WIDTH - 2), xCoord + (WIDTH - 1), yCoord + 3, zCoord + (WIDTH - 1)));
			for (Object obj : nearbyEntities)
			{
				Entity entity = (Entity)obj;
				
				entity.fallDistance = 0F;
				if (!worldObj.isRemote)
				{
					int dimension = LOTRDimension.UTUMNO.dimensionID;
					
					LOTRTeleporterUtumno teleporter = new LOTRTeleporterUtumno(DimensionManager.getWorld(dimension));
					teleporter.setTargetCoords(targetX, targetZ);
					if (entity instanceof EntityPlayerMP)
					{
						MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)entity, dimension, teleporter);
					}
					else
					{
						LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
					}
					
					targetResetTick = targetResetTick_max;
				}
		    }
		}
	}
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("TargetX", targetX);
        nbt.setInteger("TargetZ", targetZ);
        nbt.setInteger("TargetReset", targetResetTick);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
		super.readFromNBT(nbt);
		targetX = nbt.getInteger("TargetX");
		targetZ = nbt.getInteger("TargetZ");
		targetResetTick = nbt.getInteger("TargetReset");
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord - (WIDTH - 1), yCoord, zCoord - (WIDTH - 1), xCoord + WIDTH, yCoord + HEIGHT, zCoord + WIDTH);
    }
}
