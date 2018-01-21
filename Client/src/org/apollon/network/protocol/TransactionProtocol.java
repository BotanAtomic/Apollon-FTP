package org.apollon.network.protocol;

import org.apollon.entity.User;
import org.apollon.network.buffer.ByteBufferStream;

import java.nio.ByteBuffer;

public class TransactionProtocol {

    public static ByteBuffer connectionMessage(User user) {
        ByteBufferStream stream = new ByteBufferStream();
        stream.put((byte) 0);

        String data = user.getUsername() + ";" + user.getPassword();

        stream.putInt(data.length());
        stream.put(data.getBytes());

        return stream.convert();
    }

}
