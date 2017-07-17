package com.ybyb.zzq.freecartoon.ui.cartoon.usa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ybyb.zzq.freecartoon.App;
import com.ybyb.zzq.freecartoon.AppConstants;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.base.BaseActivity;
import com.ybyb.zzq.freecartoon.response.survey.Cartoon;
import com.ybyb.zzq.freecartoon.response.survey.CartoonData;
import com.ybyb.zzq.freecartoon.response.survey.CartoonOverView;
import com.ybyb.zzq.freecartoon.response.survey.MainData;
import com.ybyb.zzq.freecartoon.ui.adapter.ListRvAdapter;
import com.ybyb.zzq.freecartoon.ui.adapter.RealmRvAdapter;
import com.ybyb.zzq.freecartoon.ui.cartoon.CartoonContract;
import com.ybyb.zzq.freecartoon.ui.cartoon.CartoonPresenter;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

public class USACartoonActivity extends BaseActivity<CartoonPresenter> implements CartoonContract.View {
    private Realm mRealm;
    private Button mBeforeBtn;
    private Button mAfterBtn;
    private TextView mTitleTv;
    private RecyclerView mCartoonRv;
    private int mStartNumber = 1;
    private int mEndNumber = 20;
    private int mTotalNumber = 0;
    private int mSkip = 0;
    private RealmList<CartoonOverView> mList;
    private String mType;

    /**
     * 启动USACartoonActivity
     */
    public static void start(Context context, String type) {
        Intent intent = new Intent(context, USACartoonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cartoon_type", type);
        intent.putExtra("data", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        this.mBeforeBtn.setOnClickListener(this);
        this.mAfterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.before_btn:
                if (this.mStartNumber != 1) {
                    if (this.mStartNumber == 21) {
                        this.mSkip -= 20;
                        List<CartoonOverView> result = queryFromLocal(this.mSkip, this.mSkip + 20);
                        if (result != null && result.size() != 0) {
                            setRvShow(result);
                        }
                        else {
                            super.mPresenter.queryDataList(AppConstants.APP_KEY, this.mType);
                        }
                    }
                    else {
                        this.mSkip -= 20;
                        List<CartoonOverView> result = queryFromLocal(this.mSkip, this.mSkip + 20);
                        if (result != null && result.size() != 0) {
                            setRvShow(result);
                        }
                        else {
                            super.mPresenter.queryDataListSkip(AppConstants.APP_KEY, this.mType, this.mSkip);
                        }
                    }
                    this.mStartNumber -= 20;
                    this.mEndNumber = (this.mStartNumber + 20 > this.mTotalNumber) ? (this.mTotalNumber) : (this.mStartNumber + 19);
                }
                else {
                    Toast.makeText(this, "已经是第一页了！", Toast.LENGTH_SHORT).show();
                }
                this.mTitleTv.setText("第" + this.mStartNumber + "-" + this.mEndNumber + "条/" + this.mTotalNumber + "条");
                break;
            case R.id.after_btn:
                if (this.mEndNumber != this.mTotalNumber) {
                    this.mSkip += 20;
                    List<CartoonOverView> result = queryFromLocal(this.mSkip, this.mSkip + 20);
                    if (result != null && result.size() != 0) {
                        setRvShow(result);
                    }
                    else {
                        super.mPresenter.queryDataListSkip(AppConstants.APP_KEY, this.mType, this.mSkip);
                    }
                    this.mStartNumber += 20;
                    this.mEndNumber = (this.mStartNumber + 20 > this.mTotalNumber) ? (this.mTotalNumber) : (this.mStartNumber + 19);
                }
                else {
                    Toast.makeText(this, "已经是最后一页了！", Toast.LENGTH_SHORT).show();
                }
                this.mTitleTv.setText("第" + this.mStartNumber + "-" + this.mEndNumber + "条/" + this.mTotalNumber + "条");
                break;
        }
    }

    private void setRvShow(List<CartoonOverView> result) {
        ListRvAdapter adapter = new ListRvAdapter(this, result);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        this.mCartoonRv.setLayoutManager(manager);
        this.mCartoonRv.setAdapter(adapter);
    }

    private List<CartoonOverView> queryFromLocal(int start, int end) {
        MainData data = this.mRealm.where(MainData.class).findAll().get(0);
        CartoonData abc = data.getCartoonList().get(3).getData();
        if (abc!=null){
            this.mTotalNumber=abc.getTotal();
            this.mTitleTv.setText("第" + this.mStartNumber + "-" + this.mEndNumber + "条/" + this.mTotalNumber + "条");
        }
        if (abc != null) {
            RealmList<CartoonOverView> list = abc.getBookList();
            if (list.size() != 0) {
                List<CartoonOverView> temp = new ArrayList<>();
                int j = (end <= list.size()) ? end : list.size();
                for (int i = start; i < j; i++) {
                    temp.add(list.get(i));
                }
                return temp;
            }
            return null;
        }
        else {
            return null;
        }
    }

    @Override
    protected void initData() {
        this.mRealm = App.mRealm;
        this.mType = getIntent().getBundleExtra("data").getString("cartoon_type");
        List<CartoonOverView> result = queryFromLocal(this.mSkip, this.mSkip + 20);
        if (result != null) {
            setRvShow(result);
        }
        else {
            super.mPresenter.queryDataList(AppConstants.APP_KEY, mType);
        }
    }

    @Override
    protected void bindViews() {
        this.mBeforeBtn = (Button) findViewById(R.id.before_btn);
        this.mAfterBtn = (Button) findViewById(R.id.after_btn);
        this.mTitleTv = (TextView) findViewById(R.id.title_tv);
        this.mCartoonRv = (RecyclerView) findViewById(R.id.cartoon_rv);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_usacartoon;
    }

    @Override
    protected CartoonPresenter createPresenter() {
        return new CartoonPresenter(this, this);
    }

    @Override
    public void queryDataListOk(final CartoonData result) {
        this.mTotalNumber = result.getTotal();
        int end = (this.mEndNumber > this.mTotalNumber) ? this.mTotalNumber : this.mEndNumber;
        this.mTitleTv.setText("第" + this.mStartNumber + "-" + end + "条/" + this.mTotalNumber + "条");
        this.mList = result.getBookList();
        RealmRvAdapter adapter = new RealmRvAdapter(this, this.mList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        this.mCartoonRv.setLayoutManager(manager);
        this.mCartoonRv.setAdapter(adapter);
        RealmResults<MainData> datas = this.mRealm.where(MainData.class).findAll();
        final MainData data = datas.get(0);
        RealmList<Cartoon> dataList = data.getCartoonList();
        final Cartoon cartoon = dataList.get(3);
        this.mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartoonData data1 = realm.createObject(CartoonData.class);
                RealmList<CartoonOverView> list = new RealmList<>();
                for (CartoonOverView view : result.getBookList()) {
                    CartoonOverView view1 = realm.createObject(CartoonOverView.class);
                    view1.setName(view.getName());
                    view1.setType(view.getType());
                    view1.setArea(view.getArea());
                    view1.setDes(view.getDes());
                    view1.setCoverImg(view.getCoverImg());
                    view1.setLastUpdate(view.getLastUpdate());
                    view1.setFinish(view.isFinish());
                    list.add(view1);
                }
                data1.setBookList(list);
                data1.setLimit(result.getLimit());
                data1.setTotal(result.getTotal());
                cartoon.setData(data1);
            }
        });
    }

    @Override
    public void queryDataListError() {
        Toast.makeText(this, "网络错误，请稍后重试！", Toast.LENGTH_SHORT).show();
    }
}
