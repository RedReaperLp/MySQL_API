package com.github.redreaperlp.Connection.events.manager.event;

import com.github.redreaperlp.Connection.events.manager.enums.ConnectionProgresses;
import com.github.redreaperlp.Connection.SQL_ErrorType;
import com.github.redreaperlp.Connection.events.manager.objects.Event;

public class ConnectionProgress extends Event {
    private ConnectionProgresses progress;
    private SQL_ErrorType errorType;
    private boolean isSucceeded;
    private boolean isAsked;
    String errorMessage;

    public ConnectionProgress(ConnectionProgresses progress, SQL_ErrorType errorType, boolean isSucceeded, String errorMessage, boolean isAsked) {
        super(ConnectionProgress.class);
        this.progress = progress;
        this.errorType = errorType;
        this.isSucceeded = isSucceeded;
        if (errorType == null) this.errorMessage = errorMessage;
        this.isAsked = isAsked;
    }

    public ConnectionProgresses getProgress() {
        return progress;
    }

    public SQL_ErrorType getErrorType() {
        return errorType;
    }

    public boolean isSucceeded() {
        return isSucceeded;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setProgress(ConnectionProgresses progress) {
        this.progress = progress;
    }

    public void setErrorType(SQL_ErrorType errorType) {
        this.errorType = errorType;
    }

    public void setSucceeded(boolean succeeded) {
        isSucceeded = succeeded;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isAsked() {
        return isAsked;
    }
}

