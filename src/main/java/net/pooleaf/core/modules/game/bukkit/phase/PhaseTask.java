package net.pooleaf.core.modules.game.bukkit.phase;

import net.pooleaf.core.modules.commonscheduler.common.CommonSchedulerTask;

public class PhaseTask extends CommonSchedulerTask {

    private Phase phase;

    private int phaseCount; // Phase 초
    private int currentCount; // 현재 초 (phaseStartCount부터 1초마다 - 1);


    public PhaseTask(Phase phase, int phaseCount) {
        super(phase.getPipeline().getPlugin());

        this.phaseCount = phaseCount;
        this.currentCount = phaseCount + 1;
    }


    @Override
    public void run() {
        currentCount--;

        if (currentCount == phaseCount) { // 시작
            phase.onStart();
        }

        phase.onRun(); // 매초마다 실행

        if (currentCount < 1) { // 종료
            phase.onEnd();
            cancel();
        }
    }

}
