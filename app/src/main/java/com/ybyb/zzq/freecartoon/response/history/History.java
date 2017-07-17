package com.ybyb.zzq.freecartoon.response.history;

import com.ybyb.zzq.freecartoon.response.detail.ChapterCartoon;
import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/15
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class History extends RealmObject {
    private ChapterCartoon cartoon;
    private int lastSee;

    public ChapterCartoon getCartoon() {
        return cartoon;
    }

    public void setCartoon(ChapterCartoon cartoon) {
        this.cartoon = cartoon;
    }

    public int getLastSee() {
        return lastSee;
    }

    public void setLastSee(int lastSee) {
        this.lastSee = lastSee;
    }
}
