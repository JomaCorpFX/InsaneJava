
import com.insaneio.insane.cryptography.AesManager;
import com.insaneio.insane.cryptography.HashAlgorithm;
import com.insaneio.insane.cryptography.RsaKeyPair;
import com.insaneio.insane.cryptography.RsaManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Megam
 */
public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println("█ Aes Encryption/Decryption Test");
        String text = "HelloWorld";
        String key = "12345";
        text = AesManager.encryptToBase64(text, key, false);
        System.out.println("Encrypted: " + text);
        text = AesManager.decryptFromBase64(text, key);
        System.out.println("Decrypted: " + text);
        
        System.out.println("\n█ RSA Generate Key Test");
        RsaKeyPair keypair = RsaManager.createKeyPair(4096, false, false);
        System.out.println(keypair.toString());
        
        System.out.println("\n█ Rsa Encryption/Decryption Test");
//        String publicKey = "";
//        String privateKey = "";
//        keypair.setPublicKey(publicKey);
//        keypair.setPrivateKey(privateKey);
        text = RsaManager.encryptToBase64(text, keypair.getPublicKey(), false, false);
        System.out.println("Encrypted: " + text);
        // System.exit(0);
        text = RsaManager.decryptFromBase64(text, keypair.getPrivateKey(), false);
        System.out.println("Decrypted: " + text);
        
        System.out.println("\n█ Rsa Sign/Verify Test");
        String signature = RsaManager.SignBase64(text, HashAlgorithm.SHA512, keypair.getPrivateKey(), false, false);
        System.out.println("Encrypted: " + signature);
        System.out.println("Verified: " + RsaManager.VerifyBase64Signature(text, HashAlgorithm.SHA512, signature, keypair.getPublicKey(), false));

    }
}
