package org.apollon.network.core;

import org.apollon.entity.Event;
import org.apollon.entity.User;
import org.apollon.network.handler.EventHandler;
import org.apollon.network.message.Message;
import org.apollon.network.message.processor.ClientMessageProcessor;
import org.apollon.network.protocol.TransactionProtocol;

import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PooledFTPClient {

    private static final Logger logger = Logger.getLogger("Apollon.FTP");

    private final ClientMessageProcessor messageProcessor;

    private final Socket channel;

    private boolean authenticated = false;

    private EventHandler eventHandler = event -> {

    };

    public PooledFTPClient(InetSocketAddress address, User user) throws Exception {
        channel = new Socket(address.getHostName(), address.getPort());
        messageProcessor = new ClientMessageProcessor(channel);

        new Thread(() -> {
            try {

                while (!channel.isConnected()) ;

                logger.log(Level.INFO, "FTP client successfully connected [{0}]", address.toString());

                channel.getOutputStream().write(TransactionProtocol.connectionMessage(user).array());

                DataInputStream inputStream = new DataInputStream(channel.getInputStream());

                while (channel.isConnected()) {
                    byte id = inputStream.readByte();

                    int length = inputStream.readInt();
                    byte[] data = new byte[length];
                    inputStream.read(data);

                    messageProcessor.parse(new Message(id, data), this);

                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An error was occurred on FTP Client [{0}]", e.getMessage());
            }
        }, "pooled-ftp-client").start();

    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void handleEvent(Event event) {
        this.eventHandler.handleEvent(event);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
