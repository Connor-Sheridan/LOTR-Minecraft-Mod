package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.passive.EntityWolf;

public class LOTRBiomeGenEnedwaithWoodlandsDense extends LOTRBiomeGenEnedwaithWoodlands
{
	public LOTRBiomeGenEnedwaithWoodlandsDense(int i)
	{
		super(i);
		
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 16, 4, 8));
		
		spawnableLOTRAmbientList.clear();
		
		hasPodzol = true;
		decorator.treesPerChunk = 12;
		decorator.logsPerChunk = 1;
		
		decorator.addTree(LOTRTreeType.OAK_LARGE, 600);
		decorator.addTree(LOTRTreeType.OAK_HUGE, 200);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 200);
	}
}
