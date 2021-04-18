package colosseum;

import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;
import mindustry.world.Tile;

public class CWaves {
    private static float enemyCalculator(float difficulty, int wave) {
        return wave * difficulty * 5f;
    }

    private static void spreadUnits(int x, int y, float range, Seq<UnitType> units) {
        units.each( u -> {
            float newX = Mathf.random(x - range, x + range);
            float newY = Mathf.random(y - range, y + range);

            u.spawn(CVars.enemyTeam, newX, newY);
        });
    }

    public static void spawnWave(float difficulty, int wave) {
        Groups.unit.each(u -> u.team == CVars.enemyTeam && !CVars.itemMap.containsKey(u.type), Unitc::kill);

        float enemyAmount = enemyCalculator(difficulty, wave);
        Seq<Tile> spawnTiles = Vars.spawner.getSpawns();

        Seq<UnitType> units = Seq.with();

        for (int i = 0; i < enemyAmount / spawnTiles.size; i++) {
            UnitType unit = UnitTypes.crawler;
            if (Mathf.chance(0.25 * difficulty) && CVars.wave > 8) unit = UnitTypes.atrax;
            if (Mathf.chance(0.025 * difficulty) && CVars.wave > 13) unit = UnitTypes.spiroct;
            if (Mathf.chance(0.005 * difficulty) && CVars.wave > 17) unit = UnitTypes.arkyid;
            if (Mathf.chance(0.0005 * difficulty) && CVars.wave > 21) unit = UnitTypes.toxopid;

            units.add(unit);
        }

        spawnTiles.each( t -> {
            spreadUnits(t.x * Vars.tilesize, t.y * Vars.tilesize, Vars.state.rules.dropZoneRadius / 10, units);
        });
    }
}
