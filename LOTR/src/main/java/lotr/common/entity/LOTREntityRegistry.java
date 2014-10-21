package lotr.common.entity;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import lotr.common.LOTRAlignmentValues;
import lotr.common.LOTRFaction;

import org.apache.commons.io.input.BOMInputStream;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LOTREntityRegistry
{
	public static Map registeredNPCs = new HashMap();
	
	public static class RegistryInfo
	{
		public LOTRFaction alignmentFaction;
		public boolean shouldTargetEnemies;
		public LOTRAlignmentValues.AlignmentBonus alignmentBonus;
		
		public RegistryInfo(String entityName, LOTRFaction side, boolean flag, int bonus)
		{
			alignmentFaction = side;
			shouldTargetEnemies = flag;
			alignmentBonus = new LOTRAlignmentValues.AlignmentBonus(bonus, "entity." + entityName + ".name");
			alignmentBonus.needsTranslation = true;
		}
	}
	
	public static void loadRegisteredNPCs(FMLPreInitializationEvent event)
	{
		StringBuilder stringbuilder = new StringBuilder();
		for (LOTRFaction faction : LOTRFaction.values())
		{
			if (!faction.allowEntityRegistry)
			{
				continue;
			}
			
			if (faction.ordinal() > 0)
			{
				stringbuilder.append(", ");
			}
			stringbuilder.append(faction.codeName());
		}
		String allFactions = stringbuilder.toString();
		
		try
		{
			File file = event.getModConfigurationDirectory();
			File config = new File(file, "LOTR_EntityRegistry.txt");
			if (!config.exists())
			{
				if (config.createNewFile())
				{
					PrintStream writer = new PrintStream(new FileOutputStream(config));
					writer.println("#Lines starting with '#' will be ignored");
					writer.println("#");
					writer.println("#Use this file to register entities with the LOTR alignment system.");
					writer.println("#");
					writer.println("#An example format for registering an entity is as follows: (do not use spaces)");
					writer.println("#name=LOTR.MordorOrc,faction=MORDOR,targetEnemies=true,bonus=1");
					writer.println("#");
					writer.println("#'name' is the entity name, prefixed with the associated mod ID.");
					writer.println("#The mod ID can be found in the Mod List on the main menu - for example, \"LOTR\" for the LOTR mod.");
					writer.println("#The entity name is not necessarily the in-game name. It is the name used to register the entity in the code.");
					writer.println("#You may be able to discover the entity name in the mod's language file if there is one - otherwise, contact the mod author.");
					writer.println("#The mod ID and entity name must be separated by a '.' character.");
					writer.println("#Vanilla entities have no mod ID and therefore no prefix.");
					writer.println("#");
					writer.println("#'faction' can be " + allFactions);
					writer.println("#");
					writer.println("#'targetEnemies' can be true or false.");
					writer.println("#If true, the entity will be equipped with AI modules to target its enemies.");
					writer.println("#Actual combat behaviour may or may not be present, depending on whether the entity is designed with combat AI modules.");
					writer.println("#");
					writer.println("#'bonus' is the alignment bonus awarded to a player who kills the entity.");
					writer.println("#It can be positive, negative, or zero, in which case no bonus will be awarded.");
					writer.println("#");
					writer.close();
				}
			}
			else
			{
				BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(config))));
				String s = "";

				while ((s = bufferedreader.readLine()) != null)
				{
					String line = s;
					
					if (s.startsWith("#"))
					{
						continue;
					}
					
					String name;
					LOTRFaction faction = null;
					boolean targetEnemies;
					int bonus;
					
					if (!s.startsWith("name="))
					{
						continue;
					}
					
					s = s.substring("name=".length());
					if (s.startsWith("LOTR."))
					{
						continue;
					}

					int i = s.indexOf(",side=");
					if (i < 0)
					{
						continue;
					}
					int j = s.indexOf(",targetEnemies=");
					if (j < 0)
					{
						continue;
					}
					int k = s.indexOf(",bonus=");
					if (k < 0)
					{
						continue;
					}
					
					name = s.substring(0, i);
					if (name.length() == 0)
					{
						continue;
					}
					
					String factionString = s.substring(i + "faction=".length() + 1, j);
					for (LOTRFaction f : LOTRFaction.values())
					{
						if (f.codeName().equals(factionString))
						{
							faction = f;
							break;
						}
					}
					
					if (faction == null)
					{
						continue;
					}
					
					String targetEnemiesString = s.substring(j + "targetEnemies=".length() + 1, k);
					if (targetEnemiesString.equals("true"))
					{
						targetEnemies = true;
					}
					else if (targetEnemiesString.equals("false"))
					{
						targetEnemies = false;
					}
					else
					{
						continue;
					}
					
					String bonusString = s.substring(k + "bonus=".length() + 1);
					bonus = Integer.parseInt(bonusString);
					
					registeredNPCs.put(name, new RegistryInfo(name, faction, targetEnemies, bonus));
					System.out.println("Successfully registered entity " + name + " with the LOTR alignment system as " + line);
				}
				
				bufferedreader.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
