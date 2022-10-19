package com.github.redreaperlp.Connection;

public enum SQL_ErrorType {

    /**
     * The error type for a connection error.
     */
    CommunicationLinkFailure,

    /**
     * The error type for wrong credentials.
     */
    AccessDeniedForUser,

    /**
     * The error type for if there is no valid database selected.
     *
     * @example: if the database name contains spaces.
     */
    NoValidDatabaseSelected,

    /**
     * The error type for if the connection was cancelled.
     */
    ConnectionCancelled,

    /**
     * The error type for if the syntax is wrong.
     */
    SQL_SyntaxError,

    ConnectionDead;

    /**
     * Returns the SQL_ErrorType of the given error message.
     */
    public static SQL_ErrorType getErrorType(int e) {
        switch (e) {
            case 0:
                return CommunicationLinkFailure;
            case 1045:
                return AccessDeniedForUser;
            case 1049:
                return NoValidDatabaseSelected;
            case 1064:
                return SQL_SyntaxError;
            default:
                return null;
        }
    }
}
