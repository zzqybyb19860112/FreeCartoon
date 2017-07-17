package com.ybyb.zzq.freecartoon.ui.main;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.ybyb.zzq.freecartoon.App;
import com.ybyb.zzq.freecartoon.AppConstants;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.base.BaseActivity;
import com.ybyb.zzq.freecartoon.response.survey.Cartoon;
import com.ybyb.zzq.freecartoon.response.survey.MainData;
import com.ybyb.zzq.freecartoon.ui.cartoon.child.ChildCartoonActivity;
import com.ybyb.zzq.freecartoon.ui.cartoon.girl.GirlCartoonActivity;
import com.ybyb.zzq.freecartoon.ui.cartoon.usa.USACartoonActivity;
import com.ybyb.zzq.freecartoon.ui.cartoon.young.YoungCartoonActivity;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private List<TextView> mTvList;
    private Realm mRealm;

    @Override
    protected void bindViews() {
        this.mTvList = new ArrayList<>();
        TextView mTypeTv1 = (TextView) findViewById(R.id.type_tv1);
        TextView mTypeTv2 = (TextView) findViewById(R.id.type_tv2);
        TextView mTypeTv3 = (TextView) findViewById(R.id.type_tv3);
        TextView mTypeTv4 = (TextView) findViewById(R.id.type_tv4);
        this.mTvList.add(mTypeTv1);
        this.mTvList.add(mTypeTv2);
        this.mTvList.add(mTypeTv3);
        this.mTvList.add(mTypeTv4);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        for (TextView tv : this.mTvList) {
            tv.setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {
        this.mRealm = App.mRealm;
        RealmResults<MainData> datas = mRealm.where(MainData.class).findAll();
        if (datas.size() != 0) {
            MainData data = datas.get(0);
            List<Cartoon> cartoons = data.getCartoonList();
            for (int i = 0; i < cartoons.size(); i++) {
                this.mTvList.get(i).setText(cartoons.get(i).getType());
            }
        }
        else {
            super.mPresenter.queryTypes(AppConstants.APP_KEY);
        }
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    public void queryDataSuccess(List<String> data) {
        if (data.size() != 0) {
            for (int i = 0; i < data.size(); i++) {
                this.mTvList.get(i).setText(data.get(i));
            }
            this.mRealm.beginTransaction();
            MainData main = this.mRealm.createObject(MainData.class);
            for (String result : data) {
                Cartoon cartoon = this.mRealm.createObject(Cartoon.class);
                cartoon.setType(result);//前一个界面， 查询到漫画的主分类，总共四种：少年，青年，少女，耽美，但是没有设置具体的漫画数据
                main.getCartoonList().add(cartoon);
            }
            this.mRealm.commitTransaction();
        }
        else {
            Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.type_tv1:
                ChildCartoonActivity.start(this, this.mTvList.get(0).getText().toString());
                break;
            case R.id.type_tv2:
                YoungCartoonActivity.start(this, this.mTvList.get(1).getText().toString());
                break;
            case R.id.type_tv3:
                GirlCartoonActivity.start(this, this.mTvList.get(2).getText().toString());
                break;
            case R.id.type_tv4:
                USACartoonActivity.start(this, this.mTvList.get(3).getText().toString());
                break;
        }
    }

    @Override
    public void queryDataError() {
        Toast.makeText(this, "未知错误,请稍候重试！", Toast.LENGTH_SHORT).show();
    }
}
