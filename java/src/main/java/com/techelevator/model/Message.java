package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Message {
    private int messageID;
    @NotNull
    @NotEmpty
    private String messageText;
    @NotNull
    @NotEmpty
    private String messageSender;
    @NotNull
    @NotEmpty
    private String messageReceiver;

    private LocalDateTime messageTimestamp;

    private boolean read;

    public Message() {
    }

    public Message(int messageID, String messageText, String messageSender, String messageReceiver, LocalDateTime messageTimestamp, boolean read) {
        this.messageID = messageID;
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
        this.messageTimestamp = messageTimestamp;
        this.read = read;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageReceiver() {
        return messageReceiver;
    }

    public void setMessageReceiver(String messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public LocalDateTime getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(LocalDateTime messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", messageText='" + messageText + '\'' +
                ", messageSender='" + messageSender + '\'' +
                ", messageReceiver='" + messageReceiver + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                ", read=" + read +
                '}';
    }
}

