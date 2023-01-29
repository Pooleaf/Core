package net.pooleaf.core.modules.support.common.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractManager<K, V> {

    @Setter
    @Getter
    protected Map<K, V> datas;


    public AbstractManager() {
        datas = new HashMap<>();
    }

    @SneakyThrows
    public AbstractManager(Map map) {
        datas = map;
    }


    public void set(K key, V value) {
        datas.put(key, value);
    }

    public V get(K key) {
        return datas.get(key);
    }

    public V getOrDefault(K key, V defaultValue) {
        return datas.getOrDefault(key, defaultValue);
    }

    public V getOrMake(K key, V defaultValue) {
        V value = getOrDefault(key, defaultValue);
        datas.put(key, value);

        return value;
    }

    public V load(K key) {
        V value = loadNoCache(key);
        if (value != null) {
            datas.put(key, value);
        }

        return value;
    }

    public V loadNoCache(K key) {
        new UnsupportedOperationException("불러오기가 구현되지 않았습니다.");

        return null;
    }

    public V getOrLoad(K key) {
        if (datas.containsKey(key)) {
            return datas.get(key);
        }

        return load(key);
    }

    public V getOrLoadNoCache(K key) {
        if (datas.containsKey(key)) {
            return datas.get(key);
        }

        V value = loadNoCache(key);

        return value;
    }

    public boolean exists(K key) {
        return datas.containsKey(key);
    }

    public void remove(K key) {
        datas.remove(key);
    }

    public void clear() {
        datas.clear();
    }

    public int count() {
        return datas.size();
    }

    public Set<K> keys() {
        return datas.keySet();
    }

    public Collection<V> values() {
        return datas.values();
    }

}
