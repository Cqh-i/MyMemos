package com.mymemosproject.www.mymemos.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mymemosproject.www.mymemos.Activity.HomeActivity;
import com.mymemosproject.www.mymemos.R;

import org.litepal.LitePal;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv;
    private MyCountDownTimer mc;
    private Handler handler=new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_welcome);
        tv = findViewById(R.id.tv_time);
        tv.setOnClickListener(this);
        mc = new MyCountDownTimer(3000, 1000);
        mc.start();
        handler.postDelayed(runnable = new Runnable() {

            @Override
            public void run() {
                Intent intent=new Intent(WelcomeActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, 3000);
/*        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent it=new Intent(getApplicationContext(),HomeActivity.class);//启动MainActivity
                    startActivity(it);
                    finish();//关闭当前活动
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程*/
        Thread createDB=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    LitePal.getDatabase();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        createDB.start();//启动线程

    }

    /**
     * 点击跳过
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            tv.setText("正在跳转");
        }

        public void onTick(long millisUntilFinished) {
            tv.setText("倒计时(" + millisUntilFinished / 1000 + ")");
        }

    }
}
