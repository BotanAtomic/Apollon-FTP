package org.apollon.network.handler;

import org.apollon.entity.Event;

public interface EventHandler {

    void handleEvent(Event event);

    static EventHandler empty() {
        return event -> {

        };
    }

}
