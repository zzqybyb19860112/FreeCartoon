package com.ybyb.zzq.freecartoon.ui.cartoon;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.ApiSubscriber;
import com.ybyb.zzq.freecartoon.response.survey.CartoonData;
import com.ybyb.zzq.freecartoon.util.RxSchedulers;
import com.ybyb.zzq.freecartoon.util.UserRepository;
import rx.Subscription;

/**
 * CartoonPresenter
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class CartoonPresenter extends CartoonContract.Presenter {
    /**
     * 构造方法
     *
     * @param context
     * @param view
     */
    public CartoonPresenter(Context context, CartoonContract.View view) {
        super(context, view);
    }
    /**
     * 一般查询漫画分类
     *
     */
    @Override
    public void queryDataList(String key, String type) {
        Subscription subscription = UserRepository.getInstance().queryDataList(key,type).compose(RxSchedulers.<CartoonData>io_main()).subscribe(new ApiSubscriber<CartoonData>(mContext) {
                    @Override
                    protected void _onNext(CartoonData data) {
                        mView.queryDataListOk(data);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.queryDataListError();
                    }
                    @Override
                    protected void _onCompleted() {
                    }
                });
                super.addSubscribe(subscription);
            }
    /**
     * 跳转查询漫画分类
     *
     */
    @Override
    public void queryDataListSkip(String key, String type, int skip) {
        Subscription subscription = UserRepository.getInstance().queryDataListSkip(key,type,skip).compose(RxSchedulers.<CartoonData>io_main()).subscribe(new ApiSubscriber<CartoonData>(mContext) {
            @Override
            protected void _onNext(CartoonData data) {
                mView.queryDataListOk(data);
            }
            @Override
            protected void _onError(String message) {
                mView.queryDataListError();
            }
            @Override
            protected void _onCompleted() {
            }
        });
        super.addSubscribe(subscription);
    }
}

