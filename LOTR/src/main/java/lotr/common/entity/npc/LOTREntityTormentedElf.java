package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityTormentedElf extends LOTREntityElf
{
	public LOTREntityTormentedElf(World world)
	{
		super(world);
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24D);
    }
	
	@Override
	public Item getElfSwordId()
	{
		return LOTRMod.swordUtumno;
	}

	@Override
	public Item getElfBowId()
	{
		return LOTRMod.utumnoBow;
	}
	
	@Override
	public void onAttackModeChange(AttackMode mode)
	{
		if (mode == AttackMode.IDLE)
		{
			setCurrentItemOrArmor(0, new ItemStack(getElfSwordId(), 1, 0));
		}
		else
		{
			super.onAttackModeChange(mode);
		}
	}
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.UTUMNO;
	}
	
	@Override
	protected LOTRAchievement getKillAchievement()
	{
		return LOTRAchievement.killTormentedElf;
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		super.dropFewItems(flag, i);
	}
	
	@Override
	public boolean canElfSpawnHere()
	{
		return true;
	}
	
	@Override
	public String getSpeechBank(EntityPlayer entityplayer)
	{
		return "tormentedElf_hostile";
	}
	
	@Override
	public String getLivingSound()
	{
		return null;
	}
	
	@Override
	public String getAttackSound()
	{
		return null;
	}
}
