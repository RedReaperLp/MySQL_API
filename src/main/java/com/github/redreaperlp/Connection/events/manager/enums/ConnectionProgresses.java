package com.github.redreaperlp.Connection.events.manager.enums;

public enum ConnectionProgresses {
    CONNECTED("Connected"),
    SEARCHING_DATABASE("Searching Database"),
    DATABASE_FOUND("Database found"),
    DATABASE_NOT_FOUND("Database not found"),
    CREATING_DATABASE("Creating Database"),
    DATABASE_CREATED("Database created"),
    SEARCHING_TABLE("Searching Table"),
    TABLE_FOUND("Table found"),
    TABLE_NOT_FOUND("Table not found"),
    CREATING_TABLE("Creating Table"),
    TABLE_CREATED("Table created");

    final String messageValue;

    private ConnectionProgresses(String messageValue) {
        this.messageValue = messageValue;
    }

    public ConnectionProgresses getProgress() {
        return this;
    }

    public String getMessageValue() {
        return messageValue;
    }
}
