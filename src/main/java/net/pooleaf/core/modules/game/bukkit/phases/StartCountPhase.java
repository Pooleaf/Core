package net.pooleaf.core.modules.game.bukkit.phases;

import net.pooleaf.core.modules.game.bukkit.phase.Phase;
import net.pooleaf.core.modules.game.bukkit.phase.PhasePipeline;
import net.pooleaf.core.modules.gui.bukkit.title.TitleBuilder;
import org.bukkit.Sound;

public class StartCountPhase extends Phase {

    public StartCountPhase(PhasePipeline pipeline) {
        super(pipeline);

        setPhaseCount(15);
    }


    @Override
    protected void onStart() {
        getGame().broadcastTitle(new TitleBuilder()
                .title("§c잠시 후 게임이 시작됩니다.")
                .build());
        getGame().broadcastSound(Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
    }

    @Override
    protected void onRun() {
        String color = getPhaseCount() > 5 ? "§e" : "§c";
        getGame().broadcastTitle(new TitleBuilder()
                .title(color + getPhaseCount())
                .build());
        getGame().broadcastSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
    }

    @Override
    protected void onEnd() {
        getGame().startSetting();
    }
}
