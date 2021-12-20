package models;

/**
 * Класс для представления текста письма
 */
public class MessageTextResponse {
    //текст письма
    private String message;

    public MessageTextResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
