package com.insaneio.insane.cryptography;

import com.insaneio.insane.string.Strings;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * @author Joma Espinoza Bone on 14/10/2014.
 */
public class RsaManager {

    public static String SignBase64(String data, HashAlgorithm hashAlgorithm, String privateKey, Boolean keyAsXml, Boolean getUrlSafe) throws Exception {
        return HashManager.toBase64(signRaw(HashManager.toByteArray(data), hashAlgorithm, privateKey, keyAsXml), false, getUrlSafe);
    }

    public static Boolean VerifyBase64Signature(String data, HashAlgorithm hashAlgorithm, String signature, String publicKey, Boolean keyAsXml) throws Exception {
        return verifyRaw(HashManager.toByteArray(data), hashAlgorithm, HashManager.base64ToByteArray(signature), publicKey, keyAsXml);
    }

    public static String SignHex(String data, HashAlgorithm hashAlgorithm, String privateKey, Boolean keyAsXml, Boolean getUrlSafe) throws Exception {
        return HashManager.toHex(signRaw(HashManager.toByteArray(data), hashAlgorithm, privateKey, keyAsXml));
    }

    public static Boolean VerifyHexSignature(String data, HashAlgorithm hashAlgorithm, String signature, String publicKey, Boolean keyAsXml) throws Exception {
        return verifyRaw(HashManager.toByteArray(data), hashAlgorithm, HashManager.hexToByteArray(signature), publicKey, keyAsXml);
    }

    public static byte[] signRaw(byte[] data, HashAlgorithm hashAlgorithm, String privateKey, Boolean keyAsXml) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(X.get("CX_SC0ngfg-ZVziK1KHUxgJhHZn8J17IHVP-QRpODGw,", true)/* "RSA" */);
        String algorithm = "SHA1withRSA";
        
        switch (hashAlgorithm.toString()) {
            case "SHA-256":
                algorithm = "SHA256withRSA";
                break;
            case "SHA-384":
                algorithm = "SHA384withRSA";
                break;
            case "SHA-512":
                algorithm = "SHA512withRSA";
                break;
        }

