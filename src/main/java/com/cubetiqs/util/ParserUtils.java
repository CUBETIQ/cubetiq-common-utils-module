package com.cubetiqs.util;

import org.jetbrains.annotations.NotNull;

/**
 * Parser Libra helper
 *
 * @author sombochea
 * @see Integer
 * @see Double
 * @see Number
 * @see StringUtils
 * @since 1.o
 */
public final class ParserUtils {
    @NotNull
    public static Integer toInt(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public static Integer toInt(String value, Integer fallback) {
        if (StringUtils.isNullOrEmpty(value)) {
            return fallback;
        }
        return Integer.parseInt(value);
    }

    @NotNull
    public static Integer toInt(Number value) {
        if (StringUtils.isNull(value)) {
            return 0;
        }

        return Integer.parseInt(value.toString());
    }

    public static Integer toInt(Number value, Integer fallback) {
        if (StringUtils.isNull(value)) {
            return fallback;
        }

        return Integer.parseInt(value.toString());
    }

    @NotNull
    public static Double toDouble(String value) {
        if (StringUtils.isNull(value)) {
            return 0D;
        }

        return Double.parseDouble(value);
    }

    public static Double toDouble(String value, Double fallback) {
        if (StringUtils.isNull(value)) {
            return fallback;
        }

        return Double.parseDouble(value);
    }

    @NotNull
    public static Double toDouble(Number value) {
        if (StringUtils.isNull(value)) {
            return 0D;
        }

        return Double.parseDouble(value.toString());
    }

    public static Double toDouble(Number value, Double fallback) {
        if (StringUtils.isNull(value)) {
            return fallback;
        }

        return Double.parseDouble(value.toString());
    }
}
