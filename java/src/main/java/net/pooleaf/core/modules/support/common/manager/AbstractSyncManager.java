package net.pooleaf.core.modules.support.common.manager;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSyncManager<K, V> extends AbstractManager<K, V> {

    public AbstractSyncManager() {
        datas = new ConcurrentHashMap<>();
    }

}
