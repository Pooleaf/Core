package net.pooleaf.core.modules.eventsupport;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.eventsupport.common.EventRegistererFactory;
import net.pooleaf.core.plugin.CorePlugin;

/**
 * 각 플랫폼에 있는 기존 이벤트들을 사용하여
 * 더 사용하기 편한 이벤트를 제공합니다.
 */
@Getter
public class EventSupportModule extends CoreModule {

    @Override
    public String getName() {
        return "EventSupport";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "Support" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        // BootstrapPlugin에서 등록
//        EventRegistererFactory.createEventRegisterer().registerEvents();
    }

}
