package org.apollon.network.message;

import org.apollon.core.FTPServer;
import org.apollon.network.session.FTPSession;

public interface MessageHandler {

    void parse(byte[] data, FTPSession session, FTPServer server);

}
