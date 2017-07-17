package com.ybyb.zzq.freecartoon.base;

import android.content.Context;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 作者：周正权 on 2017/6/7
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public abstract class ApiSubscriber<T> extends Subscriber<T> {

    private Context mContext;

    public ApiSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 500 || code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            else if (code == 504) {
                msg = "网络不给力，请稍后再试";
            }
            this._onError(msg);
        }else {
            this._onError(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        this._onNext(t);
    }

    @Override
    public void onCompleted() {
        this._onCompleted();
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

    protected abstract void _onCompleted();
}
