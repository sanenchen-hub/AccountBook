package com.sanenchen.UsersManager.recyclerViewAdapter.passWordList;

public class PassWordList {
    private int id, checkLove;
    private String title, user, password, remark, url, createTime;

    public PassWordList(int id, String title, String user, String password, String remark, String url, String createTime, int checkLove) {
        this.title = title;
        this.id = id;
        this.user = user;
        this.password = password;
        this.remark = remark;
        this.url = url;
        this.createTime = createTime;
        this.checkLove = checkLove;
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

    public String getRemark() {
        return remark;
    }

    public String getUrl() {
        return url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getCheckLove() {
        return checkLove;
    }

}
