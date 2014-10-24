package lotr.common.tileentity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

import lotr.common.*;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRTileEntityUtumnoReturnPortal extends TileEntity
{
	public static int PORTAL_TOP = 250;
	
	private int beamCheck = 0;
	
	public int ticksExisted;
	
	@Override
	public void updateEntity()
	{
		ticksExisted++;
		
		if (!worldObj.isRemote)
		{
			if (beamCheck % 20 == 0)
			{
				int i = xCoord;
				int k = zCoord;
				for (int j = yCoord + 1; j <= PORTAL_TOP; j++)
				{
					worldObj.setBlock(i, j, k, LOTRMod.utumnoReturnLight, 0, 3);
				}
			}
			beamCheck++;
		}
			
		List nearbyEntities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, PORTAL_TOP, zCoord + 1));
		for (Object obj : nearbyEntities)
		{
			Entity entity = (Entity)obj;
			
			if (LOTRMod.getNPCFaction(entity) == LOTRFaction.UTUMNO)
			{
				continue;
			}
			
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)entity;
				if (entityplayer.capabilities.isFlying)
				{
					continue;
				}
			}

			if (!worldObj.isRemote)
			{
				float motionY = 0.2F;
				entity.motionY += motionY;
				entity.setPosition(xCoord + 0.5D, entity.boundingBox.minY, zCoord + 0.5D);
				entity.fallDistance = 0F;
			}
			
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)entity;
				LOTRMod.proxy.setInUtumnoReturnPortal(entityplayer);
				
				if (entityplayer instanceof EntityPlayerMP)
				{
					EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
					
					ByteBuf data = Unpooled.buffer();
					
					data.writeDouble(entityplayer.posX);
					data.writeDouble(entityplayer.posZ);
					
					S3FPacketCustomPayload packet = new S3FPacketCustomPayload("lotr.utumnoReturn", data);
					entityplayermp.playerNetServerHandler.sendPacket(packet);
					
					entityplayermp.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(entityplayer));
				}
			}
			
			if (!worldObj.isRemote)
			{
				if (entity.posY >= PORTAL_TOP - 5D)
				{
					int dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
					LOTRTeleporterUtumno teleporter = new LOTRTeleporterUtumno(DimensionManager.getWorld(dimension));
					if (entity instanceof EntityPlayerMP)
					{
						MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)entity, dimension, teleporter);
					}
					else
					{
						LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
					}
				}
			}
	    }
	}

	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }
}
