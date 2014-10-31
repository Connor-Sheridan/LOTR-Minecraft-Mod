package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenFarHaradCloudForest extends LOTRBiomeGenFarHaradJungle
{
	public LOTRBiomeGenFarHaradCloudForest(int i)
	{
		super(i);

		decorator.treesPerChunk = 10;
		
		decorator.addTree(LOTRTreeType.JUNGLE_CLOUD, 5000);
		
		biomeColors.setGrass(0x208E4E);
		biomeColors.setFoliage(0x05722A);
		biomeColors.setSky(0xAEC1BB);
		biomeColors.setFoggy(true);
	}
	
	@Override
	public float getChanceToSpawnAnimals()
	{
		return super.getChanceToSpawnAnimals() * 0.5F;
	}
}
