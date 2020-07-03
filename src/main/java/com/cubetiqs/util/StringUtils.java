package com.cubetiqs.util;

import com.cubetiqs.logging.Log;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;

/**
 * String Utilities and Libra helper
 *
 * @author sombochea
 * @see Pattern
 * @see Matcher
 * @see Collectors
 * @see Function
 * @since 1.0
 */
public final class StringUtils {
    private static final String EMPTY_STRING = "";
    private static final String ALPHA_REGEX = "^[a-zA-Z]+$";
    private static final String NUMERIC_REGEX = "^[0-9]+$";
    private static final String ALPHA_NUMERIC_REGEX = "^[a-zA-Z0-9]+$";
    private static final String ALPHA_NUMERIC_SPACE_REGEX = "^[a-zA-Z0-9 ]+$";
    private static final Logger logger = Log.getLogger(StringUtils.class);

    public StringUtils() {
        // nothing to do here
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }

    public static boolean isEmpty(String value) {
        return value.length() == 0;
    }

    public static String ifEmptyNull(String value) {
        if (!isNull(value) && isEmpty(value)) {
            return null;
        }
        return value;
    }

    public static String ifNullEmpty(String value) {
        if (isNull(value)) {
            return EMPTY_STRING;
        }
        return value;
    }

    public static boolean isNullOrEmpty(String value) {
        return isNull(value) || isEmpty(value);
    }

    public static String getOrDefault(String text, String fallback) {
        if (isNull(text) || isEmpty(text)) {
            return fallback;
        }
        return text;
    }

    @NotNull
    public static String getOrEmpty(String text) {
        return getOrDefault(text, EMPTY_STRING);
    }

    public static String fromSpaceToUnderScoreAndUpperCase(String value) {
        if (isNull(value)) {
            return null;
        }
        return value.replace(' ', '_').toUpperCase();
    }

    public static boolean isAllCaps(String value) {
        if (isNull(value)) {
            return false;
        }

        return value.matches(".*[a-z].*");
    }

