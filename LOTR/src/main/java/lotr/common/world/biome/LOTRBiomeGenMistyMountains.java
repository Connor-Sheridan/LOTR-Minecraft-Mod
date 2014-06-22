package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.npc.LOTREntityGundabadWarg;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.npc.LOTREntityUrukHaiBerserker;
import lotr.common.entity.npc.LOTREntityUrukHaiCrossbower;
import lotr.common.entity.npc.LOTREntityUrukWarg;
import lotr.common.world.feature.LOTRWorldGenLarch;
import lotr.common.world.structure.LOTRWorldGenStoneRuin;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenMistyMountains extends LOTRBiome
{
	private WorldGenerator mithrilGen = new WorldGenMinable(LOTRMod.oreMithril, 6);
	
	public LOTRBiomeGenMistyMountains(int i)
	{
		super(i);

		spawnableCreatureList.clear();
		
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHai.class, 20, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiCrossbower.class, 7, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukHaiBerserker.class, 3, 1, 2));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityGundabadWarg.class, 20, 4, 4));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityUrukWarg.class, 20, 4, 4));
		
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		decorator.generateWater = false;
		
		registerMountainsFlowers();
		
		decorator.generateOrcDungeon = true;
		
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin(1, 4), 2000);
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterMistyMountains;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.MISTY_MOUNTAINS;
	}
	
	@Override
	public boolean getEnableRiver()
	{
		return false;
	}
	
	public boolean isSnowCovered()
	{
		return true;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
		genStandardOre(world, random, i, k, 2, mithrilGen, 0, 16);
		
        super.decorate(world, random, i, k);
		
		for (int count = 0; count < 6; count++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			if (j1 < 100)
			{
				func_150567_a(random).generate(world, random, i1, j1, k1);
			}
		}
		
		for (int count = 0; count < 3; count++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
			if (j1 < 100)
			{
				getRandomWorldGenForGrass(random).generate(world, random, i1, j1, k1);
			}
		}
    }
	
	@Override
    public WorldGenAbstractTree func_150567_a(Random random)
    {
		if (random.nextInt(20) == 0)
		{
			if (random.nextInt(10) == 0)
			{
				return new WorldGenMegaPineTree(false, true);
			}
			return new WorldGenMegaPineTree(false, false);
		}
		else if (random.nextInt(5) == 0)
		{
			return new LOTRWorldGenLarch(false);
		}
        return random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2(false);
    }
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0F;
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0F;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float f)
    {
		return 0xBACBD1;
    }
}
