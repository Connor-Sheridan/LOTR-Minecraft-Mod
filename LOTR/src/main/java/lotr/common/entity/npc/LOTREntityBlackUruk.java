package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackUruk extends LOTREntityMordorOrc
{
	public LOTREntityBlackUruk(World world)
	{
		super(world);
		setSize(0.6F, 1.8F);
		isWeakOrc = false;
		npcShield = LOTRShields.ALIGNMENT_MORDOR;
	}
	
	@Override
	public EntityAIBase createOrcAttackAI()
	{
		return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24D);
    }
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(8);
		
		if (i == 0 || i == 1 || i == 2)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.scimitarBlackUruk));
		}
		else if (i == 3)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.battleaxeBlackUruk));
		}
		else if (i == 4)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.daggerBlackUruk));
		}
		else if (i == 5)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.daggerBlackUrukPoisoned));
		}
		else if (i == 6)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.spearBlackUruk));
		}
		else if (i == 7)
		{
			setCurrentItemOrArmor(0, new ItemStack(LOTRMod.hammerBlackUruk));
		}
		
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlackUruk));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlackUruk));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlackUruk));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetBlackUruk));
		
		return data;
    }
	
	@Override
	public LOTRFaction getFaction()
	{
		return LOTRFaction.MORDOR;
	}
	
	@Override
	public int getAlignmentBonus()
	{
		return LOTRAlignmentValues.Bonuses.BLACK_URUK;
	}
	
	@Override
	protected float getSoundPitch()
	{
		return super.getSoundPitch() * 0.75F;
	}
	
	@Override
	public boolean canOrcSkirmish()
	{
		return false;
	}
}
