package org.apollon.network.message;

import org.apollon.network.core.FTPClient;

import java.net.Socket;

public interface ClientMessageHandler {

    void parse(byte[] data, FTPClient client, Socket socket);

}
