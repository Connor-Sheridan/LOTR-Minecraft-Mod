package lotr.common.block;

import java.util.Random;

import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityAlloyForge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class LOTRBlockAlloyForge extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon[] forgeIcons;
	
	public LOTRBlockAlloyForge()
	{
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabDeco);
		setHardness(4F);
		setStepSound(Block.soundTypeStone);
	}
	
	@Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        setDefaultDirection(world, i, j, k);
    }

    private void setDefaultDirection(World world, int i, int j, int k)
    {
        if (!world.isRemote)
        {
            Block i1 = world.getBlock(i, j, k - 1);
            Block j1 = world.getBlock(i, j, k + 1);
            Block k1 = world.getBlock(i - 1, j, k);
            Block l1 = world.getBlock(i + 1, j, k);
            byte meta = 3;

            if (i1.isOpaqueCube() && !j1.isOpaqueCube())
            {
                meta = 3;
            }

            if (j1.isOpaqueCube() && !i1.isOpaqueCube())
            {
                meta = 2;
            }

            if (k1.isOpaqueCube() && !l1.isOpaqueCube())
            {
                meta = 5;
            }

            if (l1.isOpaqueCube() && !k1.isOpaqueCube())
            {
                meta = 4;
            }

            world.setBlockMetadataWithNotify(i, j, k, meta, 2);
        }
    }

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side)
    {
        if (side == 1 || side == 0)
        {
            return forgeIcons[1];
        }
        else
        {
            int meta = world.getBlockMetadata(i, j, k) & 7;
            return side != meta ? forgeIcons[0] : (isForgeActive(world, i, j, k) ? forgeIcons[3] : forgeIcons[2]);
        }
    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int i, int j)
    {
        return (i == 1 || i == 0) ? forgeIcons[1] : (i == 3 ? forgeIcons[2] : forgeIcons[0]);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister)
    {
		forgeIcons = new IIcon[4];
        forgeIcons[0] = iconregister.registerIcon(getTextureName() + "_side");
		forgeIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
		forgeIcons[2] = iconregister.registerIcon(getTextureName() + "_front");
		forgeIcons[3] = iconregister.registerIcon(getTextureName() + "_active");
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if (isForgeActive(world, i, j, k))
        {
            int meta = world.getBlockMetadata(i, j, k) & 7;
            float f = (float)i + 0.5F;
            float f1 = (float)j + 0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)k + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;

            if (meta == 4)
            {
                world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0D, 0D, 0D);
                world.spawnParticle("flame", f - f3, f1, f2 + f4, 0D, 0D, 0D);
            }
            else if (meta == 5)
            {
                world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0D, 0D, 0D);
                world.spawnParticle("flame", f + f3, f1, f2 + f4, 0D, 0D, 0D);
            }
            else if (meta == 2)
            {
                world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0D, 0D, 0D);
                world.spawnParticle("flame", f + f4, f1, f2 - f3, 0D, 0D, 0D);
            }
            else if (meta == 3)
            {
                world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0D, 0D, 0D);
                world.spawnParticle("flame", f + f4, f1, f2 + f3, 0D, 0D, 0D);
            }
            
            for (int l = 0; l < 6; l++)
            {
            	float f10 = random.nextBoolean() ? 0F : 1F;
            	float f11 = random.nextBoolean() ? 0F : 1F;
            	float f12 = 0.5F;
            	f10 += -0.1F + random.nextFloat() * 0.2F;
            	f11 += -0.1F + random.nextFloat() * 0.2F;
            	
            	if (random.nextInt(3) > 0)
            	{
            		world.spawnParticle("largesmoke", i + f10, j + f12, k + f11, 0D, 0D, 0D);
            	}
            	else
            	{
            		world.spawnParticle("smoke", i + f10, j + f12, k + f11, 0D, 0D, 0D);
            	}
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack)
    {
        int rotation = MathHelper.floor_double((entity.rotationYaw * 4F / 360F) + 0.5D) & 3;

        if (rotation == 0)
        {
            world.setBlockMetadataWithNotify(i, j, k, 2, 2);
        }

        if (rotation == 1)
        {
            world.setBlockMetadataWithNotify(i, j, k, 5, 2);
        }

        if (rotation == 2)
        {
            world.setBlockMetadataWithNotify(i, j, k, 3, 2);
        }

        if (rotation == 3)
        {
            world.setBlockMetadataWithNotify(i, j, k, 4, 2);
        }
		
        if (itemstack.hasDisplayName())
        {
            ((LOTRTileEntityAlloyForge)world.getTileEntity(i, j, k)).setSpecialForgeName(itemstack.getDisplayName());
        }
    }

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k)
	{
		return isForgeActive(world, i, j, k) ? 13 : 0;
	}
	
	public static boolean isForgeActive(IBlockAccess world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		return meta > 7;
	}
	
	public static void setForgeActive(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockMetadataWithNotify(i, j, k, meta ^ 8, 2);
		world.updateLightByType(EnumSkyBlock.Block, i, j, k);
	}
	
	@Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2)
    {
		if (!world.isRemote)
		{
			entityplayer.openGui(LOTRMod.instance, LOTRCommonProxy.GUI_ID_ALLOY_FORGE, world, i, j, k);
		}
		return true;
    }
	
	@Override
    public void breakBlock(World world, int i, int j, int k, Block block, int meta)
    {
		LOTRTileEntityAlloyForge forge = (LOTRTileEntityAlloyForge)world.getTileEntity(i, j, k);
		if (forge != null)
		{
			LOTRMod.dropContainerItems(forge, world, i, j, k);
			world.func_147453_f(i, j, k, block);
		}

        super.breakBlock(world, i, j, k, block, meta);
    }
	
	@Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int i, int j, int k, int direction)
    {
        return Container.calcRedstoneFromInventory((IInventory)world.getTileEntity(i, j, k));
    }
}
