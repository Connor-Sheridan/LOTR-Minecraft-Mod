package lotr.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LOTRConfig
{
	public static Configuration config;
	
	private static List<String> allCategories = new ArrayList();
	private static String CATEGORY_GENERAL = getCategory("general");
	private static String CATEGORY_DIMENSION = getCategory("dimension");
	private static String CATEGORY_GAMEPLAY = getCategory("gameplay");
	private static String CATEGORY_GUI = getCategory("gui");
	private static String CATEGORY_ENVIRONMENT = getCategory("environment");
	
	private static String getCategory(String category)
	{
		allCategories.add(category);
		return category;
	}

	public static boolean allowBannerProtection;
	public static boolean allowSelfProtectingBanners;
	
	public static boolean alwaysShowAlignment;
	public static int alignmentXOffset;
	public static int alignmentYOffset;
	public static boolean displayAlignmentAboveHead;
	public static boolean enableSepiaMap;
	public static boolean enableOnscreenCompass;
	public static boolean compassExtraInfo;
	public static boolean hiredUnitHealthBars;
	
	public static boolean enableLOTRSky;
	public static boolean enableMistyMountainsMist;
	public static boolean enableAmbience;
	
	public static void setupAndLoad(FMLPreInitializationEvent event)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		load();
	}
	
	public static void load()
	{
		LOTRDimension.configureDimensions(config, CATEGORY_DIMENSION);
		
		allowBannerProtection = config.get(CATEGORY_GAMEPLAY, "Allow Banner Protection", true).getBoolean();
		allowSelfProtectingBanners = config.get(CATEGORY_GAMEPLAY, "Allow Self-Protecting Banners", true).getBoolean();
		
		alwaysShowAlignment = config.get(CATEGORY_GUI, "Always show alignment", false, "If set to false, the alignment bar will only be shown in Middle-earth. If set to true, it will be shown in all dimensions").getBoolean();
		alignmentXOffset = config.get(CATEGORY_GUI, "Alignment x-offset", 0, "Configure the x-position of the alignment bar on-screen. Negative values move it left, positive values right").getInt();
		alignmentYOffset = config.get(CATEGORY_GUI, "Alignment y-offset", 0, "Configure the y-position of the alignment bar on-screen. Negative values move it up, positive values down").getInt();
		displayAlignmentAboveHead = config.get(CATEGORY_GUI, "Display alignment above head", true, "Enable or disable the rendering of other players' alignment values above their heads").getBoolean();
		enableSepiaMap = config.get(CATEGORY_GUI, "Sepia Map", false, "Display the Middle-earth map in sepia colours").getBoolean();
		enableOnscreenCompass = config.get(CATEGORY_GUI, "On-screen Compass", true).getBoolean();
		compassExtraInfo = config.get(CATEGORY_GUI, "On-screen Compass Extra Info", true, "Display co-ordinates and biome below compass").getBoolean();
		hiredUnitHealthBars = config.get(CATEGORY_GUI, "Hired NPC Health Bars", true).getBoolean();
		
		enableLOTRSky = config.get(CATEGORY_ENVIRONMENT, "Middle-earth sky", true, "Toggle the new Middle-earth sky").getBoolean();
		enableMistyMountainsMist = config.get(CATEGORY_ENVIRONMENT, "Misty Misty Mountains", true, "Toggle mist in the Misty Mountains").getBoolean();
		enableAmbience = config.get(CATEGORY_ENVIRONMENT, "Ambience", true).getBoolean();

		if (config.hasChanged())
		{
			config.save();
		}
	}
	
	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList();
		for (String category : allCategories)
		{
			list.addAll(new ConfigElement(config.getCategory(category)).getChildElements());
		}
		return list;
	}
}
