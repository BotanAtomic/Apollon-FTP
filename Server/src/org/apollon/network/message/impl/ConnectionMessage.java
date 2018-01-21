package org.apollon.network.message.impl;

import org.apollon.core.FTPServer;
import org.apollon.entity.User;
import org.apollon.network.message.MessageHandler;
import org.apollon.network.protocol.ErrorCode;
import org.apollon.network.protocol.ServerTransactionProtocol;
import org.apollon.network.session.FTPSession;

public class ConnectionMessage implements MessageHandler {

    @Override
    public void parse(byte[] data, FTPSession session, FTPServer server) {
        String[] credentials = new String(data).split(";");

        if(credentials.length > 1) {
            User user = server.getUser(credentials[0], credentials[1]);

            if(user != null) {
                session.attachUser(user);
                session.getChannel().write(ServerTransactionProtocol.connectionSuccess());
            } else {
                session.getChannel().write(ServerTransactionProtocol.connectionFailed());
            }

        } else {
            session.getChannel().write(ServerTransactionProtocol.returnError(ErrorCode.BAD_PROTOCOL));
        }


    }
}
