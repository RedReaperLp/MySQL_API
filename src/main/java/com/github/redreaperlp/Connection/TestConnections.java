package com.github.redreaperlp.Connection;

import com.github.redreaperlp.Connection.ConnectionPool.ConnectionPool;
import com.github.redreaperlp.MySQL_API;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestConnections {

    /**
     * Checks if the connection is alive.
     *
     * @return true if the connection is alive. Otherwise, false and it will try to reconnect.
     */
    public static boolean isConnected() {
        if (!MySQL_API.wasConnected) {
            DatabaseCreator.createDataBase(DatabaseCreator.getConnectionSettings(), true);
            return false;
        }
        Connection con = ConnectionPool.getConnection();
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement("SELECT 1"); // Trys to execute a query if it fails the connection is dead.
                ps.executeQuery();
                ConnectionPool.releaseConnection(con);
                return true;
            } catch (SQLException e) {
                ConnectionPool.createPool(DatabaseCreator.getConnectionSettings(), true); //Will try to Create a new pool if the connection is dead.
                return false;
            }
        } else {
            ConnectionPool.createPool(DatabaseCreator.getConnectionSettings(), true);
            return false;
        }
    }
}
