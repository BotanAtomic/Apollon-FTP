package org.apollon.core;

import org.apollon.network.core.PooledFTPClient;

import java.net.InetSocketAddress;

public class FTPClient extends PooledFTPClient {


    public FTPClient(InetSocketAddress address) {
        super(address);
    }

}
