package com.ybyb.zzq.freecartoon.response.survey;

import com.ybyb.zzq.freecartoon.response.survey.Chapter;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/13
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ChapterData extends RealmObject {
    private int total;
    private int limit;
    private String comicName;
    private RealmList<Chapter> chapterList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public RealmList<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(RealmList<Chapter> chapterList) {
        this.chapterList = chapterList;
    }
}
