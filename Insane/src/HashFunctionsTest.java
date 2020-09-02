
import com.insaneio.insane.cryptography.HashAlgorithm;
import com.insaneio.insane.cryptography.HashManager;
import com.insaneio.insane.cryptography.HashSaltPair;
import com.insaneio.insane.cryptography.RsaKeyPair;
import com.insaneio.insane.cryptography.RsaManager;
import com.insaneio.insane.string.Strings;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Megam
 */
public class HashFunctionsTest
{

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String data = "HolaMundo!HolaMundo!HolaMundo!HolaMundo!HolaMundo!HolaMundo!HolaMundo!";
        String base64String = HashManager.toBase64(data.getBytes(), Boolean.FALSE, Boolean.TRUE);
        String base64StringLineBreak = HashManager.toBase64(data.getBytes(), Boolean.TRUE, Boolean.TRUE);
        String line = "_____________________________";
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "String -> Base64String", base64String));
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "String -> Base64String with linebraks", base64StringLineBreak));

        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64String -> String", HashManager.base64ToString(base64String)));
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64String with linebraks -> String", HashManager.base64ToString(base64StringLineBreak)));

        String hexString = HashManager.toHex(data.getBytes());
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "HexString -> String", hexString));
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "String -> HexString", HashManager.hexToString(hexString)));

        HashSaltPair pair1 = HashManager.toBase64Hash(data, HashAlgorithm.SHA512, false, false, 110, 120);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64 SHA-512 pair", pair1.toString()));
        HashSaltPair pair2 = HashManager.toBase64Hash(data, HashAlgorithm.SHA512, true, false, 110, 120);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64 SHA-512 with linebreaks pair", pair2.toString()));
        HashSaltPair pair3 = HashManager.toHexHash(data, HashAlgorithm.SHA512, 110, 120);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Hex SHA-512 pair", pair3.toString()));

        String hash1 = HashManager.toBase64Hash(data, pair1.getSalt(), HashAlgorithm.SHA512, Boolean.FALSE, false, 110);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64 SHA-512", hash1));
        String hash2 = HashManager.toBase64Hash(data, pair2.getSalt(), HashAlgorithm.SHA512, Boolean.TRUE, false, 110);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64 SHA-512 with linebreaks", hash2));
        String hash3 = HashManager.toHexHash(data, pair3.getSalt(), HashAlgorithm.SHA512, 110);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Hex SHA-512", hash3));

        boolean check1 = HashManager.matchesBase64Hash(data, pair1.getHash(), pair1.getSalt(), HashAlgorithm.SHA512, Boolean.FALSE, 120);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64 SHA-512 String check", check1));
        boolean check2 = HashManager.matchesBase64Hash(data, pair2.getHash(), pair2.getSalt(), HashAlgorithm.SHA512, Boolean.TRUE, 120);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64 SHA-512 with linebreaks String check", check2));
        boolean check3 = HashManager.matchesHexHash(data, pair3.getHash(), pair3.getSalt(), HashAlgorithm.SHA512, 120);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Hex SHA-512 String check", check3));

        String normalHash1 = HashManager.toBase64Hash(data, HashAlgorithm.SHA512, true, false);
        String normalHash2 = HashManager.toHexHash(data, HashAlgorithm.SHA512);
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Base64 SHA-512 String", normalHash1));
        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "Hex SHA-512 String", normalHash2));

        System.out.println(Strings.format("{0} {1} {0}\n{2}\n", line, "byteArray -> String", HashManager.toString(data.getBytes())));

        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAM4dOcX3O4yVw/TBHXq7YzYiMIG8PudRbNJBLamzVysX+u1TEDBcNn6nIGK2DEnsJ6/+ttpvigh9uCwkwQmSekkCAwEAAQ==";
            String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAzh05xfc7jJXD9MEdertjNiIwgbw+51Fs0kEtqbNXKxf67VMQMFw2fqcgYrYMSewnr/622m+KCH24LCTBCZJ6SQIDAQABAkAFDpFXN/vhdN+K1ChyQAHiNzVb9X+l7WjqAViKtZWhqb4YdqkgxxtUgUu4yI+oaAWYXMWKP9HwAJVcWSEVXGGVAiEA7AEACtOsqyJmvZRG4wlirgdsRZiL+oHFkvKyjK4XC70CIQDfk+hOwWPO+zENXPa2kkbzaG5Dgc7wgGMl297nf80rfQIgI0Ta/sM3cLPJatd36IIEj0FoutXbRWqTXiyK5l817nkCIC02VuMePpXLvEashwOynwB4HNwv8u41uFcemHwOHyptAiADB61JPrAag2QtSRborXco2RqTqUdTULDTnsyrL7KTCA==";
            String plainData = "Hola";
            Boolean keyAsXml = false;
            Boolean getUrlSafe = false;
            RsaKeyPair keys = RsaManager.createKeyPair(1024, keyAsXml, true);
            HashAlgorithm algorithm = HashAlgorithm.SHA1;
            String signature = RsaManager.SignBase64(plainData, algorithm, keys.getPrivateKey(), keyAsXml, getUrlSafe);
            System.out.println(Strings.format("La firma es : {0}", signature));

            Boolean verified = RsaManager.VerifyBase64Signature(plainData, algorithm, signature, keys.getPublicKey(), keyAsXml);
            System.out.println(Strings.format("Verificado : {0}", verified));

    }
}
