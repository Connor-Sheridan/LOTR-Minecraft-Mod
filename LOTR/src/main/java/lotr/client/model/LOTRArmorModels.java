package lotr.client.model;

import java.util.HashMap;
import java.util.Map;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LOTRArmorModels
{
	public static LOTRModelLeatherHat leatherHat = new LOTRModelLeatherHat();
	
	private static Map<Item, ModelBiped> specialArmorModels = new HashMap();
	private static Map<Item, String> specialArmorNames = new HashMap();
	private static boolean registered = false;
	
	private static void registerModels()
	{
		registerModel(LOTRMod.helmetGondorWinged, new LOTRModelWingedHelmet(1F), "wingedHelmet");
		registerModel(LOTRMod.helmetMorgul, new LOTRModelMorgulHelmet(1F), "helmet");
		registerModel(LOTRMod.helmetGemsbok, new LOTRModelGemsbokHelmet(1F));
		registerModel(LOTRMod.helmetHighElven, new LOTRModelElvenHelmet(1F));
		registerModel(LOTRMod.helmetBlackUruk, new LOTRModelBlackUrukHelmet(1F));
		registerModel(LOTRMod.helmetUruk, new LOTRModelUrukHelmet(1F), "helmet");
		registerModel(LOTRMod.helmetNearHaradWarlord, new LOTRModelNearHaradWarlordHelmet(1F), "warlordHelmet");
		
		registered = true;
	}
	
	private static void registerModel(Item item, ModelBiped model)
	{
		registerModel(item, model, null);
	}
	
	private static void registerModel(Item item, ModelBiped model, String name)
	{
		specialArmorModels.put(item, model);
		if (name != null)
		{
			specialArmorNames.put(item, name);
		}
	}

	public static ModelBiped getSpecialArmorModel(ItemStack itemstack)
	{
		if (!registered)
		{
			registerModels();
		}
		
		Item item = itemstack.getItem();
		ModelBiped model = specialArmorModels.get(item);
		return model;
	}
	
	public static String getArmorName(LOTRItemArmor armor)
	{
		if (!registered)
		{
			registerModels();
		}
		
		String prefix = armor.getArmorMaterial().name().substring(LOTRMod.getModID().length() + 1).toLowerCase();
		String suffix = specialArmorNames.get(armor);
		if (suffix == null)
		{
			suffix = armor.armorType == 2 ? "2" : "1";
		}
		
		return prefix + "_" + suffix;
	}
}
