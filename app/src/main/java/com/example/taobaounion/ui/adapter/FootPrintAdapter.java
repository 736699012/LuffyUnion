package com.example.taobaounion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.FootPrintData;

import java.util.ArrayList;
import java.util.List;

public class FootPrintAdapter extends RecyclerView.Adapter<FootPrintAdapter.InnerHolder> {

    private List<FootPrintData> mFootPrintDataList = new ArrayList<>();
    private OnItemClickListen mOnItemClickListen;

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_print, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        FootPrintData data = mFootPrintDataList.get(position);
        ImageView iv = holder.itemView.findViewById(R.id.item_foot_iv);
        Glide.with(iv.getContext()).load(data.getPict_url()).into(iv);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListen != null) {
                mOnItemClickListen.onItemClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFootPrintDataList.size();
    }

    public void setData(List<FootPrintData> footPrintList) {
        mFootPrintDataList.clear();
        mFootPrintDataList.addAll(footPrintList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListen {
        void onItemClick(FootPrintData footPrintData);
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
