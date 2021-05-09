package com.example.taobaounion.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.model.bean.IBaseInfo;

import java.util.ArrayList;
import java.util.List;

public class HomePagerLooperAdapter extends PagerAdapter {

    List<HomePagerContent.DataBean> mDataBeans = new ArrayList<>();
    private OnLooperItemClickListen mOnLooperItemClickListen;
    private int[] sourceIds = {
            R.mipmap.guide_pic_one,
            R.mipmap.guide_pic_two,
            R.mipmap.guide_pic_three,
            R.mipmap.guide_pic_four,
    };

    public int getDataSize() {
//        return mDataBeans.size();
        return sourceIds.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        int realPosition = position % mDataBeans.size();
        ImageView imageView = new ImageView(container.getContext());
//        HomePagerContent.DataBean data = mDataBeans.get(realPosition);
//        HomePagerContent.DataBean data = mDataBeans.get(position);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(sourceIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setOnClickListener(v -> mOnLooperItemClickListen.onLooperItemClickListen(mDataBeans.get(realPosition)));
//        int height = container.getMeasuredHeight();
//        int width = container.getMeasuredWidth();
//        int size = (width > height ? width : height) / 2;
//        Glide.with(container.getContext()).load(Constant.IMG_BASE_URL + data.getPict_url() + "_" + size + "x" + size + ".jpg").into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
//        return Integer.MAX_VALUE;
        return sourceIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
        mDataBeans.clear();
        mDataBeans.addAll(contents);
        notifyDataSetChanged();
    }

    public void bindData(int[] arr) {

    }

    public void setOnLooperItemClickListen(OnLooperItemClickListen onLooperItemClickListen) {
        mOnLooperItemClickListen = onLooperItemClickListen;

    }

    public interface OnLooperItemClickListen {
        void onLooperItemClickListen(IBaseInfo dataBean);
    }
}
