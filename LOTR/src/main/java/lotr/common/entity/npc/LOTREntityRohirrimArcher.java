package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREntityRohirrimArcher extends LOTREntityRohirrim implements IRangedAttackMob
{
	public LOTREntityRohirrimArcher(World world)
	{
		super(world);
	}
	
	@Override
	public EntityAIBase createRohanAttackAI()
	{
		return new EntityAIArrowAttack(this, 1.25D, 30, 50, 16F);
	}
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.rohanBow));
		return data;
    }
	
	@Override
	public String getCommandSenderName()
	{
		return StatCollector.translateToLocalFormatted("entity.lotr.RohirrimArcher.entityName", new Object[] {getNPCName()});
	}
	
	@Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f)
    {
        EntityArrow arrow = new EntityArrow(worldObj, this, target, 1.5F, 1F);
        playSound("random.bow", 1F, 1F / (rand.nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(arrow);
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
