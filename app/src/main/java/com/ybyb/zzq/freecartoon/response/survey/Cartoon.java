package com.ybyb.zzq.freecartoon.response.survey;

import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/9
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class Cartoon extends RealmObject {
    private String type;
    private CartoonData data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CartoonData getData() {
        return data;
    }

    public void setData(CartoonData data) {
        this.data = data;
    }
}
