package org.apollon.network.message.impl;

import org.apollon.entity.Event;
import org.apollon.entity.enums.EventType;
import org.apollon.network.core.PooledFTPClient;
import org.apollon.network.message.ClientMessageHandler;
import org.apollon.network.protocol.ErrorCode;

import java.net.Socket;
import java.nio.ByteBuffer;

public class ErrorResponse implements ClientMessageHandler {

    @Override
    public void parse(byte[] data, PooledFTPClient client, Socket socket) {
        short error = ByteBuffer.allocate(data.length).put(data).getShort(0);

        if (error == ErrorCode.BAD_PROTOCOL) {
            client.handleEvent(new Event(EventType.BAD_PROTOCOL));
        } else {
            client.handleEvent(new Event(EventType.ERROR_CODE, error));
        }

    }

}
