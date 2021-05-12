package com.example.taobaounion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.dao.Collect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.InnerHolder> {

    private List<Collect> mCollectionBeanList = new ArrayList<>();
    private CollectionAdapter.OnItemClickListen mOnItemClickListen;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_content, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        Collect collectionBean = mCollectionBeanList.get(position);
        holder.setData(collectionBean);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListen != null) {
                mOnItemClickListen.onItemClick(collectionBean);
            }
        });
        ImageView iv = holder.itemView.findViewById(R.id.iv_collect);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListen != null) {
                    mOnItemClickListen.cancelCollect(position, collectionBean);
                }
            }
        });
    }

    public void setData(List<Collect> data) {
        mCollectionBeanList.clear();
        mCollectionBeanList.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickListen(CollectionAdapter.OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    public void remove(int pos, Collect collectionBean) {
        mCollectionBeanList.remove(collectionBean);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }


    public interface OnItemClickListen {
        void onItemClick(IBaseInfo dataBean);

        void cancelCollect(int pos, Collect collect);
    }

    @Override
    public int getItemCount() {
        return mCollectionBeanList.size();
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.collect_cover)
        public ImageView cover;
        @BindView(R.id.collect_title)
        public TextView title;
        @BindView(R.id.collect_final_price)
        public TextView finalPrice;
        @BindView(R.id.iv_collect)
        public ImageView mCollect;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Collect data) {
            if (cover != null) {
                Glide.with(itemView.getContext()).load(data.getCoverUrl()).into(cover);
            }

            if (title != null) {
                title.setText(data.getTitle());
            }
            if (finalPrice != null) {

                finalPrice.setText("券后价: " + String.format("%.2f", data.getFinalMoney()));
            }
            mCollect.setImageResource(R.mipmap.collected);
//            CollectionUtils.changeCollectIcon(mCollect, data);

        }
    }
}
