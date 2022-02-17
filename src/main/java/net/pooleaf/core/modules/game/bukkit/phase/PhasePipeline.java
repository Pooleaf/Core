package net.pooleaf.core.modules.game.bukkit.phase;

import lombok.Data;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.ArrayList;
import java.util.List;

@Data
public class PhasePipeline {

    private CorePlugin plugin;

    private List<Phase> phases = new ArrayList<>();
    private int pointer = -1;


    public PhasePipeline(CorePlugin plugin) {
        this.plugin = plugin;
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
