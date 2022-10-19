package com.github.redreaperlp.Connection.ConnectionPool;

import com.github.redreaperlp.Connection.DatabaseCreator;
import com.github.redreaperlp.Connection.TestConnections;
import com.github.redreaperlp.Connection.events.manager.EventManager;
import com.github.redreaperlp.Connection.events.manager.event.ListChange;
import com.github.redreaperlp.Connection.events.manager.event.Reconnect;
import com.github.redreaperlp.Connection.objects.ConnectionSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    static boolean wasAsked = false;
    private static List<Connection> connectionPool = new ArrayList<>(DatabaseCreator.getConnectionSettings().getMaxPoolSize());
    private static List<Connection> usedConnections = new ArrayList<>(DatabaseCreator.getConnectionSettings().getMaxPoolSize());

    /**
     * Creates a new Connection Pool.
     *
     * @param settings contains the connection settings.
     * @param isAsked this is true, if the connection was dead and a method tried to reconnect.
     * @example: Sending messages only if isAsked is true or false depending on the situation.
     */
    public static void createPool(ConnectionSettings settings, boolean isAsked) {
        if (connectionPool.size() != 0) {
            connectionPool.clear();
            usedConnections.clear();
            System.out.println("Connection pool cleared!");
        }
        for (int i = 1; i < settings.getMaxPoolSize() + 1; i++) {
            Connection con = createConnection(settings);
            if (con != null) {
                connectionPool.add(con);
                EventManager.callEvent(new ListChange(connectionPool.size(), settings.getMaxPoolSize(), isAsked, false));
            } else {
                EventManager.callEvent(new ListChange(connectionPool.size(), settings.getMaxPoolSize(), isAsked, true));
                if (isAsked) EventManager.callEvent(new Reconnect(true));
                return;
            }
        }
        if (wasAsked) {
            if (isAsked) EventManager.callEvent(new Reconnect(false));
        } else {
            wasAsked = true;
        }

    }

    /**
     * Creates a new connection.
     *
     * @param settings The settings for the connection.
     * @return The connection.
     * @see ConnectionSettings
     */
    private static Connection createConnection(ConnectionSettings settings) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://" +
                            settings.getHost() + ":" +
                            settings.getPort() + "/" +
                            settings.getDatabase(),
                    settings.getUser(),
                    settings.getPassword());
            return con;
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * @return A connection from the pool even if the pool is empty <br>
     * remember to return the connection with ConnectionPool.releaseConnection(Connection con) <br> <br>
     * Use "TestConnections.isConnected();" to if the connectios are still valid if not it will try to reconnect
     * @see TestConnections
     */
    public static Connection getConnection() {
        if (connectionPool.size() == 0) {
            connectionPool.add(createConnection(DatabaseCreator.getConnectionSettings()));
        }
        Connection con = connectionPool.get(0);
        usedConnections.add(con);
        connectionPool.remove(0);
        return con;
    }

    /**
     * The connection will be returned to the pool.
     *
     * @param con The connection to release.
     */
    public static void releaseConnection(Connection con) {
        try {
            con.close();
            connectionPool.add(con);
            usedConnections.remove(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
