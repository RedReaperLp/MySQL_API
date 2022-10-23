package com.github.redreaperlp.Connection;

import com.github.redreaperlp.Connection.ConnectionPool.ConnectionPool;
import com.github.redreaperlp.Connection.objects.ConnectionProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLTables {
    /**
     * Checks if a table exists.
     * @Note: This method doesn't create the table if it doesn't exist.
     *
     * @param tableName the name of the table you want to check.
     * @return a ConnectionProperties object containing the error type if exists and if the table exists.
     */
    public static  ConnectionProperties existsTable(String tableName) {
        if (TestConnections.isConnected()) {
            Connection con = ConnectionPool.getConnection();
            try {
                ResultSet rs = con.getMetaData().getTables(null, null, tableName, null);
                while (rs.next()) {
                    ConnectionPool.releaseConnection(con);
                    return new ConnectionProperties(null, true);
                }
                return new ConnectionProperties(null, false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        return null;
    }

    /**
     * Creates a new table if statement is correct. Otherwise, it will return the error type.
     * @param tableName
     * @param statementForIfTableDoesntExists
     * @return ConnectionProperties containing Error type and if table exists.
     * @see ConnectionProperties
     */
    public static  ConnectionProperties existsTable(String tableName, String statementForIfTableDoesntExists) {
        String statement = statementForIfTableDoesntExists;
        if (TestConnections.isConnected()) {
            Connection con = ConnectionPool.getConnection();
            try {
                ResultSet rs = con.getMetaData().getTables(null, null, tableName, null);
                while (rs.next()) {
                    ConnectionPool.releaseConnection(con);
                    return new ConnectionProperties(null, true);
                }
                if (statement != null) {
                    con.createStatement().execute(statement);
                    ConnectionPool.releaseConnection(con);
                    return new ConnectionProperties(null, true);
                }
                ConnectionPool.releaseConnection(con);
                return new ConnectionProperties(null, false);
            } catch (SQLException e) {
                SQL_ErrorType errorType = SQL_ErrorType.getErrorType(e.getErrorCode());
                return new ConnectionProperties(errorType, false);
            }
        }
        return new ConnectionProperties(SQL_ErrorType.ConnectionDead, false);
    }
}
