package com.insaneio.insane.cryptography;

import com.insaneio.insane.string.Strings;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Formatter;

/**
 * @author Joma Espinoza Bone on 14/10/2014.
 */
public class HashManager
{

    private static String getHash(byte[] data, byte[] salt, Boolean isBase64, HashAlgorithm algorithm, Boolean insertLineBreaks, boolean getUrlSafe, int iterationNumber) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm.toString());
        digest.reset();
        digest.update(salt);
        digest.update(data);
        byte[] hash = toRawHash(data, algorithm);
        for (int i = 0; i < iterationNumber; i++)
        {
            hash = toRawHash(hash, algorithm);
        }
        return isBase64 ? HashManager.toBase64(hash, insertLineBreaks, getUrlSafe) : HashManager.toHex(hash);
    }

    public static HashSaltPair toBase64Hash(String data, HashAlgorithm algorithm, Boolean insertLineBreaks, boolean getUrlSafe, int saltSize, int iterationNumber) throws Exception {
        HashSaltPair ret = new HashSaltPair();
        byte[] Salt = AesManager.generateRandomArray(saltSize);
        ret.setHash(getHash(data.getBytes(X.get("Rw_WGFewvdrU8mpVQT1t8tgjzkEQXmEjJJ6NlP6j6Y8,", true)/* "UTF-8" */), Salt, true, algorithm, insertLineBreaks, getUrlSafe, iterationNumber));
        ret.setSalt(HashManager.toBase64(Salt, insertLineBreaks, false));
        return ret;
    }

    public static String toBase64Hash(String data, HashAlgorithm algorithm, boolean insertLineBreaks, Boolean getUrlSafe) throws Exception {
        return HashManager.toBase64(toRawHash(data, algorithm), insertLineBreaks, getUrlSafe);
    }

    public static String toBase64Hash(String data, String salt, HashAlgorithm algorithm, Boolean insertLineBreaks, Boolean getUrlSafe, int iterationNumber) throws Exception {
        return getHash(data.getBytes(X.get("j-tl9Rp05hNG0q3hW2rUlIEFwZEyyim0fwdJqydOSvM,", true)/* "UTF-8" */), base64ToByteArray(salt), true, algorithm, insertLineBreaks, getUrlSafe, iterationNumber);
    }

    public static Boolean matchesBase64Hash(String data, String hash, String salt, HashAlgorithm algorithm, Boolean insertLineBreaks, int iterationNumber) throws Exception {
        return HashManager.toUrlUnsafeBase64(hash).equals(toBase64Hash(data, salt, algorithm, insertLineBreaks, false, iterationNumber));
    }

    public static String toBase64(byte[] data, Boolean insertLineBreaks, Boolean getUrlSafe) throws Exception {
        String ret;
        if (insertLineBreaks)
        {
            ret = Base64.getMimeEncoder().encodeToString(data);
        }
        else
        {
            ret = Base64.getEncoder().encodeToString(data);
        }
        return getUrlSafe ? toUrlSafeBase64(ret) : ret;
    }

    public static String toBase64(String data, Boolean insertLineBreaks, Boolean getUrlSafe) throws Exception {
        return HashManager.toBase64(data.getBytes(X.get("dbd92cnzyYt15pboCWw7QDOXFi13N6QwSw-IzeUi59U,", true)/* "UTF-8" */), insertLineBreaks, getUrlSafe);
    }

    public static byte[] base64ToByteArray(String base64) throws Exception {
        base64 = toUrlUnsafeBase64(base64);
        return base64.contains(Strings.NEW_LINE) ? Base64.getMimeDecoder().decode(base64) : Base64.getDecoder().decode(base64);
    }

    public static String base64ToString(String base64) throws Exception {
        return new String((base64ToByteArray(base64)), X.get("SZXiY6Cxw4sPTNywOHnvTXXIU4ti79G7iQfnRoZobyU,", true)/* "UTF-8" */);
    }

    public static String toUrlSafeBase64(String base64) {
        return base64.replace(X.get("ikjexpymvdIhyp0RdV5E2jbILrED4ibKJK0z48z7uCQ,", true)/* "+" */, X.get("uGFf-F9H5AMb0MLiG5SBltClC-1Q_T7bXdu6ALnZC1w,", true)/* "-" */).
                replace(X.get("bscSUqoLxLAZCMVVsBU0obvyxIRaKUgSvXvRH65W1sw,", true)/* "/" */, X.get("Mar14DUTeqwiCgQNSS5t_ZLuhf55JoJdPMOFyzaEfk0,", true)/* "_" */).
                replace(X.get("5NUUcMVl7XNCy2cFoQ-zqZ3Ts0PIvmA1jzueLJEYpNI,", true)/* "=" */, X.get("NyvW5oS-24oZlESDq7LtAb_Pir5WfGYZR9yFm5XZQKE,", true)/* "," */);
    }

    public static String toUrlUnsafeBase64(String base64) {
        return base64.replace(X.get("QEl0dXBORK2pe7llFLjrXsbWUHkvHtMfqbm___GOxNE,", true)/* "-" */, X.get("wX6ZJ6AQdauTmJXOhNwSzgWxWs67s6fzDFOvToGWbzs,", true)/* "+" */).
                replace(X.get("euG8p_1LILKPF2ZYwhlAuQPYqwK-IypyBHiPIegvBAA,", true)/* "_" */, X.get("_NLzHdF8_hsTO-W42pzOeKb2BgUMxCmGp377oAOI0K4,", true)/* "/" */).
                replace(X.get("AO_IR_ahqru-KV-ovFvPccMohwxH9kgpMTlzOJOasDk,", true)/* "," */, X.get("V-P_V3dYCjus5p5lVCDeaYCMQJ7fFRN14j_CQgVnr48,", true)/* "=" */);
    }

    public static HashSaltPair toHexHash(String data, HashAlgorithm algorithm, int saltSize, int iterationNumber) throws Exception {
        HashSaltPair ret = new HashSaltPair();
        byte[] Salt = new byte[saltSize];
        SecureRandom RandomGenerator = new SecureRandom();
        RandomGenerator.nextBytes(Salt);
        ret.setHash(getHash(data.getBytes(X.get("xHBYcXVX3WNTwc15R70juw3DxIRSMMjfvYRT4XapDYM,", true)/* "UTF-8" */), Salt, false, algorithm, false, false, iterationNumber));
        ret.setSalt(HashManager.toHex(Salt));
        return ret;
    }

    public static String toHexHash(String data, HashAlgorithm algorithm) throws Exception {
        return HashManager.toHex(toRawHash(data, algorithm));
    }

    public static String toHexHash(String data, String salt, HashAlgorithm algorithm, int iterationNumber) throws Exception {
        return getHash(data.getBytes(X.get("94yQZTFtpN4ALZNLY43Pzw5hCoW4e43kMxqmb2677OI,", true)/* "UTF-8" */), hexToByteArray(salt), false, algorithm, false, false, iterationNumber);
    }

    public static Boolean matchesHexHash(String data, String saltedHashString, String salt, HashAlgorithm algorithm, int iterationNumber) throws Exception {
        return saltedHashString.equals(toHexHash(data, salt, algorithm, iterationNumber));
    }

    public static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        try (Formatter formatter = new Formatter(sb))
        {
            String format = X.get("hI_J2v3JQBG5zXkQbMdDEGv9K4UoKy9I9fvJvkfBeWQ,", true);/* "%02x" */
            for (byte b : data)
            {
                formatter.format(format, b);
            }
        }
        return sb.toString();
    }

    public static String toHex(String data) throws Exception {
        return HashManager.toHex(data.getBytes(X.get("toM_zSO2CNHdG_miXDhDZZtH8jYSPc4tkKjmCBbfLRg,", true)/* "UTF-8" */));
    }

    public static byte[] hexToByteArray(String hex) throws Exception {
        try
        {
            int Pair = hex.length() % 2;
            byte[] ret = new byte[hex.length() / 2];
            if (Pair == 0)
            {
                for (int i = 0; i < hex.length() / 2; i++)
                {
                    ret[i] = (byte) (Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
                }
            }
            else
            {
                throw new Exception();
            }
            return ret;
        }
        catch (Exception ex)
        {
            throw new Exception(X.get("iqSwWjjWmLQrg5_s4kZNI2Imwnb979EMKmjmdosQY6tyl89G4IG5lPibtW94rFjF", true)/* "Cadena no hexadecimal." */);
        }
    }

    public static String hexToString(String hex) throws Exception {
        return new String(hexToByteArray(hex), X.get("JazWojwb3QFIB9n9ZVRTvn3UEDDlavPZ0dqWyVHJvm0,", true)/* "UTF-8" */);
    }

    public static byte[] toRawHash(String data, HashAlgorithm algorithm) throws Exception {
        return toRawHash(data.getBytes(X.get("4ZTm-V8I7v28nUGffmcP106Am_MAequqOOXE2N3FyVA,", true)/* "UTF-8" */), algorithm);
    }

    public static byte[] toRawHash(byte[] data, HashAlgorithm algorithm) throws Exception {
        byte[] buffer = data;
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toString());
        messageDigest.reset();
        messageDigest.update(buffer);
        return messageDigest.digest();
    }

    public static byte[] toByteArray(String data) throws Exception {
        return data.getBytes(X.get("6ICZMjxQ51HU5MVD1Nvsl8DSyS08UKS6C4ftL7ZxXCA,", true)/* "UTF-8" */);
    }

    public static String toString(byte[] data) throws Exception {
        return new String(data, X.get("vB65_aEUKkDpJE3HLVTZBh_GW4mgRyxqkkepXzxl0ms,", true)/* "UTF-8" */);
    }

}
