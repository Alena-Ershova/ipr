package models;

/**
 * Класс для представления заголовков писем
 */
public class MessageHeader {
    //id письма
    private int id;
    //дата отправаки письма
    private String date;
    //тема письма
    private String subject;
    //от кого письмо
    private String from;

    public MessageHeader() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
