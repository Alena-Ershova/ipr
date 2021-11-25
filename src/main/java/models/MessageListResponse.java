package models;

public class MessageListResponse {
    private MessageHeader[] messageHeaders;

    public MessageListResponse() {
    }

    public MessageHeader[] getMessageHeaders() {
        return messageHeaders;
    }

    public void setMessageHeaders(MessageHeader[] messageHeaders) {
        this.messageHeaders = messageHeaders;
    }
}
