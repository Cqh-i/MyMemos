package com.mymemosproject.www.mymemos.Utils;
import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*
 *@package:com.mymemosproject.www.mymemos.Utils;
 *@description:活动管理器
 *@author: create by Cqh_i on 2018/11/10 15:03
 */
public class ActivityCollector {
    public static List<Activity>activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

}

