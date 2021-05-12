package com.example.taobaounion.ui.adapter;

import android.text.TextUtils;
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
import com.example.taobaounion.model.bean.ChoicenessContent;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.dao.Collect;
import com.example.taobaounion.utils.CollectionUtils;
import com.example.taobaounion.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoicenessContentAdapter extends RecyclerView.Adapter<ChoicenessContentAdapter.InnerHolder> {

    private static final Object TAG = "ChoicenessContentAdapter";
    private List<ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> mData = new ArrayList<>();
    private OnItemClickListen mOnItemClickListen;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choice_right, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean itemBean = mData.get(position);
        holder.setData(itemBean);
        TextView loseTv = holder.itemView.findViewById(R.id.lose_interest_tv);
        RelativeLayout lose = holder.itemView.findViewById(R.id.lose_interest);
        lose.setVisibility(View.GONE);
        LogUtils.d(TAG, "position " + position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListen != null) {
                    mOnItemClickListen.onItemClick(itemBean);
                    lose.setVisibility(View.GONE);
                }
            }
        });
        lose.setOnClickListener(v -> {
            lose.setVisibility(View.GONE);
        });
        loseTv.setOnClickListener(v -> {
            if (mOnItemClickListen != null) {
                mOnItemClickListen.onLoseClick(position, itemBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> content) {
        mData.clear();
        mData.addAll(content);
        notifyDataSetChanged();
    }

    public void removeData(IBaseInfo content, int pos) {
        if(content instanceof ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean){
            mData.remove(content);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, getItemCount());
        }
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.right_choice_content_cover)
        public ImageView cover;
        @BindView(R.id.right_choice_content_offer_price)
        public TextView offerPrice;
        @BindView(R.id.right_choice_content_title)
        public TextView title;
        @BindView(R.id.choice_buy_bt)
        public TextView buyBtn;
        @BindView(R.id.right_choice_content_original_price)
        public TextView originalPrice;
        @BindView(R.id.iv_collect_choice)
        public ImageView mCollectChoice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            RelativeLayout lose = itemView.findViewById(R.id.lose_interest);
            itemView.setOnLongClickListener(v -> {
                lose.setVisibility(View.VISIBLE);
                return true;
            });
            ButterKnife.bind(this, itemView);
        }

        public void setData(ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean itemBean) {
            title.setText(itemBean.getTitle());
            String coverUrl = "https:" + itemBean.getPict_url();
            Glide.with(cover.getContext()).load(coverUrl).into(cover);
            if (TextUtils.isEmpty(itemBean.getCoupon_click_url())) {

                buyBtn.setVisibility(View.GONE);
                originalPrice.setText("没有优惠券喽");
            } else {
                buyBtn.setVisibility(View.VISIBLE);
                originalPrice.setText("原价:" + itemBean.getZk_final_price());
            }
            float resultPrice = 0.0f;
            if (TextUtils.isEmpty(itemBean.getCoupon_info())) {
                offerPrice.setVisibility(View.GONE);
            } else {
                offerPrice.setVisibility(View.VISIBLE);
                offerPrice.setText(itemBean.getCoupon_info());
                resultPrice = getResultPrice(itemBean.getZk_final_price(), itemBean.getCoupon_info());
            }

//            final CollectionBean collectionBean = new CollectionBean(itemBean.getTitle(), resultPrice, coverUrl, itemBean.getLink());
            Collect collect = new Collect(itemBean.getTitle(), resultPrice, coverUrl, itemBean.getLink());
            // 收藏赋值
            CollectionUtils.changeCollectIcon(mCollectChoice, collect);

        }

        private float getResultPrice(String zk_final_price, String coupon_info) {
            String temp = coupon_info.split("减")[1];
            String money = temp.replace("元", "");
            return Float.parseFloat(zk_final_price) - Float.parseFloat(money);
        }
    }


    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    public interface OnItemClickListen {
        void onItemClick(IBaseInfo itemBean);

        void onLoseClick(int position, IBaseInfo dataBean);
    }
}
