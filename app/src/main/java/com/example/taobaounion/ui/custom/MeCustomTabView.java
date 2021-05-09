package com.example.taobaounion.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.taobaounion.R;

import java.util.ArrayList;
import java.util.List;

public class MeCustomTabView extends LinearLayout {

    private List<MeItemView> mMeItemViewList = new ArrayList<>();
    private OnItemClickListen mOnItemClickListen;
    private static float DEFAULT_ITEM_SPACE = 15f;
    private int mSelfWidth;

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    public MeCustomTabView(Context context) {
        this(context, null);
    }

    public MeCustomTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeCustomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_me_tab_view, this);
        initView(view);
    }

    private void initView(View view) {

    }


//    public void setMeItemViewList(List<MeItemView> list) {
//        mMeItemViewList.clear();
//        removeAllViews();
//        mMeItemViewList.addAll(list);
//        for (MeItemView child : mMeItemViewList) {
//            child.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mOnItemClickListen != null) {
//                        mOnItemClickListen.onItemClick(v);
//                    }
//                }
//            });
//            addView(child);
//        }
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (getChildCount() == 0) {
//            return;
//        }
//        mSelfWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//        }
//        int height = getChildAt(0).getMeasuredHeight();
//        setMeasuredDimension(mSelfWidth, height);
//    }


    public interface OnItemClickListen {
        void onCollectClick(View view);
        void onFootClick();
        void onSaleClick();
    }
}
