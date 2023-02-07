package net.pooleaf.core.modules.support.common.manager;

import java.util.Collection;

/**
 * 인스턴스를 관리하는 Manager 인터페이스
 * @param <K> 키
 * @param <V> 값
 */
public interface Manager<K, V> {

    /**
     * 해당 키에 값을 설정합니다.
     */
    void set(K key, V value);

    /**
     * 해당 키에 맞는 값을 반환합니다.
     */
    V get(K key);

    /**
     * 해당 키에 맞는 값을 반환합니다.
     * 값이 없을 경우 기본 값을 반환합니다.
     */
    V getOrDefault(K key, V defaultValue);

    /**
     * 해당 키에 맞는 값을 반환합니다.
     * 값이 없을 경우 기본 값으로 설정하고 반환합니다.
     */
    V getOrMake(K key, V defaultValue);

    /**
     * 해당 키에 맞는 값의 존재 여부를 반환합니다.
     */
    boolean exists(K key);

    /**
     * 해당 키에 맞는 값을 제거합니다.
     * 해당 키에 맞는 값을 제거했을 경우 true, 해당 키에 맞는 값이 없을 경우 false를 반환합니다.
     */
    boolean remove(K key);

    /**
     * 모든 키와 값을 삭제합니다.
     */
    void clear();

    /**
     * 모든 값의 개수를 반환합니다.
     */
    int count();

    /**
     * 모든 키를 반환합니다.
     */
    Collection<K> keys();

    /**
     * 모든 값을 반환합니다.
     * @return
     */
    Collection<V> values();

}