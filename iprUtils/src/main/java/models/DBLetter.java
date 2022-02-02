package models;

/**
 * Класс для представления письма из базы данных
 */
public class DBLetter {
    private String email;
    private String subject;
    private String name;
    private String secondName;
    private String content;

    public DBLetter() {
    }

    public DBLetter(String email, String subject, String content) {
        this.email = email;
        this.subject = subject;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
