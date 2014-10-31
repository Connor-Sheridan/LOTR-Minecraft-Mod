package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.npc.LOTREntityAngmarOrcArcher;
import lotr.common.entity.npc.LOTREntityAngmarWarg;
import lotr.common.entity.npc.LOTREntityGundabadWarg;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure.LOTRWorldGenStoneRuin;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEttenmoors extends LOTRBiome
{
	private WorldGenerator boulderGenLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 2, 5);
	private WorldGenerator boulderGenSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
	
	public LOTRBiomeGenEttenmoors(int i)
	{
		super(i);

		spawnableCreatureList.clear();
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 10, 4, 8));

		spawnableEvilList.add(new SpawnListEntry(LOTREntityTroll.class, 40, 1, 3));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityMountainTroll.class, 20, 1, 3));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityGundabadWarg.class, 10, 4, 4));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityAngmarOrc.class, 10, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityAngmarOrcArcher.class, 5, 4, 6));
		spawnableEvilList.add(new SpawnListEntry(LOTREntityAngmarWarg.class, 5, 4, 4));
		
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 4;
		decorator.doubleGrassPerChunk = 2;
		decorator.generateAthelas = true;
		
		decorator.addTree(LOTRTreeType.SPRUCE, 1000);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 500);
		decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 100);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
		
		registerTaigaFlowers();
		
		decorator.generateOrcDungeon = true;
		decorator.generateTrollHoard = true;
		
		decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 100);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 100);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 100);
		
		setBanditChance(LOTRBanditSpawner.UNCOMMON);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.RANGER_NORTH, LOTRInvasionSpawner.UNCOMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GUNDABAD, LOTRInvasionSpawner.COMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.ANGMAR, LOTRInvasionSpawner.COMMON));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterEttenmoors;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.ETTENMOORS;
	}

	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		for (int l = 0; l < 3; l++)
		{
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1);
			if (j1 > 84)
			{
				func_150567_a(random).generate(world, random, i1, j1, k1);
			}
		}
		
		if (random.nextInt(4) == 0)
		{
			for (int l = 0; l < 3; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGenLarge.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
		
		for (int l = 0; l < 2; l++)
		{
			int i1 = i + random.nextInt(16) + 8;
			int k1 = k + random.nextInt(16) + 8;
			boulderGenSmall.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
		}
	}

	@Override
	public float getTreeIncreaseChance()
	{
		return 0.25F;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.1F;
	}
}
