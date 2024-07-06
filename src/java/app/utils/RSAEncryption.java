/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import javax.crypto.Cipher;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 * @author admin
 */
public class RSAEncryption {
    
    public String RSAdecryption(String encryptedData, String path) {
        String decrypt = "";
        try {
            // Read private key file
            FileInputStream fis = new FileInputStream(path);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            // Decode private key and regenerate it
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);
            // Decrypt data
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            byte decryptOut[] = c.doFinal(Base64.getDecoder().decode(
                    encryptedData));
            decrypt += new String(decryptOut);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return decrypt;
    }
    
    public String RSAencryption(String rawData, String path) {
        String encrypt = "";
        try {
            // read public key file
            FileInputStream fis = new FileInputStream(path);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            // decode public key and regenerate it
            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            // Encrypt data
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            byte encryptOut[] = c.doFinal(rawData.getBytes());
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
            encrypt += strEncrypt;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encrypt;
    }

    //this method generate key pair RESTRICTED
    private void generateKeys() {
        try {
            SecureRandom sr = new SecureRandom();
            // Key pair generate method,
            // length of keys should be 2048 to meet today's security requirement
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, sr);

            // initialize key pair
            KeyPair kp = kpg.genKeyPair();
            // PublicKey
            PublicKey publicKey = kp.getPublic();
            // PrivateKey
            PrivateKey privateKey = kp.getPrivate();
            File publicKeyFile = createKeyFile(new File("E:/summer2024-swp391.se1830-g6/web/private/publicKey.rsa"));
            File privateKeyFile = createKeyFile(new File("E:/summer2024-swp391.se1830-g6/web/private/privateKey.rsa"));
            System.out.println(publicKey.toString());
            System.out.println(privateKey.toString());
            // encode then save Public Key
            FileOutputStream fos = new FileOutputStream(publicKeyFile);
            fos.write(publicKey.getEncoded());
            fos.close();

            // encode then save Private Key
            fos = new FileOutputStream(privateKeyFile);
            fos.write(privateKey.getEncoded());
            fos.close();
            
            System.out.println("Generate key successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method create key file
    private File createKeyFile(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        return file;
    }
    private boolean authenData() {
        RSAEncryption rsa = new RSAEncryption();
        String path = String.valueOf(Paths.get("E:\\summer2024-swp391.se1830-g6\\web\\private\\privateKey.rsa"));
        String initData = "ngocdbhe182383@fpt.edu.vn0886799110lmaoPrefer_not_to_say45";
        String key = "J0kK2smx1GkXmaFITp5Iy1JEyyameGmKPQU8lypax9bXIktt2Dod6l8OTeGeB+6qjEVYu5ZVrX14Fq3DiRm/Q8/UjQMOCZoKoNPzQ1xBfOxX85yNskXJxGQrESYwQo8uZi3wauc6ZKkxFnYhfXtcTnnSrk3u1cuGX/ISa11z6P2h3jvXJmndKOM8hqxIDTrrUDhl+EwKPTcRpjiwwR5iBEx3vKNSiu6Hhf144/SZzGlWXia8smmQZ7lsv+v/5tErcEKf0Vv7mDYsZf3VupPzhNTQE+pVxqfjM90d11cNIAcuSGOtkqX/yo1GnSfFYxRy0myu1aNDeZTLSKTWLXM50w==";
        String truthData = rsa.RSAdecryption(key, path);
        System.out.println(initData);
        System.out.println(truthData);
        return initData.equals(truthData);
    }
    public static void main(String[] args) {
        RSAEncryption r = new RSAEncryption();
//        System.out.println(r.RSAdecryption(
//                "hLjctieDg9V9dPkTuQJL+cbMciEcZyzOdMMfUl2izuJKXtCKg+c9y7kvX1I7J3eT0UCFk5uVYzmuK25RDcYzLPxtF2b/hkajtim3x92TEHbvYPG25/JQHOOsVxCgGo0iAeVRSQ+Oct58yIA1o/ojK8LW96WaDdigONmDJPdzwt2SCtn7UWORj9+QLTNv14hmZPx7KVu/Me/Fp9mJk+eQOFpJLYQanIwzWCNqNceWIljogxZhexVn5VIW6SGBRSmdSWCakLQJWujzDD3UiDIW4y7mf1QK3uoI+0w0jzwzMTNVyxqpG5B5shS5l9LpEZBz8ESUVaU/5wNns2XAYkBifA=="
//        ));
        System.out.println(r.authenData());
    }
}
