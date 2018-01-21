package org.apollon.network.buffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferStream {

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    public void put(byte b) {
        stream.write(b);
    }

    public void put(byte[] bytes) {
        try {
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putInt(int i) {
        try {
            stream.write(ByteBuffer.allocate(Integer.SIZE).putInt(i).array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public ByteBuffer convert() {
        return ByteBuffer.allocate(stream.size()).put(stream.toByteArray());
    }
}
