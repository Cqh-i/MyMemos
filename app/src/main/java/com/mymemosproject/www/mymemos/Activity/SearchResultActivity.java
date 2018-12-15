package com.mymemosproject.www.mymemos.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mymemosproject.www.mymemos.Adapter.Memos_Item_Adapter;
import com.mymemosproject.www.mymemos.DB_Manager.Bean.List_Memos_Content;
import com.mymemosproject.www.mymemos.DB_Manager.Bean.Memos;
import com.mymemosproject.www.mymemos.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private ListView listView;
    private Memos_Item_Adapter myAdapter;
    private List<List_Memos_Content> mList;
    private List<Memos> memos1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        memos1 = new ArrayList<>();
        mList = new ArrayList<>();
        listView = findViewById(R.id.memos_search_listview);
        mList = (List<List_Memos_Content>)getIntent().getSerializableExtra("mList");
        memos1 = (List<Memos>)getIntent().getSerializableExtra("memos1");
        myAdapter = new Memos_Item_Adapter(SearchResultActivity.this, R.layout.view_list_item, mList, 1);
        listView.setAdapter(myAdapter);
        listView.setClipToPadding(false);
        listView.setClipChildren(false);
        listview_addlistener();
        Log.d("result_test", "onCreate: ");
    }

    /*
    为Listview设置监听器
     */
    public void listview_addlistener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultActivity.this, EditActivity.class);
                Memos memos = null;
                if (memos1 != null) memos = memos1.get(position);
                intent.putExtra("memos_edit_data", memos);
                Log.d("memos1_info_test", "onItemClick: "+position);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
