package org.apollon.network.core;

import org.apollon.entity.Event;
import org.apollon.entity.ClientUser;
import org.apollon.entity.enums.EventType;
import org.apollon.network.handler.EventHandler;
import org.apollon.network.message.Message;
import org.apollon.network.message.processor.ClientMessageProcessor;
import org.apollon.network.protocol.TransactionProtocol;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FTPClient {

    private static final Logger logger = Logger.getLogger("Apollon.FTP");

    private final InetSocketAddress address;
    private ClientMessageProcessor messageProcessor;
    private Socket channel;
    private boolean authenticated = false;
    private EventHandler eventHandler = EventHandler.empty();

    private Runnable onConnect;

    private final AtomicBoolean active = new AtomicBoolean(false);

    private final AtomicReference<String> path = new AtomicReference<>("");

    public FTPClient(InetSocketAddress address) {
        this.address = address;
    }

    public void connect(ClientUser user) {
        new Thread(() -> {
            try {
                channel = new Socket(address.getHostName(), address.getPort());
                messageProcessor = new ClientMessageProcessor(channel);

                while (!channel.isConnected()) ;

                active.set(true);
                logger.log(Level.INFO, "FTP client successfully connected [{0}]", address.toString());

                channel.getOutputStream().write(TransactionProtocol.connectionMessage(user).array());

                DataInputStream inputStream = new DataInputStream(channel.getInputStream());

                while (channel.isConnected() && active.get()) {
                    try {
                        byte id = inputStream.readByte();

                        int length = inputStream.readInt();
                        byte[] data = new byte[length];
                        inputStream.read(data);

                        messageProcessor.parse(new Message(id, data), this);
                    } catch (EOFException exception) {
                        handleEvent(new Event(EventType.SERVER_DISCONNECTED));
                        channel.close();
                        active.set(false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.SEVERE, "An error was occurred on FTP Client [{0}]", e.getMessage());
            }
        }, "pooled-ftp-client").start();
    }

    public String getFiles() {
        if(isAuthenticated()) {
            handleEvent(new Event(EventType.NOT_PERMITTED));
            return null;
        }



        return ""; //TODO get file
    }

    public void changeDirectory(String newDirectory) {

    }

    public void onConnect(Runnable runnable) {
        this.onConnect = runnable;
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

    public Runnable getOnConnect() {
        return onConnect;
    }
}
