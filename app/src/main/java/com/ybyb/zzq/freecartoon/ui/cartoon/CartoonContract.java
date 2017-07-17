package com.ybyb.zzq.freecartoon.ui.cartoon;

import android.content.Context;
import com.ybyb.zzq.freecartoon.base.BasePresenter;
import com.ybyb.zzq.freecartoon.base.BaseView;
import com.ybyb.zzq.freecartoon.response.survey.CartoonData;

/**
 * 找回密码契约类，MVP规范
 *
 * 作者：周正权 on 2017/1/19
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public interface CartoonContract {
    interface View extends BaseView {

        /**
         * 查询成功
         */
        void queryDataListOk(CartoonData result);

        /**
         * 验证绑定信息失败
         */
        void queryDataListError();
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
         *  一般查询漫画分类
         *
         */
        public abstract void queryDataList(String key,String type);
        /**
         * 跳转查询漫画分类
         *
         */
        public abstract void queryDataListSkip(String key,String type,int skip);
    }
}
