package org.apollon.network.message.impl;

import org.apollon.entity.Event;
import org.apollon.network.core.PooledFTPClient;
import org.apollon.network.message.ClientMessageHandler;

import java.net.Socket;

public class ErrorResponse implements ClientMessageHandler {

    @Override
    public void parse(byte[] data, PooledFTPClient client, Socket socket) {
        if (data.length > 0 && data[0] == 1) {
            client.setAuthenticated(true);
            client.handleEvent(Event.GOOD_CREDENTIALS);
        } else {
            client.handleEvent(Event.BAD_CREDENTIALS);
        }
    }

}
