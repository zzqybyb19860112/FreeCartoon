package com.ybyb.zzq.freecartoon.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：周正权 on 2017/6/7
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class RxSchedulers {
    /**
     * 从io线程切换到android主线程
     *
     * @param <T>
     *
     * @return
     */
    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
