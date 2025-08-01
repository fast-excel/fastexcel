package cn.idev.excel.util;

import java.util.function.Consumer;

/**
 * object utils
 *
 *
 */
public class ObjectUtils {

    private ObjectUtils() {}

    public static <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
