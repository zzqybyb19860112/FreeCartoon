package com.ybyb.zzq.freecartoon.base;

import rx.functions.Func1;

/**
 * 作者：周正权 on 2017/6/8
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ApiResultFunc<T> implements Func1<ResultData<T>, T> {

    @Override
    public T call(ResultData<T> resultData) {
        int state = resultData.error_code;
        if (state != 0 && state != 200) {  // 不是200
            throw new RuntimeException();
        }
        return resultData.result;
    }
}
