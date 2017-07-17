package com.ybyb.zzq.freecartoon;

import android.app.Application;
import android.content.Context;
import io.realm.Realm;

/**
 * 作者：周正权 on 2017/6/7
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class App extends Application {
    private static Context mContext;
    public static Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=this;
        Realm.init(this);
        this.mRealm=Realm.getDefaultInstance();
    }
    public static Context getContext(){
        return mContext;
    }
}
