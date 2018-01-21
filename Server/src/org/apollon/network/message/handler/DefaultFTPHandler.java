package org.apollon.network.message.handler;

import org.apollon.network.session.FTPSession;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultFTPHandler implements FTPHandler {

    @Override
    public void onRead(FTPSession session, byte[] data) {

    }

    @Override
    public void onClose(FTPSession session) {
        System.out.println("[Session " + session.getId() + "] closed");
    }

    @Override
    public void onConnect(FTPSession session) {
        System.out.println("[Session " + session.getId() + "] connected");
    }

    @Override
    public void onExceptionCaught(FTPSession session, Exception exception) {
        System.out.println("[Session " + session.getId() + "] exception : " + exception.getMessage());
    }
}
