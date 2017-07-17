package com.ybyb.zzq.freecartoon.base;


/**
 * 作者：周正权 on 2017/6/8
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ResultData<T> {

    /**
     * 状态码
     */
    public int error_code;

    /**
     * 描述
     */
    public String reason;

    /**
     * 数据
     */
    public T result;

}

