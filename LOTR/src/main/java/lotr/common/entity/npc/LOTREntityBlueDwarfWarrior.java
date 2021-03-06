package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC.AttackMode;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfWarrior extends LOTREntityBlueDwarf
{
	public LOTREntityBlueDwarfWarrior(World world)
	{
		super(world);
		npcShield = LOTRShields.ALIGNMENT_BLUE_MOUNTAINS;
	}
	
	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(6);
		
		if (i == 0)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.swordBlueDwarven));
		}
		else if (i == 1 || i == 2)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.battleaxeBlueDwarven));
		}
		else if (i == 3 || i == 4)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.hammerBlueDwarven));
		}
		else if (i == 5)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.spearBlueDwarven));
		}
		
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlueDwarven));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlueDwarven));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlueDwarven));
		
		if (rand.nextInt(10) != 0)
		{
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetBlueDwarven));
		}
		
		return data;
	}
	
	@Override
	public void onAttackModeChange(AttackMode mode) {}
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.BLUE_DWARF_WARRIOR;
	}
}
