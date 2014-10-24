package lotr.client;

import lotr.common.LOTRReflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.World;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class LOTRReflectionClient
{
	public static void testAll(World world, Minecraft mc)
	{
		setCameraRoll(mc.entityRenderer, getCameraRoll(mc.entityRenderer));
	}
	
	public static void setCameraRoll(EntityRenderer renderer, float roll)
    {
    	try
		{
			ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, roll, "camRoll", "field_78495_O");
		}
		catch (Exception e)
		{
			LOTRReflection.logFailure(e);
		}
    }
	
	public static float getCameraRoll(EntityRenderer renderer)
	{
		try
		{
			return ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, "camRoll", "field_78495_O");
		}
		catch (Exception e)
		{
			LOTRReflection.logFailure(e);
			return 0F;
		}
	}
}
