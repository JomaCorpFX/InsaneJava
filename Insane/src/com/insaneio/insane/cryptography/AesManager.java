package com.insaneio.insane.cryptography;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Joma Espinoza Bone on 14/10/2014.
 */
public class AesManager {

    private static final int MAX_IV_LENGTH = 16;
    private static final int MAX_KEY_LENGTH = 32;

    public static String decryptFromBase64(String cipherText, String key) throws Exception {
        byte[] CiPherBytes = HashManager.base64ToByteArray(cipherText);
        byte[] KeyBytes = key.getBytes(X.get("rUjtEghCNGvTKim9D6SveoXSA7eI14skNUtClkh32KA,", true)/* "UTF-8" */);
        return new String((decryptRaw(CiPherBytes, KeyBytes)));
    }

    public static byte[] generateValidKey(byte[] key) throws Exception {
        return Arrays.copyOf(HashManager.toRawHash(key, HashAlgorithm.SHA256), MAX_KEY_LENGTH);
    }

    public static String decryptFromHex(String cipherText, String key) throws Exception {
        byte[] CiPherBytes = HashManager.hexToByteArray(cipherText);
        byte[] KeyBytes = key.getBytes(X.get("sOKPkhP_BFCCX5PtjexfjPXX9GFEL-8GaCI7ZcoZF0w,", true)/* "UTF-8" */);
        return new String((decryptRaw(CiPherBytes, KeyBytes)));
    }

    public static byte[] decryptRaw(byte[] cipherBytes, byte[] keyBytes) throws Exception {
        byte[] IV = Arrays.copyOfRange(cipherBytes, cipherBytes.length - MAX_IV_LENGTH, cipherBytes.length);
        byte[] RealBytes = Arrays.copyOf(cipherBytes, cipherBytes.length - MAX_IV_LENGTH);
        Cipher AesAlgorithm = Cipher.getInstance(X.get("ijPfgsLuY0UVfPjZRJU_0NNxBP9b4-VDWixKsGpIRWbMv903Xj-TypRoxmtDLQ5o", true)/* "AES/CBC/PKCS5Padding" */);
        byte[] ValidKeyBytes = generateValidKey(keyBytes);
        SecretKeySpec secretKeySpec = new SecretKeySpec(ValidKeyBytes, X.get("eqLiIPttZ0IJiA35eUy780KvtvORr_G3yv9CeBreQ6M,", true)/* "AES" */);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
        AesAlgorithm.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] Decrypted = AesAlgorithm.doFinal(RealBytes);
        return Decrypted;
    }

    public static byte[] encryptRaw(byte[] plainBytes, byte[] keyBytes) throws Exception {
        Cipher AesAlgorithm = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ValidKeyBytes = generateValidKey(keyBytes);
        SecretKeySpec secretKeySpec = new SecretKeySpec(ValidKeyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(generateIV());
        AesAlgorithm.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] Encrypted = AesAlgorithm.doFinal(plainBytes);
        byte[] ret = new byte[Encrypted.length + MAX_IV_LENGTH];
        System.arraycopy(Encrypted, 0, ret, 0, Encrypted.length);
        System.arraycopy(ivParameterSpec.getIV(), 0, ret, Encrypted.length, MAX_IV_LENGTH);
        return ret;
    }

    private static byte[] generateIV() throws Exception {
        return generateRandomArray(MAX_IV_LENGTH);
    }

    public static byte[] generateRandomArray(int size) {
        SecureRandom RandomGenerator = new SecureRandom();
        byte[] ret = new byte[size];
        RandomGenerator.nextBytes(ret);
        return ret;
    }

    public static String encryptToBase64(String plaintext, String key, Boolean getUrlSafe) throws Exception {
        byte[] PlainBytes = plaintext.getBytes(X.get("xJmIu5ds-D0InTEFk880iO7aAFrTosE024Y5OPOv7CY,", true)/* "UTF-8" */);
        byte[] KeyBytes = key.getBytes(X.get("_xMTC5JaCRFTsinBzMoq9Yi99z0sbPNrfw37ZLYOW14,", true)/* "UTF-8" */);
        return HashManager.toBase64(encryptRaw(PlainBytes, KeyBytes), false, getUrlSafe);
    }

    public static String encryptToHex(String plaintext, String key) throws Exception {
        byte[] PlainBytes = plaintext.getBytes(X.get("VRFj7QTASXR6Uf4YsSjgpfv3I9FTvJTPEkPh5D19OcM,", true)/* "UTF-8" */);
        byte[] KeyBytes = key.getBytes(X.get("ZrP3BiJUPg1ZJwh1K1p8XY0UqxjVSZc3mziCfBJcbTQ,", true)/* "UTF-8" */);
        return HashManager.toHex(encryptRaw(PlainBytes, KeyBytes));
    }
}
