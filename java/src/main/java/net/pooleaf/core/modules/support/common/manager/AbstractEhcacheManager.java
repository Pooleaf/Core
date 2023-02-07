package net.pooleaf.core.modules.support.common.manager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractEhcacheManager<K, V> implements Manager<K, V> {

    private CacheManager cacheManager;
    private Cache cache;


    public AbstractEhcacheManager() {
        cacheManager = CacheManager.create();

        String cacheName = getClass().getCanonicalName() + ":" + UUID.randomUUID();
        cacheManager.addCache(cacheName);
        cache = cacheManager.getCache(cacheName);
    }

    public void setElement(Element element) {
        cache.put(element);
    }

    @Override
    public void set(K key, V value) {
        setElement(new Element(key, value));
    }

    public Element getElement(K key) {
        return cache.get(key);
    }

    @Override
    public V get(K key) {
        Element element = getElement(key);

        if (element == null) {
            return null;
        }

        return (V) element.getObjectValue();
    }
    
    @Override
    public V getOrDefault(K key, V defaultValue) {
        Element element = getElement(key);

        if (element == null) {
            return defaultValue;
        }

        return (V) element.getObjectValue();
    }

    @Override
    public V getOrMake(K key, V defaultValue) {
        Element element = getElement(key);

        if (element == null) {
            set(key, defaultValue);
            return defaultValue;
        }

        return (V) element.getObjectValue();
    }

    @Override
    public boolean exists(K key) {
        return cache.isKeyInCache(key);
    }

    @Override
    public boolean remove(K key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.removeAll();
    }

    @Override
    public int count() {
        return cache.getSize();
    }

    @Override
    public List<K> keys() {
        return cache.getKeys();
    }

    @Override
    public List<V> values() {
        return (List<V>) cache.getKeys().stream()
                .map(key -> cache.get(key).getObjectValue())
                .collect(Collectors.toList());
    }

    /**
     * 해당 키에 맞는 값에 n초 동안 접근하지 않을 경우 제거되도록 설정합니다.
     * 0으로 설정할 경우 제거되지 않습니다.
     */
    public boolean setTimeToIdle(K key, int seconds) {
        Element element = getElement(key);
        if (element == null) {
            return false;
        }

        element.setTimeToIdle(seconds);
        return true;
    }

    /**
     * 해당 키에 맞는 값이 n초 후 제거되도록 설정합니다.
     * 0으로 설정할 경우 제거되지 않습니다.
     */
    public boolean setTimeToLive(K key, int seconds) {
        Element element = getElement(key);
        if (element == null) {
            return false;
        }

        element.setTimeToLive(seconds);
        return true;
    }

}
