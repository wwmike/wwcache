package com.wwmike;

import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@Singleton
public class CacheService {

    private static final Map<String, Map<String, String>> cacheStorage = new HashMap<>();

    public Map<String, String> getCache(String cache) {
        if (!cacheStorage.containsKey(cache)) {
            cacheStorage.put(cache, new WeakHashMap<>());
        }

        return cacheStorage.get(cache);

    }

    public void deleteCache(String name) {
        if (!cacheStorage.containsKey(name)) {
            throw new CacheNotFoundException(name);
        }

        cacheStorage.remove(name);
    }
}
