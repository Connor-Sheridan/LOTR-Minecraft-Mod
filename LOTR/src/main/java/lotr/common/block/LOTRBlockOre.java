package lotr.common.block;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockOre extends Block
{
	public LOTRBlockOre()
	{
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
	}
	
	@Override
    public Item getItemDropped(int i, Random random, int j)
    {
		if (this == LOTRMod.oreNaurite)
		{
			return LOTRMod.nauriteGem;
		}
		if (this == LOTRMod.oreQuendite)
		{
			return LOTRMod.quenditeCrystal;
		}
		if (this == LOTRMod.oreGlowstone)
		{
			return Items.glowstone_dust;
		}
		if (this == LOTRMod.oreGulduril)
		{
			return LOTRMod.guldurilCrystal;
		}
		if (this == LOTRMod.oreSulfur)
		{
			return LOTRMod.sulfur;
		}
		if (this == LOTRMod.oreSaltpeter)
		{
			return LOTRMod.saltpeter;
		}
        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(Random random)
    {
		if (this == LOTRMod.oreNaurite)
		{
			return 1 + random.nextInt(2);
		}
		if (this == LOTRMod.oreGlowstone)
		{
			return 2 + random.nextInt(4);
		}
		if (this == LOTRMod.oreSulfur || this == LOTRMod.oreSaltpeter)
		{
			return 1 + random.nextInt(2);
		}
        return 1;
    }

	@Override
    public int quantityDroppedWithBonus(int i, Random random)
    {
        if (i > 0 && Item.getItemFromBlock(this) != getItemDropped(0, random, i))
        {
            int j = random.nextInt(i + 2) - 1;
            if (j < 0)
            {
                j = 0;
            }
            return quantityDropped(random) * (j + 1);
        }
        else
        {
            return quantityDropped(random);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune)
    {
        super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);

        if (getItemDropped(meta, world.rand, fortune) != Item.getItemFromBlock(this))
        {
            int amountXp = 0;

            if (this == LOTRMod.oreNaurite)
            {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreQuendite)
            {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreGlowstone)
            {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreGulduril)
            {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            if (this == LOTRMod.oreSulfur || this == LOTRMod.oreSaltpeter)
            {
                amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }

            dropXpOnBlockBreak(world, i, j, k, amountXp);
        }
    }
	
	@Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {
		super.harvestBlock(world, entityplayer, i, j, k, l);
		if (!world.isRemote)
		{
			if (this == LOTRMod.oreMithril)
			{
				LOTRLevelData.addAchievement(entityplayer, LOTRAchievement.mineMithril);
			}
			if (this == LOTRMod.oreQuendite)
			{
				LOTRLevelData.addAchievement(entityplayer, LOTRAchievement.mineQuendite);
			}
			if (this == LOTRMod.oreGlowstone)
			{
				LOTRLevelData.addAchievement(entityplayer, LOTRAchievement.mineGlowstone);
			}
			if (this == LOTRMod.oreNaurite)
			{
				LOTRLevelData.addAchievement(entityplayer, LOTRAchievement.mineNaurite);
			}
			if (this == LOTRMod.oreGulduril)
			{
				LOTRLevelData.addAchievement(entityplayer, LOTRAchievement.mineGulduril);
			}
		}
	}
}
