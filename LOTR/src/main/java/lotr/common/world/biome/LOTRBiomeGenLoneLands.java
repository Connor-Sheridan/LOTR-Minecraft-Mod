package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFaction;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.*;
import lotr.common.world.structure.LOTRWorldGenGundabadCamp;
import lotr.common.world.structure.LOTRWorldGenRangerCamp;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenRangerWatchtower;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenLoneLands extends LOTRBiome
{
	private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 5);
	
	public LOTRBiomeGenLoneLands(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
		
		spawnableGoodList.add(new SpawnListEntry(LOTREntityRangerNorth.class, 10, 4, 4));
		
		spawnableEvilList.add(new SpawnListEntry(LOTREntityGundabadWarg.class, 6, 4, 4));
		
		setGoodEvilWeight(2, 98);
		
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 3;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 6;
		decorator.generateAthelas = true;
		
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
		decorator.addTree(LOTRTreeType.SPRUCE, 300);
		decorator.addTree(LOTRTreeType.BEECH, 100);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 50);
		
        registerPlainsFlowers();
        
        biomeColors.setGrass(0xC4BA64);
		
		decorator.generateOrcDungeon = true;
		
		decorator.addRandomStructure(new LOTRWorldGenGundabadCamp(), 1500);
		decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 800);
		decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenRangerCamp(), 2000);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 200);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 200);
		decorator.addRandomStructure(new LOTRWorldGenRangerWatchtower(false), 2000);
		
		registerTravellingTrader(LOTREntityElvenTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		registerTravellingTrader(LOTREntityNearHaradMerchant.class);
		
		setBanditChance(LOTRBanditSpawner.COMMON);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GUNDABAD, LOTRInvasionSpawner.COMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.RANGER_NORTH, LOTRInvasionSpawner.COMMON));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.ANGMAR, LOTRInvasionSpawner.RARE));
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterLoneLands;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.LONE_LANDS;
	}
	
	@Override
    public void decorate(World world, Random random, int i, int k)
    {
        super.decorate(world, random, i, k);
		
		if (random.nextInt(8) == 0)
		{
			for (int l = 0; l < 3; l++)
			{
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
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
