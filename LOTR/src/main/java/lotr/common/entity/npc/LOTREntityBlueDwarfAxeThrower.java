package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfAxeThrower extends LOTREntityBlueDwarfWarrior implements IRangedAttackMob
{
	public LOTREntityBlueDwarfAxeThrower(World world)
	{
		super(world);
	}
	
	@Override
	public EntityAIBase getDwarfAttackAI()
	{
		return new EntityAIArrowAttack(this, 1.25D, 40, 12F);
	}
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.throwingAxeBlueDwarven));
		return data;
	}

	@Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f)
    {
        LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(worldObj, this, target, new ItemStack(LOTRMod.throwingAxeBlueDwarven), 1F, 0.75F);
        playSound("random.bow", 1F, 1F / (rand.nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(axe);
		swingItem();
    }
}
