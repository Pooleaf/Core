package net.pooleaf.core.modules.game.bukkit.phase;

import lombok.Data;
import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.plugin.CorePlugin;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

@Data
public class PhasePipeline {

    private final CorePlugin plugin;

    private final Game game;

    private List<Phase> phases = new ArrayList<>();
    private int pointer = -1;


    public boolean existsPhase(Class<? extends Phase> findPhaseClass) {
        return phases.stream()
                .map(phase -> phase.getClass())
                .filter(phaseClass -> phaseClass.equals(findPhaseClass))
                .count() > 0;
    }

    public void addPhase(Phase phase) {
        phases.add(phase);
    }

    public Phase nextPhase() {
        pointer++;

        if (phases.size() < pointer + 1) {
            return null;
        }

        Phase phase = phases.get(pointer);
        if (phase.isSkip()) {
            return nextPhase();
        }

        return phase;
    }

    public Phase getCurrentPhase() {
        if (pointer < 0 // nextPhase 한적이 없거나
                || phases.size() < pointer + 1) { // 마지막 Phase가 넘어갔을 경우
            return null;
        }

        return phases.get(pointer);
    }

    public boolean isLastPhase() {
        return phases.size() == pointer + 1;
    }

}
