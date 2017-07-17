package com.ybyb.zzq.freecartoon.ui.look;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.BasePresenter;
import com.ybyb.zzq.freecartoon.base.BaseView;
import com.ybyb.zzq.freecartoon.response.survey.ChapterData;

/**
 * 找回密码契约类，MVP规范
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public interface ChapterContract {
    interface View extends BaseView {

        /**
         * 查询成功
         */
        void queryChaptersSuccess(ChapterData data);

        /**
         * 查询漫画分类失败
         */
        void queryChaptersError();
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
         * 查询漫画章节信息
         *
         */
        public abstract void queryChapters(String key,String comicName);
        /**
         * 跳转查询漫画章节信息
         *
         */
        public abstract void queryChaptersSkip(String key,String comicName,int skip);
    }
}
