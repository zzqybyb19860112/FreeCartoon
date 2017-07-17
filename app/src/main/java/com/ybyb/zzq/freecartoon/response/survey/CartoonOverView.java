package com.ybyb.zzq.freecartoon.response.survey;

import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/9
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class CartoonOverView extends RealmObject {
    private String name;
    private String type;
    private String area;
    private String des;
    private boolean finish;
    private long lastUpdate;
    private String coverImg;

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
