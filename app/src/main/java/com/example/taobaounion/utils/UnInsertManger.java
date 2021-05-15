package com.example.taobaounion.utils;

import com.example.taobaounion.model.dao.UnInsert;

import java.util.List;

public class UnInsertManger {

    private List<UnInsert> mInsertList;

    private static UnInsertManger sManger = new UnInsertManger();

    public List<UnInsert> getInsertList() {
        return mInsertList;
    }

    public void setInsertList(List<UnInsert> insertList) {
        mInsertList = insertList;
    }

    public static UnInsertManger getManger() {
        return sManger;
    }

    public static void setManger(UnInsertManger manger) {
        sManger = manger;
    }

    private UnInsertManger() {

    }

    public static UnInsertManger getInstance() {
        return sManger;
    }
}
