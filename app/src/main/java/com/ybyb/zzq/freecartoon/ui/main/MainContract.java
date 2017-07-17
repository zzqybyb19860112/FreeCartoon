package com.ybyb.zzq.freecartoon.ui.main;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.BasePresenter;
import com.ybyb.zzq.freecartoon.base.BaseView;
import java.util.List;

/**
 * 找回密码契约类，MVP规范
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public interface MainContract {
    interface View extends BaseView {

        /**
         * 查询成功
         */
        void queryDataSuccess(List<String> data);

        /**
         * 查询漫画分类失败
         */
        void queryDataError();
    }

    abstract class Presenter extends BasePresenter<View> {

        /**
         * 构造方法
         *
         * @param context
         * @param view
         */
        public Presenter(Context context, View view) {
            super(context, view);
        }

        /**
         * 查询漫画分类
         *
         */
        public abstract void queryTypes(String key);
    }
}
