package ru.geekbrains.androidbase.smsapplication;

public class Message {
    private String phoneNumber;
    private String messageBody;

    public Message(String phoneNumber, String messageBody) {
        this.phoneNumber = phoneNumber;
        this.messageBody = messageBody;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
