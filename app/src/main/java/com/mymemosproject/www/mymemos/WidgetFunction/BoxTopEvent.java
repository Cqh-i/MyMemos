package com.mymemosproject.www.mymemos.WidgetFunction;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mymemosproject.www.mymemos.Activity.EditActivity;
import com.mymemosproject.www.mymemos.Activity.HomeActivity;
import com.mymemosproject.www.mymemos.R;

/*
 *@package:com.mymemosproject.www.mymemos.WidgetFunction
 *@description:
 *@author: create by Cqh_i on 2018/11/18 10:35
 */
public class BoxTopEvent extends RelativeLayout{
    private FloatingActionButton fab;

    public BoxTopEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.box_top, BoxTopEvent.this);

        fab  = (FloatingActionButton)findViewById(R.id.add_memos);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        fab.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //按下操作
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#94f7f6")));
                    Intent intent = new Intent(HomeActivity.allContentActivity, EditActivity.class);
                    HomeActivity.allContentActivity.startActivity(intent);
                }
                //抬起操作
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                }
                //移动操作
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                }
                return true;
            }
        });
    }
}
