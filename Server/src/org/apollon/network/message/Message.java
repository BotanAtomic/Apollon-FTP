package org.apollon.network.message;

public class Message {

    private final byte id;

    private final byte[] data;

    public Message(byte id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public byte getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }
}
