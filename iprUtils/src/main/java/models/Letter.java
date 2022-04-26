package models;

import java.util.ArrayList;

/**
 * Класс для представления письма
 */
public class Letter {
    private String address;
    private String subject;
    private String text;
    private ArrayList<String> copy;

    public Letter(String address, String subject, String text, ArrayList<String> copy) {
        this.address = address;
        this.subject = subject;
        this.text = text;
        this.copy = copy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getCopy() {
        return copy;
    }

    public void setCopy(ArrayList<String> copy) {
        this.copy = copy;
    }
}
