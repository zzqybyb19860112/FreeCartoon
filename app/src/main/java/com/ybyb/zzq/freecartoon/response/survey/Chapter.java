package com.ybyb.zzq.freecartoon.response.survey;

import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/13
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class Chapter extends RealmObject {
    private String name;
    private long id;

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
}
