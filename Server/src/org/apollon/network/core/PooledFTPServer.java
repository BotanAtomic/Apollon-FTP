package org.apollon.network.core;

import org.apollon.core.FTPServer;
import org.apollon.network.message.handler.DefaultFTPHandler;
import org.apollon.network.message.handler.FTPHandler;
import org.apollon.network.message.processor.MessageProcessor;
import org.apollon.network.session.FTPSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PooledFTPServer {

    private static final Logger logger = Logger.getLogger("Apollon.FTP");

    private final FTPServer server;

    private final MessageProcessor messageProcessor = new MessageProcessor();

    private AsynchronousServerSocketChannel serverSocketChannel;

    private FTPHandler handler;

    private final AtomicInteger identityGenerator = new AtomicInteger(0);

    private final Map<Integer, FTPSession> sessions = new ConcurrentHashMap<>();

    public PooledFTPServer(FTPServer ftpServer) {
        this.server = ftpServer;
        this.handler = new DefaultFTPHandler();
    }

    public void accept() {
        new Thread(() -> {
            try {
                AsynchronousChannelGroup socketGroup = AsynchronousChannelGroup.withThreadPool(Executors.newCachedThreadPool());
                this.serverSocketChannel = AsynchronousServerSocketChannel.open(socketGroup);
                this.serverSocketChannel.bind(new InetSocketAddress(server.getPort()));

                this.serverSocketChannel.accept(null, new CompletionHandler<>() {

                    @Override
                    public void completed(AsynchronousSocketChannel result, Object attachment) {
                        serverSocketChannel.accept(result, this);
                        register(new FTPSession(identityGenerator.getAndIncrement(),result, PooledFTPServer.this));
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        logger.log(Level.SEVERE, "Failed to open new FTP session", exc.getMessage());
                    }
                });

                Thread.currentThread().join();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error occurred on FTP server startup : {0}", e.getMessage());
            }
        }, "pooled-ftp-server").start();
    }

    private void register(FTPSession session) {
        this.sessions.put(session.getId(), session);
    }

    public void setHandler(FTPHandler handler) {
        this.handler = handler;
    }


    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public FTPServer getServer() {
        return server;
    }

    public FTPHandler getHandler() {
        return handler;
    }

    public void stop() {
        try {
            this.serverSocketChannel.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred on FTP server close event : {0}", e.getMessage());

        }
    }

    public Map<Integer, FTPSession> getSessions() {
        return sessions;
    }

}
