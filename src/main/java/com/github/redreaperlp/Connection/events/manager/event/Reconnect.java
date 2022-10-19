package com.github.redreaperlp.Connection.events.manager.event;

import com.github.redreaperlp.Connection.events.manager.objects.Event;

public class Reconnect extends Event {

    private boolean failed;

    public Reconnect(boolean failed) {
        super(Reconnect.class);
        this.failed = failed;
    }

    public boolean isFailed() {
        return failed;
    }
}
