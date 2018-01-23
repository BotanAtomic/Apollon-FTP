package org.apollon.network.message.impl;

import org.apollon.entity.Event;
import org.apollon.entity.enums.EventType;
import org.apollon.network.core.FTPClient;
import org.apollon.network.message.ClientMessageHandler;

import java.net.Socket;

public class ConnectionResponse implements ClientMessageHandler {

    @Override
    public void parse(byte[] data, FTPClient client, Socket socket) {
        if (data.length > 0 && data[0] == 1) {
            client.setAuthenticated(true);
            client.handleEvent(new Event(EventType.GOOD_CREDENTIALS));
        } else {
            client.handleEvent(new Event(EventType.BAD_CREDENTIALS));
        }
    }

}
