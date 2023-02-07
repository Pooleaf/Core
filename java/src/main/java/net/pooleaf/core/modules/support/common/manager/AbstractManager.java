package net.pooleaf.core.modules.support.common.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractManager<K, V> implements Manager<K, V> {

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


    @Override
    public void set(K key, V value) {
        datas.put(key, value);
    }

    @Override
    public V get(K key) {
        return datas.get(key);
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        return datas.getOrDefault(key, defaultValue);
    }

    @Override
    public V getOrMake(K key, V defaultValue) {
        V value = getOrDefault(key, defaultValue);
        datas.put(key, value);

        return value;
    }

    @Override
    public boolean exists(K key) {
        return datas.containsKey(key);
    }

    @Override
    public boolean remove(K key) {
        return datas.remove(key) != null;
    }

    @Override
    public void clear() {
        datas.clear();
    }

    @Override
    public int count() {
        return datas.size();
    }

    @Override
    public Set<K> keys() {
        return datas.keySet();
    }

    @Override
    public Collection<V> values() {
        return datas.values();
    }

}
