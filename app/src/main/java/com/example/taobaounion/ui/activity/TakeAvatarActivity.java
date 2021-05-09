package com.example.taobaounion.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.PersonDesc;
import com.example.taobaounion.presenter.interfaces.IPersonDescPresenter;
import com.example.taobaounion.utils.PresentManger;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;

import static com.example.taobaounion.utils.Constant.DEFAULT_USER_NAME;

public class TakeAvatarActivity extends TakePhotoActivity {

    private static final String TAG = "TakeAvatarActivity";
    private TakePhoto mTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_avatar);
        mTakePhoto = getTakePhoto();
        initView();
        initEvent();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        finish();
    }

    private void initEvent() {
        mTakePhoto.onPickFromGallery();
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<TImage> images = result.getImages();
        IPersonDescPresenter descPresenter = PresentManger.getInstance().getPersonDescPresenter();
        PersonDesc detailDesc = descPresenter.getDetailDesc();
        if (detailDesc == null) {
            detailDesc = new PersonDesc();
            detailDesc.setName(DEFAULT_USER_NAME);
        }
        detailDesc.setUrl(images.get(0).getOriginalPath());
        Log.d(TAG, "path = " + images.get(0).getOriginalPath());
        descPresenter.changeDetailDesc(detailDesc);
        finish();
    }

    private void initView() {

    }

}