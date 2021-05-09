package com.example.taobaounion.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseActivity;
import com.example.taobaounion.model.bean.PersonDesc;
import com.example.taobaounion.presenter.interfaces.IPersonDescPresenter;
import com.example.taobaounion.ui.custom.GlideCircleTransform;
import com.example.taobaounion.utils.ImageUtils;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresentManger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

import static com.example.taobaounion.utils.Constant.DEFAULT_USER_NAME;

public class EditPersonMsgActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "EditPersonMsgActivity";
    @BindView(R.id.edit_go_back)
    public ImageView mBack;
    @BindView(R.id.person_avatar)
    public ImageView mAvatar;
    @BindView(R.id.et_username)
    public EditText mName;
    @BindView(R.id.et_person_desc)
    public EditText mDesc;

    @BindView(R.id.tv_save_desc)
    public TextView mSaveDesc;
    private IPersonDescPresenter mPresenter;
    private PersonDesc mDetailDesc;


    @Override
    protected void initPresent() {

    }

    @Override
    protected void initEvent() {
        mSaveDesc.setOnClickListener(this);
        mAvatar.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mPresenter = PresentManger.getInstance().getPersonDescPresenter();
        mDetailDesc = mPresenter.getDetailDesc();
        bindView(mDetailDesc);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void event(PersonDesc desc) {
        bindView(desc);
    }

    private void bindView(PersonDesc desc) {
        if (mName == null || mAvatar == null || mDesc == null) {
            return;
        }
        if (desc == null) {
            mName.setText(DEFAULT_USER_NAME);
            mAvatar.setImageResource(R.mipmap.blank_face);
            mDetailDesc = new PersonDesc();
            mDetailDesc.setName(DEFAULT_USER_NAME);
        } else {
            if (TextUtils.isEmpty(desc.getUrl())) {
                mAvatar.setImageResource(R.mipmap.blank_face);
            } else {
                Uri uri = ImageUtils.getImageContentUri(mAvatar.getContext(), desc.getUrl());
                LogUtils.d(this, "uri = " + uri);
                Glide.with(mAvatar).load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(new GlideCircleTransform(mAvatar.getContext()))
                        .into(mAvatar);
            }
            mName.setText(desc.getName());
            mDesc.setText(desc.getDesc());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_person_msg;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "id = " + v.getId());
        switch (v.getId()) {

            case R.id.tv_save_desc:
                mDesc.setEnabled(true);
                String newDesc = mDesc.getText().toString();
                mDetailDesc.setDesc(newDesc);
                changeMsg(mDetailDesc);
                break;
            case R.id.person_avatar:
                Intent intent = new Intent(v.getContext(), TakeAvatarActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_go_back:
                finish();
                break;
        }

    }

    private void changeMsg(PersonDesc detailDesc) {
        mPresenter.changeDetailDesc(detailDesc);
//        mName.setEnabled(false);
//        mDesc.setEnabled(false);
    }

    @Override
    protected void recycle() {
        super.recycle();
        EventBus.getDefault().unregister(this);
    }
}