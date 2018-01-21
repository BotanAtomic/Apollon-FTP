package org.apollon.entity;

import org.apollon.entity.enums.EventType;

public class Event {

    private final EventType eventType;

    private final int code;

    public Event(EventType eventType) {
        this.eventType = eventType;
        this.code = 0;
    }

    public Event(EventType eventType, int code) {
        this.eventType = eventType;
        this.code = code;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getCode() {
        return code;
    }



}
