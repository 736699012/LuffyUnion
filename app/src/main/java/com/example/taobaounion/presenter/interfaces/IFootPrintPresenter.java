package com.example.taobaounion.presenter.interfaces;

import com.example.taobaounion.model.bean.FootPrintData;

public interface IFootPrintPresenter {

    FootPrintData getFootPrint();

    void addFootPrint(FootPrintData foot);

    void delFootPrint(FootPrintData foot);

    void cleanFootPrint();
}
