package com.mymemosproject.www.mymemos.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mymemosproject.www.mymemos.Utils.ActivityCollector;

/*
 *@package:com.mymemosproject.www.mymemos
 *@description:自定义Activity基类
 *@author: create by Cqh_i on 2018/11/10 14:52
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
