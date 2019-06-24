package com.sunqian.utils;

import lombok.NoArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 逻辑判断工具类
 *
 * @author sunqian
 * @date 2019/3/22
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public class LogicUtils {

    private volatile static LogicUtils logicUtils;

    public static LogicUtils getLogic() {
        return Optional.ofNullable(logicUtils).orElseGet(() -> {
            synchronized (LogicUtils.class) {
                return Optional.ofNullable(logicUtils).orElseGet(LogicUtils::new);
            }
        });
    }

    @SafeVarargs
    public final <T> boolean or(ExceptionPredicate<T> predicate, T... t) {
        return Arrays.stream(t).anyMatch(predicate);
    }

    public <T> void conOrElse(T t, ExceptionPredicate<T> predicate, ExceptionConsumer<T> consumerTrue, ExceptionConsumer<T> consumerFalse) {
        if (predicate.test(t)) {
            consumerTrue.accept(t);
        } else {
            consumerFalse.accept(t);
        }
    }

    public <T, R> R funOrElse(T t, ExceptionPredicate<T> predicate, ExceptionFunction<T, R> functionTrue, ExceptionFunction<T, R> functionFalse) {
        if (predicate.test(t)) {
            return functionTrue.apply(t);
        } else {
            return functionFalse.apply(t);
        }
    }

    public <T> void conOrThrow(T t, ExceptionPredicate<T> predicate, ExceptionConsumer<T> consumer, RuntimeException e) {
        if (predicate.test(t)) {
            consumer.accept(t);
        } else {
            throw new RuntimeException(ExceptionUtils.getRootCause(e).getMessage());
        }
    }

    public <T, R> R funOrThrow(T t, ExceptionPredicate<T> predicate, ExceptionFunction<T, R> function, RuntimeException e) {
        if (predicate.test(t)) {
            return function.apply(t);
        } else {
            throw new RuntimeException(ExceptionUtils.getRootCause(e).getMessage());
        }
    }

    public <T> void conOrEnd(T t, ExceptionPredicate<T> predicate, ExceptionConsumer<T> consumer) {
        if (predicate.test(t)) {
            consumer.accept(t);
        }
    }

    public <T, R> R funOrEnd(T t, ExceptionPredicate<T> predicate, ExceptionFunction<T, R> function, R end) {
        if (predicate.test(t)) {
            return function.apply(t);
        }
        return end;
    }

    <T> T funWithWhile(T t, ExceptionPredicate<T> predicate, ExceptionFunction<T, T> function) {
        while (predicate.test(t)) {
            t = function.apply(t);
        }
        return t;
    }

    public <T> void conWithWhile(T t, ExceptionPredicate<T> predicate, ExceptionConsumer<T> consumer) {
        while (predicate.test(t)) {
            Optional.ofNullable(consumer).ifPresent(con -> con.accept(t));
        }
    }

    @SafeVarargs
    public final <T> T generateObject(T t, ExceptionConsumer<T>... consumers) {
        Arrays.asList(consumers).forEach(consumer ->
                Optional.ofNullable(consumer).ifPresent(con -> con.accept(t))
        );
        return t;
    }

    @FunctionalInterface
    public interface ExceptionConsumer<T> extends Consumer<T> {

        /**
         * 重写默认的accept函数
         *
         * @param t 接受的泛型参数
         */
        @Override
        default void accept(T t) {
            try {
                acceptException(t);
            } catch (Exception e) {
                throw new RuntimeException(ExceptionUtils.getRootCause(e).getMessage());
            }
        }

        /**
         * 可以抛出异常的accept函数
         *
         * @param t 接受的泛型参数
         * @throws Exception 抛出异常
         */
        void acceptException(T t) throws Exception;

    }

    @FunctionalInterface
    public interface ExceptionFunction<T, R> extends Function<T, R> {

        /**
         * 重写默认的apply函数
         *
         * @param t 接受的泛型参数
         * @return 返回泛型R
         */
        @Override
        default R apply(T t) {
            try {
                return applyException(t);
            } catch (Exception e) {
                throw new RuntimeException(ExceptionUtils.getRootCause(e).getMessage());
            }
        }

        /**
         * 可以抛出异常的apply函数
         *
         * @param t 接受的泛型参数
         * @return 返回泛型R
         * @throws Exception 抛出异常
         */
        R applyException(T t) throws Exception;

    }

    @FunctionalInterface
    public interface ExceptionPredicate<T> extends Predicate<T> {

        /**
         * 重写默认的test函数
         *
         * @param t 接受的泛型参数
         * @return 返回逻辑结果
         */
        @Override
        default boolean test(T t) {
            try {
                return testException(t);
            } catch (Exception e) {
                throw new RuntimeException(ExceptionUtils.getRootCause(e).getMessage());
            }
        }

        /**
         * 可以抛出异常的test函数
         *
         * @param t 接受的泛型参数
         * @return 返回逻辑结果
         * @throws Exception 抛出异常
         */
        boolean testException(T t) throws Exception;

    }

}
