package lotr.common.entity.item;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;
import java.util.UUID;

import lotr.common.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

public class LOTREntityBanner extends Entity
{
	public static double PROTECTION_RANGE = 32D;

	private boolean playerSpecificProtection;
	private boolean selfProtection = true;
	
	public static int ALIGNMENT_PROTECTION_MIN = 1;
	public static int ALIGNMENT_PROTECTION_MAX = 1000;
	private int alignmentProtection = ALIGNMENT_PROTECTION_MIN;
	
	public static int MAX_PLAYERS = 24;
	private UUID[] allowedPlayers = new UUID[MAX_PLAYERS];
	
	public LOTREntityBanner(World world)
	{
		super(world);
		setSize(1F, 3F);
	}
	
	@Override
	protected void entityInit()
	{
		dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}
	
	private int getBannerType()
	{
		return dataWatcher.getWatchableObjectByte(18);
	}
	
	private void setBannerType(int i)
	{
		dataWatcher.updateObject(18, Byte.valueOf((byte)i));
	}
	
	public void setBannerFaction(LOTRFaction faction)
	{
		setBannerType(LOTRItemBanner.getSubtypeForFaction(faction));
	}
	
	public LOTRFaction getBannerFaction()
	{
		return LOTRItemBanner.getFaction(getBannerType());
	}
	
	public boolean isProtectingTerritory()
	{
		if (!LOTRConfig.allowBannerProtection)
		{
			return false;
		}
		
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return worldObj.getBlock(i, j - 1, k) == Blocks.gold_block;
	}
	
	public boolean isPlayerSpecificProtection()
	{
		return playerSpecificProtection;
	}
	
	public void setPlayerSpecificProtection(boolean flag)
	{
		playerSpecificProtection = flag;
		
		if (!worldObj.isRemote)
		{
			sendBannerToAllPlayers(worldObj);
		}
	}
	
	public boolean isSelfProtection()
	{
		if (!LOTRConfig.allowSelfProtectingBanners)
		{
			return false;
		}
		return selfProtection;
	}
	
	public void setSelfProtection(boolean flag)
	{
		selfProtection = flag;
		
		if (!worldObj.isRemote)
		{
			sendBannerToAllPlayers(worldObj);
		}
	}
	
	public int getAlignmentProtection()
	{
		return alignmentProtection;
	}
	
	public void setAlignmentProtection(int i)
	{
		alignmentProtection = MathHelper.clamp_int(i, ALIGNMENT_PROTECTION_MIN, ALIGNMENT_PROTECTION_MAX);
		
		if (!worldObj.isRemote)
		{
			sendBannerToAllPlayers(worldObj);
		}
	}
	
	public void setPlacingPlayer(EntityPlayer player)
	{
		whitelistPlayer(0, player.getUniqueID());
	}
	
	public UUID getPlacingPlayer()
	{
		return allowedPlayers[0];
	}
	
	public void whitelistPlayer(int index, UUID player)
	{
		allowedPlayers[index] = player;
		
		if (!worldObj.isRemote)
		{
			sendBannerToAllPlayers(worldObj);
		}
	}
	
