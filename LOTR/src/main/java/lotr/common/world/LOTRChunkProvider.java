package lotr.common.world;

import java.util.List;
import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.biome.*;
import lotr.common.world.mapgen.LOTRMapGenCaves;
import lotr.common.world.mapgen.LOTRMapGenRavine;
import lotr.common.world.mapgen.dwarvenmine.LOTRMapGenDwarvenMine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenStructure;

public class LOTRChunkProvider implements IChunkProvider
{
	private World worldObj;
    private Random rand;
	private BiomeGenBase[] biomesForGeneration;
	
	private static double COORDINATE_SCALE = 684.412D;
	private static double HEIGHT_SCALE = 1D;
	private static double MAIN_NOISE_SCALE_XZ = 400D;
	private static double MAIN_NOISE_SCALE_Y = 5000D;
	private static double DEPTH_NOISE_SCALE = 200D;
	private static double DEPTH_NOISE_EXP = 0.5D;
	private static double HEIGHT_STRETCH = 6D;
	private static double UPPER_LIMIT_SCALE = 512D;
	private static double LOWER_LIMIT_SCALE = 512D;
	
	private int noiseRadius = 10;
	private int noiseWidth = 2 * noiseRadius + 1;
	
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorOctaves noiseGen5;
    private NoiseGeneratorOctaves noiseGen6;
	private NoiseGeneratorOctaves stoneNoiseGen;
    private double[] noise1;
    private double[] noise2;
    private double[] noise3;
	private double[] noise5;
    private double[] noise6;
	private double[] stoneNoise = new double[256];
    private double[] heightNoise;
	private float[] biomeHeightNoise;
    private int[][] unusedIntArray = new int[32][32];
	
    private MapGenBase caveGenerator = new LOTRMapGenCaves();
	private MapGenBase ravineGenerator = new LOTRMapGenRavine();
	private MapGenStructure dwarvenMineGenerator = new LOTRMapGenDwarvenMine();

    public LOTRChunkProvider(World world, long l)
    {
        worldObj = world;
        rand = new Random(l);
        noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
        stoneNoiseGen = new NoiseGeneratorOctaves(rand, 4);
		noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
        noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
    }

    private void generateTerrain(int i, int j, Block[] blocks)
    {
        byte byte0 = 4;
        byte byte1 = 32;
        byte byte2 = 63;
        int k = byte0 + 1;
        byte byte3 = 33;
        int l = byte0 + 1;
        biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, i * 4 - noiseRadius, j * 4 - noiseRadius, k + noiseWidth, l + noiseWidth);
        heightNoise = initializeHeightNoise(heightNoise, i * byte0, 0, j * byte0, k, byte3, l);

