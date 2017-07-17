package com.ybyb.zzq.freecartoon.ui.look;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.ybyb.zzq.freecartoon.App;
import com.ybyb.zzq.freecartoon.AppConstants;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.base.BaseActivity;
import com.ybyb.zzq.freecartoon.response.detail.Book;
import com.ybyb.zzq.freecartoon.response.detail.ChapterDetail;
import com.ybyb.zzq.freecartoon.response.survey.Chapter;
import com.ybyb.zzq.freecartoon.response.survey.ChapterData;
import com.ybyb.zzq.freecartoon.ui.adapter.ListGridAdapter;
import com.ybyb.zzq.freecartoon.ui.adapter.RealmGridAdapter;
import com.ybyb.zzq.freecartoon.ui.detail.CartoonActivity;
import io.realm.Realm;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends BaseActivity<ChapterPresenter> implements ChapterContract.View {
    private TextView mNameTv;
    private Button mBeforeBtn;
    private Button mAfterBtn;
    private TextView mTitleTv;
    private GridView mChapterGv;
    private int mStartNumber = 1;
    private int mEndNumber = 20;
    private int mTotalNumber = 0;
    private int mSkip = 0;
    private String mComicName;
    private Realm mRealm;

    /**
     * 启动GirlCartoonActivity
     */
    public static void start(Context context, String comicName) {
        Intent intent = new Intent(context, ChapterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("comicName", comicName);
        intent.putExtra("data", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        this.mBeforeBtn.setOnClickListener(this);
        this.mAfterBtn.setOnClickListener(this);
        this.mChapterGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter= (Chapter) mChapterGv.getItemAtPosition(position);
                CartoonActivity.mIsSeekTo=true;
                CartoonActivity.start(ChapterActivity.this, mComicName, (int) chapter.getId());
            }
        });
    }

    @Override
    protected void initData() {
        this.mComicName = getIntent().getBundleExtra("data").getString("comicName");
        this.mRealm = App.mRealm;
        List<Chapter> result = queryFromLocal(this.mSkip, this.mSkip + 20,this.mComicName);
        if (result != null) {
            setRvShow(result);
        }
        else {
            super.mPresenter.queryChapters(AppConstants.APP_KEY, this.mComicName);
        }
    }

    private void setRvShow(List<Chapter> result) {
        this.mNameTv.setText(this.mComicName);
        ListGridAdapter adapter = new ListGridAdapter(this, result);
        this.mChapterGv.setNumColumns(2);
        this.mChapterGv.setAdapter(adapter);
    }

    @Override
    protected void bindViews() {
        this.mNameTv = (TextView) findViewById(R.id.name_tv);
        this.mBeforeBtn = (Button) findViewById(R.id.before_btn);
        this.mAfterBtn = (Button) findViewById(R.id.after_btn);
        this.mTitleTv = (TextView) findViewById(R.id.title_tv);
        this.mChapterGv = (GridView) findViewById(R.id.chapter_gv);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chapter;
    }

    @Override
    protected ChapterPresenter createPresenter() {
        return new ChapterPresenter(this, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.before_btn:
                if (this.mStartNumber != 1) {
                    if (this.mStartNumber == 21) {
                        this.mSkip -= 20;
                        List<Chapter> result = queryFromLocal(this.mSkip, this.mSkip + 20,this.mComicName);
                        if (result != null) {
                            setGridShow(result);
                        }
                        else {
                            super.mPresenter.queryChapters(AppConstants.APP_KEY, this.mComicName);
                        }
                    }
                    else {
                        this.mSkip -= 20;
                        List<Chapter> result = queryFromLocal(this.mSkip, this.mSkip + 20,this.mComicName);
                        if (result != null) {
                            setGridShow(result);
                        }
                        else {
                            super.mPresenter.queryChaptersSkip(AppConstants.APP_KEY, this.mComicName, this.mSkip);
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
                    List<Chapter> result = queryFromLocal(this.mSkip, this.mSkip + 20,this.mComicName);
                    if (result != null) {
                        setGridShow(result);
                    }
                    else {
                        super.mPresenter.queryChaptersSkip(AppConstants.APP_KEY, this.mComicName, this.mSkip);
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

    private void setGridShow(List<Chapter> result) {
        ListGridAdapter adapter = new ListGridAdapter(this, result);
        this.mChapterGv.setNumColumns(2);
        this.mChapterGv.setAdapter(adapter);
    }

    private List<Chapter> queryFromLocal(int start, int end,String name) {
        Book book = this.mRealm.where(Book.class).findFirst();
        if (book != null) {
            RealmList<ChapterDetail> list = book.getChapters();
            if (!name.equals(book.getName())){
                return null;
            }
            this.mTotalNumber=book.getChapters().size();
            this.mTitleTv.setText("第" + this.mStartNumber + "-" + this.mEndNumber + "条/" + this.mTotalNumber + "条");
            if (list.size() != 0) {
                List<Chapter> result = new ArrayList<>();
                int j = (end <= list.size()) ? end : list.size();
                for (int i = start; i < j; i++) {
                    Chapter chapter=new Chapter();
                    chapter.setId(list.get(i).getId());
                    chapter.setName(list.get(i).getName());
                    result.add(chapter);
                }
                return result;
            }
            else {
                return null;
            }
        }
        return null;
    }

    @Override
    public void queryChaptersSuccess(ChapterData data) {
        this.mRealm.beginTransaction();
        Book book = this.mRealm.createObject(Book.class);
        book.setName(this.mComicName);
        for (Chapter chapter : data.getChapterList()) {
            ChapterDetail detail = this.mRealm.createObject(ChapterDetail.class);
            detail.setName(chapter.getName());
            detail.setId(chapter.getId());
            book.getChapters().add(detail);
        }
        this.mRealm.commitTransaction();
        this.mNameTv.setText(this.mComicName);
        this.mTotalNumber = data.getTotal();
        int end = (this.mEndNumber > this.mTotalNumber) ? this.mTotalNumber : this.mEndNumber;
        this.mTitleTv.setText("第" + this.mStartNumber + "-" + end + "条/" + this.mTotalNumber + "条");
        RealmList<Chapter> list = data.getChapterList();
        RealmGridAdapter adapter = new RealmGridAdapter(this, list);
        this.mChapterGv.setNumColumns(2);
        this.mChapterGv.setAdapter(adapter);
    }

    @Override
    public void queryChaptersError() {
        Toast.makeText(this, "网络错误，请稍后重试！", Toast.LENGTH_SHORT).show();
    }
}
