package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUtumnoOrcArcher extends LOTREntityUtumnoOrc implements IRangedAttackMob
{
	public LOTREntityUtumnoOrcArcher(World world)
	{
		super(world);
	}
	
	@Override
	public EntityAIBase createOrcAttackAI()
	{
		return new EntityAIArrowAttack(this, 1.4D, 30, 50, 16F);
	}
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.utumnoBow));
		return data;
	}
	
	@Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f)
    {
		orcArrowAttack(target, f);
    }
	
	@Override
	protected void dropFewItems(boolean flag, int i)
	{
		super.dropFewItems(flag, i);
		
		if (rand.nextBoolean())
		{
			int j = rand.nextInt(3) + rand.nextInt(i + 1);
			for (int k = 0; k < j; k++)
			{
				dropItem(Items.arrow, 1);
			}
		}
	}
}
