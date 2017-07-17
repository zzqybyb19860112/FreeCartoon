package com.ybyb.zzq.freecartoon.response.survey;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 作者：周正权 on 2017/6/9
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class CartoonData extends RealmObject {
    private int total;
    private int limit;
    private RealmList<CartoonOverView> bookList;

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

    public RealmList<CartoonOverView> getBookList() {
        return bookList;
    }

    public void setBookList(RealmList<CartoonOverView> bookList) {
        this.bookList = bookList;
    }
}
