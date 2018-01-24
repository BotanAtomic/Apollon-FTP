package org.apollon.entity;

public class ClientUser {
    private final String username, password;


    public ClientUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
