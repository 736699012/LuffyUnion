package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.bean.FootPrintData;
import com.example.taobaounion.presenter.interfaces.IFootPrintPresenter;
import com.example.taobaounion.utils.JsonCacheUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FootPrintPresenterImpl implements IFootPrintPresenter {

    private final JsonCacheUtil mInstance;

    public static final String KEY_FOOT_PRINT = "key_foot_print";

    public FootPrintPresenterImpl() {
        mInstance = JsonCacheUtil.getInstance();
    }

    @Override
    public FootPrintData getFootPrint() {
        FootPrintData footPrintData = mInstance.getValue(KEY_FOOT_PRINT, FootPrintData.class);
        if (footPrintData == null) {
            footPrintData = new FootPrintData();
        }
        return footPrintData;
    }

    @Override
    public void addFootPrint(FootPrintData foot) {
        FootPrintData footPrint = getFootPrint();
        List<FootPrintData> list = footPrint.getFootPrintList();
        for (FootPrintData data : list) {
            if (data.getLink().equals(foot.getLink())) {
                list.remove(data);
                break;
            }
        }
        list.add(0, foot);
        footPrint.setFootPrintList(list);
        mInstance.saveCache(KEY_FOOT_PRINT, footPrint);
        changeFoot(footPrint);
    }

    @Override
    public void delFootPrint(FootPrintData foot) {
        FootPrintData footPrint = getFootPrint();
        List<FootPrintData> list = footPrint.getFootPrintList();
        for (FootPrintData data : list) {
            if (data.getLink().equals(foot.getLink())) {
                list.remove(data);
                return;
            }
        }
        footPrint.setFootPrintList(list);
        mInstance.saveCache(KEY_FOOT_PRINT, footPrint);
        changeFoot(footPrint);
    }

    @Override
    public void cleanFootPrint() {
        mInstance.delCache(KEY_FOOT_PRINT);
        changeFoot(new FootPrintData());
    }

    private void changeFoot(FootPrintData footPrintData) {
        EventBus.getDefault().post(footPrintData);
    }
}
