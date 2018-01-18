package org.apollon.network;

import org.apollon.core.FTPServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PooledFTPServer {
    private final Executor executor;

    private final FTPServer ftpServer;

    private ServerSocketChannel serverSocketChannel;

    public PooledFTPServer(FTPServer ftpServer) {
        this.ftpServer = ftpServer;
        this.executor = Executors.newCachedThreadPool();
    }

    public PooledFTPServer(FTPServer ftpServer, Executor executor) {
        this.ftpServer = ftpServer;
        this.executor = executor;
    }

    public void accept() throws Exception {
        Selector selector = Selector.open();

        this.serverSocketChannel = ServerSocketChannel.open();

        this.serverSocketChannel.bind(new InetSocketAddress(ftpServer.getPort()));
        this.serverSocketChannel.configureBlocking(false);

        SelectionKey selectionKey = serverSocketChannel.register(selector, serverSocketChannel.validOps());

        executor.execute(() -> {
            while(true) {
                try {
                   SocketChannel socketChannel = serverSocketChannel.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
