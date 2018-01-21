package org.apollon.core;

import org.apollon.entity.User;
import org.apollon.network.core.PooledFTPClient;

import java.net.InetSocketAddress;

public class FTPClient extends PooledFTPClient {

    private final User user;

    public FTPClient(InetSocketAddress address, User user) throws Exception {
        super(address, user);
        this.user = user;
    }

}
