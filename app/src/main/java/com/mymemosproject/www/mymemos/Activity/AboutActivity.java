package com.mymemosproject.www.mymemos.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mymemosproject.www.mymemos.R;

public class AboutActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView textView;
    private TextView tv_history_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.edit_title);
        toolbar.setTitle("关于");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        textView = findViewById(R.id.mail_about);
        textView.setText(Html.fromHtml("<a href='http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=5YaUjbqMpYOKnYiEjInLhoqI'>\t\tcqh_i@foxmail.com</a>"));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        tv_history_info = findViewById(R.id.history_info_tv);
        tv_history_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, HistoryInfoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
