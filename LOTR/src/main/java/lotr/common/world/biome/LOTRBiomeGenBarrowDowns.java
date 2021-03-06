package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRWaypoint;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.LOTRBanditSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenDeadTrees;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenBDBarrow;
import net.minecraft.util.Vec3;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBiomeGenBarrowDowns extends LOTRBiome
{
	public LOTRBiomeGenBarrowDowns(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(LOTREntityHorse.class, 8, 2, 6));
		
		spawnableEvilList.clear();
		
		setGoodEvilWeight(0, 100);
		
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 6;
		decorator.generateAthelas = true;
		
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
		decorator.addTree(LOTRTreeType.OAK_DEAD, 1500);
		decorator.addTree(LOTRTreeType.SPRUCE, 500);
		decorator.addTree(LOTRTreeType.BIRCH, 150);
		
        registerPlainsFlowers();
        
        biomeColors.setGrass(0x718E59);
        biomeColors.setFoliage(0x868766);
        biomeColors.setSky(0x8CA088);
        biomeColors.setClouds(0xB4B4B4);
        biomeColors.setFog(0x9B9B9B);
		
		decorator.generateOrcDungeon = true;
		
		decorator.addRandomStructure(new LOTRWorldGenBDBarrow(false), 5);
		decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(2, 7), 30);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(2, 7), 30);
		
		setBanditChance(LOTRBanditSpawner.NEVER);
	}
	
	@Override
	public LOTRAchievement getBiomeAchievement()
	{
		return LOTRAchievement.enterBarrowDowns;
	}
	
	@Override
	public LOTRWaypoint.Region getBiomeWaypoints()
	{
		return LOTRWaypoint.Region.ERIADOR;
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return 0.1F;
	}
	
	@Override
	public float getTreeIncreaseChance()
	{
		return 0.4F;
	}
	
	@Override
	public float getChanceToSpawnLakes()
	{
		return 0.2F;
	}
}
