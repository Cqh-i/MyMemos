package com.mymemosproject.www.mymemos.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mymemosproject.www.mymemos.DB_Manager.Bean.List_Memos_Content;
import com.mymemosproject.www.mymemos.DB_Manager.Bean.Memos;
import com.mymemosproject.www.mymemos.R;
import com.mymemosproject.www.mymemos.Widget.SearchView;

import org.litepal.LitePal;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private List<List_Memos_Content> mList;
    private List<Memos> memos1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        memos1 = new ArrayList<>();
        mList = new ArrayList<>();
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                String sql = "select * from Memos where title like '%" + string +"%' or html_memo_date like '%" + string + "%'";
                Log.d("test_had_start", sql);
                Cursor cursor = LitePal.findBySQL(sql);
                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        String date_str = cursor.getString(cursor.getColumnIndex("create_date"));
                        SimpleDateFormat format;
                        Date date = null;
                        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            date = format.parse(date_str);
                        } catch (ParseException e) {
                            //Log.d("MemosTest", date_str);
                            Date dateOld = new Date(Long.valueOf(date_str));
                            String sDateTime = format.format(dateOld); // 把date类型的时间转换为string
                            try {
                                date = format.parse(sDateTime); // 把String类型转换为Date类型
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                        int state = cursor.getInt(cursor.getColumnIndex("state"));
                        String isfinshed;
                        if (state == 0) {
                            isfinshed = "未完成";
                        }
                        else {
                            isfinshed = "已完成";
                        }
                        String category = cursor.getString(cursor.getColumnIndex("category"));
                        int ImageId = 0;
                        if (category.equals("工作")) {
                            ImageId = R.drawable.work;
                        } else if (category.equals("生活")) {
                            ImageId = R.drawable.life;
                        } else if (category.equals("学习")) {
                            ImageId = R.drawable.study;
                        }
                        String html_memo_date = cursor.getString(cursor.getColumnIndex("html_memo_date"));
                        Memos memos = new Memos(title, date, state, category, html_memo_date);
                        List_Memos_Content list_memos_content = new List_Memos_Content(ImageId, title, format.format(date), html_memo_date, isfinshed);
                        if(list_memos_content != null) mList.add(list_memos_content);
                        memos1.add(memos);
                    } while (cursor.moveToNext());
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("mList", (Serializable)mList);
                    intent.putExtra("memos1", (Serializable)memos1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else{
                    Log.d("AlertDialog", "SearchAciton: ");
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("查询不到相关的Memos记录!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                //Log.d("sql_test", "SearchAciton: "+sql);
            }
        });
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
}
