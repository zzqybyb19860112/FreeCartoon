package com.ybyb.zzq.freecartoon.response.detail;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/13
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ChapterDetail extends RealmObject{
    private String name;
    private long id;
    private RealmList<DetailCartoon> imageList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<DetailCartoon> getImageList() {
        return imageList;
    }

    public void setImageList(RealmList<DetailCartoon> imageList) {
        this.imageList = imageList;
    }
}
