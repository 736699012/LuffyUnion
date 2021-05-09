package com.example.taobaounion.utils;

import com.example.taobaounion.presenter.impl.CategoryPagerPresenterImpl;
import com.example.taobaounion.presenter.impl.ChoicenessPresenterImpl;
import com.example.taobaounion.presenter.impl.CollectionPresenterImpl;
import com.example.taobaounion.presenter.impl.FlashSalePresenterImpl;
import com.example.taobaounion.presenter.impl.FootPrintPresenterImpl;
import com.example.taobaounion.presenter.impl.HomePresentImpl;
import com.example.taobaounion.presenter.impl.LoginPresenterImpl;
import com.example.taobaounion.presenter.impl.OnSellPresenterImpl;
import com.example.taobaounion.presenter.impl.PersonDescPresenterImpl;
import com.example.taobaounion.presenter.impl.RegisterPresenterImpl;
import com.example.taobaounion.presenter.impl.SearchPresenterImpl;
import com.example.taobaounion.presenter.impl.TicketPresentImpl;
import com.example.taobaounion.presenter.interfaces.ICategoryPagerPresenter;
import com.example.taobaounion.presenter.interfaces.IChoicenessPresenter;
import com.example.taobaounion.presenter.interfaces.ICollectionPresenter;
import com.example.taobaounion.presenter.interfaces.IFlashSalePresenter;
import com.example.taobaounion.presenter.interfaces.IFootPrintPresenter;
import com.example.taobaounion.presenter.interfaces.IHomePresenter;
import com.example.taobaounion.presenter.interfaces.ILoginPresenter;
import com.example.taobaounion.presenter.interfaces.IOnSellPresenter;
import com.example.taobaounion.presenter.interfaces.IPersonDescPresenter;
import com.example.taobaounion.presenter.interfaces.IRegisterPresenter;
import com.example.taobaounion.presenter.interfaces.ISearchPresenter;
import com.example.taobaounion.presenter.interfaces.ITicketPresenter;

public class PresentManger {
    private static final PresentManger ourInstance = new PresentManger();
    private final IHomePresenter mHomePresent;
    private final ICategoryPagerPresenter mCategoryPagerPresenter;
    private final ITicketPresenter mTicketPresent;
    private final IChoicenessPresenter mChoicenessPresenter;
    private final IOnSellPresenter mOnSellPresenter;
    private final ISearchPresenter mSearchPresenter;
    private final ICollectionPresenter mCollectionPresenter;
    private final IPersonDescPresenter mPersonDescPresenter;
    private final IFootPrintPresenter mFootPrintPresenter;
    private final IFlashSalePresenter mFlashSalePresenter;
    private final ILoginPresenter mLoginPresenter;
    private final IRegisterPresenter mRegisterPresenter;

    public IPersonDescPresenter getPersonDescPresenter() {
        return mPersonDescPresenter;
    }

    public ICollectionPresenter getCollectionPresenter() {
        return mCollectionPresenter;
    }

    public static PresentManger getInstance() {
        return ourInstance;
    }

    public IHomePresenter getHomePresent() {
        return mHomePresent;
    }

    public ICategoryPagerPresenter getCategoryPagerPresenter() {
        return mCategoryPagerPresenter;
    }

    public ITicketPresenter getTicketPresent() {
        return mTicketPresent;
    }

    public IChoicenessPresenter getChoicenessPresenter() {
        return mChoicenessPresenter;
    }

    public IOnSellPresenter getOnSellPresenter() {
        return mOnSellPresenter;
    }

    public ISearchPresenter getSearchPresenter() {
        return mSearchPresenter;
    }

    public IFlashSalePresenter getFlashSalePresenter() {
        return mFlashSalePresenter;
    }

    public ILoginPresenter getLoginPresenter() {
        return mLoginPresenter;
    }

    public IRegisterPresenter getRegisterPresenter() {
        return mRegisterPresenter;
    }

    private PresentManger() {
        mHomePresent = new HomePresentImpl();
        mCategoryPagerPresenter = new CategoryPagerPresenterImpl();
        mTicketPresent = new TicketPresentImpl();
        mChoicenessPresenter = new ChoicenessPresenterImpl();
        mOnSellPresenter = new OnSellPresenterImpl();
        mSearchPresenter = new SearchPresenterImpl();
        mCollectionPresenter = new CollectionPresenterImpl();
        mPersonDescPresenter = new PersonDescPresenterImpl();
        mFootPrintPresenter = new FootPrintPresenterImpl();
        mFlashSalePresenter = new FlashSalePresenterImpl();
        mLoginPresenter = new LoginPresenterImpl();
        mRegisterPresenter = new RegisterPresenterImpl();
    }

    public IFootPrintPresenter getFootPrintPresenter() {
        return mFootPrintPresenter;
    }
}
