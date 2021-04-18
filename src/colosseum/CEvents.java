package colosseum;

import arc.Core;
import arc.Events;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.modules.ItemModule;

public class CEvents {
    public static void init() {
        Core.settings.defaults("autohost", true);

        Events.on(EventType.PlayerJoin.class, e -> {
            Vars.state.rules.bannedBlocks.addAll(ObjectSet.with(
                    Blocks.mechanicalDrill,
                    Blocks.pneumaticDrill,
                    Blocks.laserDrill,
                    Blocks.blastDrill,

                    Blocks.graphitePress,
                    Blocks.multiPress,
                    Blocks.siliconSmelter,
                    Blocks.siliconCrucible,
                    Blocks.kiln,
                    Blocks.plastaniumCompressor,
                    Blocks.phaseWeaver,
                    Blocks.surgeSmelter,
                    Blocks.pyratiteMixer,
                    Blocks.blastMixer,
                    Blocks.separator,
                    Blocks.disassembler,
                    Blocks.sporePress,
                    Blocks.coalCentrifuge,

                    Blocks.additiveReconstructor,
                    Blocks.multiplicativeReconstructor,
                    Blocks.exponentialReconstructor,
                    Blocks.tetrativeReconstructor
            ));

            Vars.state.rules.unitCap = 1000;
            Vars.state.rules.fire = false;

            Call.setRules(Vars.state.rules);

            int worldCenterX = Vars.world.width() / 2;
            int worldCenterY = Vars.world.height() / 2;

            if (!CVars.money.containsKey(e.player.uuid())) {
                e.player.sendMessage("[sky]It seems you are new to Crawler Colosseum. Use [accent]/info[] to find out how to play.[]");
                CVars.money.put(e.player.uuid(), 100f);
            }

            if (CVars.units.containsKey(e.player.uuid())) {
                Unit unit = Groups.unit.find( u -> u.type == CVars.units.get(e.player.uuid()) && u.team == e.player.team() );

                if (unit != null) e.player.unit(unit); else e.player.unit(CVars.units.get(e.player.uuid()).spawn(worldCenterX, worldCenterY));
            }
        });

        Events.on(EventType.WaveEvent.class, e -> {
           CVars.wave = Vars.state.wave;

           CWaves.spawnWave(CVars.difficulty, CVars.wave);
           CVars.difficulty *= CVars.difficultyMultiplier;
        });

        Events.on(EventType.ResetEvent.class, e -> {
           CVars.wave = 0;
           CVars.difficulty = 0.1f;
            CVars.money.forEach(args -> {
                CVars.money.remove(args.key);
                CVars.money.put(args.key, 100f);
            });
        });

        Events.on(EventType.UnitDestroyEvent.class, e -> {
            UnitType unit = e.unit.type;

            if (e.unit.team != Team.sharded) {

                float money = CVars.costs.get(unit.name);
                CVars.money.forEach(args -> {
                    CVars.money.remove(args.key);
                    CVars.money.put(args.key, args.value + money);
                });

                ItemStack[] items = CVars.itemMap.get(unit);
                if (items != null) {
                    ItemModule itemm = new ItemModule();
                    for (ItemStack i : items) {
                        itemm.add(i.item, i.amount);
                    }

                    if (Team.sharded.core() != null) Team.sharded.core().items.add(itemm);
                }
            }
        });
    }
}
