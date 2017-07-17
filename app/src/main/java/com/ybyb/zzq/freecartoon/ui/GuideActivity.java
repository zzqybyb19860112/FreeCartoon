package com.ybyb.zzq.freecartoon.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ybyb.zzq.freecartoon.App;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.base.BaseActivity;
import com.ybyb.zzq.freecartoon.base.BasePresenter;
import com.ybyb.zzq.freecartoon.response.history.History;
import com.ybyb.zzq.freecartoon.ui.detail.CartoonActivity;
import com.ybyb.zzq.freecartoon.ui.main.MainActivity;
import io.realm.Realm;

public class GuideActivity extends BaseActivity {
    private ImageView mHomeIv;
    private TextView mGuideTv;
    private Handler mHandler = new Handler();
    private MyCountDownTimer mCountDownTimer;
    private Runnable mTask;

    @Override
    protected void initListener() {
        this.mGuideTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        this.mCountDownTimer = new MyCountDownTimer(5000, 1000, this.mGuideTv);
        this.mCountDownTimer.start();
        this.mTask = new Runnable() {
            @Override
            public void run() {
                judgeSkip();
            }
        };
        this.mHandler.postDelayed(this.mTask, 5000);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.guide_tv) {
            judgeSkip();
            this.mHandler.removeCallbacks(this.mTask);
        }
    }

    private void judgeSkip() {
        Realm realm = App.mRealm;
        History history = realm.where(History.class).findFirst();
        if (history == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, CartoonActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void bindViews() {
        this.mGuideTv = (TextView) findViewById(R.id.guide_tv);
        this.mHomeIv = (ImageView) findViewById(R.id.home_iv);
        Glide.with(this).load(R.drawable.guide).into(new GlideDrawableImageViewTarget(this.mHomeIv, 10000));
        Log.i("tag","ok");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private class MyCountDownTimer extends CountDownTimer {

        private TextView mTextView;

        /**
         * 构造方法
         *
         * @param millisInFuture    倒计时的总时间, 单位ms
         * @param countDownInterval 倒计时的间隔时间, 单位ms
         * @param textView          倒计时的view
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String content = "欢迎进入美丽的动漫世界，" + (millisUntilFinished / 1000) + "秒后将自动进入系统，点击可快速进入";
            this.mTextView.setText(content);
        }

        @Override
        public void onFinish() {
        }
    }
}
