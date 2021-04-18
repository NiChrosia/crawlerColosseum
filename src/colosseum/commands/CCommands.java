package colosseum.commands;

import arc.Core;
import arc.util.Log;
import colosseum.CVars;
import arc.graphics.Color;
import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;

public class CCommands {
    private static String parseUpgrade(String color, String name) {
        String formattedName = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
        return ("\n  " + CVars.emojis.get(name) + " [" + color + "]" + formattedName + "[]  :  $[sky]" + CVars.costs.get(name) + "[]");
    }

    private static String parseUpgrade(Color color, String name) {
        String formattedName = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
        return ("\n  " + CVars.emojis.get(name) + " [#" + color + "]" + formattedName + "[]  :  $[sky]" + CVars.costs.get(name) + "[]");
    }

    private static String parseUpgradeCategory(String color, String name) {
        return "\n\n[" + color + "]" + name + ":[]";
    }

    private static String parseUpgradeCategory(Color color, String name) {
        return "\n\n[#" + color + "]" + name + ":[]";
    }

    public static void loadClient(CommandHandler handler) {
        handler.<Player>register("info", CVars.descriptions.get("info"), (args, player) -> {
            Call.infoMessage(player.con(),
                    "[accent]Info:[]" +
                            "\n\nMoney is gained at the end of every wave, depending on the amount of damage dealt to enemies on that wave. " +
                            "Each health point is equivalent to $1." +
                            "\nYou will respawn after a wave is over if you die." +
                            "\n\n\n[accent]Commands:[]" +
                            "\n\n  [accent]'upgrades':[] Shows all available unit upgrades, and their costs." +
                            "\n\n  [accent]'bal'[]|[accent]'balance':[] Provides your current balance." +
                            "\n\n  [accent]'upgrade':[] Upgrades your current unit to the new unit, if you have enough money to afford it. " +
                            "[acid](not case-sensitive)[]" +
                            "\n\n  [accent]'info':[] This command."
            );
        });

        handler.<Player>register("upgrades", CVars.descriptions.get("upgrades"), (args, player) -> {
            player.sendMessage("[sky]Displaying unit upgrades.");
            Call.infoMessage(player.con(),
                    "[accent]Upgrades:[]" +

                            parseUpgradeCategory("gold", "Core Units") +

                            parseUpgrade("accent", "alpha") +
                            parseUpgrade("accent", "beta") +
                            parseUpgrade("accent", "gamma") +

                            parseUpgradeCategory(Color.red, "Ground Assault") +

                            parseUpgrade("red", "dagger") +
                            parseUpgrade("red", "mace") +
                            parseUpgrade("red", "fortress") +
                            parseUpgrade("red", "scepter") +
                            parseUpgrade("red", "reign") +

                            parseUpgradeCategory(Pal.heal.cpy().shiftSaturation(0.4f), "Ground Support") +

                            parseUpgrade(Pal.heal, "nova") +
                            parseUpgrade(Pal.heal, "pulsar") +
                            parseUpgrade(Pal.heal, "quasar") +
                            parseUpgrade(Pal.heal, "vela") +
                            parseUpgrade(Pal.heal, "corvus") +

                            parseUpgradeCategory(Pal.spore.cpy().shiftSaturation(0.4f), "Spider") +

                            parseUpgrade(Pal.spore, "crawler") +
                            parseUpgrade(Pal.spore, "atrax") +
                            parseUpgrade(Pal.spore, "spiroct") +
                            parseUpgrade(Pal.spore, "arkyid") +
                            parseUpgrade(Pal.spore, "toxopid") +

                            parseUpgradeCategory(Color.red, "Air Assault") +

                            parseUpgrade("red", "flare") +
                            parseUpgrade("red", "horizon") +
                            parseUpgrade("red", "zenith") +
                            parseUpgrade("red", "antumbra") +
                            parseUpgrade("red", "eclipse") +

                            parseUpgradeCategory(Pal.heal.cpy().shiftSaturation(0.4f), "Air Support") +

                            parseUpgrade(Pal.heal, "mono") +
                            parseUpgrade(Pal.heal, "poly") +
                            parseUpgrade(Pal.heal, "mega") +
                            parseUpgrade(Pal.heal, "quad") +
                            parseUpgrade(Pal.heal, "oct") +

                            parseUpgradeCategory(Color.sky.cpy().shiftSaturation(0.4f), "Naval Assault") +

                            parseUpgrade(Color.sky, "risso") +
                            parseUpgrade(Color.sky, "minke") +
                            parseUpgrade(Color.sky, "bryde") +
                            parseUpgrade(Color.sky, "sei") +
                            parseUpgrade(Color.sky, "omura") +
                            "\n\n[accent]Balance:[]" +
                            "\n$[sky]" + CVars.money.get(player.uuid()) + "[]"
            );
        });

        handler.<Player>register("balance", "Provides your current balance in Crawler Colosseum.", (args, player) -> {
            player.sendMessage("[accent]Balance:[] $[sky]" + CVars.money.get(player.uuid()) + "[]");
        });

        handler.<Player>register("bal", "Shorthand command for '[gold]balance[]'", (args, player) -> {
            player.sendMessage("[accent]Balance:[] $[sky]" + CVars.money.get(player.uuid()) + "[]");
        });

        handler.<Player>register("upgrade", "<unit>", "Upgrades your unit to the desired one, if you have enough money.", (args, player) -> {
            String arg = args[0];
            UnitType unit = Vars.content.getByName(ContentType.unit, arg.toLowerCase());
            if (unit != null) {
                Float money = CVars.money.get(player.uuid());
                Integer cost = CVars.costs.get(arg);

                if (money >= cost) {
                    Floor floor = player.tileOn().floor();
                    if (CVars.navalUnits.contains(unit)) {
                        if (floor.liquidDrop != null) {
                            player.unit().kill();
                            player.unit(unit.spawn(player.x, player.y));
                            CVars.money.remove(player.uuid());
                            CVars.money.put(player.uuid(), money - cost);
                        } else {
                            player.sendMessage("[#" + Color.red + "]Naval units cannot be placed on land.[]");
                        }
                    } else {
                        player.unit().kill();
                        player.unit(unit.spawn(player.x, player.y));
                        CVars.money.put(player.uuid(), money - cost);
                    }
                } else {
                    player.sendMessage("[#" + Color.red + "]Cannot afford upgrade.[]");
                }
            } else {
                player.sendMessage("[#" + Color.red + "]Invalid unit type.");
            }
        });
    }

