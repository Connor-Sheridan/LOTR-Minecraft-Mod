package lotr.common.item;

import static lotr.common.item.LOTRItemBow.BowState.*;

import java.util.Arrays;

import lotr.client.render.item.LOTRRenderBow;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRItemBow extends ItemBow
{
	private ToolMaterial bowMaterial;
	private double arrowDamageBonus;
	private float arrowRangeBonus;
	@SideOnly(Side.CLIENT)
	private IIcon[] bowPullIcons;
	private int bowPullTime;
	
	public LOTRItemBow(ToolMaterial material, double d, float f)
	{
		super();
		bowMaterial = material;
		setMaxDamage((int)(material.getMaxUses() * 1.5F));
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		arrowDamageBonus = d;
		arrowRangeBonus = f;
		bowPullTime = 20;
	}
	
	public LOTRItemBow setDrawTime(int i)
	{
		bowPullTime = i;
		return this;
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
    {
        int useTick = getMaxItemUseDuration(itemstack) - i;
        boolean consumeNoArrow = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;

        if (consumeNoArrow || entityplayer.inventory.hasItem(Items.arrow))
        {
            float f = (float)useTick / getMaxDrawTime();
            f = (f * f + f * 2F) / 3F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1F)
            {
                f = 1F;
            }

            EntityArrow arrow = new EntityArrow(world, entityplayer, (f * 2F) * (1F + arrowRangeBonus));
			arrow.setDamage(arrow.getDamage() + arrowDamageBonus);
			if (arrow.getDamage() < 1D)
			{
				arrow.setDamage(1D);
			}

            if (f == 1F)
            {
                arrow.setIsCritical(true);
            }

            int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
            if (power > 0)
            {
                arrow.setDamage(arrow.getDamage() + (double)power * 0.5D + 0.5D);
            }

            int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
            if (punch > 0)
            {
                arrow.setKnockbackStrength(punch);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
            {
                arrow.setFire(100);
            }

            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "random.bow", 1F, 1F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (consumeNoArrow)
            {
                arrow.canBePickedUp = 2;
            }
            else
            {
                entityplayer.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(arrow);
            }
        }
    }
	
	public float getMaxDrawTime()
	{
		return (float)bowPullTime;
	}

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.hasItem(Items.arrow))
        {
            entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }

    @Override
    public int getItemEnchantability()
    {
        return 1 + bowMaterial.getEnchantability() / 5;
    }
	
	@Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem)
    {
        return repairItem.getItem() == Items.string ? true : super.getIsRepairable(itemstack, repairItem);
    }
	
	public BowState getBowState(EntityLivingBase entity, ItemStack usingItem, int useRemaining)
	{
		if (entity instanceof EntityPlayer && usingItem != null && usingItem.getItem() == this)
		{
			int ticksInUse = usingItem.getMaxItemUseDuration() - useRemaining;
			double useAmount = (double)ticksInUse / (double)bowPullTime;
			if (useAmount >= 0.9D)
			{
				return PULL_2;
			}
			else if (useAmount > 0.65D)
			{
				return PULL_1;
			}
			else if (useAmount > 0D)
			{
				return PULL_0;
			}
		}
		return HELD;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining)
    {
		BowState bowState = getBowState(entityplayer, usingItem, useRemaining);
		if (bowState == PULL_0)
		{
			return bowPullIcons[0];
		}
		if (bowState == PULL_1)
		{
			return bowPullIcons[1];
		}
		if (bowState == PULL_2)
		{
			return bowPullIcons[2];
		}
		return itemIcon;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconregister)
    {
		itemIcon = iconregister.registerIcon(getIconString());
		bowPullIcons = new IIcon[3];
		
		IItemRenderer bowRenderer = MinecraftForgeClient.getItemRenderer(new ItemStack(this), IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
		if (bowRenderer instanceof LOTRRenderBow && ((LOTRRenderBow)bowRenderer).isLargeBow)
		{
			Arrays.fill(bowPullIcons, itemIcon);
		}
		else
		{
			bowPullIcons[0] = iconregister.registerIcon(getIconString() + PULL_0.iconName);
			bowPullIcons[1] = iconregister.registerIcon(getIconString() + PULL_1.iconName);
	        bowPullIcons[2] = iconregister.registerIcon(getIconString() + PULL_2.iconName);
		}
    }
	
	public static enum BowState
	{
		HELD(""),
		PULL_0("_pull_0"),
		PULL_1("_pull_1"),
		PULL_2("_pull_2");
		
		public final String iconName;
		
		private BowState(String s)
		{
			iconName = s;
		}
	}
}
