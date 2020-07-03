package com.cubetiqs.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Number Libra helper
 *
 * @author sombochea
 * @since 1.0
 */
public final class NumberUtils {
    private static final String DECIMAL_PATTERN = "#.##";
    private static final String ROUNDING_DECIMAL_PATTERN = "##0";

    public static String toStringDecimal(Number value, String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            pattern = DECIMAL_PATTERN;
        }
        return new DecimalFormat(pattern).format(value);
    }

    public static String toDecimalPrecision(Number value, int precision, RoundingMode roundingMode) {
        if (value == null) {
            return null;
        }

        StringBuilder pattern = new StringBuilder(ROUNDING_DECIMAL_PATTERN);

        if (precision > 0) {
            pattern.append(".");
        }

        while (precision > 0) {
            pattern.append("0");
            precision--;
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());

        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_EVEN;
        }

        decimalFormat.setRoundingMode(roundingMode);

        return decimalFormat.format(value);
    }
}
