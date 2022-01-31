package dbintegration;

import models.DBLetter;

import java.sql.*;
import java.util.Scanner;

public class DbDataParser {

    public static DBLetter getLetter() {
        DBLetter letter = new DBLetter();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/user/Projects/ipr/contact_mail.db");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT Letter.letter_id, email, content\n" +
                    "FROM Contact INNER JOIN Letter_Contact INNER JOIN Letter INNER JOIN content\n" +
                    "on Letter_Contact.letter_id=Letter.letter_id\n" +
                    "AND Contact.contact_id = Letter_Contact.receiver_id\n" +
                    "AND Letter.content_id=content.content_id;");
            System.out.println("Выберите email и введите его id");
            while (result.next()) {
                System.out.print(result.getInt("letter_id"));
                System.out.println("  " + result.getString("email"));
            }
            int letterId = new Scanner(System.in).nextInt();
            Statement secondStatement = connection.createStatement();
            result = secondStatement.executeQuery("SELECT Letter.letter_id, email, content\n" +
                    "FROM Contact INNER JOIN Letter_Contact INNER JOIN Letter INNER JOIN content\n" +
                    "on Contact.contact_id = Letter_Contact.receiver_id\n" +
                    "AND Letter_Contact.letter_id=Letter.letter_id\n" +
                    "AND Letter.content_id=content.content_id\n" +
                    "WHERE Letter.letter_id ="+letterId+";");
            letter.setSubject(result.getString("email"));
            letter.setContent(result.getString("content"));
            letter.setEmail(result.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return letter;
    }
}