    public static void loadServer(CommandHandler handler) {
        handler.register("setbalance", "<player> <balance>", "Sets the player's balance to the specified amount.", arg -> {
            if (arg.length != 2) {
                Log.err("Both arguments are necessary.");
            } else {
                Player player = Groups.player.find(p -> p.name.equals(arg[0]));

                if (player != null) {
                    try {
                        int balance = Integer.parseInt(arg[1]);

                        CVars.money.put(player.uuid(), (float) balance);

                        Log.info("Set @'s balance to @.", arg[0], arg[1]);
                    } catch (Exception err) {
                        Log.err("Argument 'balance' must be a valid number (<= 9 digits).");
                    }
                } else {
                    Log.err("Invalid player name.");
                }
            }
        });

        handler.register("difficulty", "Returns the current difficulty.", arg -> {
            Log.info(CVars.difficulty);
        });

        handler.register("setdifficulty", "<difficulty>", "Sets the difficulty to the specified value.", arg -> {
            try {
                CVars.difficulty = Integer.parseInt(arg[0]);
                Log.info("Set difficulty to @.", arg[0]);
            } catch(Exception e) {
                Log.info("Invalid difficulty.");
            }
        });

        handler.register("autohost", "<true/false>", "Enables or disables autohost.", arg -> {
            Core.settings.put("autohost", Boolean.parseBoolean(arg[0]));
            Log.info("Set autohost to @.", Core.settings.getBool("autohost"));
        });

        String map = "Crawler_Colosseum";
        if (Core.settings.getBool("autohost")) handler.handleMessage("host " + map + " survival");
    }
}
