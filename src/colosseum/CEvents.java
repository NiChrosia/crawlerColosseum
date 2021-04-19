package colosseum;

import arc.Core;
import arc.Events;
import colosseum.ai.ArenaAI;
import colosseum.ai.SuicideArenaAI;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.modules.ItemModule;

import java.util.concurrent.atomic.AtomicBoolean;

public class CEvents {
    public static void init() {
        Core.settings.defaults("autohost", true);

        Events.on(EventType.ServerLoadEvent.class, e -> {
            UnitTypes.crawler.defaultController = SuicideArenaAI::new;
            UnitTypes.crawler.maxRange = 8000;
            UnitTypes.crawler.speed = 0.6f;

            UnitTypes.atrax.defaultController = ArenaAI::new;
            UnitTypes.atrax.maxRange = 8000;
            UnitTypes.atrax.weapons.each(w -> {
                w.bullet.collidesAir = true;
            });

            UnitTypes.spiroct.defaultController = ArenaAI::new;
            UnitTypes.spiroct.maxRange = 8000;

            UnitTypes.arkyid.defaultController = ArenaAI::new;
            UnitTypes.arkyid.maxRange = 8000;

            UnitTypes.toxopid.defaultController = ArenaAI::new;
            UnitTypes.toxopid.maxRange = 8000;

            Vars.state.rules.unitCap = 1000;
            Vars.state.rules.fire = false;

            Vars.state.rules.canGameOver = false;
            Call.setRules(Vars.state.rules);

            AtomicBoolean liquid = new AtomicBoolean(false);
            Vars.world.tiles.eachTile(t -> liquid.set(liquid.get() || t.floor().liquidDrop != null));

            if (!liquid.get()) {
                CVars.navalUnits.each(u -> {
                    u.flying = true;
                    u.drag -= 0.1;
                });
            }
        });

        Events.on(EventType.PlayerJoin.class, e -> {
            Vars.state.rules.canGameOver = false;
            Call.setRules(Vars.state.rules);

            Building core = e.player.team().core();
            if (core != null) core.kill();

            int worldCenterX = Vars.world.width() / 2;
            int worldCenterY = Vars.world.height() / 2;

            if (!CVars.money.containsKey(e.player.uuid())) {
                e.player.sendMessage("[sky]It seems you are new to Crawler Colosseum. Use [accent]/info[] to find out how to play.[]");
                e.player.unit(CVars.startingUnit.spawn(worldCenterX * Vars.tilesize, worldCenterY * Vars.tilesize));
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

           Groups.player.each( p -> {
               p.unit().heal();
               p.sendMessage("[#" + Pal.heal + "]Healed your unit.[]");
           });
        });

        Events.on(EventType.ResetEvent.class, e -> {
           CVars.wave = 0;
           CVars.difficulty = 0.1f;
            CVars.money.forEach(args -> {
                CVars.money.remove(args.key);
                CVars.money.put(args.key, 100f);
            });

            Vars.state.rules.canGameOver = false;
            Call.setRules(Vars.state.rules);
        });

        Events.on(EventType.UnitDestroyEvent.class, e -> {
            UnitType unit = e.unit.type;

            if (e.unit.team != Team.sharded) {
                float money = CVars.costs.get(unit.name);

                ItemStack[] items = CVars.itemMap.get(unit);
                if (items != null) {
                    CVars.money.forEach(args -> CVars.money.put(args.key, args.value + money));

                    Call.label("+ $[sky]" + money + "[]", 3, e.unit.x, e.unit.y);

                    ItemModule itemm = new ItemModule();
                    for (ItemStack i : items) {
                        itemm.add(i.item, i.amount);
                    }

                    if (Team.sharded.core() != null) Team.sharded.core().items.add(itemm);
                }
            }
        });

        Events.on(EventType.BlockBuildBeginEvent.class, e -> {
            if (e.breaking) e.tile.setBlock(e.tile.block(), e.team, e.tile.build.rotation); else e.tile.setNet(Blocks.air);
        });
    }
}
