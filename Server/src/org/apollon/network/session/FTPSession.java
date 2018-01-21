package org.apollon.network.session;

import org.apollon.entity.User;
import org.apollon.network.core.PooledFTPServer;
import org.apollon.network.message.Message;
import org.apollon.network.message.type.MessageType;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.atomic.AtomicReference;

public class FTPSession {

    private final int id;
    private final AsynchronousSocketChannel channel;

    private final PooledFTPServer root;

    private User user;

    public FTPSession(int id, AsynchronousSocketChannel channel, PooledFTPServer root) {
        this.id = id;
        this.channel = channel;
        this.root = root;

        root.getHandler().onConnect(this);
        root.getHandler().onExceptionCaught(this, new Exception("Salam"));

        listen();
    }

    private void listen() {
        AtomicReference<ByteBuffer> atomicBuffer = new AtomicReference<>(ByteBuffer.allocate(MessageType.ID.getSize()));

        channel.read(atomicBuffer.get(), null, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, Object attachment) {
                if(result == -1) {
                    close(false);
                    return;
                }

                byte id = atomicBuffer.get().get(0);
                int length = directRead(MessageType.LENGTH.getSize()).getInt(0);
                byte[] data = directRead(length).array();

                root.getHandler().onRead(FTPSession.this, data);

                root.getMessageProcessor().parse(new Message(id, data), FTPSession.this, root.getServer());

                listen();
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                root.getHandler().onExceptionCaught(FTPSession.this, new Exception(exc));
            }
        });

    }

    private ByteBuffer directRead(int size) {
        ByteBuffer buffer = ByteBuffer.allocate(size);
        channel.read(buffer);
        return buffer;
    }

    public void close(boolean force) {
        try {
            if (force)
                channel.close();
        } catch (Exception e) {
            root.getHandler().onExceptionCaught(this, e);
        } finally {
            root.getHandler().onClose(this);
            root.getSessions().remove(this.id);
        }
    }

    public void attachUser(User user) {
        this.user = user;
    }

    public int getId() {
        return this.id;
    }

    public AsynchronousSocketChannel getChannel() {
        return this.channel;
    }
}
