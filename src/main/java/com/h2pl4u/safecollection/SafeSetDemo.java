package com.h2pl4u.safecollection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;

/**
 * Created by Liuwei on 2020/9/23 17:20
 */
public class SafeSetDemo {
    public static void main(String[] args) {
        unsafeSetFunc();
        synchronizedSetFunc();
        copyOnWriteSetFunc();
    }

    private static void copyOnWriteSetFunc() {
        CopyOnWriteArraySet set = new CopyOnWriteArraySet();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> set.add(String.valueOf(i)), String.valueOf(i)).start());
    }

    private static void synchronizedSetFunc() {
        Set<String> set = new HashSet<>();
        Set<String> safeSet = Collections.synchronizedSet(set);
        IntStream.range(0, 30).forEach(i -> new Thread(() -> safeSet.add(String.valueOf(i)), String.valueOf(i)).start());
    }

    private static void unsafeSetFunc() {
        Set<String> set = new HashSet<>();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> set.add(String.valueOf(i)), String.valueOf(i)).start());
    }
}
