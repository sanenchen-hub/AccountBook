package com.sanenchen.UsersManager.recyclerViewAdapter.passWordList;

public class PassWordList {
    private int id;
    private String title, user, password, other;

    public PassWordList(int id, String title, String user, String password, String other) {
        this.title = title;
        this.id = id;
        this.user = user;
        this.password = password;
        this.other = other;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getOther() {
        return other;
    }

}
