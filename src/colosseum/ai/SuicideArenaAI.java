package colosseum.ai;

import arc.math.geom.Geometry;
import arc.struct.Seq;
import mindustry.ai.types.SuicideAI;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Teamc;

public class SuicideArenaAI extends SuicideAI {
    @Override
    public void updateUnit() {
        Teamc closestUnit = Units.closest(Team.sharded, unit.x, unit.y, unit.type.maxRange, u -> true);
        Teamc closestBlock = Units.bestTarget(Team.sharded, unit.x, unit.y, unit.type.maxRange, u -> false, b -> true, (b, x, y) -> -b.dst(unit.x, unit.y));

        Seq<Teamc> closestSeq = Seq.with();

        if (closestUnit != null) closestSeq.add(closestUnit);
        if (closestBlock != null) closestSeq.add(closestBlock);

        Teamc closest = Geometry.findClosest(unit.x, unit.y, closestSeq);

        if (closest != null) {
            if (unit.dst(closest.x(), closest.y()) > 8) {
                unit.moveAt(vec.set(closest).sub(unit).limit(unit.speed()));
                if (unit.moving()) unit.lookAt(unit.vel().angle());
            } else {
                unit.controlWeapons(false, true);
            }
        }
    }
}
