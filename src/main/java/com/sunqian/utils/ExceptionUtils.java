package com.sunqian.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExceptionUtils {

    public static String getRootMessage(final Throwable throwable) {
        return Optional.ofNullable(getRootCause(throwable)).map(Throwable::getMessage).orElse("");
    }

    public static Throwable getRootCause(final Throwable throwable) {
        final List<Throwable> list = getThrowableList(throwable);
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    public static List<Throwable> getThrowableList(Throwable throwable) {
        final List<Throwable> list = new ArrayList<>();
        while (throwable != null && !list.contains(throwable)) {
            list.add(throwable);
            throwable = throwable.getCause();
        }
        return list;
    }
}
