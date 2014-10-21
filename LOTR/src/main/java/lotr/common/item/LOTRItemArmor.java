package lotr.common.item;

import lotr.client.model.LOTRArmorModels;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRItemArmor extends ItemArmor
{
	public LOTRItemArmor(ArmorMaterial material, int slotType)
	{
		super(material, 0, slotType);
		setCreativeTab(LOTRCreativeTabs.tabCombat);
	}
	
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type)
	{
		String filename = LOTRArmorModels.getArmorName(this);
		String path = entity instanceof LOTREntityDwarf ? "lotr:mob/dwarf/" : "lotr:armor/";
		return path + filename + ".png";
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack itemstack, int slot)
    {
        ModelBiped specialModel = LOTRArmorModels.getSpecialArmorModel(itemstack);
        if (specialModel != null)
        {
        	return specialModel;
        }
		return super.getArmorModel(entity, itemstack, slot);
    }
}
