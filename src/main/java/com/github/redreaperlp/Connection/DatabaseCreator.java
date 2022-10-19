package com.github.redreaperlp.Connection;

import com.github.redreaperlp.Connection.ConnectionPool.ConnectionPool;
import com.github.redreaperlp.Connection.events.manager.enums.ConnectionProgresses;
import com.github.redreaperlp.Connection.events.manager.event.ConnectionProgress;
import com.github.redreaperlp.Connection.events.manager.event.OpenConnection;
import com.github.redreaperlp.Connection.events.manager.EventManager;
import com.github.redreaperlp.Connection.objects.ConnectionSettings;
import com.github.redreaperlp.MySQL_API;

import java.sql.*;

public class DatabaseCreator {
    private static ConnectionSettings connectionSettings = null;

    /**
     * Creates a new Database if it doesn't exist <br>
     * <br>
     *
     * @param settings
     * @return an SQL_ErrorType if an error occurred, otherwise null
     * @return an SQL_ErrorType if an error occurred, otherwise null
     * @events: OpenConnection - Monitoring Only <br>
     * OpenConnectionDone - Monitoring Only - has a boolean success and a String message
     * @see ConnectionSettings
     */
    public static SQL_ErrorType createDataBase(ConnectionSettings settings, boolean isAsked) {
        try {
            if (settings.getDatabase() == null || settings.getDatabase().contains(" ") || settings.getDatabase().isEmpty()) {
                return SQL_ErrorType.NoValidDatabaseSelected;
            }
            OpenConnection e = new OpenConnection(false, settings);
            EventManager.callEvent(e);
            connectionSettings = e.getSettings();
            if (e.isCancelled()) return SQL_ErrorType.ConnectionCancelled;
            Connection con = DriverManager.getConnection("jdbc:mysql://" +
                            e.getHost() + ":" +
                            e.getPort() + "/",
                    e.getUser(),
                    e.getPassword());
            ConnectionProgress progress = new ConnectionProgress(ConnectionProgresses.CONNECTED, null, true, null, isAsked);
            EventManager.callEvent(progress);
            MySQL_API.wasConnected = true;
            EventManager.callEvent(new ConnectionProgress(ConnectionProgresses.SEARCHING_DATABASE, null, true, null, isAsked));
            {
                ResultSet rs = con.getMetaData().getCatalogs();
                while (rs.next()) {
                    if (rs.getString(1).equals(e.getDatabase())) {
                        EventManager.callEvent(new ConnectionProgress(ConnectionProgresses.DATABASE_FOUND, null, true, null, isAsked));
                        con.close();
                        ConnectionPool.createPool(settings, false);
                        return null;
                    }
                }
            }
            EventManager.callEvent(new ConnectionProgress(ConnectionProgresses.DATABASE_NOT_FOUND, null, true, null, isAsked));
            EventManager.callEvent(new ConnectionProgress(ConnectionProgresses.CREATING_DATABASE, null, true, null, isAsked));
            PreparedStatement stmt = con.prepareStatement("CREATE DATABASE IF NOT EXISTS " + e.getDatabase());
            stmt.executeUpdate();
            EventManager.callEvent(new ConnectionProgress(ConnectionProgresses.DATABASE_CREATED, null, true, null, isAsked));
            con.close();
            ConnectionPool.createPool(settings, false);
        } catch (SQLException e) {
            SQL_ErrorType t = SQL_ErrorType.getErrorType(e.getErrorCode());
            EventManager.callEvent(new ConnectionProgress(null, t, false, null, isAsked));
            if (t == null) {
            } else {
                return t;
            }
        }
        return null;
    }

    public static ConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }
}