    public static boolean regex(String value, String regex) {
        if (isNull(value)) {
            return false;
        }

        if (isNull(regex)) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public boolean isAlpha(String value) {
        return regex(value, ALPHA_REGEX);
    }

    public boolean notAlpha(String value) {
        return !isAlpha(value);
    }

    public boolean isNumeric(String value) {
        return regex(value, NUMERIC_REGEX);
    }

    public boolean notNumeric(String value) {
        return !isNumeric(value);
    }

    public boolean isAlphaNumeric(String value) {
        return regex(value, ALPHA_NUMERIC_REGEX);
    }

    public boolean isAlphaNumeric(String value, boolean space) {
        String regex = ALPHA_NUMERIC_REGEX;
        if (space) {
            regex = ALPHA_NUMERIC_SPACE_REGEX;
        }
        return regex(value, regex);
    }

    public boolean isAlphaNumeric(String value, CharSequence sequence) {
        String regex = ALPHA_NUMERIC_REGEX;
        if (isNotNull(sequence)) {
            regex = "^[a-zA-Z0-9" + sequence + "]+$";
        }
        return regex(value, regex);
    }

    public boolean isSymbol(String value) {
        return notAlpha(value) && notNumeric(value);
    }

    public static List<String> splitting(String value, char splitter) {
        if (!isEmpty(value)) {
            return Arrays.asList(value.trim().split("\\s*" + splitter + "\\s*"));
        }

        return new ArrayList<>();
    }

    public static List<String> splitting(String data, String regex) {
        if (!isEmpty(data)) {
            return Arrays.asList(data.trim().split(regex));
        }

        return new ArrayList<>();
    }

    public static List<String> splitFields(String data, String delimiters) {
        if (!isEmpty(data)) {
            return Arrays.stream(data.split("[" + delimiters + "]"))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public static Map<String, String> mapFields(String fields) {
        List<String> rawFields = splitFields(fields, ";");
        Map<String, String> finalFields = new Hashtable<>();
        rawFields.forEach(
                i -> {
                    List<String> subFields = splitting(i, ':');
                    if (subFields.size() > 0) {
                        if (splitting(subFields.get(0), ',').size() > 1) {
                            // more logic
                            finalFields.put("default", i.trim());
                        } else {
                            subFields.forEach(j -> finalFields.put(subFields.get(0).trim(), j.trim()));
                        }
                    }
                });

        return finalFields;
    }

    public static Set<String> splittingAsHashSet(String data, char splitter) {
        return new HashSet<>(splitting(data, splitter));
    }

    public static String implode(List<String> data, CharSequence separator) {
        return String.join(separator, data);
    }

    public static String implode(String[] data, CharSequence separator) {
        return String.join(separator, data);
    }

    public static String[] explode(String data, String splitter) {
        return data.split(splitter);
    }

    public static String concat(String[] data) {

        StringBuilder ext = new StringBuilder();
        for (String a : data) {
            ext.append(a);
        }

        return ext.toString();
    }

    /**
     * Replacer with Template and Separator.
     *
     * @param template    String
     * @param replacement Map<String, Object>
     * @param separator   String.
     * @return String
     */
    public static String replacer(
            String template, Map<String, Object> replacement, CharSequence separator) {
        String patternString =
                separator + "(" + StringUtils.join(replacement.keySet(), '|') + ")" + separator;
        Pattern pattern = compile(patternString);
        Matcher matcher = pattern.matcher(template);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(stringBuffer, "\\" + replacement.get(matcher.group(1)) + "");
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
        matcher.appendTail(stringBuffer);

        return stringBuffer + "";
    }

    /**
     * Replacer with Template.
     *
     * @param template     String
     * @param replaceKey   String | Key to replace value with
     * @param replaceValue String | Value to replace.
     * @return String
     */
    public static String replacer(String template, String replaceKey, Object replaceValue) {
        Map<String, Object> replacement = new Hashtable<>();
        replacement.put(replaceKey, replaceValue);
        return replacer(template, replacement);
    }

    /**
     * Replacer with Template.
     *
     * @param template    String
     * @param replacement Map<String, Object>
     * @return String
     */
    public static String replacer(String template, Map<String, Object> replacement) {
        return replacer(template, replacement, "%");
    }

    /**
     * Find Specific Values by {want} delimiter.
     *
     * @param template String
     * @param sequence CharSequence
     * @return List of String.
     */
    public static List<String> findValues(String template, CharSequence sequence) {
        return findValues(template, sequence, sequence);
    }

    /**
     * Find Specific Values by {want} delimiter.
     *
     * @param template String
     * @param sequenceStart CharSequence
     * @param sequenceEnd CharSequence
     * @return List of String.
     */
    public static List<String> findValues(String template, CharSequence sequenceStart, CharSequence sequenceEnd) {
        List<String> data = new ArrayList<>();
        Pattern pattern = compile(escape4Regex(sequenceStart) + "(.*?)" + escape4Regex(sequenceEnd));
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            data.add(matcher.group(1));
        }

        return data;
    }

    public static String escape4Regex(CharSequence sequence) {
        return escape4Regex(sequence.toString());
    }

    public static String escape4Regex(String text) {
        return text.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
    }

    public static String join(String[] array) {
        if (isNull(array)) {
            return EMPTY_STRING;
        }

        return join(Arrays.asList(array));
    }


    public static void join(String[] array, char separator, StringBuilder sb) {
        if (isNull(array)) {
            return;
        }

        join(Arrays.asList(array), separator, sb);
    }


    public static String join(Collection<String> collection) {
        return join(collection, ',');
    }


    public static String join(Collection<String> collection, char separator) {
        if (isNull(collection) || collection.isEmpty()) {
            return EMPTY_STRING;
        }

        StringBuilder result = new StringBuilder();
        join(collection, separator, result);

        return result.toString();
    }


    public static void join(Iterable<String> iterable, char separator, StringBuilder sb) {
        join(iterable, separator, (x) -> x, sb);
    }

    public static <T> void join(T[] array, char separator, Function<T, String> function, StringBuilder sb) {
        if (isNull(array)) {
            return;
        }

        join(Arrays.asList(array), separator, function, sb);
    }


    public static <T> void join(Iterable<T> iterable, char separator, Function<T, String> function, StringBuilder sb) {
        if (isNull(iterable)) {
            return;
        }
        boolean first = true;
        for (T value : iterable) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            sb.append(function.apply(value));
        }
    }

    /**
     * Text is equal or not
     * Without case-sensitive
     *
     * @param s1 String
     * @param s2 String
     * @return boolean
     */
    public static boolean isEquals(String s1, String s2) {
        return (s1 + "".toUpperCase()).equals(s2 + "".toUpperCase());
    }
}