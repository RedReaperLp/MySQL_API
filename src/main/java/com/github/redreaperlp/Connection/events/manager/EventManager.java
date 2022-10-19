package com.github.redreaperlp.Connection.events.manager;

import com.github.redreaperlp.Connection.events.manager.objects.Event;
import com.github.redreaperlp.Connection.events.annotation.EventLanger;
import com.github.redreaperlp.Connection.events.manager.objects.ClassMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static List<ClassMethod> changeListEvent = new ArrayList<>();
    private static List<ClassMethod> openConnectionEvent = new ArrayList<>();
    private static List<ClassMethod> connectionProgress = new ArrayList<>();

    private static List<ClassMethod> reconnectEvent = new ArrayList<>();

    /**
     * Register a class to the event manager with: <br>
     * - EventManager.register(new Listener()); <br>
     * - Tag it with @EventLanger <br>
     *
     * @param event
     */
    public static void registerEvent(Object event) {
        Method[] m = event.getClass().getMethods();
        for (Method method : m) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof EventLanger) {
                    switch (method.getParameterTypes()[0].getName()) {
                        case "com.github.redreaperlp.Connection.events.manager.event.ListChange" ->
                                changeListEvent.add(new ClassMethod(event, method));
                        case "com.github.redreaperlp.Connection.events.manager.event.OpenConnection" ->
                                openConnectionEvent.add(new ClassMethod(event, method));
                        case "com.github.redreaperlp.Connection.events.manager.event.ConnectionProgress" ->
                                connectionProgress.add(new ClassMethod(event, method));
                        case "com.github.redreaperlp.Connection.events.manager.event.Reconnect" ->
                                reconnectEvent.add(new ClassMethod(event, method));
                        default -> System.out.println("Unknown event type: " + method.getParameterTypes()[0].getName());
                    }
                }
            }
        }
    }

    /**
     * Call the event
     *
     * @param event
     */
    public static void callEvent(Event event) {
        List<ClassMethod> list = null;
        switch (event.getClass().getName()) {
            case "com.github.redreaperlp.Connection.events.manager.event.ListChange" -> list = changeListEvent;
            case "com.github.redreaperlp.Connection.events.manager.event.OpenConnection" -> list = openConnectionEvent;
            case "com.github.redreaperlp.Connection.events.manager.event.ConnectionProgress" -> list = connectionProgress;
            case "com.github.redreaperlp.Connection.events.manager.event.Reconnect" -> list = reconnectEvent;
            default -> System.out.println("Unknown event type: " + event.getClass().getName());
        }

        for (ClassMethod clazz : list) {
            if (clazz.getMethod().getParameterTypes()[0].equals(event.getClass())) {
                try {
                    clazz.getMethod().invoke(clazz.getClazz(), event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

