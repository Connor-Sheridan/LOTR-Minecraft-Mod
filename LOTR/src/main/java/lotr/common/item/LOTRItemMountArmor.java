package lotr.common.item;

import java.util.HashMap;
import java.util.Map;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRReflection;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRItemMountArmor extends Item
{
	public static enum Mount
	{
		HORSE,
		ELK,
		BOAR,
		CAMEL;
	}
	
	private ArmorMaterial armorMaterial;
	private Mount mountType;
	private int damageReduceAmount;
	private Item templateItem;
	
	public LOTRItemMountArmor(ArmorMaterial material, Mount mount)
	{
		super();
		armorMaterial = material;
		damageReduceAmount = material.getDamageReductionAmount(1) + material.getDamageReductionAmount(2);
		mountType = mount;
		setMaxStackSize(1);
		setCreativeTab(LOTRCreativeTabs.tabCombat);
	}
	
	public LOTRItemMountArmor setTemplateItem(Item item)
	{
		templateItem = item;
		return this;
	}
	
	@Override
    public String getItemStackDisplayName(ItemStack itemstack)
    {
		if (templateItem != null)
		{
			return templateItem.getItemStackDisplayName(createTemplateItemStack(itemstack));
		}
		else
		{
			return super.getItemStackDisplayName(itemstack);
		}
    }

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack itemstack)
    {
		if (templateItem != null)
		{
			return templateItem.getIconIndex(createTemplateItemStack(itemstack));
		}
		else
		{
			return super.getIconIndex(itemstack);
		}
    }
	
	private ItemStack createTemplateItemStack(ItemStack source)
	{
		ItemStack template = new ItemStack(templateItem);
		template.stackSize = source.stackSize;
		template.setItemDamage(source.getItemDamage());
		if (source.getTagCompound() != null)
		{
			template.setTagCompound(source.getTagCompound());
		}
		return template;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister)
	{
		if (templateItem == null)
		{
			super.registerIcons(iconregister);
		}
	}
	
	public boolean isValid(LOTREntityHorse horse)
	{
		if (horse instanceof LOTREntityElk)
		{
			return mountType == Mount.ELK;
		}
		return mountType == Mount.HORSE;
	}
	
	public int getDamageReduceAmount()
	{
		return damageReduceAmount;
	}

	@Override
    public int getItemEnchantability()
    {
        return 0;
    }
	
	@Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem)
    {
        return armorMaterial.func_151685_b() == repairItem.getItem() ? true : super.getIsRepairable(itemstack, repairItem);
    }
	
	private static Map textures = new HashMap();
	
	public ResourceLocation getArmorTexture()
	{
		String path = null;
		
		if (templateItem != null)
		{
			int index = 0;
			
			if (templateItem == Items.iron_horse_armor)
			{
				index = 1;
			}
			if (templateItem == Items.golden_horse_armor)
			{
				index = 2;
			}
			if (templateItem == Items.diamond_horse_armor)
			{
				index = 3;
			}
			
			path = LOTRReflection.getHorseArmorTextures()[index];
		}
		else
		{
			String mountName = mountType.name().toLowerCase();
			String materialName = armorMaterial.name().toLowerCase();
			if (materialName.startsWith("lotr_"))
			{
				materialName = materialName.substring(0, "lotr_".length());
			}
			path = "lotr:armor/mount/" + mountName + "_" + materialName + ".png";
		}
		
		ResourceLocation texture = (ResourceLocation)textures.get(path);
		if (texture == null)
		{
			texture = new ResourceLocation(path);
			textures.put(path, texture);
		}
		
		return texture;
	}
}