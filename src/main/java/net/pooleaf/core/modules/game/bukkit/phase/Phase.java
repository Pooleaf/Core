package net.pooleaf.core.modules.game.bukkit.phase;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.plugin.CorePlugin;

@Data
@RequiredArgsConstructor
public class Phase {

    private final PhasePipeline pipeline;

    private int phaseCount; // Phase 초
    private PhaseTask task;


    public CorePlugin getPlugin() {
        return pipeline.getPlugin();
    }

    public Game getGame() {
        return pipeline.getGame();
    }

    /**
     * 이 Phase를 건너뛸 것인지 여부를 반환합니다.
     * 오버라이딩해서 사용
     * @return 이 Phase를 건너뛸 것인지 여부
     */
    public boolean isSkip() {
        return false;
    }

    protected void onStart() {}

    protected void onRun() {}

    protected void onEnd() {}

    protected void onCancel() {}


    public void start() {
        task = new PhaseTask(this, phaseCount);
        task.runAsync(0, 20L);
    }

    public void cancel() {
        onCancel();
        task.cancel();
    }

}
