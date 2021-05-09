package com.example.taobaounion.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.bean.ILinearInfo;
import com.example.taobaounion.utils.CollectionUtils;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePagerContentAdapter extends RecyclerView.Adapter<HomePagerContentAdapter.InnerHolder> {

    private List<ILinearInfo> mDataBeans = new ArrayList<>();
    private OnItemClickListen mOnItemClickListen;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        LogUtils.d(this, "onCreateViewHolder...");
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ILinearInfo dataBean = mDataBeans.get(position);
        LogUtils.d(this, "onBindViewHolder..." + position);
        holder.setData(dataBean);
        TextView loseTv = holder.itemView.findViewById(R.id.lose_interest_tv);
        RelativeLayout lose = holder.itemView.findViewById(R.id.lose_interest);
        lose.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListen != null) {
                mOnItemClickListen.onItemClickListen(dataBean);
                lose.setVisibility(View.GONE);
            }
        });
        lose.setOnClickListener(v -> {
            lose.setVisibility(View.GONE);
        });
        loseTv.setOnClickListener(v -> {
            if (mOnItemClickListen != null) {
                mOnItemClickListen.onLoseClick(position, dataBean);
            }
        });
    }

    public void removeData(ILinearInfo content, int pos) {
        mDataBeans.remove(content);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public void setData(List<? extends ILinearInfo> contents) {
        mDataBeans.clear();
        mDataBeans.addAll(contents);
        notifyDataSetChanged();
    }

    public void addData(List<? extends ILinearInfo> contents) {
        int begin = mDataBeans.size();
        mDataBeans.addAll(contents);
        notifyItemRangeChanged(begin, contents.size());
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_cover)
        public ImageView mImageView;

        @BindView(R.id.goods_title)
        public TextView mTitle;

        @BindView(R.id.goods_offer_prise)
        public TextView mOfferPrise;
        @BindView(R.id.goods_after_offer_prise)
        public TextView mFinalPrise;
        @BindView(R.id.goods_before_offer_prise)
        public TextView mBeforePrise;
        @BindView(R.id.goods_sell_count)
        public TextView mSellCount;
        @BindView(R.id.iv_collect_home)
        public ImageView mCollectHome;
        @BindView(R.id.lose_interest)
        public RelativeLayout mLose;
        @BindView(R.id.lose_interest_tv)
        public TextView mLoseTv;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLose.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }

        public void setData(ILinearInfo dataBean) {
            Context context = itemView.getContext();
            mTitle.setText(dataBean.getTitle());
            String finalPrice = dataBean.getZk_final_price();
            long couponAmount = dataBean.getCoupon_amount();
            mOfferPrise.setText(context.getString(R.string.text_offer_prise, couponAmount));
            ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
            int height = layoutParams.height;
            int width = layoutParams.width;
            int size = (width > height ? width : height) / 2;
            String coverUrl = UrlUtil.getCoverUrl(dataBean.getPict_url(), size);
            Glide.with(context).load(coverUrl).into(mImageView);
            mBeforePrise.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mBeforePrise.setText(finalPrice);
            float resultPrice = Float.parseFloat(finalPrice) - couponAmount;
            mFinalPrise.setText(context.getString(R.string.text_goods_before_offer_price, String.format("%.2f", resultPrice)));
            mSellCount.setText(context.getString(R.string.text_goods_sell_count, dataBean.getVolume()));
            final CollectionBean collectionBean = new CollectionBean(dataBean.getTitle(), resultPrice, coverUrl, dataBean.getLink());
            // 收藏赋值
            CollectionUtils.changeCollectIcon(mCollectHome, collectionBean);
        }

    }

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    public interface OnItemClickListen {
        void onItemClickListen(IBaseInfo dataBean);

        void onLoseClick(int pos, ILinearInfo dataBean);
    }
}
