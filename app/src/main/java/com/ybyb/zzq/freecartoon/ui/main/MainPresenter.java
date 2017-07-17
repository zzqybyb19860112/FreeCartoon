package com.ybyb.zzq.freecartoon.ui.main;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.ApiSubscriber;
import com.ybyb.zzq.freecartoon.util.RxSchedulers;
import com.ybyb.zzq.freecartoon.util.UserRepository;
import java.util.List;
import rx.Subscription;

/**
 * DetailPresenter
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class MainPresenter extends MainContract.Presenter {
    /**
     * 构造方法
     *
     * @param context
     * @param view
     */
    public MainPresenter(Context context, MainContract.View view) {
        super(context, view);
    }

    @Override
    public void queryTypes(String key) {
        Subscription subscription = UserRepository.getInstance().queryTypes(key).compose(RxSchedulers.<List<String>>io_main()).subscribe(new ApiSubscriber<List<String>>(mContext) {
            @Override
            protected void _onNext(List<String> data) {
                mView.queryDataSuccess(data);
            }

            @Override
            protected void _onError(String message) {
                mView.queryDataError();
            }

            @Override
            protected void _onCompleted() {

            }
        });
        super.addSubscribe(subscription);
    }
}

