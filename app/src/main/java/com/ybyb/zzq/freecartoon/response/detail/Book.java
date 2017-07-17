package com.ybyb.zzq.freecartoon.response.detail;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/13
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class Book extends RealmObject {
    private String name;
    private RealmList<ChapterDetail> chapters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<ChapterDetail> getChapters() {
        return chapters;
    }

    public void setChapters(RealmList<ChapterDetail> chapters) {
        this.chapters = chapters;
    }
}
