package org.apollon.network.protocol;

import java.nio.ByteBuffer;

public class ServerTransactionProtocol {

    public static ByteBuffer connectionSuccess() {
        return ByteBuffer.allocate(Integer.SIZE + 2).put((byte) 0).putInt(1).put((byte) 1).flip();
    }

    public static ByteBuffer connectionFailed() {
        return ByteBuffer.allocate(Integer.SIZE + 2).put((byte) 0).putInt((byte) 1).put((byte) 0).flip();
    }

    public static ByteBuffer returnError(short errorCode) {
        return ByteBuffer.allocate(Integer.SIZE + Short.SIZE + 1).put(Byte.MAX_VALUE).putInt(Short.SIZE).putShort(errorCode).flip();
    }

}
