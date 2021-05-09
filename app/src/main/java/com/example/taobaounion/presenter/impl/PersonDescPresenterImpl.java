package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.bean.PersonDesc;
import com.example.taobaounion.presenter.interfaces.IPersonDescPresenter;
import com.example.taobaounion.utils.JsonCacheUtil;

import org.greenrobot.eventbus.EventBus;

public class PersonDescPresenterImpl implements IPersonDescPresenter {

    public static final String KEY_PERSON_DESC = "key_person_desc";


    @Override
    public PersonDesc getDetailDesc() {
        return JsonCacheUtil.getInstance()
                .getValue(KEY_PERSON_DESC, PersonDesc.class);
    }

    @Override
    public void changeDetailDesc(PersonDesc personDesc) {
        JsonCacheUtil.getInstance().saveCache(KEY_PERSON_DESC, personDesc);
        EventBus.getDefault().postSticky(personDesc);
    }
}
