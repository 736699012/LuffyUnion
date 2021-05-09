package com.example.taobaounion.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseActivity;
import com.example.taobaounion.model.bean.FlashSaleData;
import com.example.taobaounion.model.bean.FootPrintData;
import com.example.taobaounion.model.bean.Ticket;
import com.example.taobaounion.presenter.interfaces.IFlashSalePresenter;
import com.example.taobaounion.presenter.interfaces.IFootPrintPresenter;
import com.example.taobaounion.presenter.interfaces.ITicketPresenter;
import com.example.taobaounion.utils.Constant;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.ToastUtil;
import com.example.taobaounion.view.ITicketCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class TicketActivity extends BaseActivity implements ITicketCallBack {


    private ITicketPresenter mTicketPresent;

    private boolean isHasTaoBao = false;
    @BindView(R.id.ticket_back_press)
    public ImageView mTicketBack;

    @BindView(R.id.ticket_cover)
    public ImageView mTicketCover;

    @BindView(R.id.ticket_tao_kou_ling)
    public TextView mTaoKouLing;
    @BindView(R.id.code_open_bt)
    public TextView mOpenBt;
    @BindView(R.id.ticket_error_text)
    public TextView mErrorText;
    @BindView(R.id.ticket_loading)
    public View mLoadingView;
    @BindView(R.id.flash_sale_bt)
    public TextView mFlashBt;
//    @BindView(R.id.circle_count_down)
//    public CircleView mCountDown;


    @Override
    protected void initPresent() {
        mTicketPresent = PresentManger.getInstance().getTicketPresent();
        mTicketPresent.registerViewCallBack(this);
        //判断是否安装有淘宝
        //act=android.intent.action.VIEW flg=0x4000000 hwFlg=0x10
        // pkg=com.taobao.taobao  包名
        // cmp=com.taobao.taobao/com.taobao.tao.TBMainActivity (has extras)}
        // from uid 10131
//        检查是否有淘宝
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(Constant.TAOBAO_PACKEDG, PackageManager.MATCH_UNINSTALLED_PACKAGES);
            isHasTaoBao = packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isHasTaoBao = false;
        }
        LogUtils.d(this, "isHasTaoBao " + isHasTaoBao);
        mOpenBt.setText(isHasTaoBao ? "打开淘宝" : "复制淘口令");
    }

    @Override
    protected void recycle() {
        if (mTicketPresent != null) {
            mTicketPresent.unRegisterViewCallBack(this);
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mFlashBt.setOnClickListener(v -> {
            Intent intent = new Intent(this, FlashSaleActivity.class);
            startActivity(intent);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void event(FootPrintData data) {
        if (data.getTicket() != null && !TextUtils.isEmpty(data.getLink())) {
            FlashSaleData flashSaleData = new FlashSaleData();
            flashSaleData.setCouponCount(data.getCoupon());
            flashSaleData.setCover(data.getPict_url());
            flashSaleData.setTitles(data.getTitle());
            flashSaleData.setLinkUrl(data.getLink());
            onTicket(data.getTicket(), flashSaleData);
        }
    }

    @Override
    protected void initEvent() {
        mTicketBack.setOnClickListener(v -> finish());
        mOpenBt.setOnClickListener(v -> {
//                复制口令
            String tao = mTaoKouLing.getText().toString().trim();
            LogUtils.d(TicketActivity.this, tao);
            ClipboardManager cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("luffy_taobao_ticket", tao);
            if (cbm != null) {
                cbm.setPrimaryClip(data);
            }
            if (isHasTaoBao) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.taobao.taobao", "com.taobao.tao.TBMainActivity");
                intent.setComponent(componentName);
//                    跳转页面
                startActivity(intent);
            } else {
                ToastUtil.showToast("复制口令,打开淘宝");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket;
    }

    @Override
    public void onTicket(Ticket ticket, FlashSaleData data) {

        if (mTicketCover != null && !TextUtils.isEmpty(data.getCover())) {

            Glide.with(this).load(data.getCover()).into(mTicketCover);
        }
        if (TextUtils.isEmpty(data.getCover())) {
            mTicketCover.setImageResource(R.mipmap.no_image);
        }
        if (mTaoKouLing != null && ticket != null && ticket.getData() != null && ticket.getData().getTbk_tpwd_create_response() != null) {

            mTaoKouLing.setText(ticket.getData().getTbk_tpwd_create_response().getData().getModel());
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
        FootPrintData footPrintData = new FootPrintData();
        footPrintData.setUrl(data.getCover());
        footPrintData.setTicket(ticket);
        footPrintData.setTitle(data.getTitle());
        footPrintData.setLink(data.getLinkUrl());
        footPrintData.setCoupon(data.getCouponCount());
        IFootPrintPresenter footPrintPresenter = PresentManger.getInstance().getFootPrintPresenter();
        footPrintPresenter.addFootPrint(footPrintData);

        if (data.getCouponCount() >= 30) {
            FlashSaleData saleData = new FlashSaleData(data);
            IFlashSalePresenter flashSalePresenter = PresentManger.getInstance().getFlashSalePresenter();
            List<FlashSaleData> saleList = flashSalePresenter.getFlashSaleList();
            mFlashBt.setVisibility(View.VISIBLE);
            for (FlashSaleData temp : saleList) {
                if (temp.equals(saleData)) {
                    saleData = new FlashSaleData(temp);
                    long startTime = saleData.getStartTime();
                    long hasTime = System.currentTimeMillis() - startTime;
//                    mCountDown.changeAngle(hasTime);
                    return;
                }
            }
            saleData.setTicket(ticket);
            saleData.setStartTime(System.currentTimeMillis());
            flashSalePresenter.addFlashSale(saleData);
//            mCountDown.changeAngle(10 * 60 * 1000);
        } else {
            mFlashBt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoading() {
        if (mErrorText != null) {
            mErrorText.setVisibility(View.GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError() {
        if (mErrorText != null) {
            mErrorText.setVisibility(View.VISIBLE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onEmpty() {

    }
}
