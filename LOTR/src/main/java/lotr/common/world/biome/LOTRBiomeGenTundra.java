package lotr.common.world.biome;

import lotr.common.LOTRWaypoint;
import lotr.common.entity.npc.LOTREntityGundabadWarg;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import net.minecraft.entity.passive.EntityWolf;

public class LOTRBiomeGenTundra extends LOTRBiome
{
	public LOTRBiomeGenTundra(int i)
	{
		super(i);
		
		setEnableSnow();
		
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 10, 4, 8));
		
		spawnableGoodList.clear();
		
		spawnableEvilList.add(new SpawnListEntry(LOTREntityGundabadWarg.class, 12, 4, 4));
		
		setGoodEvilWeight(0, 100);

		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 4;
		decorator.doubleGrassPerChunk = 1;
		decorator.generateOrcDungeon = true;
		
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
		decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 100);
		
		registerTaigaFlowers();
		
		decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 1500);
		
		setBanditChance(LOTRBanditSpawner.UNCOMMON);
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.FORODWAITH;
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.04F;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.02F;
	}
	
	@Override
	public int getSnowHeight()
	{
		return 68;
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return 4;
	}
}