        Signature sig = Signature.getInstance(algorithm);
        PrivateKey privKey;
        if (keyAsXml) {
            RSAPrivateCrtKeySpec Key = readPrivateXmlStringKey(privateKey);
            privKey = factory.generatePrivate(Key);
        } else {
            PKCS8EncodedKeySpec PrivKeySpec = new PKCS8EncodedKeySpec(HashManager.base64ToByteArray(privateKey));
            privKey = factory.generatePrivate(PrivKeySpec);
        }
        sig.initSign(privKey);
        sig.update(data);
        return sig.sign();
    }

    public static Boolean verifyRaw(byte[] data, HashAlgorithm hashAlgorithm, byte[] signature, String publicKey, Boolean keyAsXml) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(X.get("CX_SC0ngfg-ZVziK1KHUxgJhHZn8J17IHVP-QRpODGw,", true)/* "RSA" */);
        String algorithm = "SHA1withRSA";
        switch (hashAlgorithm.toString()) {
            case "SHA-256":
                algorithm = "SHA256withRSA";
                break;
            case "SHA-384":
                algorithm = "SHA384withRSA";
                break;
            case "SHA-512":
                algorithm = "SHA512withRSA";
                break;
        }
        Signature sig = Signature.getInstance(algorithm);
        PublicKey pubKey;
        if (keyAsXml) {
            RSAPublicKeySpec Key = readPublicXmlStringKey(publicKey);
            pubKey = factory.generatePublic(Key);
        } else {
            X509EncodedKeySpec PubKeySpec = new X509EncodedKeySpec(HashManager.base64ToByteArray(publicKey));
            pubKey = factory.generatePublic(PubKeySpec);
        }
        sig.initVerify(pubKey);
        sig.update(data);
        return sig.verify(signature);
    }

    private static BigInteger bigIntegerWithoutSignBit(final byte[] bigInt) {
        byte[] tmp = new byte[bigInt.length - 1];
        if (bigInt[0] == 0) {
            System.arraycopy(bigInt, 1, tmp, 0, tmp.length);
            return new BigInteger(tmp);
        } else {
            return new BigInteger(bigInt);
        }
    }

    public static RsaKeyPair createKeyPair(int keySize, Boolean keyAsXml, Boolean indentXml) throws Exception {
        RsaKeyPair result = new RsaKeyPair();
        KeyPairGenerator MyKeyPairGenerator = KeyPairGenerator.getInstance(X.get("CA0glAbzo0weHxkmqH42IEv8Ci3jbvv4GlI6sHYF0Lc,", true)/* "RSA" */);
        MyKeyPairGenerator.initialize(keySize, new SecureRandom());
        KeyPair MyKeyPair = MyKeyPairGenerator.generateKeyPair();
        if (keyAsXml) {
            KeyFactory MyKeyFactory = KeyFactory.getInstance(X.get("ycD9jyv1IFJ1P6EbOSu8gvIRq5SvzfsILKoPURCXw5Q,", true)/* "RSA" */);
            RSAPrivateCrtKeySpec MyRSAPrivateKey = MyKeyFactory.getKeySpec(MyKeyPair.getPrivate(), RSAPrivateCrtKeySpec.class);
            result.setPublicKey(generatePublicXmlStrKey(MyRSAPrivateKey, indentXml));// generatePublicXmlStringKey or generatePublicXmlStrKey
            result.setPrivateKey(generatePrivateXmlStrKey(MyRSAPrivateKey, indentXml));// generatePrivateXmlStringKey or generatePrivateXmlStrKey
        } else {
            result.setPublicKey(HashManager.toBase64(MyKeyPair.getPublic().getEncoded(), false, false));
            result.setPrivateKey(HashManager.toBase64(MyKeyPair.getPrivate().getEncoded(), false, false));
        }
        return result;
    }

    private static String generatePrivateXmlStrKey(RSAPrivateCrtKeySpec key, Boolean indent) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        // doc.setXmlStandalone(true);

        Element rootElement = doc.createElement(X.get("m3wvV8T5_Mi-uKUUfcSuZQ57b-NnPRrpnrAGqw-Zf0c,", true)/* "RSAKeyValue" */);
        doc.appendChild(rootElement);

        Element MyElement = doc.createElement(X.get("t1Y1y6b_7dWI9Nznz2pW3FtGaFccnugzTU-HUiK0hdE,", true)/* "Modulus" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getModulus().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("5p5fwuqxsfo9uvgZqrP5RrltDsSIuFbFLs39O1i6egQ,", true)/* "Exponent" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getPublicExponent().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("NW5KS-ezK6DI2kehLiYlptZ-V6_NETXyu8CB2RbNFTY,", true)/* "P" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getPrimeP().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("dRAsIHHr30_miZ_mUmIETk4nNrG8su2Ly2aYMapG3ss,", true)/* "Q" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getPrimeQ().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("jjUAK-JkORd38Xw-fpivDEJwifuqDwLjzRgQWcthsBQ,", true)/* "DP" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getPrimeExponentP().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("XrY8p4VDvkIcVHPvNihAF7rXSwPrRkTRtc38mSgOpZw,", true)/* "DQ" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getPrimeExponentQ().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("pAjmS1JIDibd9xLeSBeGIzzQ9VyUDfofheaj0DCVsS8,", true)/* "InverseQ" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getCrtCoefficient().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("LL6ylni53LFJ7FXNWLYTo4pPz7ytGJ1aCdv7FMl1Xn4,", true)/* "D" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getPrivateExponent().toByteArray()).toByteArray(), false, false)));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // transformer.setOutputProperty(OutputKeys.ENCODING, "utf-16");
        if (indent) {
            transformer.setOutputProperty(X.get("CFk2YcYSTiV3Ipx7-TvE22IQsSsrLZzjXiW8cUJGNkPwAToq_gtzx9j6dDc5ercLFTXCjwCmSnWBsFuYoqqdkw,,", true)/* "{http://xml.apache.org/xslt}indent-amount" */, "2");
            transformer.setOutputProperty(OutputKeys.INDENT, X.get("rUvNDk5eoL7YfPMp5bsoV4wZ7Ov3ijrVzdAhkALZtko,", true)/* "yes" */);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, X.get("fFp8ET5RdpJduJpfBQLQxYCuNABViq28tv7lGNE5-qg,", true)/* "yes" */);
        DOMSource source = new DOMSource(doc);
        Writer w = new StringWriter();
        StreamResult result = new StreamResult(w);
        transformer.transform(source, result);
        return result.getWriter().toString();
    }

    private static String generatePublicXmlStrKey(RSAPrivateCrtKeySpec key, Boolean indent) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        // doc.setXmlStandalone(true);

        Element rootElement = doc.createElement(X.get("jNYZ5se3prk3j204Fiq_nWYzRMSM-NnLePdWuX-SZ3E,", true)/* "RSAKeyValue" */);
        doc.appendChild(rootElement);

        Element MyElement = doc.createElement(X.get("M8t9V_KfGAUHBa5e7mwyIDg80zWP8myqzpkP-LwR1WM,", true)/* "Exponent" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getPublicExponent().toByteArray()).toByteArray(), false, false)));

        MyElement = doc.createElement(X.get("sxCeFO7XB53zdXT7fMamXhfSV5sgwScxgNBbm6Xxz0g,", true)/* "Modulus" */);
        rootElement.appendChild(MyElement);
        MyElement.appendChild(doc.createTextNode(HashManager.toBase64(bigIntegerWithoutSignBit(key.getModulus().toByteArray()).toByteArray(), false, false)));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // transformer.setOutputProperty(OutputKeys.ENCODING, "utf-16");
        if (indent) {
            transformer.setOutputProperty(X.get("YkRAZdI9PMfIv_TvrzkzhyiSM1HhfVOWeM-IudK1gSxlKH26PV39r-Y5ObSZW7vvI_Tm61Uza5fpIUH0PT0FLw,,", true)/* "{http://xml.apache.org/xslt}indent-amount" */, "2");
            transformer.setOutputProperty(OutputKeys.INDENT, X.get("QIz0MWj486qrKY6zJ5hpM8HUT-ferTB23eF1ITKeHg8,", true)/* "yes" */);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, X.get("i9zFnWneH-Tj7BIOZGO9EFoHwgCiAlb1X-vCDC-vkE0,", true)/* "yes" */);
        DOMSource source = new DOMSource(doc);
        Writer w = new StringWriter();
        StreamResult result = new StreamResult(w);
        transformer.transform(source, result);
        return result.getWriter().toString();
    }

    public static String decryptFromBase64(String encryptedText, String privateKey, Boolean keyAsXml) throws Exception {
        return new String(decryptRaw(HashManager.base64ToByteArray(encryptedText), privateKey, keyAsXml), "UTF-8");
    }

    private static RSAPrivateCrtKeySpec readPrivateXmlStringKey(String key) throws Exception {
        Reader xml = new StringReader(key);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(new InputSource(xml));

        BigInteger Modulus = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("OmetgvVlOjtB7kUaMPzY1twLIAII2fFaAHuVdkiUIao,", true)/* "Modulus" */).item(0).getTextContent()));
        BigInteger Exponent = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("lqNfbnrp4gy83kwvYBBYS9K4XoWvGFsKkboAgEvrILQ,", true)/* "Exponent" */).item(0).getTextContent()));
        BigInteger P = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("jce5DpZFFshKsuNSDBxfiGHDFMVMUro_kFEFP-rmZzM,", true)/* "P" */).item(0).getTextContent()));
        BigInteger Q = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("2Isza7k6DZFHOUEHB7hQoFw9HgdFftAMnqGGo81izRo,", true)/* "Q" */).item(0).getTextContent()));
        BigInteger DP = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("UhnOQNx9f4uAEjT3ki-mL9Dqznur0o_uQussU2wSd7E,", true)/* "DP" */).item(0).getTextContent()));
        BigInteger DQ = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("fYBQMx2k_Oj7Sxu0q5rI-apgYIozfOyKPpXXooGiUU8,", true)/* "DQ" */).item(0).getTextContent()));
        BigInteger InverseQ = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("3u8xTeXpLAmWPSl6sJg96ZB4tZdlTVKZHxtAZj4YZpE,", true)/* "InverseQ" */).item(0).getTextContent()));
        BigInteger D = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("8aKboRGDVgH3HYQ1Q4iH2REd8Xmts8ByvHUp--yDdJc,", true)/* "D" */).item(0).getTextContent()));
        return new RSAPrivateCrtKeySpec(Modulus, Exponent, D, P, Q, DP, DQ, InverseQ);
    }

    private static BigInteger bigIntegerWithSignBit(final byte[] bigInt) {
        byte[] array = bigInt;
        byte[] tmp = new byte[array.length + 1];
        tmp[0] = 0;
        System.arraycopy(array, 0, tmp, 1, tmp.length - 1);
        return new BigInteger(tmp);
    }

    public static String decryptFromHex(String encryptedText, String privateKey, Boolean keyAsXml) throws Exception {
        return new String(decryptRaw(HashManager.hexToByteArray(encryptedText), privateKey, keyAsXml), X.get("wp7lKcnmiL77Eu7L2S460n5q14J7lTSxswaEUHIMqvk,", true)/* "UTF-8" */);
    }

    public static byte[] decryptRaw(byte[] encryptedBytes, String privateKey, Boolean keyAsXml) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(X.get("CX_SC0ngfg-ZVziK1KHUxgJhHZn8J17IHVP-QRpODGw,", true)/* "RSA" */);
        Cipher MyCipher = Cipher.getInstance(X.get("kJU7H9peNcfd8fwdBle8xOL71oT3IaUbBn8sneLFrge6A7hmXde1o_rHTPbZ4-JY", true)/* "RSA/ECB/PKCS1Padding" */);
        if (keyAsXml) {
            RSAPrivateCrtKeySpec Key = readPrivateXmlStringKey(privateKey);
            java.security.PrivateKey privKey = factory.generatePrivate(Key);
            MyCipher.init(Cipher.DECRYPT_MODE, privKey);
            return MyCipher.doFinal(encryptedBytes);
        } else {
            PKCS8EncodedKeySpec PrivKeySpec = new PKCS8EncodedKeySpec(HashManager.base64ToByteArray(privateKey));
            java.security.PrivateKey privKey = factory.generatePrivate(PrivKeySpec);
            MyCipher.init(Cipher.DECRYPT_MODE, privKey);
            return MyCipher.doFinal(encryptedBytes);
        }
    }

    public static byte[] encryptRaw(byte[] plainBytes, String publicKey, Boolean keyAsXml) throws Exception {
        byte[] ret;
        KeyFactory factory = KeyFactory.getInstance(X.get("-GQIlXi5EqHF482JMioFaiDxR-F6NVSX_yipmUtI29w,", true)/* "RSA" */);
        Cipher MyCipher = Cipher.getInstance(X.get("D4wqcMZ3-U6k025xyXoVd-N-kJHLBoWHPRoPRvayJ5GFqGyVac_BiVMY1xwWh53A", true)/* "RSA/ECB/PKCS1Padding" */);
        if (keyAsXml) {
            RSAPublicKeySpec Key = readPublicXmlStringKey(publicKey);
            java.security.PublicKey pubKey = factory.generatePublic(Key);
            MyCipher.init(Cipher.ENCRYPT_MODE, pubKey);
            ret = MyCipher.doFinal(plainBytes);
        } else {
            X509EncodedKeySpec PubKeySpec = new X509EncodedKeySpec(HashManager.base64ToByteArray(publicKey));
            java.security.PublicKey pubKey = factory.generatePublic(PubKeySpec);
            MyCipher.init(Cipher.ENCRYPT_MODE, pubKey);
            ret = MyCipher.doFinal(plainBytes);
        }
        return ret;
    }

    private static RSAPublicKeySpec readPublicXmlStringKey(String key) throws Exception {
        Reader xml = new StringReader(key);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(new InputSource(xml));
        BigInteger Modulus = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("0fjHx8dMDcADqncoPA7zZZkJ3wZWTfGcF7GXITaJXM8,", true)/* "Modulus" */).item(0).getTextContent()));
        BigInteger Exponent = bigIntegerWithSignBit(HashManager.base64ToByteArray(dom.getElementsByTagName(X.get("aEgSqsJK5c1Y5UVzuU76kLYl3iJVH2CZAaisoWCdGJs,", true)/* "Exponent" */).item(0).getTextContent()));
        return new RSAPublicKeySpec(Modulus, Exponent);
    }

    public static String encryptToBase64(String plainText, String publicKey, Boolean keyAsXml, Boolean getUrlSafe) throws Exception {
        return HashManager.toBase64(encryptRaw(plainText.getBytes(X.get("arm1d0ERHr63ow94qukjJ2vizTNkNmxP5e-XWJlAcyQ,", true)/* "UTF-8" */), publicKey, keyAsXml), false, getUrlSafe);
    }

    public static String encryptToHex(String plainText, String publicKey, Boolean keyAsXml) throws Exception {
        return HashManager.toHex(encryptRaw(plainText.getBytes(X.get("sbfsx_McumlsmEDOBOLMotgMOYD6RGAJr1CbXftHTfk,", true)/* "UTF-8" */), publicKey, keyAsXml));
    }

}
// @SuppressLint("TrulyRandom") es para engañar al compilador que esta
// clase/método utiliza un generador de números pséudo aleatorios confiable y
// seguro. Este error solo afecta a dispositivos android con versiones menores a 4.3.
// De lo que se tiene conocimiento es que google ya ha solucionado el bug en
// versiones futuras.

// Tamaño correcto de claves comienza en 512bits con intervalos de 8bits hasta
// 16384.
// Recomendaciones para tamaño de claves revisar:
// http://stackoverflow.com/questions/589834/what-rsa-key-length-should-i-use-for-my-ssl-certificates
// http://www.javamex.com/tutorials/cryptography/rsa_key_length.shtml
