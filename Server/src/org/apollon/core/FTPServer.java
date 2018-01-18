package org.apollon.core;

import org.apollon.entity.User;
import org.apollon.network.PooledFTPServer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

public class FTPServer {

    private final int port;

    private final List<User> users;

    private final PooledFTPServer pooledFTPServer;

    public FTPServer(int port) {
        this.port = port;
        this.users = new CopyOnWriteArrayList<>();
        this.pooledFTPServer = new PooledFTPServer(this);
    }

    public FTPServer(int port, List<User> users) {
        this.port = port;
        this.users = new CopyOnWriteArrayList<>(users);
        this.pooledFTPServer = new PooledFTPServer(this);
    }

    public FTPServer(int port, Executor executor) {
        this.port = port;
        this.users = new CopyOnWriteArrayList<>();
        this.pooledFTPServer = new PooledFTPServer(this, executor);
    }

    public FTPServer(int port, List<User> users, Executor executor) {
        this.port = port;
        this.users = new CopyOnWriteArrayList<>(users);
        this.pooledFTPServer = new PooledFTPServer(this, executor);
    }

    public FTPServer bind() {
        return this;
    }


    public int getPort() {
        return port;
    }
}
