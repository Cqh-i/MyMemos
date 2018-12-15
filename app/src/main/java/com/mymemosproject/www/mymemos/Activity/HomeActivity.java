package com.mymemosproject.www.mymemos.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.mymemosproject.www.mymemos.Fragment.AllmemosFragment;
import com.mymemosproject.www.mymemos.Fragment.SomedayFragment;
import com.mymemosproject.www.mymemos.Fragment.TodayFragment;
import com.mymemosproject.www.mymemos.R;
import com.mymemosproject.www.mymemos.Utils.ActivityCollector;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private DrawerLayout mDrawerLayout;//滑动菜单
    private FloatingActionButton fab;
    private BottomNavigationBar bottomNavigationBar;//底部导航栏
    int DefaultSelectedPosition = 2;//底部导航栏的默认选项
    private TodayFragment todayFragment;
    private SomedayFragment somedayFragment;
    private AllmemosFragment allmemosFragment;
    public static HomeActivity allContentActivity;
    private long backPressFirst = 0;//第一次按下返回键的时间
    private int BottomNavigation_fragment_position;//记录Fragment位置
    private String time;//记录选取的时间
    private FragmentTransaction transaction1;//开启事务
    private TextView title_top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        allContentActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//标题栏

        //fab = (FloatingActionButton) findViewById(R.id.add_memos);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title_top = findViewById(R.id.title_text);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//滑动菜单
        NavigationView navView = findViewById(R.id.nav_view);
        View headview = navView.inflateHeaderView(R.layout.nav_header);
        CircleImageView civ = headview.findViewById(R.id.icon_image);
        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_all:
                        title_top.setText("MyMemos");
                        break;
                    case R.id.nav_work:
                        title_top.setText("MyMemos: 工作");
                        break;
                    case R.id.nav_life:
                        title_top.setText("MyMemos: 生活");
                        break;
                    case R.id.nav_study:
                        title_top.setText("MyMemos: 学习");
                        break;
                    case R.id.about_mymemos:
                        Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.exit_mymemos:
                        ActivityCollector.finishAll();
                        break;
                    default:
                        break;
                }
                onStart();
                Thread myThread=new Thread(){
                    @Override
                    public void run() {
                        try{
                            sleep(40);
                            mDrawerLayout.closeDrawers();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                myThread.start();
                return true;
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);//激活导航栏按钮
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_white_36dp);
        }
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.baseline_select_all_white_48, "全部").setActiveColor(Color.parseColor("#3F51B5")))
                .addItem(new BottomNavigationItem(R.mipmap.baseline_date_range_white_48, "日期").setActiveColor(Color.parseColor("#3F51B5")))
                .addItem(new BottomNavigationItem(R.mipmap.baseline_today_white_48, "今日").setActiveColor(Color.parseColor("#3F51B5")))
                .setFirstSelectedPosition(DefaultSelectedPosition)
                .initialise();
        setDefaultFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if(BottomNavigation_fragment_position == 2){
            todayFragment = TodayFragment.newInstance();
            transaction.replace(R.id.to_fragment, todayFragment);
        }
        if(BottomNavigation_fragment_position == 0){
            allmemosFragment = AllmemosFragment.newInstance();
            transaction.replace(R.id.to_fragment, allmemosFragment);
        }
        if(BottomNavigation_fragment_position == 1){
            somedayFragment = SomedayFragment.newInstance();
            transaction.replace(R.id.to_fragment, somedayFragment);
        }
        transaction.commit();
        //Log.d("mymemostest", "onStart: ");
    }



    //菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://导航栏按钮
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                break;
        }
        return true;
    }

    private void setDefaultFragment() {
        BottomNavigation_fragment_position = 2;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        todayFragment = TodayFragment.newInstance();
        transaction.replace(R.id.to_fragment, todayFragment);
        transaction.commit();
    }

    public void onTabSelected(int position) {
        //Log.d("123", "onTabSelected() called with: " + "position = [" + position + "]");
        BottomNavigation_fragment_position = position;
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        transaction1 = fm.beginTransaction();
        switch (position) {
            case 0:
                if (allmemosFragment == null) {
                    allmemosFragment = AllmemosFragment.newInstance();
                }
                transaction1.replace(R.id.to_fragment, allmemosFragment);
                break;
            case 1:
                if (somedayFragment == null) {
                    somedayFragment = SomedayFragment.newInstance();
                }
                transaction1.replace(R.id.to_fragment, somedayFragment);
                break;
                                /*if (somedayFragment == null) {
                    time = "2018-11-09";
                    somedayFragment = SomedayFragment.newInstance(time);
                }
                somedayFragment.setSomeday_date(time);
                transaction1.replace(R.id.to_fragment, somedayFragment);*/
            case 2:
                if (todayFragment == null) {
                    todayFragment = TodayFragment.newInstance();
                }
                transaction1.replace(R.id.to_fragment, todayFragment);
                break;
            default:
                break;
        }
        // 事务提交
        transaction1.commit();
    }

    public void onTabUnselected(int position) {
        //Log.d("123", "onTabUnselected() called with: " + "position = [" + position + "]");
        switch (position) {
            case 1:
                setBottomNavigationItem(bottomNavigationBar, "日期");
                break;
            default:
                break;
        }
    }

    public void onTabReselected(int position) {
        //Log.d("againsomeday", "onTabReselected: ");
        BottomNavigation_fragment_position = position;
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        transaction1 = fm.beginTransaction();
        switch (position) {
            case 1:
                somedayFragment = SomedayFragment.newInstance();
                transaction1.replace(R.id.to_fragment, somedayFragment);
                break;
            default:
                break;
        }
        transaction1.commit();
    }

    public BottomNavigationBar getBottomNavigationBar() {
        return bottomNavigationBar;
    }

    public TextView getTitle_top() {
        return title_top;
    }
    /*
        通过反射修改底部导航栏的信息
     */
    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, String data_text){
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        //Log.d("fieldstest", "setBottomNavigationItem: "+fields.length);
        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.getName().equals("mTabContainer")) {
                try {
                    //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                    View view = mTabContainer.getChildAt(1);
                    TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                    labelView.setText(data_text);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long backPressSecond = System.currentTimeMillis();
                if (backPressSecond - backPressFirst > 2000) { //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this,"再按一次返回键退出应用", Toast.LENGTH_SHORT).show();
                    backPressFirst = backPressSecond;//更新firstTime
                    return true;
                } else {  //两次按键小于2秒时，退出应用
                    ActivityCollector.finishAll();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
