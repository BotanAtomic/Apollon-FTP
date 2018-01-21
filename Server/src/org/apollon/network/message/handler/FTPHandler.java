package org.apollon.network.message.handler;


import org.apollon.network.session.FTPSession;

public interface FTPHandler {

    void onRead(FTPSession session, byte[] data);

    void onClose(FTPSession session);

    void onConnect(FTPSession session);

    void onExceptionCaught(FTPSession session, Exception exception);

}
