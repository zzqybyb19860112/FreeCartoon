package com.ybyb.zzq.freecartoon.response.detail;

import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/13
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class DetailCartoon extends RealmObject {
    private String imageUrl;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
