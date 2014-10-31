package lotr.common.world.biome;

import lotr.common.LOTRFaction;
import lotr.common.world.LOTRInvasionSpawner;
import lotr.common.world.LOTRInvasionSpawner.BiomeInvasionListEntry;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenLothlorienEdge extends LOTRBiomeGenLothlorien
{
	public LOTRBiomeGenLothlorienEdge(int i)
	{
		super(i);

		decorator.treesPerChunk = 3;
		decorator.flowersPerChunk = 2;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 1;
		
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.OAK, 300);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
		decorator.addTree(LOTRTreeType.LARCH, 200);
		decorator.addTree(LOTRTreeType.BEECH, 100);
		decorator.addTree(LOTRTreeType.MALLORN, 100);
		
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.GUNDABAD, LOTRInvasionSpawner.RARE));
		invasionSpawns.add(new BiomeInvasionListEntry(LOTRFaction.DOL_GULDUR, LOTRInvasionSpawner.RARE));
	}
	
	@Override
	public int spawnCountMultiplier()
	{
		return super.spawnCountMultiplier() * 2;
	}
}
