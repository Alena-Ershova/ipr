package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static utils.cipher.EncryptionUtils.decrypt;
import static utils.cipher.EncryptionUtils.getKeyFromFile;

/**
 * Класс для получения данных из json файла
 */
public class TestDataStorage {
    private static Map<String, LoginData> loginStorage;

    private static void init() {
        loginStorage = new HashMap<>();
        Key key = getKeyFromFile();
        ObjectMapper objectMapper = new ObjectMapper();
        LoginData data = new LoginData();
        try {
            LoginData loginData = objectMapper.readValue(TestDataStorage.class.getResource("/LoginData.json"), LoginData.class);
            data.setLogin(decrypt(loginData.getLogin(), key));
            data.setPassword(decrypt(loginData.getPassword(), key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginStorage.put("default", data);
    }

    public static String getLogin(String key) {
        if (loginStorage == null) {
            init();
        }
        return loginStorage.get(key).getLogin();
    }

    public static String getPassword(String key) {
        if (loginStorage == null) {
            init();
        }
        return loginStorage.get(key).getPassword();
    }

    public static String getHash(String key){
        if (loginStorage == null) {
            init();
        }
        return loginStorage.get(key).getHash();
    }
}
