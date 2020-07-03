package com.cubetiqs.util;

import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;

/**
 * Math Libra helper
 *
 * @author sombochea
 * @since 1.0
 */
public final class MathUtils {
    /**
     * Math Float Round for Double.
     * Example: 9.999999 ... n => 10
     * 9.99 => 9.99
     */
    public static Double fround(Number value, int precision, RoundingMode roundingMode) {
        if (StringUtils.isNull(value)) {
            return 0D;
        }
        if (StringUtils.isNull(roundingMode)) {
            roundingMode = RoundingMode.HALF_DOWN;
        }
        return ParserUtils.toDouble(NumberUtils.toDecimalPrecision(value, precision, roundingMode));
    }

    public static Double getPrecisionValue(Number value) {
        if (StringUtils.isNull(value)) {
            return 0D;
        }

        return ParserUtils.toDouble(value) - ParserUtils.toInt(value);
    }

    @NotNull
    public static Double minus(Number value1, Number value2) {
        if (StringUtils.isNull(value1)) {
            return 0D;
        }

        if (StringUtils.isNull(value2)) {
            return 0D;
        }

        return ParserUtils.toDouble(value1) - ParserUtils.toDouble(value2);
    }
}
