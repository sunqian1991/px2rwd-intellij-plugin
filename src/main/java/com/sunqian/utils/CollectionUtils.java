package com.sunqian.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 集合工具类
 *
 * @author sunqian
 * @date 2019/6/6
 */
@SuppressWarnings({"unused"})
public class CollectionUtils {

    private volatile static CollectionUtils collectionUtils;

    public static CollectionUtils getCollections() {
        if (collectionUtils == null) {
            synchronized (CollectionUtils.class) {
                if (collectionUtils == null) {
                    collectionUtils = new CollectionUtils();
                }
            }
        }
        return collectionUtils;
    }

    public <E, R> R getFirst(List<E> list, Function<E, R> function) {
        return Optional.ofNullable(list).filter(l -> l.size() > 0).map(l -> function.apply(l.get(0))).orElse(null);
    }

    @SafeVarargs
    public final <T> List<T> concatenate(List<T>... lists) {
        return LogicUtils.getLogic().generateObject(new ArrayList<>(), list -> Stream.of(lists).forEach(list::addAll));
    }

}
