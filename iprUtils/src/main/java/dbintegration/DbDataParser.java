package dbintegration;

import models.DBLetter;

import java.sql.SQLException;

import static dbintegration.Connect.getDataFromDataBase;

public class DbDataParser {

    public static DBLetter getLetter(int index){
        DBLetter letter = new DBLetter();
        getContact(letter,index);
        getLetter(letter,index);
        return letter;
    }

    public static void getContact(DBLetter dbLetter, int index){
        try {
            int contactId = getDataFromDataBase("select receiver_id from Letter_Contact where letter_id="+index).getInt(1);
            dbLetter.setEmail(getDataFromDataBase("select email from Contact where contact_id="+contactId).getString(1));
            dbLetter.setName(getDataFromDataBase("select name from Contact where contact_id="+contactId).getString(1));
            dbLetter.setSecondName(getDataFromDataBase("select second_name from Contact where contact_id="+contactId).getString(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void getContent(DBLetter dbLetter, int index){
        try {
            dbLetter.setContent(getDataFromDataBase("select content from Content where content_id="+index).getString(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void getLetter(DBLetter dbLetter, int index){
        try {
            dbLetter.setSubject(getDataFromDataBase("select subject from Letter where content_id="+index).getString(1));
            int contentId = getDataFromDataBase("select content_id from Letter where content_id="+index).getInt(1);
            getContent(dbLetter, contentId);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
