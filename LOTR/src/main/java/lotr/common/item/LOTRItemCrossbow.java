package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.item.LOTRItemBow.BowState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRItemCrossbow extends ItemBow
{
	private double boltDamageBonus;
	@SideOnly(Side.CLIENT)
	private IIcon[] crossbowPullIcons;
	private ToolMaterial crossbowMaterial;
	
	public LOTRItemCrossbow(ToolMaterial material)
	{
		super();
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		
		crossbowMaterial = material;
		setMaxDamage((int)(crossbowMaterial.getMaxUses() * 1.25F));
		setMaxStackSize(1);
		
		boltDamageBonus = (crossbowMaterial.getDamageVsEntity() - 2F) * 0.2F;
		if (boltDamageBonus < 0F)
		{
			boltDamageBonus = 0F;
		}
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	if (isLoaded(itemstack))
    	{
    		if (!world.isRemote)
    		{
    			setLoaded(itemstack, false);
    		}
    		
            float f = (float)getMaxDrawTime();
            f = (f * f + f * 2F) / 3F;
            f = Math.min(f, 1F);

            LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, entityplayer, f * 2F);
			bolt.boltDamageFactor += boltDamageBonus;
			if (bolt.boltDamageFactor < 1D)
			{
				bolt.boltDamageFactor = 1D;
			}

            if (f == 1F)
            {
                bolt.setIsCritical(true);
            }
			
            int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
            if (power > 0)
            {
                bolt.boltDamageFactor += (double)power * 0.5D + 0.5D;
            }
			
	        int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
            if (punch > 0)
            {
                bolt.knockbackStrength = punch;
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
            {
                bolt.setFire(1000);
            }

            if (!shouldConsumeBolt(itemstack, entityplayer))
            {
                bolt.canBePickedUp = 2;
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(bolt);
            }
            
            world.playSoundAtEntity(entityplayer, "lotr:item.crossbow", 1F, 1F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            itemstack.damageItem(1, entityplayer);
            if (!world.isRemote)
            {
            	setLoaded(itemstack, false);
            }
    	}
    	else
    	{
	        if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.hasItem(LOTRMod.crossbowBolt))
	        {
	            entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
	        }
    	}
        return itemstack;
    }
	
	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count)
    {
		World world = entityplayer.worldObj;
		if (!world.isRemote && !isLoaded(itemstack) && getMaxItemUseDuration(itemstack) - count == getMaxDrawTime())
		{
			world.playSoundAtEntity(entityplayer, "lotr:item.crossbowLoad", 1F, 1.5F + world.rand.nextFloat() * 0.2F);
		}
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int useTick)
    {
		int ticksInUse = getMaxItemUseDuration(itemstack) - useTick;
        if (ticksInUse >= getMaxDrawTime() && !isLoaded(itemstack))
        {
        	boolean consumeBolt = shouldConsumeBolt(itemstack, entityplayer);
        	if (!consumeBolt || entityplayer.inventory.hasItem(LOTRMod.crossbowBolt))
            {
        		if (consumeBolt)
                {
                    entityplayer.inventory.consumeInventoryItem(LOTRMod.crossbowBolt);
                }
        		if (!world.isRemote)
        		{
        			setLoaded(itemstack, true);
        		}
            }
        	entityplayer.clearItemInUse();
        }
    }
	
	public int getMaxDrawTime()
	{
		return 50;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 72000;
    }

    public static boolean isLoaded(ItemStack itemstack)
    {
    	if (itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow)
    	{
    		NBTTagCompound nbt = itemstack.getTagCompound();
    		if (nbt == null || !nbt.hasKey("LOTRCrossbowLoaded"))
    		{
    			return false;
    		}
    		return nbt.getBoolean("LOTRCrossbowLoaded");
    	}
    	return false;
    }
    
    private void setLoaded(ItemStack itemstack, boolean loaded)
    {
    	if (itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow)
    	{
    		NBTTagCompound nbt = itemstack.getTagCompound();
    		if (nbt == null)
    		{
    			nbt = new NBTTagCompound();
    			itemstack.setTagCompound(nbt);
    		}
    		nbt.setBoolean("LOTRCrossbowLoaded", loaded);
    	}
    }
    
    private boolean shouldConsumeBolt(ItemStack itemstack, EntityPlayer entityplayer)
    {
    	return !entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0;
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack itemstack)
    {
        String name = super.getItemStackDisplayName(itemstack);
        if (isLoaded(itemstack))
        {
        	name = StatCollector.translateToLocalFormatted("item.lotr.crossbow.loaded", name);
        }
        return name;
    }
	
	@Override
    public int getItemEnchantability()
    {
        return 1 + crossbowMaterial.getEnchantability() / 5;
    }
	
	@Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem)
    {
        return crossbowMaterial.func_150995_f() == repairItem.getItem() ? true : super.getIsRepairable(itemstack, repairItem);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining)
    {
		if (isLoaded(itemstack))
		{
			return crossbowPullIcons[2];
		}
		else
		{
			if (usingItem != null && usingItem.getItem() == this)
			{
				int ticksInUse = usingItem.getMaxItemUseDuration() - useRemaining;
				double useAmount = (double)ticksInUse / (double)getMaxDrawTime();
				if (useAmount >= 1D)
				{
					return crossbowPullIcons[2];
				}
				else if (useAmount > 0.5D)
				{
					return crossbowPullIcons[1];
				}
				else if (useAmount > 0D)
				{
					return crossbowPullIcons[0];
				}
			}
		}
		return itemIcon;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack itemstack, int pass)
    {
		if (isLoaded(itemstack))
		{
			return crossbowPullIcons[2];
		}
		else
		{
			return itemIcon;
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	
	@Override
	public int getRenderPasses(int metadata)
    {
        return 1;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconregister)
    {
		itemIcon = iconregister.registerIcon(getIconString());
		crossbowPullIcons = new IIcon[3];
		crossbowPullIcons[0] = iconregister.registerIcon(getIconString() + BowState.PULL_0.iconName);
		crossbowPullIcons[1] = iconregister.registerIcon(getIconString() + BowState.PULL_1.iconName);
		crossbowPullIcons[2] = iconregister.registerIcon(getIconString() + BowState.PULL_2.iconName);
    }
}
