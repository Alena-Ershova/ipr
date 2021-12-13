package utils.cipher;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;


/**
 * Класс содержит методы шифровки и дешифровки строк,
 * генерации ключа для шифрования и получения
 * записанного ключа из файла
 */
public class EncryptionUtils {

    public static String encrypt(String message, Key key) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] input = message.getBytes();
            result = cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            System.err.println("Ошибка шифрования.");
        }
        return Base64.getEncoder().withoutPadding().encodeToString(result);
    }

    public static String decrypt(String message, Key key) {
        String result = "";
        byte[] input = Base64.getDecoder().decode(message);
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = new String(cipher.doFinal(input), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            System.err.println("Ошибка дешифрования.");
        }
        return result;
    }

    public static void createAnsStoreKey() {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Key key = keyGen.generateKey();
        File file = new File("key");
        try (OutputStream os = new FileOutputStream(file);) {
            os.write(key.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Key getKeyFromFile() {
        File file = new File("key");
        byte[] key = null;
        byte[] emptyArray = new byte[128];
        try (InputStream is = new FileInputStream(file)){
            //узнаем точную длину ключа
            int keySize = is.read(emptyArray);
            //переписываем в новый массив только байты ключа
            key = Arrays.copyOfRange(emptyArray, 0, keySize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(key, "AES");
    }
}