        for (int i1 = 0; i1 < byte0; i1++)
        {
            for (int j1 = 0; j1 < byte0; j1++)
            {
                for (int k1 = 0; k1 < byte1; k1++)
                {
                    double d = 0.125D;
                    double d1 = heightNoise[((i1 + 0) * l + j1 + 0) * byte3 + k1 + 0];
                    double d2 = heightNoise[((i1 + 0) * l + j1 + 1) * byte3 + k1 + 0];
                    double d3 = heightNoise[((i1 + 1) * l + j1 + 0) * byte3 + k1 + 0];
                    double d4 = heightNoise[((i1 + 1) * l + j1 + 1) * byte3 + k1 + 0];
                    double d5 = (heightNoise[((i1 + 0) * l + j1 + 0) * byte3 + k1 + 1] - d1) * d;
                    double d6 = (heightNoise[((i1 + 0) * l + j1 + 1) * byte3 + k1 + 1] - d2) * d;
                    double d7 = (heightNoise[((i1 + 1) * l + j1 + 0) * byte3 + k1 + 1] - d3) * d;
                    double d8 = (heightNoise[((i1 + 1) * l + j1 + 1) * byte3 + k1 + 1] - d4) * d;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                            int j2 = i2 + i1 * 4 << 12 | 0 + j1 * 4 << 8 | k1 * 8 + l1;
                            short s = 256;
                            j2 -= s;
                            double d14 = 0.25D;
                            double d15 = (d11 - d10) * d14;
                            double d16 = d10 - d15;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                if ((d16 += d15) > 0D)
                                {
                                    blocks[j2 += s] = Blocks.stone;
                                }
                                else if (k1 * 8 + l1 < byte2)
                                {
                                    blocks[j2 += s] = Blocks.water;
                                }
                                else
                                {
                                    blocks[j2 += s] = Blocks.air;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    private void replaceBlocksForBiome(int i, int j, Block[] blocks, byte[] metadata, BiomeGenBase[] biomeArray)
    {
        double d = 1D / 32D;
        stoneNoise = stoneNoiseGen.generateNoiseOctaves(stoneNoise, i * 16, j * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for (int k = 0; k < 16; k++)
        {
            for (int l = 0; l < 16; l++)
            {
            	int index = k + l * 16;
                BiomeGenBase biome = biomeArray[index];
                biome.genTerrainBlocks(worldObj, rand, blocks, metadata, i * 16 + k, j * 16 + l, stoneNoise[index]);  
            }
		}
	}
	
	@Override
    public Chunk loadChunk(int i, int j)
    {
        return provideChunk(i, j);
    }

	@Override
    public Chunk provideChunk(int i, int j)
    {
        rand.setSeed((long)i * 341873128712L + (long)j * 132897987541L);
        Block[] blocks = new Block[65536];
		byte[] metadata = new byte[65536];
        generateTerrain(i, j, blocks);
        biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, i * 16, j * 16, 16, 16);
        replaceBlocksForBiome(i, j, blocks, metadata, biomesForGeneration);
		
        caveGenerator.func_151539_a(this, worldObj, i, j, blocks);
        ravineGenerator.func_151539_a(this, worldObj, i, j, blocks);
        dwarvenMineGenerator.func_151539_a(this, worldObj, i, j, blocks);
		
        Chunk chunk = new Chunk(worldObj, i, j);
		ExtendedBlockStorage blockStorage[] = chunk.getBlockStorageArray();
		
        byte[] biomes = chunk.getBiomeArray();
        for (int k = 0; k < biomes.length; k++)
        {
            biomes[k] = (byte)biomesForGeneration[k].biomeID;
        }
		
        for (int i1 = 0; i1 < 16; i1++)
        {
            for (int k1 = 0; k1 < 16; k1++)
            {
                for (int j1 = 0; j1 < 256; j1++)
                {
                    Block block = blocks[i1 << 12 | k1 << 8 | j1];

                    if (block == Blocks.air)
                    {
                        continue;
                    }
                    
					byte meta = metadata[i1 << 12 | k1 << 8 | j1];

                    int j2 = j1 >> 4;

                    if (blockStorage[j2] == null)
                    {
                        blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
                    }

                    blockStorage[j2].func_150818_a(i1, j1 & 15, k1, block);
					blockStorage[j2].setExtBlockMetadata(i1, j1 & 15, k1, meta & 15);
                }
            }
        }
		
        chunk.generateSkylightMap();
        return chunk;
    }

	private double[] initializeHeightNoise(double[] noise, int i, int j, int k, int xSize, int ySize, int zSize)
    {
        if (noise == null)
        {
            noise = new double[xSize * ySize * zSize];
        }
        
        if (biomeHeightNoise == null)
        {
            biomeHeightNoise = new float[noiseWidth * noiseWidth];

            for (int i1 = -noiseRadius; i1 <= noiseRadius; i1++)
            {
                for (int k1 = -noiseRadius; k1 <= noiseRadius; k1++)
                {
                    float var10 = 10F / MathHelper.sqrt_float(i1 * i1 + k1 * k1 + 0.2F);
                    biomeHeightNoise[i1 + noiseRadius + (k1 + noiseRadius) * noiseWidth] = var10;
                }
            }
        }

        noise5 = noiseGen5.generateNoiseOctaves(noise5, i, k, xSize, zSize, 1.121D, 1.121D, DEPTH_NOISE_EXP);
        noise6 = noiseGen6.generateNoiseOctaves(noise6, i, k, xSize, zSize, DEPTH_NOISE_SCALE, DEPTH_NOISE_SCALE, DEPTH_NOISE_EXP);
        noise3 = noiseGen3.generateNoiseOctaves(noise3, i, j, k, xSize, ySize, zSize, COORDINATE_SCALE / MAIN_NOISE_SCALE_XZ, HEIGHT_SCALE / MAIN_NOISE_SCALE_Y, COORDINATE_SCALE / MAIN_NOISE_SCALE_XZ);
        noise1 = noiseGen1.generateNoiseOctaves(noise1, i, j, k, xSize, ySize, zSize, COORDINATE_SCALE, HEIGHT_SCALE, COORDINATE_SCALE);
        noise2 = noiseGen2.generateNoiseOctaves(noise2, i, j, k, xSize, ySize, zSize, COORDINATE_SCALE, HEIGHT_SCALE, COORDINATE_SCALE);

        int xzPass = 0;
        int yPass = 0;  

        for (int i1 = 0; i1 < xSize; i1++)
        {
            for (int k1 = 0; k1 < zSize; k1++)
            {
                float totalBaseHeight = 0F;
                float totalHeightVariation = 0F;
                float totalHeightNoise = 0F;

                BiomeGenBase centreBiome = biomesForGeneration[i1 + noiseRadius + (k1 + noiseRadius) * (xSize + noiseWidth)];

                for (int i2 = -noiseRadius; i2 <= noiseRadius; i2++)
                {
                    for (int k2 = -noiseRadius; k2 <= noiseRadius; k2++)
                    {
                        BiomeGenBase biome = biomesForGeneration[i1 + i2 + noiseRadius + (k1 + k2 + noiseRadius) * (xSize + noiseWidth)];
                        float heightNoise = biomeHeightNoise[i2 + noiseRadius + (k2 + noiseRadius) * noiseWidth] / (biome.rootHeight + 2F) / 2F;
                        heightNoise = Math.abs(heightNoise);
						
                        if (biome.rootHeight > centreBiome.rootHeight)
                        {
                        	heightNoise /= 2F;
                        }

                        totalBaseHeight += biome.rootHeight * heightNoise;
                        totalHeightVariation += biome.heightVariation * heightNoise;
                        totalHeightNoise += heightNoise;
                    }
                }
                
                float avgBaseHeight = totalBaseHeight / totalHeightNoise;
                float avgHeightVariation = totalHeightVariation / totalHeightNoise;
                
                avgBaseHeight = (avgBaseHeight * 4F - 1F) / 8F;
                avgHeightVariation = avgHeightVariation * 0.9F + 0.1F;
                
                double var47 = noise6[xzPass] / 8000D;
                if (var47 < 0D)
                {
                    var47 = -var47 * 0.3D;
                }
                var47 = var47 * 3D - 2D;
				if (var47 < 0D)
                {
                    var47 /= 2D;
                    if (var47 < -1D)
                    {
                        var47 = -1D;
                    }
                    var47 /= 1.4D;
                    var47 /= 2D;
                }
                else
                {
                    if (var47 > 1D)
                    {
                        var47 = 1D;
                    }
                    var47 /= 8D;
                }
                ++xzPass;
                
                for (int j1 = 0; j1 < ySize; j1++)
                {
                    double baseHeight = (double)avgBaseHeight;
                    double heightVariation = (double)avgHeightVariation;
                    
                    baseHeight += var47 * 0.2D;
                    baseHeight = baseHeight * (double)ySize / 16D;
                    double var28 = (double)ySize / 2D + baseHeight * 4D;
                    double var30 = 0D;
                    double var32 = ((double)j1 - var28) * HEIGHT_STRETCH * 128D / 256D / heightVariation;
                    if (var32 < 0D)
                    {
                        var32 *= 4D;
                    }
                    
                    double var34 = noise1[yPass] / UPPER_LIMIT_SCALE;
                    double var36 = noise2[yPass] / LOWER_LIMIT_SCALE;
                    double var38 = (noise3[yPass] / 10D + 1D) / 2D;
                    if (var38 < 0D)
                    {
                        var30 = var34;
                    }
                    else if (var38 > 1D)
                    {
                        var30 = var36;
                    }
                    else
                    {
                        var30 = var34 + (var36 - var34) * var38;
                    }
                    var30 -= var32;
                    
                    if (j1 > ySize - 4)
                    {
                        double var40 = (double)((float)(j1 - (ySize - 4)) / 3F);
                        var30 = var30 * (1D - var40) + -10D * var40;
                    }
                    
                    noise[yPass] = var30;
                    yPass++;
                }
            }
        }
        return noise;
    }

	@Override
    public boolean chunkExists(int i, int j)
    {
        return true;
    }

	@Override
    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
        BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(k + 16, l + 16);
		LOTRBiome biome;
		if (biomegenbase instanceof LOTRBiome)
		{
			biome = (LOTRBiome)biomegenbase;
		}
		else
		{
			return;
		}
        rand.setSeed(worldObj.getSeed());
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        long l2 = (rand.nextLong() / 2L) * 2L + 1L;
        rand.setSeed((long)i * l1 + (long)j * l2 ^ worldObj.getSeed());
		
        dwarvenMineGenerator.generateStructuresInChunk(worldObj, rand, i, j);
		
        if (rand.nextInt(4) == 0)
        {
            int i1 = k + rand.nextInt(16) + 8;
            int j2 = rand.nextInt(128);
            int k3 = l + rand.nextInt(16) + 8;
			
			if (j2 < 60 || rand.nextFloat() < biome.getChanceToSpawnLakes())
			{
				(new WorldGenLakes(Blocks.water)).generate(worldObj, rand, i1, j2, k3);
			}
        }
		
        if (rand.nextInt(8) == 0)
        {
            int i1 = k + rand.nextInt(16) + 8;
            int j1 = rand.nextInt(rand.nextInt(120) + 8);
            int k1 = l + rand.nextInt(16) + 8;

            if (j1 < 60 || rand.nextFloat() < biome.getChanceToSpawnLavaLakes())
            {
                (new WorldGenLakes(Blocks.lava)).generate(worldObj, rand, i1, j1, k1);
            }
        }
		
		biome.decorate(worldObj, rand, k, l);
		
		if (biome.getChanceToSpawnAnimals() <= 1F)
		{
			if (rand.nextFloat() < biome.getChanceToSpawnAnimals())
			{
				SpawnerAnimals.performWorldGenSpawning(worldObj, biome, k + 8, l + 8, 16, 16, rand);
			}
		}
		else
		{
			for (int i1 = 0; i1 < MathHelper.floor_double(biome.getChanceToSpawnAnimals()); i1++)
			{
				SpawnerAnimals.performWorldGenSpawning(worldObj, biome, k + 8, l + 8, 16, 16, rand);
			}
		}
		
        k += 8;
        l += 8;

        for (int i1 = 0; i1 < 16; i1++)
        {
            for (int k1 = 0; k1 < 16; k1++)
            {
                int j1 = worldObj.getPrecipitationHeight(k + i1, l + k1);

                if (worldObj.isBlockFreezable(i1 + k, j1 - 1, k1 + l))
                {
                    worldObj.setBlock(i1 + k, j1 - 1, k1 + l, Blocks.ice, 0, 2);
                }

                if (worldObj.func_147478_e(i1 + k, j1, k1 + l, true))
                {
                    worldObj.setBlock(i1 + k, j1, k1 + l, Blocks.snow_layer, 0, 2);
                }
            }
        }

        BlockSand.fallInstantly = false;
    }

	@Override
    public boolean saveChunks(boolean flag, IProgressUpdate update)
    {
        return true;
    }
	
	@Override
	public void saveExtraData() {}

	@Override
    public boolean unloadQueuedChunks()
    {
        return false;
    }

	@Override
    public boolean canSave()
    {
        return true;
    }

	@Override
    public String makeString()
    {
        return "MiddleEarthLevelSource";
    }
	
	@Override
    public List getPossibleCreatures(EnumCreatureType creatureType, int i, int j, int k)
    {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		return biome == null ? null : biome.getSpawnableList(creatureType);
    }

	@Override
    public ChunkPosition func_147416_a(World world, String type, int i, int j, int k)
    {
        return null;
    }
	
	@Override
    public int getLoadedChunkCount()
    {
        return 0;
    }
	
	@Override
    public void recreateStructures(int i, int j)
	{
		dwarvenMineGenerator.func_151539_a(this, worldObj, i, j, null);
	}
}
