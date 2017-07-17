package com.ybyb.zzq.freecartoon.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 基础的activity
 *
 * @author 周正权
 * @date 2017-1-13
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPresenter = this.createPresenter();
        super.setContentView(this.getContentViewId());
        this.bindViews();
        this.initData();
        this.initListener();
    }

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void bindViews();

    protected abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mPresenter != null) {
            this.mPresenter.unSubscribe();
        }
    }

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P createPresenter();


    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }
}
