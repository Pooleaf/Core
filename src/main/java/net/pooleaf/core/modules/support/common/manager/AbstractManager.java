package net.pooleaf.core.modules.support.common.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractManager<K, V> {

    @Setter
    @Getter
    protected Map<K, V> datas;


    public AbstractManager() {
        datas = new HashMap<>();
    }

    @SneakyThrows
    public AbstractManager(Class<? extends Map> mapClass) {
        datas = mapClass.newInstance();
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

    public V load(K key) {
        new UnsupportedOperationException("불러오기가 구현되지 않았습니다.");

        return null;
    }

    public V getOrLoad(K key) {
        if (datas.containsKey(key)) {
            return datas.get(key);
        }

        V value = load(key);
        if (value != null) {
            datas.put(key, value);
        }

        return value;
    }

    public V getOrLoadNoCache(K key) {
        if (datas.containsKey(key)) {
            return datas.get(key);
        }

        V value = load(key);

        return value;
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
