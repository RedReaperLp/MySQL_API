package com.github.redreaperlp.Connection.objects;

import com.github.redreaperlp.Connection.SQL_ErrorType;
import org.jetbrains.annotations.Nullable;

public class ConnectionProperties {
    private SQL_ErrorType errorType;
    private boolean tableExists;

    /**
     * Creates a new ConnectionProperties object.
     *
     * @param errorType  contains the error type if exists.
     * @param tableExists contains if errorType is null. <br>
     *      - true = table exists <br>
     *      - false = table doesn't exists
     */
    public ConnectionProperties(@Nullable SQL_ErrorType errorType, boolean tableExists) {
        this.errorType = errorType;
        this.tableExists = tableExists;
    }

    public SQL_ErrorType getErrorType() {
        return errorType;
    }

    public boolean hasError() {
        return errorType != null;
    }

    public boolean tableExists() {
        return tableExists;
    }
}
