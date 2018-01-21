package org.apollon.entity;

import org.apollon.ftp.UserRights;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    private final String username, password;

    private final List<UserRights> rights;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.rights = new CopyOnWriteArrayList<>();
    }

    public User(String username, String password, List<UserRights> rights) {
        this.username = username;
        this.password = password;
        this.rights = new CopyOnWriteArrayList<>(rights);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<UserRights> getRights() {
        return rights;
    }
}
