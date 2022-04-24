package ru.otus.homework11.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private Map<K, V> cache = new WeakHashMap<>(10,1);
    private List<HwListener> listenerList = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }

    @Override
    public List<V> findAll() {
        return cache.values()
                    .stream()
                    .toList();
    }

    @Override
    public V update(K key, V value) {
        cache.computeIfPresent(key, (k, v) -> v = value);
        return cache.get(key);
    }
}
