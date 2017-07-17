package com.ybyb.zzq.freecartoon.ui.detail;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.ApiSubscriber;
import com.ybyb.zzq.freecartoon.response.detail.ChapterCartoon;
import com.ybyb.zzq.freecartoon.util.RxSchedulers;
import com.ybyb.zzq.freecartoon.util.UserRepository;
import rx.Subscription;

/**
 * DetailPresenter
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class DetailPresenter extends DetailContract.Presenter {
    /**
     * 构造方法
     *
     * @param context
     * @param view
     */
    public DetailPresenter(Context context, DetailContract.View view) {
        super(context, view);
    }

    @Override
    public void queryDetail(String key, String comicName, int id) {
        Subscription subscription =
            UserRepository.getInstance().queryDetail(key, comicName, id).compose(RxSchedulers.<ChapterCartoon>io_main()).subscribe(new ApiSubscriber<ChapterCartoon>(mContext) {

                @Override
                protected void _onNext(ChapterCartoon chapterCartoon) {
                    mView.queryDetailSuccess(chapterCartoon);
                }

                @Override
                protected void _onError(String message) {
                    mView.queryDetailError();
                }

                @Override
                protected void _onCompleted() {
                }
            });
        super.addSubscribe(subscription);
    }
}

