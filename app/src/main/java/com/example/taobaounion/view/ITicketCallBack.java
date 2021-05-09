package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallback;
import com.example.taobaounion.model.bean.FlashSaleData;
import com.example.taobaounion.model.bean.Ticket;

public interface ITicketCallBack extends IBaseCallback {

    void onTicket(Ticket ticket, FlashSaleData data);
}
