package org.apollon.network.message.impl;

import org.apollon.core.FTPServer;
import org.apollon.network.message.MessageHandler;
import org.apollon.network.session.FTPSession;

public class ConnectionMessage implements MessageHandler {

    @Override
    public void parse(byte[] data, FTPSession session, FTPServer server) {
        String[] messageData = new String(data).split(";");

    }
}
