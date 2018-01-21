package org.apollon.network.message.processor;


import org.apollon.core.FTPServer;
import org.apollon.network.message.Message;
import org.apollon.network.message.MessageHandler;
import org.apollon.network.message.impl.ConnectionMessage;
import org.apollon.network.session.FTPSession;

import java.nio.channels.AsynchronousChannel;

public class MessageProcessor {

    private final MessageHandler[] handlers;

    public MessageProcessor() {
        handlers = new MessageHandler[Byte.MAX_VALUE];

        init();
    }

    private void init() {
        handlers[0] = new ConnectionMessage();
    }

    public void parse(Message message, FTPSession session, FTPServer server)  {
        this.handlers[message.getId()].parse(message.getData(), session, server);
        System.out.println("Parse message " + message.getId() + " : Data = " + new String(message.getData()));
    }
}
