package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRBlockOreStorage2 extends LOTRBlockOreStorageBase
{
	public LOTRBlockOreStorage2()
	{
		super();
		setOreStorageNames("blackUrukSteel");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j)
	{
		return super.getIcon(i, j);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister)
    {
        super.registerBlockIcons(iconregister);
    }
}
