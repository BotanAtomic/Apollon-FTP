package org.apollon.network.message.handler;

import org.apollon.network.session.FTPSession;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultFTPHandler implements FTPHandler {

    private static final Logger logger = Logger.getLogger("Apollon.FTP");

    @Override
    public void onRead(FTPSession session, byte[] data) {

    }

    @Override
    public void onClose(FTPSession session) {
        logger.log(Level.INFO,"[Session {0}] closed", session.getId());
    }

    @Override
    public void onConnect(FTPSession session) {
        logger.log(Level.INFO,"[Session {0}] connected", session.getId());
    }

    @Override
    public void onExceptionCaught(FTPSession session, Exception exception) {
        logger.log(Level.SEVERE,"[Session {0}] exception : {1}", new Object[]{String.valueOf(session.getId()), exception.getMessage()});
    }
}
