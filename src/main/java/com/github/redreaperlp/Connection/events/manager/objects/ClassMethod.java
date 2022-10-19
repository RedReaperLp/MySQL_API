package com.github.redreaperlp.Connection.events.manager.objects;

import com.github.redreaperlp.Connection.events.manager.EventManager;

import java.lang.reflect.Method;

public class ClassMethod {
    private Object clazz;
    private Method method;

    /**
     * Create a new ClassMethod object
     *
     * @Use: EventManager.registerEvent(new Listener()); <br> - For getting the Method in the Listener class
     * @param clazz
     * @param method
     * @see EventManager
     */
    public ClassMethod(Object clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public Object getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }
}
