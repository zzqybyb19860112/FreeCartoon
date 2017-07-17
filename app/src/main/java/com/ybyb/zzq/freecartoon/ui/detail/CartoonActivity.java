package com.ybyb.zzq.freecartoon.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;
import com.ybyb.zzq.freecartoon.App;
import com.ybyb.zzq.freecartoon.AppConstants;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.base.BaseActivity;
import com.ybyb.zzq.freecartoon.response.detail.ChapterCartoon;
import com.ybyb.zzq.freecartoon.response.detail.DetailCartoon;
import com.ybyb.zzq.freecartoon.response.history.History;
import com.ybyb.zzq.freecartoon.ui.adapter.GalleryAdapter;
import com.ybyb.zzq.freecartoon.ui.main.MainActivity;
import io.realm.Realm;
import io.realm.RealmList;

public class CartoonActivity extends BaseActivity<DetailPresenter> implements DetailContract.View, View.OnTouchListener, GestureDetector.OnGestureListener {
    private Realm mRealm;
    private String mComicName;
    private int mId;
    private ImageView mMainIv;
    private Gallery mReviewGl;
    GestureDetector mGestureDetector;
    private static final int FLING_MIN_DISTANCE = 50;
    private static final int FLING_MIN_VELOCITY = 0;
    private int mPostion;
    private int mMax;
    private SlidingMenu menu;
    public static boolean mIsSeekTo=false;

    /**
     * 启动CartoonActivity
     */
    public static void start(Context context, String comicName, int id) {
        Intent intent = new Intent(context, CartoonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("comicName", comicName);
        bundle.putInt("id", id);
        intent.putExtra("data", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        this.mMainIv.setOnTouchListener(this);
        this.mReviewGl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPostion = position;
                String url = ((DetailCartoon) mReviewGl.getItemAtPosition(position)).getImage();
                Picasso.with(CartoonActivity.this).load(url).into(mMainIv);
            }
        });
        TextView homeTv = (TextView) this.menu.findViewById(R.id.home_tv);
        TextView confirmTv = (TextView) this.menu.findViewById(R.id.confirm_tv);
        final EditText skipEt = (EditText) this.menu.findViewById(R.id.skip_et);
        homeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartoonActivity.this, MainActivity.class);
                CartoonActivity.this.startActivity(intent);
                finish();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int skip = Integer.valueOf(skipEt.getText().toString());
                if (skip < mMax && skip >= 0) {
                    DetailCartoon c = ((DetailCartoon) mReviewGl.getItemAtPosition(skip));
                    mReviewGl.setSelection(skip);
                    Picasso.with(CartoonActivity.this).load(c.getImage()).into(mMainIv);
                }
                else {
                    Toast.makeText(CartoonActivity.this, "页码输入错误，请检查！", Toast.LENGTH_SHORT).show();
                }
                skipEt.setHint("跳转到：");
                skipEt.setText("");
            }
        });
    }

    @Override
    protected void initData() {
        this.mGestureDetector = new GestureDetector(this);
        this.mRealm = App.mRealm;
        History history = this.mRealm.where(History.class).findFirst();
        if (this.mIsSeekTo){
            this.mComicName = getIntent().getBundleExtra("data").getString("comicName");
            this.mId = getIntent().getBundleExtra("data").getInt("id");
            super.mPresenter.queryDetail(AppConstants.APP_KEY, this.mComicName, this.mId);
        }else {
            if (history != null) {
                Toast.makeText(this, "上次您看到这个位置，系统已为您自动跳转！", Toast.LENGTH_SHORT).show();
                RealmList<DetailCartoon> list = history.getCartoon().getImageList();
                int last = history.getLastSee();
                this.mMax = list.size() - 1;
                String url = list.get(last).getImage();
                Picasso.with(this).load(url).into(mMainIv);
                GalleryAdapter adapter = new GalleryAdapter(this, list);
                this.mReviewGl.setAdapter(adapter);
                this.mReviewGl.setSelection(last);
            }
            else {
                this.mComicName = getIntent().getBundleExtra("data").getString("comicName");
                this.mId = getIntent().getBundleExtra("data").getInt("id");
                super.mPresenter.queryDetail(AppConstants.APP_KEY, this.mComicName, this.mId);
            }
        }
    }

    @Override
    protected void bindViews() {
        this.mMainIv = (ImageView) findViewById(R.id.main_iv);
        this.mReviewGl = (Gallery) findViewById(R.id.review_gl);
        this.mMainIv.setLongClickable(true);
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.LEFT);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.color.colorAccent);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        menu.setBehindWidth(300);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.left_menu);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_cartoon;
    }

    @Override
    protected void onDestroy() {
        this.mRealm.beginTransaction();
        History history = this.mRealm.where(History.class).findFirst();
        history.setLastSee(this.mPostion);
        this.mRealm.commitTransaction();
        super.onDestroy();
    }

    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter(this, this);
    }

    @Override
    public void queryDetailSuccess(ChapterCartoon data) {
        RealmList<DetailCartoon> list = data.getImageList();
        this.mMax = list.size() - 1;
        String url = list.get(0).getImage();
        Picasso.with(this).load(url).into(mMainIv);
        GalleryAdapter adapter = new GalleryAdapter(this, list);
        this.mReviewGl.setAdapter(adapter);
        this.mRealm.beginTransaction();
        History history = this.mRealm.createObject(History.class);
        ChapterCartoon cartoon = this.mRealm.createObject(ChapterCartoon.class);
        RealmList<DetailCartoon> result = new RealmList<>();
        for (DetailCartoon c1 : data.getImageList()) {
            DetailCartoon detailCartoon = this.mRealm.createObject(DetailCartoon.class);
            detailCartoon.setId(c1.getId());
            detailCartoon.setImage(c1.getImage());
            result.add(detailCartoon);
        }
        cartoon.setImageList(result);
        history.setCartoon(cartoon);
        this.mRealm.commitTransaction();
    }

    @Override
    protected void onUserLeaveHint() {
        this.mRealm.beginTransaction();
        History history = this.mRealm.where(History.class).findFirst();
        history.setLastSee(this.mPostion);
        this.mRealm.commitTransaction();
        super.onUserLeaveHint();
    }

    @Override
    public void queryDetailError() {
        Toast.makeText(this, "网络错误，请稍后重试！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!menu.isMenuShowing()) {
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                if (this.mPostion != this.mMax) {
                    String url = ((DetailCartoon) this.mReviewGl.getItemAtPosition(this.mPostion + 1)).getImage();
                    Picasso.with(CartoonActivity.this).load(url).into(this.mMainIv);
                    this.mPostion = this.mPostion + 1;
                    this.mReviewGl.setSelection(this.mPostion);
                }
                else {
                    Toast.makeText(this, "已经是最后一页了", Toast.LENGTH_SHORT).show();//向左
                }
            }
            else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                if (this.mPostion != 0) {
                    String url = ((DetailCartoon) this.mReviewGl.getItemAtPosition(this.mPostion - 1)).getImage();
                    Picasso.with(CartoonActivity.this).load(url).into(this.mMainIv);
                    this.mPostion = this.mPostion - 1;
                    this.mReviewGl.setSelection(this.mPostion);
                }
                else {
                    Toast.makeText(this, "已经是第一页了", Toast.LENGTH_SHORT).show();//向右
                }
            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
