package com.simonflapse.gearth.events;

import com.google.common.eventbus.EventBus;

public class EventSystem {
    private static final EventBus priorityEventBus = new EventBus();
    private static final EventBus eventBus = new EventBus();

    public static void registerPriority(Object object) {
        priorityEventBus.register(object);
    }

    public static void register(Object object) {
        eventBus.register(object);
    }

    public static void post(Object event) {
        priorityEventBus.post(event);
        eventBus.post(event);
    }
}
