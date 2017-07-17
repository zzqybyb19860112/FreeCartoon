package com.ybyb.zzq.freecartoon.response.survey;

import com.ybyb.zzq.freecartoon.response.survey.Cartoon;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/9
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class MainData extends RealmObject {
    private RealmList<Cartoon> cartoonList;

    public RealmList<Cartoon> getCartoonList() {
        return cartoonList;
    }

    public void setCartoonList(RealmList<Cartoon> cartoonList) {
        this.cartoonList = cartoonList;
    }
}
