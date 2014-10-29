package lotr.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;

public class LOTRItemDagger extends LOTRItemSword
{
	private int effect;
	
	public LOTRItemDagger(ToolMaterial material, int j)
	{
		super(material);
		setMaxDamage(MathHelper.floor_double(material.getMaxUses() * 0.8D));
		effect = j;
		lotrWeaponDamage = material.getDamageVsEntity() + 3F;
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user)
	{
		itemstack.damageItem(1, user);
		if (effect == 0)
		{
			return true;
		}
		else if (effect == 1)
		{
			EnumDifficulty difficulty = user.worldObj.difficultySetting;
			int duration = 1 + difficulty.getDifficultyId() * 2;
			PotionEffect poison = new PotionEffect(Potion.poison.id, (duration + itemRand.nextInt(duration)) * 20);
			hitEntity.addPotionEffect(poison);
		}
		return true;
	}
}
