package com.sava4632.ecommerce_api.model.payload;

import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import javax.crypto.Cipher;
import java.util.Base64;


public class EncryptionUtils {
    private static final String ALGORITHM = "AES";
    private static final String KEY = "YourSecretKey"; // Cambia esto por tu clave secreta

    public static String encrypt(String valueToEncrypt) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedValue = cipher.doFinal(valueToEncrypt.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Key generateKey() throws NoSuchAlgorithmException {
        return new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    }
}
