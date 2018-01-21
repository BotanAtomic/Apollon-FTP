package org.apollon.core;

import org.apollon.entity.User;
import org.apollon.network.core.PooledFTPServer;
import org.apollon.network.message.handler.FTPHandler;
import org.apollon.network.session.FTPSession;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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


    public void bind() {
        this.pooledFTPServer.accept();
    }

    public void close() {
        this.pooledFTPServer.stop();
    }

    public void setHandler(FTPHandler handler) {
        this.pooledFTPServer.setHandler(handler);
    }

    public int getPort() {
        return port;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(String username, String password) {
        return this.users.stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findAny().orElse(null);
    }

    public FTPSession getSession(int id) {
        return pooledFTPServer.getSessions().get(id);
    }
}
