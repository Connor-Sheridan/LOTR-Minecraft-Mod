package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTREntityBlueDwarf extends LOTREntityDwarf
{
	public LOTREntityBlueDwarf(World world)
	{
		super(world);
		
		familyInfo.marriageEntityClass = LOTREntityBlueDwarf.class;
		familyInfo.marriageAchievement = LOTRAchievement.marryBlueDwarf;
	}
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.BLUE_MOUNTAINS;
	}
	
	@Override
	protected Item getDwarfDagger()
	{
		return LOTRMod.daggerBlueDwarven;
	}
	
	@Override
	protected Item getDwarfSteelDrop()
	{
		return LOTRMod.blueDwarfSteel;
	}

	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killBlueDwarf;
	}
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.BLUE_DWARF;
	}
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		if (isFriendly(entityplayer))
		{
			if (hiredNPCInfo.getHiringPlayer() == entityplayer)
			{
				return "blueDwarf/dwarf/hired";
			}
			return isChild() ? "blueDwarf/child/friendly" : "blueDwarf/dwarf/friendly";
		}
		else
		{
			return isChild() ? "blueDwarf/child/hostile" : "blueDwarf/dwarf/hostile";
		}
	}
	
	@Override
	public LOTRMiniQuest createMiniQuest(EntityPlayer entityplayer)
	{
		return LOTRMiniQuestFactory.BLUE_MOUNTAINS.createQuest(entityplayer);
	}
}
