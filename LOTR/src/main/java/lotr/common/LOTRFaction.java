package lotr.common;

import java.awt.Color;
import java.util.*;
import java.util.Map.Entry;

import lotr.common.entity.npc.*;
import lotr.common.world.LOTRInvasionSpawner.InvasionSpawnEntry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum LOTRFaction
{
	HOBBIT(0x59CE4E, new FactionMapInfo(830, 745, 100)),
	RANGER_NORTH(0x40703A, new FactionMapInfo(920, 720, 200)),
	BLUE_MOUNTAINS(0x648DBC, new FactionMapInfo(650, 600, 125)),
	HIGH_ELF(0x49C2FF, new FactionMapInfo(570, 770, 200)),
	GUNDABAD(0x966C54, new FactionMapInfo(1160, 670, 150)),
	ANGMAR(0x779177, new FactionMapInfo(1080, 600, 125)),
	WOOD_ELF(0x39964E, new FactionMapInfo(1420, 640, 50)),
	DOL_GULDUR(0x353B44, new FactionMapInfo(1380, 870, 100)),
	DWARF(0x4B6182, new FactionMapInfo(1650, 650, 125)),
	GALADHRIM(0xE5D180, new FactionMapInfo(1230, 900, 75)),
	DUNLAND(0xA8948F, new FactionMapInfo(1030, 980, 125)),
	URUK_HAI(0x738970, new FactionMapInfo(1110, 1070, 50)),
	FANGORN(0x49B752, new FactionMapInfo(1200, 1000, 75)),
	ROHAN(0xCC986C, new FactionMapInfo(1230, 1080, 150)),
	GONDOR(0x00D8FF, new FactionMapInfo(1280, 1280, 200)),
	MORDOR(0xC11D15, new FactionMapInfo(1600, 1240, 200)),
	NEAR_HARAD(0xEAAB5D, new FactionMapInfo(1450, 1720, 250)),
	FAR_HARAD(0x2E6342, new FactionMapInfo(0, 0, 0)),
	HALF_TROLL(0x9E8373, new FactionMapInfo(1900, 2500, 200)),
	
	DARK_HUORN(0, null, true, true, -1, null),
	
	UTUMNO(0x330500, LOTRDimension.UTUMNO, -66666),
	
	HOSTILE(true, -1),
	UNALIGNED(false, 0);
	
	private static Random factionRand = new Random();

	public LOTRDimension factionDimension;
	private String factionName;
	public Color factionColor;
	
	public boolean allowPlayer;
	public boolean allowEntityRegistry;
	
	public boolean hasFixedAlignment;
	public int fixedAlignment;
	
	private Set<LOTRFaction> enemies = new HashSet();
	public Set<LOTRFaction> killBonuses = new HashSet();
	public Set<LOTRFaction> killPenalties = new HashSet();

	public List<InvasionSpawnEntry> invasionMobs = new ArrayList<InvasionSpawnEntry>();
	
	private Map<Integer, LOTRAchievement> alignmentAchievements = new HashMap();
	private LOTRAchievement miniquestAchievement;
	
	public FactionMapInfo factionMapInfo;

	private LOTRFaction(int color, FactionMapInfo mapInfo)
	{
		this(color, LOTRDimension.MIDDLE_EARTH, mapInfo);
	}
	
	private LOTRFaction(int color, LOTRDimension dim, FactionMapInfo mapInfo)
	{
		this(color, dim, true, true, Integer.MIN_VALUE, mapInfo);
	}
	
	private LOTRFaction(int color, LOTRDimension dim, int alignment)
	{
		this(color, dim, true, true, alignment, null);
	}
	
	private LOTRFaction(boolean registry, int alignment)
	{
		this(0, null, false, registry, alignment, null);
	}
	
	private LOTRFaction(int color, LOTRDimension dim, boolean player, boolean registry, int alignment, FactionMapInfo mapInfo)
	{
		allowPlayer = player;
		allowEntityRegistry = registry;
		
		factionColor = new Color(color);
		
		factionDimension = dim;
		if (dim != null)
		{
			dim.factionList.add(this);
		}
		
		if (alignment != Integer.MIN_VALUE)
		{
			setFixedAlignment(alignment);
		}
		
		if (mapInfo != null)
		{
			factionMapInfo = mapInfo;
		}
	}
	
	private void setFixedAlignment(int alignment)
	{
		hasFixedAlignment = true;
		fixedAlignment = alignment;
	}
	
	private void addAlignmentAchievement(int alignment, LOTRAchievement achievement)
	{
		if (achievement.allyFaction != this)
		{
			throw new IllegalArgumentException("Faction alignment achievements must require alliance with the faction");
		}
		if (alignmentAchievements.containsKey(alignment))
		{
			throw new IllegalArgumentException("Alignment value is already registered");
		}
		if (alignmentAchievements.containsValue(achievement))
		{
			throw new IllegalArgumentException("Achievement is already registered");
		}
		
		alignmentAchievements.put(alignment, achievement);
	}
	
	public void checkAlignmentAchievements(LOTRPlayerData playerData, int alignment)
	{
		for (Entry<Integer, LOTRAchievement> entry : alignmentAchievements.entrySet())
		{
			if (alignment >= entry.getKey())
			{
				playerData.addAchievement(entry.getValue());
			}
		}
	}
	
	private void setMiniquestAchievement(LOTRAchievement achievement)
	{
		if (achievement.allyFaction != this)
		{
			throw new IllegalArgumentException("Miniquest achievements must require alliance with the faction");
		}
		else
		{
			miniquestAchievement = achievement;
		}
	}
	
	public LOTRAchievement getMiniquestAchievement()
	{
		return miniquestAchievement;
	}

	public static void initFactionProperties()
	{
		HOBBIT.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_HOBBIT);
		HOBBIT.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_HOBBIT);
		HOBBIT.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_HOBBIT);
		
		HOBBIT.setMiniquestAchievement(LOTRAchievement.doMiniquestHobbit);
		
		HOBBIT.addEnemy(GUNDABAD);
		HOBBIT.addEnemy(ANGMAR);
		HOBBIT.addEnemy(DOL_GULDUR);
		HOBBIT.addEnemy(URUK_HAI);
		HOBBIT.addEnemy(MORDOR);
		HOBBIT.addEnemy(HALF_TROLL);
		HOBBIT.addEnemy(DARK_HUORN);
		
		HOBBIT.addKillPenalty(HOBBIT);
		HOBBIT.addKillPenalty(RANGER_NORTH);
		
		HOBBIT.addKillBonus(GUNDABAD);
		
		HOBBIT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHobbitShirriff.class, 15));
		
		RANGER_NORTH.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_RANGER_NORTH);
		RANGER_NORTH.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_RANGER_NORTH);
		RANGER_NORTH.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_RANGER_NORTH);
		
		RANGER_NORTH.setMiniquestAchievement(LOTRAchievement.doMiniquestRanger);
		
		RANGER_NORTH.addEnemy(GUNDABAD);
		RANGER_NORTH.addEnemy(ANGMAR);
		RANGER_NORTH.addEnemy(DOL_GULDUR);
		RANGER_NORTH.addEnemy(DUNLAND);
		RANGER_NORTH.addEnemy(URUK_HAI);
		RANGER_NORTH.addEnemy(MORDOR);
		RANGER_NORTH.addEnemy(NEAR_HARAD);
		RANGER_NORTH.addEnemy(FAR_HARAD);
		RANGER_NORTH.addEnemy(HALF_TROLL);
		RANGER_NORTH.addEnemy(DARK_HUORN);
		
		RANGER_NORTH.addKillPenalty(RANGER_NORTH);
		RANGER_NORTH.addKillPenalty(HIGH_ELF);
		
		RANGER_NORTH.addKillBonus(GUNDABAD);
		RANGER_NORTH.addKillBonus(ANGMAR);
		
		RANGER_NORTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerNorth.class, 15));
		RANGER_NORTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerNorthBannerBearer.class, 2));
		
		BLUE_MOUNTAINS.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_BLUE_MOUNTAINS);
		BLUE_MOUNTAINS.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_BLUE_MOUNTAINS);
		BLUE_MOUNTAINS.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_BLUE_MOUNTAINS);
		
		BLUE_MOUNTAINS.setMiniquestAchievement(LOTRAchievement.doMiniquestBlueMountains);
		
		BLUE_MOUNTAINS.addEnemy(GUNDABAD);
		BLUE_MOUNTAINS.addEnemy(ANGMAR);
		BLUE_MOUNTAINS.addEnemy(DOL_GULDUR);
		BLUE_MOUNTAINS.addEnemy(URUK_HAI);
		BLUE_MOUNTAINS.addEnemy(MORDOR);
		BLUE_MOUNTAINS.addEnemy(HALF_TROLL);
		
		BLUE_MOUNTAINS.addKillPenalty(BLUE_MOUNTAINS);
		BLUE_MOUNTAINS.addKillPenalty(DWARF);
		
		BLUE_MOUNTAINS.addKillBonus(GUNDABAD);
		
		BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfWarrior.class, 10));
		BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfAxeThrower.class, 5));
		BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfBannerBearer.class, 2));
		
		HIGH_ELF.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_HIGH_ELF);
		HIGH_ELF.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_HIGH_ELF);
		HIGH_ELF.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_HIGH_ELF);
		
		HIGH_ELF.setMiniquestAchievement(LOTRAchievement.doMiniquestHighElf);
		
		HIGH_ELF.addEnemy(GUNDABAD);
		HIGH_ELF.addEnemy(ANGMAR);
		HIGH_ELF.addEnemy(DOL_GULDUR);
		HIGH_ELF.addEnemy(URUK_HAI);
		HIGH_ELF.addEnemy(MORDOR);
		HIGH_ELF.addEnemy(NEAR_HARAD);
		HIGH_ELF.addEnemy(FAR_HARAD);
		HIGH_ELF.addEnemy(HALF_TROLL);
		
		HIGH_ELF.addKillPenalty(HIGH_ELF);
		HIGH_ELF.addKillPenalty(RANGER_NORTH);
		HIGH_ELF.addKillPenalty(GALADHRIM);
		
		HIGH_ELF.addKillBonus(GUNDABAD);
		HIGH_ELF.addKillBonus(ANGMAR);
		
		HIGH_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHighElfWarrior.class, 15));
		HIGH_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHighElfBannerBearer.class, 2));
		
		GUNDABAD.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_GUNDABAD);
		GUNDABAD.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_GUNDABAD);
		GUNDABAD.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_GUNDABAD);
		
		GUNDABAD.setMiniquestAchievement(LOTRAchievement.doMiniquestGundabad);
		
		GUNDABAD.addEnemy(HOBBIT);
		GUNDABAD.addEnemy(RANGER_NORTH);
		GUNDABAD.addEnemy(BLUE_MOUNTAINS);
		GUNDABAD.addEnemy(HIGH_ELF);
		GUNDABAD.addEnemy(WOOD_ELF);
		GUNDABAD.addEnemy(DWARF);
		GUNDABAD.addEnemy(GALADHRIM);
		GUNDABAD.addEnemy(DUNLAND);
		GUNDABAD.addEnemy(FANGORN);
		GUNDABAD.addEnemy(ROHAN);
		GUNDABAD.addEnemy(GONDOR);
		
		GUNDABAD.addKillPenalty(GUNDABAD);
		GUNDABAD.addKillPenalty(ANGMAR);
		GUNDABAD.addKillPenalty(DOL_GULDUR);

		GUNDABAD.addKillBonus(RANGER_NORTH);
		GUNDABAD.addKillBonus(BLUE_MOUNTAINS);
		GUNDABAD.addKillBonus(HIGH_ELF);
		GUNDABAD.addKillBonus(WOOD_ELF);
		GUNDABAD.addKillBonus(DWARF);
		GUNDABAD.addKillBonus(GALADHRIM);
		GUNDABAD.addKillBonus(DUNLAND);
		GUNDABAD.addKillBonus(FANGORN);
		
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadOrc.class, 10));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadOrcArcher.class, 5));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadWarg.class, 5));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadBannerBearer.class, 2));
		
		ANGMAR.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_ANGMAR);
		ANGMAR.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_ANGMAR);
		ANGMAR.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_ANGMAR);
		
		ANGMAR.setMiniquestAchievement(LOTRAchievement.doMiniquestAngmar);
		
		ANGMAR.addEnemy(HOBBIT);
		ANGMAR.addEnemy(RANGER_NORTH);
		ANGMAR.addEnemy(BLUE_MOUNTAINS);
		ANGMAR.addEnemy(HIGH_ELF);
		ANGMAR.addEnemy(WOOD_ELF);
		ANGMAR.addEnemy(DWARF);
		ANGMAR.addEnemy(GALADHRIM);
		ANGMAR.addEnemy(DUNLAND);
		ANGMAR.addEnemy(FANGORN);
		ANGMAR.addEnemy(ROHAN);
		ANGMAR.addEnemy(GONDOR);
		
		ANGMAR.addKillPenalty(ANGMAR);
		ANGMAR.addKillPenalty(GUNDABAD);
		
		ANGMAR.addKillBonus(RANGER_NORTH);
		ANGMAR.addKillBonus(HIGH_ELF);
		
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrc.class, 10));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrcArcher.class, 5));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrcWarrior.class, 5));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrcBombardier.class, 3));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarBannerBearer.class, 2));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWarg.class, 10));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWargBombardier.class, 1));
		
		WOOD_ELF.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_WOOD_ELF);
		WOOD_ELF.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_WOOD_ELF);
		WOOD_ELF.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_WOOD_ELF);
		
		WOOD_ELF.setMiniquestAchievement(LOTRAchievement.doMiniquestWoodElf);
		
		WOOD_ELF.addEnemy(GUNDABAD);
		WOOD_ELF.addEnemy(ANGMAR);
		WOOD_ELF.addEnemy(DOL_GULDUR);
		WOOD_ELF.addEnemy(URUK_HAI);
		WOOD_ELF.addEnemy(MORDOR);
		WOOD_ELF.addEnemy(NEAR_HARAD);
		WOOD_ELF.addEnemy(FAR_HARAD);
		WOOD_ELF.addEnemy(HALF_TROLL);
		
		WOOD_ELF.addKillPenalty(WOOD_ELF);
		WOOD_ELF.addKillPenalty(HIGH_ELF);
		WOOD_ELF.addKillPenalty(GALADHRIM);
		
		WOOD_ELF.addKillBonus(GUNDABAD);
		WOOD_ELF.addKillBonus(DOL_GULDUR);
		
		WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfWarrior.class, 10));
		WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfScout.class, 5));
		WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfBannerBearer.class, 2));
		
		DOL_GULDUR.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_DOL_GULDUR);
		DOL_GULDUR.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_DOL_GULDUR);
		DOL_GULDUR.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_DOL_GULDUR);
		
		DOL_GULDUR.setMiniquestAchievement(LOTRAchievement.doMiniquestDolGuldur);
		
		DOL_GULDUR.addEnemy(HOBBIT);
		DOL_GULDUR.addEnemy(RANGER_NORTH);
		DOL_GULDUR.addEnemy(BLUE_MOUNTAINS);
		DOL_GULDUR.addEnemy(HIGH_ELF);
		DOL_GULDUR.addEnemy(WOOD_ELF);
		DOL_GULDUR.addEnemy(DWARF);
		DOL_GULDUR.addEnemy(GALADHRIM);
		DOL_GULDUR.addEnemy(DUNLAND);
		DOL_GULDUR.addEnemy(FANGORN);
		DOL_GULDUR.addEnemy(ROHAN);
		DOL_GULDUR.addEnemy(GONDOR);
		
		DOL_GULDUR.addKillPenalty(DOL_GULDUR);
		DOL_GULDUR.addKillPenalty(GUNDABAD);
		
		DOL_GULDUR.addKillBonus(WOOD_ELF);
		DOL_GULDUR.addKillBonus(GALADHRIM);
		
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMirkwoodSpider.class, 15));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurOrc.class, 10));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurOrcArcher.class, 5));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurBannerBearer.class, 2));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMirkTroll.class, 3));
		
		DWARF.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_DWARF);
		DWARF.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_DWARF);
		DWARF.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_DWARF);
		
		DWARF.setMiniquestAchievement(LOTRAchievement.doMiniquestDwarf);
		
		DWARF.addEnemy(GUNDABAD);
		DWARF.addEnemy(ANGMAR);
		DWARF.addEnemy(DOL_GULDUR);
		DWARF.addEnemy(URUK_HAI);
		DWARF.addEnemy(MORDOR);
		DWARF.addEnemy(HALF_TROLL);
		
		DWARF.addKillPenalty(DWARF);
		DWARF.addKillPenalty(BLUE_MOUNTAINS);
		
		DWARF.addKillBonus(GUNDABAD);
		
		DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfWarrior.class, 10));
		DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfAxeThrower.class, 5));
		DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfBannerBearer.class, 2));
		
		GALADHRIM.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_GALADHRIM);
		GALADHRIM.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_GALADHRIM);
		GALADHRIM.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_GALADHRIM);
		
		GALADHRIM.setMiniquestAchievement(LOTRAchievement.doMiniquestGaladhrim);
		
		GALADHRIM.addEnemy(GUNDABAD);
		GALADHRIM.addEnemy(ANGMAR);
		GALADHRIM.addEnemy(DOL_GULDUR);
		GALADHRIM.addEnemy(URUK_HAI);
		GALADHRIM.addEnemy(MORDOR);
		GALADHRIM.addEnemy(NEAR_HARAD);
		GALADHRIM.addEnemy(FAR_HARAD);
		GALADHRIM.addEnemy(HALF_TROLL);
		
		GALADHRIM.addKillPenalty(GALADHRIM);
		GALADHRIM.addKillPenalty(HIGH_ELF);
		GALADHRIM.addKillPenalty(WOOD_ELF);
		
		GALADHRIM.addKillBonus(GUNDABAD);
		GALADHRIM.addKillBonus(DOL_GULDUR);
		
		GALADHRIM.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGaladhrimWarrior.class, 15));
		GALADHRIM.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGaladhrimBannerBearer.class, 2));
		
		DUNLAND.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_DUNLAND);
		DUNLAND.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_DUNLAND);
		DUNLAND.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_DUNLAND);
		
		DUNLAND.setMiniquestAchievement(LOTRAchievement.doMiniquestDunland);
		
		DUNLAND.addEnemy(RANGER_NORTH);
		DUNLAND.addEnemy(GUNDABAD);
		DUNLAND.addEnemy(ANGMAR);
		DUNLAND.addEnemy(DOL_GULDUR);
		DUNLAND.addEnemy(ROHAN);
		DUNLAND.addEnemy(GONDOR);
		
		DUNLAND.addKillPenalty(DUNLAND);
		
		DUNLAND.addKillBonus(ROHAN);
		DUNLAND.addKillBonus(GONDOR);
		
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlending.class, 10));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingWarrior.class, 5));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingArcher.class, 5));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingBannerBearer.class, 2));
		
		URUK_HAI.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_URUK_HAI);
		URUK_HAI.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_URUK_HAI);
		URUK_HAI.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_URUK_HAI);
		
		URUK_HAI.setMiniquestAchievement(LOTRAchievement.doMiniquestUrukHai);
		
		URUK_HAI.addEnemy(HOBBIT);
		URUK_HAI.addEnemy(RANGER_NORTH);
		URUK_HAI.addEnemy(BLUE_MOUNTAINS);
		URUK_HAI.addEnemy(HIGH_ELF);
		URUK_HAI.addEnemy(WOOD_ELF);
		URUK_HAI.addEnemy(DWARF);
		URUK_HAI.addEnemy(GALADHRIM);
		URUK_HAI.addEnemy(FANGORN);
		URUK_HAI.addEnemy(ROHAN);
		URUK_HAI.addEnemy(GONDOR);
		
		URUK_HAI.addKillPenalty(URUK_HAI);
		
		URUK_HAI.addKillBonus(ROHAN);
		URUK_HAI.addKillBonus(FANGORN);
		
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHai.class, 10));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiCrossbower.class, 5));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiBerserker.class, 5));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiSapper.class, 3));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiBannerBearer.class, 2));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukWarg.class, 10));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukWargBombardier.class, 1));
		
		FANGORN.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_FANGORN);
		FANGORN.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_FANGORN);
		FANGORN.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_FANGORN);
		
		FANGORN.addEnemy(GUNDABAD);
		FANGORN.addEnemy(ANGMAR);
		FANGORN.addEnemy(DOL_GULDUR);
		FANGORN.addEnemy(URUK_HAI);
		FANGORN.addEnemy(MORDOR);
		FANGORN.addEnemy(HALF_TROLL);
		
		FANGORN.addKillPenalty(FANGORN);

		FANGORN.addKillBonus(GUNDABAD);
		FANGORN.addKillBonus(URUK_HAI);
		
		FANGORN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEnt.class, 10));
		FANGORN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHuorn.class, 20));
		
		ROHAN.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_ROHAN);
		ROHAN.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_ROHAN);
		ROHAN.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_ROHAN);
		
		ROHAN.setMiniquestAchievement(LOTRAchievement.doMiniquestRohan);
		
		ROHAN.addEnemy(GUNDABAD);
		ROHAN.addEnemy(ANGMAR);
		ROHAN.addEnemy(DOL_GULDUR);
		ROHAN.addEnemy(DUNLAND);
		ROHAN.addEnemy(URUK_HAI);
		ROHAN.addEnemy(MORDOR);
		ROHAN.addEnemy(NEAR_HARAD);
		ROHAN.addEnemy(FAR_HARAD);
		ROHAN.addEnemy(HALF_TROLL);
		
		ROHAN.addKillPenalty(ROHAN);
		ROHAN.addKillPenalty(GONDOR);
		
		ROHAN.addKillBonus(DUNLAND);
		ROHAN.addKillBonus(URUK_HAI);
		ROHAN.addKillBonus(MORDOR);
		
		ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohirrim.class, 10));
		ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohirrimArcher.class, 5));
		ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohanBannerBearer.class, 2));
		
		GONDOR.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_GONDOR);
		GONDOR.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_GONDOR);
		GONDOR.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_GONDOR);
		
		GONDOR.setMiniquestAchievement(LOTRAchievement.doMiniquestGondor);
		
		GONDOR.addEnemy(GUNDABAD);
		GONDOR.addEnemy(ANGMAR);
		GONDOR.addEnemy(DOL_GULDUR);
		GONDOR.addEnemy(DUNLAND);
		GONDOR.addEnemy(URUK_HAI);
		GONDOR.addEnemy(MORDOR);
		GONDOR.addEnemy(NEAR_HARAD);
		GONDOR.addEnemy(FAR_HARAD);
		GONDOR.addEnemy(HALF_TROLL);
		
		GONDOR.addKillPenalty(GONDOR);
		GONDOR.addKillPenalty(RANGER_NORTH);
		GONDOR.addKillPenalty(ROHAN);
		
		GONDOR.addKillBonus(MORDOR);
		GONDOR.addKillBonus(NEAR_HARAD);
		GONDOR.addKillBonus(FAR_HARAD);
		GONDOR.addKillBonus(HALF_TROLL);
		
		GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorSoldier.class, 10));
		GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorArcher.class, 5));
		GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorBannerBearer.class, 2));
		
		MORDOR.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_MORDOR);
		MORDOR.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_MORDOR);
		MORDOR.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_MORDOR);
		
		MORDOR.setMiniquestAchievement(LOTRAchievement.doMiniquestMordor);
		
		MORDOR.addEnemy(HOBBIT);
		MORDOR.addEnemy(RANGER_NORTH);
		MORDOR.addEnemy(BLUE_MOUNTAINS);
		MORDOR.addEnemy(HIGH_ELF);
		MORDOR.addEnemy(WOOD_ELF);
		MORDOR.addEnemy(DWARF);
		MORDOR.addEnemy(GALADHRIM);
		MORDOR.addEnemy(FANGORN);
		MORDOR.addEnemy(ROHAN);
		MORDOR.addEnemy(GONDOR);
		
		MORDOR.addKillPenalty(MORDOR);
		MORDOR.addKillPenalty(NEAR_HARAD);
		MORDOR.addKillPenalty(FAR_HARAD);
		MORDOR.addKillPenalty(HALF_TROLL);
		
		MORDOR.addKillBonus(ROHAN);
		MORDOR.addKillBonus(GONDOR);
		
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrc.class, 20));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcArcher.class, 10));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcBombardier.class, 5));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorBannerBearer.class, 5));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUruk.class, 10));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukArcher.class, 5));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWarg.class, 25));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWargBombardier.class, 2));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityOlogHai.class, 10));
		
		NEAR_HARAD.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_NEAR_HARAD);
		NEAR_HARAD.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_NEAR_HARAD);
		NEAR_HARAD.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_NEAR_HARAD);
		
		NEAR_HARAD.setMiniquestAchievement(LOTRAchievement.doMiniquestNearHarad);
		
		NEAR_HARAD.addEnemy(RANGER_NORTH);
		NEAR_HARAD.addEnemy(HIGH_ELF);
		NEAR_HARAD.addEnemy(WOOD_ELF);
		NEAR_HARAD.addEnemy(GALADHRIM);
		NEAR_HARAD.addEnemy(ROHAN);
		NEAR_HARAD.addEnemy(GONDOR);
		
		NEAR_HARAD.addKillPenalty(NEAR_HARAD);
		NEAR_HARAD.addKillPenalty(MORDOR);
		
		NEAR_HARAD.addKillBonus(GONDOR);
		
		NEAR_HARAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradrimWarrior.class, 10));
		NEAR_HARAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradrimArcher.class, 5));
		NEAR_HARAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradBannerBearer.class, 2));
		
		FAR_HARAD.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_FAR_HARAD);
		FAR_HARAD.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_FAR_HARAD);
		FAR_HARAD.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_FAR_HARAD);
		
		FAR_HARAD.addEnemy(RANGER_NORTH);
		FAR_HARAD.addEnemy(HIGH_ELF);
		FAR_HARAD.addEnemy(WOOD_ELF);
		FAR_HARAD.addEnemy(GALADHRIM);
		FAR_HARAD.addEnemy(ROHAN);
		FAR_HARAD.addEnemy(GONDOR);
		
		FAR_HARAD.addKillPenalty(FAR_HARAD);
		FAR_HARAD.addKillPenalty(MORDOR);
		
		FAR_HARAD.addKillBonus(GONDOR);
		
		// No Far Harad invasion mobs yet
		
		HALF_TROLL.addAlignmentAchievement(10, LOTRAchievement.alignmentGood10_HALF_TROLL);
		HALF_TROLL.addAlignmentAchievement(100, LOTRAchievement.alignmentGood100_HALF_TROLL);
		HALF_TROLL.addAlignmentAchievement(1000, LOTRAchievement.alignmentGood1000_HALF_TROLL);
		
		HALF_TROLL.addEnemy(HOBBIT);
		HALF_TROLL.addEnemy(RANGER_NORTH);
		HALF_TROLL.addEnemy(BLUE_MOUNTAINS);
		HALF_TROLL.addEnemy(HIGH_ELF);
		HALF_TROLL.addEnemy(WOOD_ELF);
		HALF_TROLL.addEnemy(DWARF);
		HALF_TROLL.addEnemy(GALADHRIM);
		HALF_TROLL.addEnemy(FANGORN);
		HALF_TROLL.addEnemy(ROHAN);
		HALF_TROLL.addEnemy(GONDOR);
		
		HALF_TROLL.addKillPenalty(HALF_TROLL);
		HALF_TROLL.addKillPenalty(MORDOR);
		
		HALF_TROLL.addKillBonus(GONDOR);
		
		HALF_TROLL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHalfTroll.class, 10));
		
		DARK_HUORN.addEnemy(LOTRFaction.HOBBIT);
		DARK_HUORN.addEnemy(LOTRFaction.RANGER_NORTH);
		
		DARK_HUORN.addKillBonus(LOTRFaction.HOBBIT);
		DARK_HUORN.addKillBonus(LOTRFaction.RANGER_NORTH);
		
		for (LOTRFaction f : LOTRFaction.values())
		{
			if (f.allowPlayer && f != LOTRFaction.UTUMNO)
			{
				LOTRFaction.UTUMNO.addEnemy(f);
				f.addEnemy(LOTRFaction.UTUMNO);
			}
		}
	}
	
	public void addEnemy(LOTRFaction f)
	{
		enemies.add(f);
	}
	
	public void addKillBonus(LOTRFaction f)
	{
		killBonuses.add(f);
	}
	
	public void addKillPenalty(LOTRFaction f)
	{
		killPenalties.add(f);
	}
	
	public boolean isEnemy(LOTRFaction other)
	{
		if (this == UNALIGNED || other == UNALIGNED)
		{
			return false;
		}
		if (this == HOSTILE || other == HOSTILE)
		{
			return true;
		}
		return enemies.contains(other);
	}
	
	public String codeName()
	{
		return name();
	}

	public String factionName()
	{
		if (LOTRMod.isAprilFools())
		{
			String[] names = {"9GAG", "Yes Scotland", "No Scotland", "Empire of Great Britain", "Detroit", "Kentucky Fried Chicken"};
			
			int i = ordinal();
			i = (int)(i + (i ^ 62341L) + 28703L * (i * i ^ 3195015L));
			factionRand.setSeed(i);
			List<String> list = Arrays.asList(names);
			Collections.shuffle(list, factionRand);
			return list.get(0);
		}
		
		return StatCollector.translateToLocal("lotr.faction." + codeName() + ".name");
	}
	
	public String factionEntityName()
	{
		return StatCollector.translateToLocal("lotr.faction." + codeName() + ".entity");
	}
	
	public String factionSubtitle()
	{
		return StatCollector.translateToLocal("lotr.faction." + codeName() + ".subtitle");
	}
	
	public boolean isAllied(LOTRFaction f)
	{
		if (this == UNALIGNED || this == HOSTILE)
		{
			return false;
		}
		if (f == UNALIGNED || f == HOSTILE)
		{
			return false;
		}
		if (this == f)
		{
			return true;
		}
		return !isEnemy(f);
	}
	
	public ItemStack createAlignmentReward()
	{
		return null;
	}
	
	public Map<Integer, LOTRAchievement> getAlignmentAchievements()
	{
		return alignmentAchievements;
	}
	
	public static LOTRFaction forName(String factionName)
	{
		for (LOTRFaction f : values())
		{
			if (f.codeName().equals(factionName))
			{
				return f;
			}
		}
		return null;
	}
	
	public static LOTRFaction forID(int ID)
	{
		for (LOTRFaction f : values())
		{
			if (f.ordinal() == ID)
			{
				return f;
			}
		}
		return null;
	}
	
	public static List getFactionNameList()
	{
		ArrayList list = new ArrayList();
		for (LOTRFaction f : values())
		{
			if (!f.allowPlayer || f.hasFixedAlignment)
			{
				continue;
			}
			list.add(f.codeName());
		}
		return list;
	}
	
	public static class FactionMapInfo
	{
		public final int posX;
		public final int posY;
		public final int radius;
		
		public FactionMapInfo(int x, int y, int r)
		{
			posX = x;
			posY = y;
			radius = r;
		}
	}
}
