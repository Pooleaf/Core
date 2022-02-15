package net.pooleaf.core.modules.commonscheduler;

import net.pooleaf.core.modules.commonscheduler.common.CommonScheduler;

public class CommonSchedulerAdapter<T extends CommonScheduler> {

    public T getScheduler() {
        return (T) CommonSchedulerModule.getScheduler();
    }

}
