package org.apollon.network.core;

import org.apollon.entity.User;
import org.apollon.network.protocol.TransactionProtocol;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PooledFTPClient {
    private static final Logger logger = Logger.getLogger("Apollon.FTP");

    private final AsynchronousSocketChannel channel;

    public PooledFTPClient(InetSocketAddress address, User user) throws Exception {
        channel = AsynchronousSocketChannel.open();

        channel.connect(address, null, new CompletionHandler<>() {
            @Override
            public void completed(Void result, Object attachment) {
                logger.log(Level.SEVERE, "FTP client successfully connected [{0}]", address.toString());

                channel.write(TransactionProtocol.connectionMessage(user));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                logger.log(Level.SEVERE, "Error occurred on FTP client startup : {0}", exc.getMessage());
            }
        });

    }

}
