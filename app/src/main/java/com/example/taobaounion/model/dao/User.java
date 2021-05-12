package com.example.taobaounion.model.dao;

import java.util.Objects;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {

    private String username;

    private String password;

    private String desc;
    private String coverUrl;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public User() {

    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.desc = user.desc;
        this.coverUrl = user.coverUrl;
        setObjectId(user.getObjectId());
        setACL(user.getACL());
        setCreatedAt(user.getCreatedAt());
        setUpdatedAt(user.getUpdatedAt());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
