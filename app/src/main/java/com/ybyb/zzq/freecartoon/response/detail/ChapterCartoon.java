package com.ybyb.zzq.freecartoon.response.detail;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/14
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ChapterCartoon extends RealmObject {
    private RealmList<DetailCartoon> imageList;

    public RealmList<DetailCartoon> getImageList() {
        return imageList;
    }

    public void setImageList(RealmList<DetailCartoon> imageList) {
        this.imageList = imageList;
    }
}
