package com.ybyb.zzq.freecartoon.ui.detail;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.BasePresenter;
import com.ybyb.zzq.freecartoon.base.BaseView;
import com.ybyb.zzq.freecartoon.response.detail.ChapterCartoon;

/**
 * 找回密码契约类，MVP规范
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public interface DetailContract {
    interface View extends BaseView {

        /**
         * 查询成功
         */
        void queryDetailSuccess(ChapterCartoon data);

        /**
         * 查询漫画分类失败
         */
        void queryDetailError();
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
         * 查询漫画单章节信息
         */
        public abstract void queryDetail(String key, String comicName, int id);
    }
}
