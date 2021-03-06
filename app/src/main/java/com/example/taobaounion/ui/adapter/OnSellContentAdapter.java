package com.example.taobaounion.ui.adapter;

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
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.bean.OnSellContent;
import com.example.taobaounion.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnSellContentAdapter extends RecyclerView.Adapter<OnSellContentAdapter.InnerHolder> {

    List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mDataBeans = new ArrayList<>();
    private OnItemClickListen mOnItemClickListen;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_sell_content, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean mapDataBean = mDataBeans.get(position);
        holder.setData(mapDataBean);

        TextView loseTv = holder.itemView.findViewById(R.id.lose_interest_tv);
        RelativeLayout lose = holder.itemView.findViewById(R.id.lose_interest);
        lose.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListen != null) {
                mOnItemClickListen.onItemClick(mapDataBean);
                lose.setVisibility(View.GONE);
            }
        });
        lose.setOnClickListener(v -> {
            lose.setVisibility(View.GONE);
        });
        loseTv.setOnClickListener(v -> {
            if (mOnItemClickListen != null) {
                mOnItemClickListen.onLoseClick(position, mapDataBean);
            }
        });
    }

    public void removeData(IBaseInfo content, int pos) {
        if(content instanceof OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean){
            mDataBeans.remove(content);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,getItemCount());
        }
    }

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    public interface OnItemClickListen {
        void onItemClick(IBaseInfo dataBean);

        void onLoseClick(int position, IBaseInfo dataBean);
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public void setData(List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> content) {
        mDataBeans.clear();
        mDataBeans.addAll(content);
        notifyDataSetChanged();
    }

    public void addMoreData(OnSellContent moreContent) {
        int size = mDataBeans.size();
        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> newAddData = moreContent.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        mDataBeans.addAll(newAddData);
        notifyItemChanged(size, newAddData.size());
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.on_sell_cover)
        public ImageView cover;
        @BindView(R.id.on_sell_title)
        public TextView title;
        @BindView(R.id.on_sell_original_price)
        public TextView originPrice;
        @BindView(R.id.on_sell_final_price)
        public TextView finalPrice;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            RelativeLayout lose = itemView.findViewById(R.id.lose_interest);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    lose.setVisibility(View.VISIBLE);
                    return true;
                }
            });
            ButterKnife.bind(this, itemView);
        }

        public void setData(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean mapDataBean) {
            String zk_final_price = mapDataBean.getZk_final_price();
            if (title != null) {
                title.setText(mapDataBean.getTitle());
            }
            if (originPrice != null) {
                originPrice.setText("???" + zk_final_price + "  ");
                originPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if (cover != null) {
                String baseUrl;

                if (mapDataBean.getSmall_images() == null || mapDataBean.getSmall_images().getString().size() == 0) {
                    baseUrl = mapDataBean.getPict_url();
                } else {
                    baseUrl = mapDataBean.getSmall_images().getString().get(0);
                }
                String coverUrl = UrlUtil.getCoverUrl(baseUrl);
                Glide.with(itemView.getContext()).load(coverUrl).into(cover);
            }
            if (finalPrice != null) {
                float f = Float.parseFloat(zk_final_price);
                int coupon_amount = mapDataBean.getCoupon_amount();
                float finalPriceF = f - coupon_amount;
                finalPrice.setText("????????????" + String.format("%.2f", finalPriceF));
            }
        }
    }
}
