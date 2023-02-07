package net.pooleaf.core.modules.support.common.manager;

public interface ILoadableManager<K, V> extends IManager<K, V> {

    /**
     * 데이터베이스 등에서 해당 키에 맞는 값을 캐싱 없이 불러와 반환합니다.
     */
    V loadWithoutCache(K key);

    /**
     * 데이터베이스 등에서 해당 키에 맞는 값을 불러와 캐싱하고 반환합니다.
     */
    default V load(K key) {
        V value = loadWithoutCache(key);

        if (value == null) {
            return null;
        }

        set(key, value);
        return value;
    }

    /**
     * 해당 키에 맞는 값을 반환합니다.
     * 만약 캐싱되어 있지 않을 경우 데이터베이스 등에서 불러와 캐싱하고 반환합니다.
     */
    default V getOrLoad(K key) {
        V value = get(key);

        if (value == null) {
            return load(key);
        }

        return value;
    }

    /**
     * 해당 키에 맞는 값을 반환합니다.
     * 만약 캐싱되어 있지 않을 경우 데이터베이스 등에서 캐싱 없이 불러와 반환합니다.
     */
    default V getOrLoadWithoutCache(K key) {
        V value = get(key);

        if (value == null) {
            value = loadWithoutCache(key);
        }

        return value;
    }

}
