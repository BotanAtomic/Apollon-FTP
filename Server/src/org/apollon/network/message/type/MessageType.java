package org.apollon.network.message.type;

public enum MessageType {
    ID(1),
    LENGTH(Integer.SIZE),
    DATA(0);

    private final int size;

    MessageType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
