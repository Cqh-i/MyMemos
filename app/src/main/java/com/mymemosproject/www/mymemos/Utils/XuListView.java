package com.mymemosproject.www.mymemos.Utils;

/*
 *@package:com.mymemosproject.www.mymemos.Utils
 *@description:
 *@author: create by Cqh_i on 2018/12/8 14:03
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 模仿滚轮动画缩放的ListView
 * Created by xu on 2017/3/3.
 */
public class XuListView extends ListView implements AbsListView.OnScrollListener {
    private static final String TAG = "XuListView";

    /**
     * 中点的Y坐标
     */
    private float centerY = 0f;

    public XuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置一个滚动监听
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //计算中点
        centerY = getHeight()/2;
        //判断中点的有效性
        if(centerY <= 0){
            return;
        }
        //开始对当前显示的View进行缩放
        for(int i = 0; i < visibleItemCount; i++){
            //获取item
            View temp_view = getChildAt(i);
            //计算item的中点Y坐标
            float itemY = temp_view.getBottom()-(temp_view.getHeight()/2);
            //计算离中点的距离
            float distance = centerY;
            if(itemY > centerY){
                distance = itemY - centerY;
            }else{
                distance = centerY - itemY;
            }
            //根据距离进行缩放
            temp_view.setScaleY(1.1f - (distance / centerY) < 0 ? 0 : 1.1f - (distance / centerY));
            temp_view.setScaleX(1.1f - (distance / centerY) < 0 ? 0 : 1.1f - (distance / centerY));
            //根据距离改变透明度
            temp_view.setAlpha(1.1f - (distance / centerY) < 0 ? 0 : 1.1f - (distance / centerY));
        }
    }
}

