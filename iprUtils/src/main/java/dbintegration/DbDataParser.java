package dbintegration;

import models.DBLetter;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс для получения данных и базы данных
 */
public class DbDataParser {

    public static DBLetter getLetter() {
        File dbFile = new File("contact_mail.db");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:"+dbFile.getAbsolutePath())) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT Letter.letter_id, email, content\n" +
                    "FROM Contact INNER JOIN Letter_Contact INNER JOIN Letter INNER JOIN content\n" +
                    "on Letter_Contact.letter_id=Letter.letter_id\n" +
                    "AND Contact.contact_id = Letter_Contact.receiver_id\n" +
                    "AND Letter.content_id=content.content_id;");
            List<DBLetter> letters = new ArrayList<>();
            while (result.next()) {
                DBLetter letter = new DBLetter(result.getString("email"),
                        result.getString("email"),
                        result.getString("content"));
                letters.add(letter);
            }
            Random rand = new Random();
            int letterNumber = rand.nextInt(letters.size());
            return letters.get(letterNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