	public boolean isPlayerWhitelisted(EntityPlayer entityplayer)
	{
		if (playerSpecificProtection)
		{
			for (UUID uuid : allowedPlayers)
			{
				if (uuid != null && uuid.equals(entityplayer.getUniqueID()))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}
	
	@Override
    public AxisAlignedBB getBoundingBox()
    {
        return null;
    }
	
	@Override
    public void onUpdate()
    {
        super.onUpdate();
		
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        
        motionX = motionZ = 0D;
        motionY -= 0.04D;

        func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2D, posZ);
        moveEntity(motionX, motionY, motionZ);
        
        motionY *= 0.98D;
        
        if (!worldObj.isRemote && !onGround)
        {
        	dropAsItem(true);
        }
        
        ignoreFrustumCheck = isProtectingTerritory();
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
		nbt.setByte("BannerType", (byte)getBannerType());
		nbt.setBoolean("PlayerProtection", playerSpecificProtection);
		nbt.setBoolean("SelfProtection", selfProtection);
        nbt.setInteger("AlignmentProtection", alignmentProtection);
        
        NBTTagList allowedPlayersTags = new NBTTagList();
        for (int i = 0; i < allowedPlayers.length; i++)
        {
        	NBTTagCompound playerData = new NBTTagCompound();
        	playerData.setInteger("Index", i);
        	if (allowedPlayers[i] != null)
        	{
        		UUID uuid = allowedPlayers[i];
        		playerData.setLong("UUIDMost", uuid.getMostSignificantBits());
        		playerData.setLong("UUIDLeast", uuid.getLeastSignificantBits());
        	}
        	allowedPlayersTags.appendTag(playerData);
        }
        
        nbt.setTag("AllowedPlayers", allowedPlayersTags);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	setBannerType(nbt.getByte("BannerType"));
    	playerSpecificProtection = nbt.getBoolean("PlayerProtection");
    	
    	if (nbt.hasKey("SelfProtection"))
    	{
    		selfProtection = nbt.getBoolean("SelfProtection");
    	}
    	else
    	{
    		selfProtection = true;
    	}
    	
    	setAlignmentProtection(nbt.getInteger("AlignmentProtection"));
    	
    	NBTTagList allowedPlayersTags = nbt.getTagList("AllowedPlayers", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < allowedPlayersTags.tagCount(); i++)
        {
        	NBTTagCompound playerData = allowedPlayersTags.getCompoundTagAt(i);
        	int index = playerData.getInteger("Index");
        	if (playerData.hasKey("UUIDMost") && playerData.hasKey("UUIDLeast"))
        	{
        		allowedPlayers[index] = new UUID(playerData.getLong("UUIDMost"), playerData.getLong("UUIDLeast"));
        	}
        }
    }
    
    @Override
    public boolean interactFirst(EntityPlayer entityplayer)
    {
    	if (!worldObj.isRemote)
    	{
    		if (isProtectingTerritory() && entityplayer.getUniqueID().equals(allowedPlayers[0]))
    		{
    			sendBannerToPlayer(entityplayer, true);
    		}
    	}
    	return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f)
    {
    	if (isProtectingTerritory() && !(damagesource.getEntity() instanceof EntityPlayer))
    	{
    		return false;
    	}
    	else
    	{
			if (!isDead && !worldObj.isRemote)
			{
				if (damagesource.getEntity() instanceof EntityPlayer)
				{
					EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
					if (LOTRBannerProtection.isProtectedByBanner(worldObj, this, LOTRBannerProtection.forPlayer(entityplayer), true))
					{
						boolean protecting = isProtectingTerritory();
						if (protecting && selfProtection)
						{
							return false;
						}
						else if (!protecting)
						{
							return false;
						}
					}
				}
				
				setBeenAttacked();
				worldObj.playSoundAtEntity(this, Blocks.planks.stepSound.getBreakSound(), (Blocks.planks.stepSound.getVolume() + 1F) / 2F, Blocks.planks.stepSound.getPitch() * 0.8F);
				
				boolean drop = true;
				if (damagesource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damagesource.getEntity()).capabilities.isCreativeMode)
				{
					drop = false;
				}
				
				dropAsItem(drop);
			}
			
			return true;
    	}
    }
    
    private void dropAsItem(boolean drop)
    {
    	setDead();
    	if (drop)
    	{
    		entityDropItem(new ItemStack(LOTRMod.banner, 1, getBannerType()), 0F);
    	}
    }
	
	@Override
    public ItemStack getPickedResult(MovingObjectPosition target)
    {
        return new ItemStack(LOTRMod.banner, 1, getBannerType());
    }
	
	public void sendBannerToPlayer(EntityPlayer entityplayer, boolean openGui)
	{
		sendBannerData(new EntityPlayer[] {entityplayer}, openGui);
	}
	
	public void sendBannerToAllPlayers(World world)
	{
		List<EntityPlayer> players = world.playerEntities;
		sendBannerData(players.toArray(new EntityPlayer[0]), false);
	}
	
	private void sendBannerData(EntityPlayer[] players, boolean openGui)
	{
		ByteBuf data = Unpooled.buffer();
		
		data.writeInt(getEntityId());
		
		data.writeBoolean(openGui);
		
		data.writeBoolean(playerSpecificProtection);
		data.writeBoolean(selfProtection);
		data.writeInt(getAlignmentProtection());

		for (int i = 0; i < allowedPlayers.length; i++)
		{
			UUID uuid = allowedPlayers[i];
			if (uuid != null)
			{
				GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
				if (profile != null)
				{
					if (StringUtils.isEmpty(profile.getName()))
					{
						MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
					}

					String username = profile.getName();
					if (!StringUtils.isEmpty(username))
					{
						data.writeInt(i);
						
						data.writeLong(uuid.getMostSignificantBits());
						data.writeLong(uuid.getLeastSignificantBits());
						
						data.writeByte(username.length());
						data.writeBytes(username.getBytes(Charsets.UTF_8));
					}
				}
			}
		}
		data.writeInt(-1);
		
		for (EntityPlayer entityplayer : players)
		{
			Packet packet = new S3FPacketCustomPayload("lotr.bannerData", data);
			((EntityPlayerMP)entityplayer).playerNetServerHandler.sendPacket(packet);
		}
	}
}
