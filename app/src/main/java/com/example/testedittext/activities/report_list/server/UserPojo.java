package com.example.testedittext.activities.report_list.server;

import com.google.gson.annotations.SerializedName;

public class UserPojo {

    @SerializedName("id")
    private long id;
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
