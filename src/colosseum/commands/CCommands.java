package colosseum.commands;

import arc.Core;
import arc.struct.Seq;
import arc.util.Log;
import colosseum.CVars;
import arc.graphics.Color;
import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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

    private static String formatAbilityCategory(String color, String string) {
        return "\n\n[" + color + "]" + string + ":[]";
     }

    private static String formatAbilityCategory(Color color, String string) {
        return "\n\n[#" + color + "]" + string + ":[]";
    }

    private static String formatAbility(String color, String abilityName, int min, int max) {
        return "\n[" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatAbility(String color, String abilityName, double min, int max) {
        return "\n[" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatAbility(String color, String abilityName, int min, double max) {
        return "\n[" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatAbility(String color, String abilityName, double min, double max) {
        return "\n[" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatAbility(String color, String abilityName, float min, int max) {
        return "\n[" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatAbility(String color, String abilityName, int min, float max) {
        return "\n[" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatAbility(String color, String abilityName, float min, float max) {
        return "\n[" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatAbility(Color color, String abilityName, int min, int max) {
        return "\n[#" +  color + "]" + abilityName + ":[] [sky]" + min + "[] - [sky]" + max + "[]";
    }

    private static String formatStatusAbility(String color, String abilityName, String types) {
        return "\n[" + color + "]" + abilityName + ":[] [sky]" + types + "[]";
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
                            "\n\n  [accent]'abilities:[] Shows all available unit abilities, and their costs.'" +
                            "\n\n  [accent]'ability:[] Adds the ability, if you can afford it.'" +
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

        String size = "[ [accent]Size[] ]";

        handler.<Player>register("abilities", CVars.descriptions.get("abilities"), (args, player) -> {
            player.sendMessage("[sky]Displaying ability upgrades.");
            Call.infoMessage(player.con(), "[accent]Abilities:[]" +
                    formatAbilityCategory("gold", "ForceField") +

                    formatAbility("accent", "Radius Tiles", 1, 25) +
                    formatAbility("accent", "Regen", 0.1, 10) +
                    formatAbility("accent", "Max", 100, 100000) +
                    formatAbility("accent", "Cooldown Seconds", 10, 0.1) +

                    formatAbilityCategory(Pal.heal, "RepairField") +


                    formatAbility("accent", "Range Tiles", 1, 50) +
                    formatAbility("accent", "Reload Seconds", 10, 0.1) +

                    formatAbilityCategory("gold", "ShieldRegenField") +

                    formatAbility("accent", "Shield per Reload", 10, 1000) +
                    formatAbility("accent", "Max Shield Health", 1000, 100000) +
                    formatAbility("accent", "Reload Seconds", 10, 0.1) +

                    formatAbilityCategory("gold", "StatusField") +

                    formatStatusAbility("accent", "Status Effect", "Overclock, Boss") +
                    formatAbility("accent", "Range Tiles", 1, 50) +
                    formatAbility("accent", "Reload Seconds", 10, 0.1) +
                    formatAbility("accent", "Duration Seconds", 1, 120)
            );
        });

        handler.<Player>register("balance", CVars.descriptions.get("balance"), (args, player) -> {
            player.sendMessage("[accent]Balance:[] $[sky]" + CVars.money.get(player.uuid()) + "[]");
        });

        handler.<Player>register("bal", CVars.descriptions.get("balance"), (args, player) -> {
            player.sendMessage("[accent]Balance:[] $[sky]" + CVars.money.get(player.uuid()) + "[]");
        });

        handler.<Player>register("upgrade", "<unit>", CVars.descriptions.get("upgrade"), (args, player) -> {
            String arg = args[0];
            UnitType unit = Vars.content.getByName(ContentType.unit, arg.toLowerCase());
            try {
                if (!CVars.itemMap.containsKey(unit)) {
                    if (unit != null) {
                        Float money = CVars.money.get(player.uuid());
                        Integer cost = CVars.costs.get(arg);

                        if (money >= cost) {
                            Floor floor = player.tileOn().floor();

                            String unitName = String.valueOf(unit.name.charAt(0)).toUpperCase() + unit.name.substring(1);

                            if (CVars.navalUnits.contains(unit)) {
                                if (floor.liquidDrop != null || unit.flying) {
                                    player.unit().kill();
                                    player.unit(unit.spawn(player.x, player.y));
                                    CVars.money.remove(player.uuid());
                                    CVars.money.put(player.uuid(), money - cost);

                                    player.sendMessage("[accent]Upgraded to unit [gold]" + unitName + "[] for $[sky]" + cost + "[]. Current balance is $[sky]" + CVars.money.get(player.uuid()) + "[].[]");
                                } else {
                                    player.sendMessage("[#" + Color.red + "]Naval units cannot be placed on land.[]");
                                }
                            } else {
                                player.unit().kill();
                                player.unit(unit.spawn(player.x, player.y));
                                CVars.money.put(player.uuid(), money - cost);

                                player.sendMessage("[accent]Upgraded to unit [#" + CVars.colorMap.get(unit) + "]" + unitName + "[] for $[sky]" + cost + "[]. Current balance is $[sky]" + CVars.money.get(player.uuid()) + "[].[]");
                            }
                        } else {
                            player.sendMessage("[#" + Color.red + "]Cannot afford upgrade.[]");
                        }
                    } else {
                        player.sendMessage("[#" + Color.red + "]Invalid unit type.");
                    }
                } else {
                    player.sendMessage("[#" + Color.red + "]Invalid unit type.");
                }
            } catch(Exception e) {
                player.sendMessage("[#" + Color.red + "]Invalid unit type '" + arg + "'.");
            }
        });

        handler.<Player>register("shield", "<amount>", CVars.descriptions.get("shield"), (arg, player) -> {
            int shieldAmount = Integer.parseInt(arg[0]);

            int cost = shieldAmount * 5;
            float money = CVars.money.get(player.uuid());
            if (money >= cost) {
                CVars.money.put(player.uuid(), money - cost);
                player.unit().shield(player.unit().shield + shieldAmount);
                player.sendMessage("[accent]Added [sky]" + shieldAmount + "[] shield to your unit.[]");
            } else {
                player.sendMessage("[#" + Color.red.cpy().shiftSaturation(-0.2f) + "]Cannot afford shield. ($[sky]" + cost + "[] > $[sky]" + CVars.money.get(player.uuid()).intValue() + ")[][]");
            }
        });

        handler.<Player>register("heal", CVars.descriptions.get("heal"), (arg, player) -> {
            float healAmount = player.unit().maxHealth - player.unit().health();

            float cost = healAmount * player.unit().hitSize / 10;

            if (CVars.money.get(player.uuid()) > cost) {
                player.unit().heal();
                player.sendMessage("[accent]Healing unit [sky]" + (int)healAmount + "[] health for $[sky]" + (int)cost + "[]. New balance: $[sky]" + CVars.money.get(player.uuid()) + "[][]");
                CVars.money.put(player.uuid(), (float)(CVars.money.get(player.uuid()).intValue() - (int)cost));
            } else {
                player.sendMessage("[#" + Color.red + "]Cannot afford heal. ($" + (int)cost + " > $" + CVars.money.get(player.uuid()).intValue() + ")[]");
            }
        });
    }

    public static void loadServer(CommandHandler handler) {
        handler.register("setbalance", "<player> <balance>", CVars.descriptions.get("setbalance"), arg -> {
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

        handler.register("difficulty", CVars.descriptions.get("difficulty"), arg -> {
            Log.info(CVars.difficulty);
        });

        handler.register("setdifficulty", "<difficulty>", CVars.descriptions.get("setdifficulty"), arg -> {
            try {
                CVars.difficulty = Integer.parseInt(arg[0]);
                Log.info("Set difficulty to @.", arg[0]);
            } catch(Exception e) {
                Log.info("Invalid difficulty.");
            }
        });

        handler.register("autohost", "<true/false>", CVars.descriptions.get("autohost"), arg -> {
            Core.settings.put("autohost", Boolean.parseBoolean(arg[0]));
            Log.info("Set autohost to @.", Core.settings.getBool("autohost"));
        });

        String map = "Crawler_Colosseum";
        if (Core.settings.getBool("autohost")) handler.handleMessage("host " + map + " survival");
    }
}
