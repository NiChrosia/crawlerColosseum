package colosseum;

import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;

public class CVars {
    public static final ObjectMap<String, String> descriptions = ObjectMap.of(
            "info", "Provides necessary information about how to play Crawler Colosseum.",
            "upgrades", "Shows all available unit upgrades, and their costs.",
            "balance", "Provides your current balance",
            "upgrade", "Upgrade your current unit into another. Requires the money to do so.",
            "heal", "Heals your unit to full health, for $x price.",
            "shield", "Adds x shield health to your unit.",
            "shieldamount", "Gets your current amount of shield.",
            "setbalance", "Sets the player's balance to the specified amount.",
            "difficulty", "Returns the current difficulty",
            "setdifficulty", "Sets the difficulty to the specified value.",
            "autohost", "Enables or disables autohost."
    );

    public static final ObjectMap<String, String> emojis = ObjectMap.of(
            "alpha", "\uF7EB",
            "beta", "\uF7EA",
            "gamma", "\uF7E9",

            "dagger", "\uF800",
            "mace", "\uF7FF",
            "fortress", "\uF7FE",
            "scepter", "\uF7DB",
            "reign", "\uF7DA",

            "nova", "\uF7FD",
            "pulsar", "\uF7FC",
            "quasar", "\uF7FB",
            "vela", "\uF7C1",
            "corvus", "\uF7C0",

            "crawler", "\uF7FA",
            "atrax", "\uF7F9",
            "spiroct", "\uF7F8",
            "arkyid", "\uF7F7",
            "toxopid", "\uF7DE",

            "flare", "\uF7F6",
            "horizon", "\uF7F5",
            "zenith", "\uF7F4",
            "antumbra", "\uF7F3",
            "eclipse", "\uF7F2",

            "mono", "\uF7F1",
            "poly", "\uF7F0",
            "mega", "\uF7EF",
            "quad", "\uF7C3",
            "oct", "\uF7C2",

            "risso", "\uF7E7",
            "minke", "\uF7ED",
            "bryde", "\uF7EC",
            "sei", "\uF7C4",
            "omura", "\uF7C6"

    );

    public static final ObjectMap<String, Integer> costs = ObjectMap.of(
            "alpha", 100,
            "beta", 250,
            "gamma", 450,

            "dagger", 10, // Significantly lower than the others due to it being the starting unit
            "mace", 250,
            "fortress", 650,
            "scepter", 8500,
            "reign", 24000,

            "nova", 150,
            "pulsar", 350,
            "quasar", 850,
            "vela", 11500,
            "corvus", 30000,

            "crawler", 25,
            "atrax", 150,
            "spiroct", 550,
            "arkyid", 10500,
            "toxopid", 32000,

            "flare", 100,
            "horizon", 150,
            "zenith", 850,
            "antumbra", 9500,
            "eclipse", 22000,

            "mono", 200,
            "poly", 450,
            "mega", 1200,
            "quad", 12000,
            "oct", 26000,

            "risso", 450,
            "minke", 850,
            "bryde", 1200,
            "sei", 14500,
            "omura", 36000
    );

    public static Seq<UnitType> navalUnits = Seq.with(
            UnitTypes.risso,
            UnitTypes.minke,
            UnitTypes.bryde,
            UnitTypes.sei,
            UnitTypes.omura
    );

    public static ObjectMap<UnitType, ItemStack[]> itemMap = ObjectMap.of(
            UnitTypes.crawler, ItemStack.with(
                    Items.copper, 10,
                    Items.lead, 10
            ),
            UnitTypes.atrax, ItemStack.with(
                    Items.silicon, 25,
                    Items.metaglass, 15,
                    Items.graphite, 15
            ),
            UnitTypes.spiroct, ItemStack.with(
                Items.titanium, 35,
                    Items.thorium, 25,
                    Items.plastanium, 15
            ),
            UnitTypes.arkyid, ItemStack.with(
                Items.copper, 500,
                    Items.lead, 500,
                    Items.silicon, 350,
                    Items.metaglass, 250,
                    Items.graphite, 250,
                    Items.titanium, 150,
                    Items.thorium, 150
            ),
            UnitTypes.toxopid, ItemStack.with(
                Items.plastanium, 400,
                    Items.phaseFabric, 400,
                    Items.surgeAlloy, 450
            )
    );

    public static ObjectMap<UnitType, Color> colorMap = ObjectMap.of(
            UnitTypes.alpha, Pal.accent,
            UnitTypes.beta, Pal.accent,
            UnitTypes.gamma, Pal.accent,

            UnitTypes.dagger, Color.red,
            UnitTypes.mace, Color.red,
            UnitTypes.fortress, Color.red,
            UnitTypes.scepter, Color.red,
            UnitTypes.reign, Color.red,

            UnitTypes.nova, Pal.heal,
            UnitTypes.pulsar, Pal.heal,
            UnitTypes.quasar, Pal.heal,
            UnitTypes.vela, Pal.heal,
            UnitTypes.corvus, Pal.heal,

            UnitTypes.crawler, Pal.sap,
            UnitTypes.atrax, Pal.sap,
            UnitTypes.spiroct, Pal.sap,
            UnitTypes.arkyid, Pal.sap,
            UnitTypes.toxopid, Pal.sap,

            UnitTypes.flare, Color.red,
            UnitTypes.horizon, Color.red,
            UnitTypes.zenith, Color.red,
            UnitTypes.antumbra, Color.red,
            UnitTypes.eclipse, Color.red,

            UnitTypes.mono, Pal.heal,
            UnitTypes.poly, Pal.heal,
            UnitTypes.mega, Pal.heal,
            UnitTypes.quad, Pal.heal,
            UnitTypes.oct, Pal.heal,

            UnitTypes.risso, Color.sky,
            UnitTypes.minke, Color.sky,
            UnitTypes.bryde, Color.sky,
            UnitTypes.sei, Color.sky,
            UnitTypes.omura, Color.sky
    );

    public static ObjectMap<String, Float> money = ObjectMap.of();
    public static ObjectMap<String, UnitType> units = ObjectMap.of();
    public static int wave = Vars.state.wave;
    public static float difficulty = 0.1f;
    public static float difficultyMultiplier = 1.35f;
    public static Team enemyTeam = Team.crux;
    public static final UnitType startingUnit = UnitTypes.dagger;
}
