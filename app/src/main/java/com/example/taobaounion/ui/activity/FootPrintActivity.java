package com.example.taobaounion.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseActivity;
import com.example.taobaounion.model.bean.FootPrintData;
import com.example.taobaounion.presenter.interfaces.IFootPrintPresenter;
import com.example.taobaounion.ui.adapter.FootPrintAdapter;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.SizeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class FootPrintActivity extends BaseActivity implements FootPrintAdapter.OnItemClickListen {

    @BindView(R.id.foot_print_rv)
    public RecyclerView mRecyclerView;
    @BindView(R.id.foot_clean)
    public TextView mClean;

    private FootPrintAdapter mAdapter;
    private IFootPrintPresenter mFootPrintPresenter;


    @Override
    protected void initPresent() {
        mFootPrintPresenter = PresentManger.getInstance().getFootPrintPresenter();
        FootPrintData footPrint = mFootPrintPresenter.getFootPrint();
        mAdapter.setData(footPrint.getFootPrintList());
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new FootPrintAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListen(this);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = SizeUtils.dip2px(FootPrintActivity.this, 10);
                outRect.right = SizeUtils.dip2px(FootPrintActivity.this, 10);
                outRect.top = SizeUtils.dip2px(FootPrintActivity.this, 10);
                outRect.bottom = SizeUtils.dip2px(FootPrintActivity.this, 10);
            }
        });
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFootPrintPresenter.cleanFootPrint();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_foot_print;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(FootPrintData footPrintData) {
        if (footPrintData.getFootPrintList() != null) {
            mAdapter.setData(footPrintData.getFootPrintList());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFootPrintPresenter != null) {
            mAdapter.setData(mFootPrintPresenter.getFootPrint().getFootPrintList());
        }
    }

    @Override
    protected void recycle() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(FootPrintData footPrintData) {
        EventBus.getDefault().postSticky(footPrintData);
        Intent intent = new Intent(this, TicketActivity.class);
        startActivity(intent);
    }
}