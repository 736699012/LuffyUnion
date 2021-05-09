package com.example.taobaounion.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.taobaounion.R;

public class MeItemView extends LinearLayout {

    private View mView;
    private ImageView mPic;
    private TextView mTitle;
    private int mSourceId;

    public MeItemView(Context context) {
        this(context, null);
    }

    public MeItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mView = LayoutInflater.from(context).inflate(R.layout.item_me_view, this);
        setPadding(10, 10, 10, 10);
        initView();
    }

    private void initView() {
        mPic = mView.findViewById(R.id.item_custom_iv);
        mTitle = mView.findViewById(R.id.item_custom_tv);
    }

    public void bindData(int sourceId, String title) {
        mSourceId = sourceId;
        mPic.setImageResource(sourceId);
        mTitle.setText(title);
    }

    public int getSourceId() {
        return mSourceId;
    }

}
