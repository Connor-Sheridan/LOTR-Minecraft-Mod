package lotr.common.item;

import java.util.ArrayList;
import java.util.List;

import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemBalrogWhip extends LOTRItemSword
{
	public LOTRItemBalrogWhip()
	{
		super(LOTRMod.toolUtumno);
		lotrWeaponDamage = 7F;
		setMaxDamage(1000);
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user)
	{
		if (super.hitEntity(itemstack, hitEntity, user))
		{
			if (!user.worldObj.isRemote && hitEntity.hurtTime == hitEntity.maxHurtTime)
			{
				launchWhip(user);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
    {
        return EnumAction.bow;
    }
	
	@Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 20;
    }

	@Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemstack;
    }
	
	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		entityplayer.swingItem();
		if (!world.isRemote)
		{
			launchWhip(entityplayer);
		}
		itemstack.damageItem(1, entityplayer);
		return itemstack;
    }
	
	private void launchWhip(EntityLivingBase user)
	{
		user.worldObj.playSoundAtEntity(user, "lotr:item.balrogWhip", 2F, 0.7F + itemRand.nextFloat() * 0.6F);
		
		double range = 16D;
		Vec3 position = Vec3.createVectorHelper(user.posX, user.posY, user.posZ);
		Vec3 look = user.getLookVec();
		Vec3 sight = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
		float sightWidth = 1F;
		List list = user.worldObj.getEntitiesWithinAABBExcludingEntity(user, user.boundingBox.addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand((double)sightWidth, (double)sightWidth, (double)sightWidth));
		
		List<EntityLivingBase> whipTargets = new ArrayList();
		
		for (int i = 0; i < list.size(); i++)
		{
			Entity obj = (Entity)list.get(i);
			
			if (!(obj instanceof EntityLivingBase))
			{
				continue;
			}
			EntityLivingBase entity = (EntityLivingBase)obj;
			
			if (entity == user.ridingEntity && !entity.canRiderInteract())
			{
				continue;
			}
			
			if (user instanceof EntityPlayer)
			{
				if (!LOTRMod.canPlayerAttackEntity((EntityPlayer)user, entity, false))
				{
					continue;
				}
			}
			else if (user instanceof EntityCreature)
			{
				if (!LOTRMod.canNPCAttackEntity((EntityCreature)user, entity))
				{
					continue;
				}
			}

			if (entity.canBeCollidedWith())
			{
				float width = 1F;
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)width, (double)width, (double)width);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(position, sight);

				if (axisalignedbb.isVecInside(position))
				{
					whipTargets.add(entity);
				}
				else if (movingobjectposition != null)
				{
					whipTargets.add(entity);
				}
			}
		}
		
		for (EntityLivingBase entity : whipTargets)
		{
			entity.setFire(5);
		}
		
		Vec3 eyeHeight = position.addVector(0D, user.getEyeHeight(), 0D);
		for (int l = 4; l < (int)range; l++)
		{
			double d = (double)l / range;
			
			double dx = sight.xCoord - eyeHeight.xCoord;
			double dy = sight.yCoord - eyeHeight.yCoord;
			double dz = sight.zCoord - eyeHeight.zCoord;
			
			double x = eyeHeight.xCoord + dx * d;
			double y = eyeHeight.yCoord + dy * d;
			double z = eyeHeight.zCoord + dz * d;
			
			int i = MathHelper.floor_double(x);
			int j = MathHelper.floor_double(y);
			int k = MathHelper.floor_double(z);

			fireLoop:
			for (int j1 = j - 3; j1 <= j + 3; j1++)
			{
				if (World.doesBlockHaveSolidTopSurface(user.worldObj, i, j1 - 1, k) && user.worldObj.getBlock(i, j1, k).isReplaceable(user.worldObj, i, j1, k))
				{
					boolean protection = false;
					
					if (user instanceof EntityPlayer)
					{
						protection = LOTRBannerProtection.isProtectedByBanner(user.worldObj, i, j1, k, LOTRBannerProtection.forPlayer((EntityPlayer)user), false);
					}
					else if (user instanceof EntityLiving)
					{
						protection = LOTRBannerProtection.isProtectedByBanner(user.worldObj, i, j1, k, LOTRBannerProtection.forNPC((EntityLiving)user), false);
					}
					
					if (!protection)
					{
						user.worldObj.setBlock(i, j1, k, Blocks.fire, 0, 3);
						break fireLoop;
					}
				}
			}
		}
	}
	
	@Override
	public int getItemEnchantability()
    {
        return 0;
    }

	@Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem)
    {
        return false;
    }
}
