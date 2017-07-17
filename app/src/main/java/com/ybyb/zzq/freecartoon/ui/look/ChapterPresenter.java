package com.ybyb.zzq.freecartoon.ui.look;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.ApiSubscriber;
import com.ybyb.zzq.freecartoon.response.survey.ChapterData;
import com.ybyb.zzq.freecartoon.util.RxSchedulers;
import com.ybyb.zzq.freecartoon.util.UserRepository;
import rx.Subscription;

/**
 * DetailPresenter
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ChapterPresenter extends ChapterContract.Presenter {
    /**
     * 构造方法
     *
     * @param context
     * @param view
     */
    public ChapterPresenter(Context context, ChapterContract.View view) {
        super(context, view);
    }

    @Override
    public void queryChapters(String key, String comicName) {
        Subscription subscription =
            UserRepository.getInstance().queryChapters(key, comicName).compose(RxSchedulers.<ChapterData>io_main()).subscribe(new ApiSubscriber<ChapterData>(mContext) {
                @Override
                protected void _onNext(ChapterData chapterData) {
                    mView.queryChaptersSuccess(chapterData);
                }

                @Override
                protected void _onError(String message) {
                    mView.queryChaptersError();
                }

                @Override
                protected void _onCompleted() {
                }
            });
        super.addSubscribe(subscription);
    }

    @Override
    public void queryChaptersSkip(String key, String comicName, int skip) {
        Subscription subscription =
            UserRepository.getInstance().queryChaptersSkip(key, comicName, skip).compose(RxSchedulers.<ChapterData>io_main()).subscribe(new ApiSubscriber<ChapterData>(mContext) {
                @Override
                protected void _onNext(ChapterData chapterData) {
                    mView.queryChaptersSuccess(chapterData);
                }

                @Override
                protected void _onError(String message) {
                    mView.queryChaptersError();
                }

                @Override
                protected void _onCompleted() {
                }
            });
        super.addSubscribe(subscription);
    }
}

