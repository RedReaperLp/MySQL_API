package com.github.redreaperlp.Connection;

import com.github.redreaperlp.Connection.events.manager.event.ConnectionProgress;
import com.github.redreaperlp.Connection.events.manager.event.ListChange;
import com.github.redreaperlp.Connection.events.manager.event.OpenConnection;
import com.github.redreaperlp.Connection.events.annotation.EventLanger;
import com.github.redreaperlp.Connection.events.manager.event.Reconnect;

public class Listener {
    @EventLanger
    public void reconnect(Reconnect e) {
        if (e.isFailed()) {
            System.out.println("Reconnect failed");
        } else {
            System.out.println("Reconnect successful");
        }
    }

    @EventLanger
    public void openConn(OpenConnection event) {
        System.out.println("Connection opening!");
    }

    @EventLanger
    public void changeList(ListChange event) {
        if (event.isFailed() || event.isAsked()) {
            return;
        }
        System.out.println("Connection created: " + event.getValues() + "/" + event.getMaxValues());
    }

    @EventLanger
    public void connectionProgress(ConnectionProgress event) {
        if (event.getProgress() != null && !event.isAsked()) {
            switch (event.getProgress()) {
                case CONNECTED:
                    System.out.println("Connected!");
                    break;
                case SEARCHING_DATABASE:
                    System.out.println("Searching Database!");
                    break;
                case DATABASE_FOUND:
                    System.out.println("Database found!");
                    break;
                case DATABASE_NOT_FOUND:
                    System.out.println("Database not found!");
                    break;
                case CREATING_DATABASE:
                    System.out.println("Creating Database!");
                    break;
                case DATABASE_CREATED:
                    System.out.println("Database created!");
                    break;
                case SEARCHING_TABLE:
                    System.out.println("Searching Table!");
                    break;
                case TABLE_FOUND:
                    System.out.println("Table found!");
                    break;
                case TABLE_NOT_FOUND:
                    System.out.println("Table not found!");
                    break;
                case CREATING_TABLE:
                    System.out.println("Creating Table!");
                    break;
                case TABLE_CREATED:
                    System.out.println("Table created!");
                    break;
            }
            return;
        }

        if (event.getErrorType() != null) {
            switch (event.getErrorType()) {
                case ConnectionCancelled:
                    System.out.println("Connection was cancelled!");
                    break;
                case CommunicationLinkFailure:
                    System.out.println("Connection failed!");
                    break;
                case NoValidDatabaseSelected:
                    System.out.println("No valid database selected!");
                    break;
                case AccessDeniedForUser:
                    System.out.println("Wrong credentials!");
                    break;
                default:
                    System.out.println("Unknown error!");
                    System.out.println("Error code: " + event.getErrorMessage());
                    break;
            }
        } else {
            System.out.println("Unknown error!");
            System.out.println("Error code: " + event.getErrorMessage());
        }
    }
}
