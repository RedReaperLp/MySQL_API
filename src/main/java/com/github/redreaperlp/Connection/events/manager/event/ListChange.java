package com.github.redreaperlp.Connection.events.manager.event;

import com.github.redreaperlp.Connection.events.manager.objects.Event;


public class ListChange extends Event {

    private int values;
    private int maxValues;
    private boolean isAsked;
    private boolean failed;


    public ListChange(int values, int maxValues, boolean isAsked, boolean failed) {
        super(ListChange.class);
        this.values = values;
        this.maxValues = maxValues;
        this.isAsked = isAsked;
        this.failed = failed;
    }

    public boolean isAsked() {
        return isAsked;
    }

    public boolean isFailed() {
        return failed;
    }

    public int getValues() {
        return values;
    }

    public int getMaxValues() {
        return maxValues;
    }
}
