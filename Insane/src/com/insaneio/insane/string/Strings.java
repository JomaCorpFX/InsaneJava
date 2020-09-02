package com.insaneio.insane.string;

import com.insaneio.insane.object.Objects;
import com.insaneio.insane.utility.Random;

/**
 * @author Joma Espinoza Bone on 09/09/2015.
 */
public class Strings
{
    public static final String EMPTY = X.get("MmJWZc1PFvmuS51kXAm6Z6mZPtEmqjpZ4QjIclvLy0Y,", true)/* "" */;
    public static final String NEW_LINE = newLineX();

    private static String coalesceX(String value, String replaceValue)
    {
        if(isNullOrWhiteSpaceX(value))
        {
            value = null;
            return Objects.coalesce(value, replaceValue);
        }
        else
        {
            return value;
        }
    }

    private static Boolean isNullOrWhiteSpaceX(String value)
    {
        if (value == null)
        {
            return true;
        }
        return value.trim().length() == 0;
    }

    private static String escapeUnicodeX(String input) {
        StringBuilder b = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            b.append(String.format(X.get("0IcLhCrWA4pdOvU0x7CQC7qwcj48ZuRWk_-69TGgwrc,", true)/* "\\u%04x" */, (int)c));
        }
        return b.toString();
    }


    private static String formatX(String format, Object... params) {
        for (int i = 0; i < params.length; i++) {
            format = format.replace(String.format(X.get("03sA9jGIEvNHbFwcZXct0NkeGYgpGiJ0kIZ0A2mFUAE,", true)/* "{%s}" */, i), params[i].toString());
        }
        return format;
    }


    private static Boolean isNullOrEmptyX(String value)
    {
        if (value != null)
        {
            return value.length() == 0;
        }
        else
        {
            return true;
        }
    }


    private static String newLineX()
    {
        return System.getProperty(X.get("sonsLG9wS3xz0SmKoSZHDo-LRKOLUtzr_kh-6mQRzPU,", true)/* "line.separator" */);
    }


    private static String randomPadLeftX(String value, int maxWidth, char paddingChar) {
        return padLeftX(value, Random.generate(1, maxWidth), paddingChar);
    }


    private static String padLeftX(String value, int totalWidth, char paddingChar)
    {
        if(totalWidth <= value.length())
        {
            return value;
        }

        return String.format(formatX(X.get("cl4Ul7mlT9ePA1qSSUWkgHBSV5lDLdm7MV0ggId-0hA,", true)/* "%{0}s%s" */, totalWidth - value.length()), paddingChar, value).replace(X.get("FQaEMJ--EU-KF9z-vz2Bx8u-rBYMjKAm1oZduJFbMiE,", true)/* " " */, String.valueOf(paddingChar));
    }

    private static String randomPadRightX(String value, int maxWidth, char paddingChar) {
        return padRightX(value, Random.generate(1, maxWidth), paddingChar);
    }


    private static String padRightX(String value, int totalWidth, char paddingChar)
    {
        if(totalWidth <= value.length())
        {
            return value;
        }

        return String.format(formatX(X.get("mpLx_wT1js9U3mghX4ASb1OfvWkGwsGRr8__6-tzMoM,", true)/* "%s%{0}s" */, totalWidth - value.length()), value,paddingChar).replace(X.get("Cz_Gz2sdVYewjDpdQh5wktrgi-1rzw224s6on9mYTxk,", true)/* " " */, String.valueOf(paddingChar));
    }


    private static String reverseX(String value)
    {
        return new StringBuilder(value).reverse().toString();
    }

    public static String coalesce(String value, String replaceValue)
    {
        return coalesceX(value, replaceValue);
    }

    public static Boolean isNullOrWhiteSpace(String value)
    {
        return isNullOrWhiteSpaceX(value);
    }

    public static String escapeUnicode(String input)
    {
        return escapeUnicodeX(input);
    }

    public static String format(String format, Object... params)
    {
        return formatX(format, params);
    }

    public static Boolean isNullOrEmpty(String value)
    {
        return isNullOrEmptyX(value);
    }

    public static String randomPadLeft(String value, int maxWidth, char paddingChar)
    {
        return randomPadLeftX(value, maxWidth, paddingChar);
    }

    public static String padLeft(String value, int totalWidth, char paddingChar)
    {
        return padLeftX(value, totalWidth, paddingChar);
    }

    public static String randomPadRight(String value, int maxWidth, char paddingChar)
    {
        return randomPadRightX(value, maxWidth, paddingChar);
    }

    public static String padRight(String value, int totalWidth, char paddingChar)
    {
        return padRightX(value, totalWidth, paddingChar);
    }

    public static String reverse(String value)
    {
        return reverseX(value);
    }

}
