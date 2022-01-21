package net.pooleaf.core.modules.support.common.util;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractManager<K, V> {

    @Setter
    @Getter
    protected Map<K, V> datas = new HashMap<>();


    public void set(K key, V value) {
        datas.put(key, value);
    }

    public V get(K key) {
        return datas.get(key);
    }

    public V getOrDefault(K key, V defaultValue) {
        return datas.getOrDefault(key, defaultValue);
    }

    public boolean exists(K key) {
        return datas.containsKey(key);
    }

    public void remove(K key) {
        datas.remove(key);
    }

    public int count() {
        return datas.size();
    }

}
