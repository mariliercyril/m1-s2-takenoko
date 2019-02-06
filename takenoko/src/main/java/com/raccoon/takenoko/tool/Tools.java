package com.raccoon.takenoko.tool;

import java.util.Map;

public final class Tools {

    private Tools() {

    }

    /**
     *
     * @param map A map that associates keys of some type to <b>positive</b> integers
     * @param <T> The key type
     * @return
     */
    public static <T> T mapMaxKey(Map<T, Integer> map) {

        T maxKey = (T)map.keySet().toArray()[0];
        int maxValue = map.get(maxKey);

        for (T key : map.keySet()) {
            if (map.get(key) >= maxValue) {
                maxValue = map.get(key);
                maxKey = key;
            }
        }

        return maxKey;
    }
}
