package org.apollon.network.message;

import org.apollon.network.core.PooledFTPClient;

import java.net.Socket;

public interface ClientMessageHandler {

    void parse(byte[] data, PooledFTPClient client, Socket socket);

}
