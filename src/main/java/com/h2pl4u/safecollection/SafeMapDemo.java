package com.h2pl4u.safecollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * 推荐使用ConcurrentHashMap
 * Created by Liuwei on 2020/9/23 17:23
 */
public class SafeMapDemo {
    public static void main(String[] args) {
        unsafeMapFunc();
        hashtableFunc();
        concurrentHashMapFunc();
        synchronizedMapFunc();
    }

    private static void synchronizedMapFunc() {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> safeMap = Collections.synchronizedMap(map);
        IntStream.range(0, 30).forEach(i -> new Thread(() -> safeMap.put(i, String.valueOf(i)), String.valueOf(i)).start());
    }

    private static void concurrentHashMapFunc() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> map.put(i, String.valueOf(i)), String.valueOf(i)).start());
    }

    private static void hashtableFunc() {
        Hashtable<Integer, String> map = new Hashtable<>();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> map.put(i, String.valueOf(i)), String.valueOf(i)).start());
    }

    private static void unsafeMapFunc() {
        Map<Integer, String> map = new HashMap<>();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> map.put(i, String.valueOf(i)), String.valueOf(i)).start());
    }

}
