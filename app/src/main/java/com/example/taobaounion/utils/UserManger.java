package com.example.taobaounion.utils;

import com.example.taobaounion.model.dao.User;

public class UserManger {

    private User mUser;

    private static UserManger mUserManger = new UserManger();

    private UserManger() {
//        mUserManger = new UserManger();
    }

    public static UserManger getInstance() {
        return mUserManger;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
