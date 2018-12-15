package com.mymemosproject.www.mymemos.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mr5.icarus.Callback;
import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.TextViewToolbar;
import com.github.mr5.icarus.Toolbar;
import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.button.FontScaleButton;
import com.github.mr5.icarus.button.TextViewButton;
import com.github.mr5.icarus.entity.Options;
import com.github.mr5.icarus.popover.FontScalePopoverImpl;
import com.mymemosproject.www.mymemos.DB_Manager.Bean.Memos;
import com.mymemosproject.www.mymemos.R;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class EditActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private List<String> memo_category_list;//备忘录分类数据源
    protected Icarus icarus;//富文本编辑器
    private WebView webView;
    private EditText title_text;
    private TextView date_text;
    private SwitchCompat isfinish_switch;
    private int switch_state;
    private Spinner spinner;//分类下拉列表
    private String kind_memos;
    private String title;
    private Memos memos;
    private Memos memos_from_click;
    private Date date;
    private android.support.v7.widget.Toolbar toolbar1;
    private ArrayAdapter<String> arr_adapter;
    private Date date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initEditView();

        webView = (WebView) findViewById(R.id.editor);
        TextViewToolbar toolbar = new TextViewToolbar();
        Options options = new Options();
        if(memos_from_click == null) options.setPlaceholder("开始记录您重要的事情吧...");
        //  img: ['src', 'alt', 'width', 'height', 'data-non-image']
        // a: ['href', 'target']
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height", "data-non-image"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));

        icarus = new Icarus(toolbar, options, webView);
        prepareToolbar(toolbar, icarus);
        icarus.loadCSS("file:///android_asset/editor.css");
        icarus.loadJs("file:///android_asset/test.js");
        if(memos_from_click != null){
            String html_cont = memos_from_click.getHtml_memo_date();
            if (html_cont.equals("{\"content\":\"\"}"))options.setPlaceholder("开始记录您重要的事情吧...");
            else icarus.setContent(html_cont.substring(12, html_cont.length() - 2));
        }
        icarus.render();
    }

    private Toolbar prepareToolbar(TextViewToolbar toolbar, Icarus icarus) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "Simditor.ttf");
        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put(Button.NAME_BOLD, R.id.button_bold);
        generalButtons.put(Button.NAME_OL, R.id.button_list_ol);
        generalButtons.put(Button.NAME_BLOCKQUOTE, R.id.button_blockquote);
        generalButtons.put(Button.NAME_HR, R.id.button_hr);
        generalButtons.put(Button.NAME_UL, R.id.button_list_ul);
        generalButtons.put(Button.NAME_ALIGN_LEFT, R.id.button_align_left);
        generalButtons.put(Button.NAME_ALIGN_CENTER, R.id.button_align_center);
        generalButtons.put(Button.NAME_ALIGN_RIGHT, R.id.button_align_right);
        generalButtons.put(Button.NAME_ITALIC, R.id.button_italic);
        generalButtons.put(Button.NAME_INDENT, R.id.button_indent);
        generalButtons.put(Button.NAME_OUTDENT, R.id.button_outdent);
        generalButtons.put(Button.NAME_CODE, R.id.button_math);
        generalButtons.put(Button.NAME_UNDERLINE, R.id.button_underline);
        generalButtons.put(Button.NAME_STRIKETHROUGH, R.id.button_strike_through);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }

        TextView fontScaleTextView = (TextView) findViewById(R.id.button_font_scale);
        fontScaleTextView.setTypeface(iconfont);
        TextViewButton fontScaleButton = new FontScaleButton(fontScaleTextView, icarus);
        fontScaleButton.setPopover(new FontScalePopoverImpl(fontScaleTextView, icarus));
        toolbar.addButton(fontScaleButton);
        return toolbar;
    }

    /*
        初始化EditView
     */
    private void initEditView() {
        Intent intent = getIntent();
        memos_from_click = (Memos)intent.getSerializableExtra("memos_edit_data");
        /*Log.d("intent_memos", "initEditView: "+memos.getTitle());
        Log.d("intent_memos", "initEditView: "+memos.getCreate_date());
        Log.d("intent_memos", "initEditView: "+memos.getState());
        Log.d("intent_memos", "initEditView: "+memos.getCategory());
        Log.d("intent_memos", "initEditView: "+memos.getHtml_memo_date());*/
        toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.title_text);
        textView.setText("");
        textView.setGravity(Gravity.LEFT);

        setSupportActionBar(toolbar1);
        //这里必须将此语句放到添加监听器前面，否则监听器不起作用
        /*toolbar1.setNavigationIcon(R.drawable.baseline_cancel_white_36);//设置工具栏取消图标

        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });*/
        title_text = findViewById(R.id.title_create);
        isfinish_switch = findViewById(R.id.switch1);
        isfinish_switch.setOnCheckedChangeListener(this);
        date_text = (TextView) findViewById(R.id.date_create);

        //初始化下拉列表
        spinner = (Spinner) findViewById(R.id.Spinner1);

        //数据
        memo_category_list = new ArrayList<String>();
        memo_category_list.add("工作");
        memo_category_list.add("生活");
        memo_category_list.add("学习");

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, memo_category_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                kind_memos = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        if(memos_from_click != null){
            title_text.setText(memos_from_click.getTitle());
            Date date_text1 = memos_from_click.getCreate_date();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString1 = formatter1.format(date_text1);
            date_text.setText(dateString1);
            if(memos_from_click.getState() == 0){
                isfinish_switch.setChecked(false);
            }else{
                isfinish_switch.setChecked(true);
            }
            String kind1 = memos_from_click.getCategory();
            if(kind1.equals("工作")){
                spinner.setSelection(0, true);
            }else if(kind1.equals("生活")){
                spinner.setSelection(1, true);
            }else if(kind1.equals("生活")){
                spinner.setSelection(2,true);
            }
        }else{
            //初始化日期
            date = new Date();
            long times = date.getTime();//时间戳
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(date);
            date_text.setText(dateString);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIf Statement
        if (id == R.id.cancel_edit) {
            finish();
            return true;
        }
        if (id == R.id.finish_edit) {
            memos = new Memos();
            if (icarus == null) {
                return true;
            }
            title = title_text.getText().toString().trim();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date2 = null;
            try {
                date2 = formatter.parse(date_text.getText().toString().trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(memos_from_click == null) memos.setCreate_date(date2);
            //Log.d("memosstage", "state "+switch_state);
            memos.setState(switch_state);
            memos.setCategory(kind_memos);
            icarus.getContent(new Callback() {
                @Override
                public void run(String params) {
                    if ((params != null && !params.equals("{\"content\":\"\"}")) || !title.equals("")) {
                        if (title.equals("")) title = "无标题";
                        memos.setTitle(title);
                        memos.setHtml_memo_date(params);
                        if(memos_from_click != null){
                            long data_time = date2.getTime();
                            int m = memos.updateAll("create_date = ?", String.valueOf(data_time));
                            //Log.d("save_text", "run: "+m);
                        }
                        else{
                            memos.save();
                        }

                        Toast.makeText(EditActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            finish();
            //Log.d("MemosTest", "Date is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memos.getCreate_date()));
            return true;
        }
        if (id == R.id.action_refresh) {
            icarus.render();
            return true;
        }
/*        if (id == R.id.action_get_content) {
            *//*if (icarus == null) {
                return true;
            }
            icarus.getContent(new Callback() {
                @Override
                public void run(String params) {
                    Log.d("content", params);
                }
            });*//*
            List<Memos> memos1 = LitePal.where().find(Memos.class);
            for (Memos memos : memos1) {
                Log.d("MemosTest", "Title is " + memos.getTitle());
                //String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memos.getCreate_date());
                Log.d("MemosTest", "Date is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memos.getCreate_date()));
                Log.d("MemosTest", "State is " + memos.getState());
                Log.d("MemosTest", "Kind is " + memos.getCategory());
                Log.d("MemosTest", "Html is " + memos.getHtml_memo_date());
            }
            return true;
        }*/
        if (id == R.id.action_set_content) {
            List<Memos> memos1 = LitePal.where().find(Memos.class);
            /*for (Memos memos : memos1) {
                Log.d("MemosTest", "Html is " + memos.getHtml_memo_date());
            }*/
            if (icarus == null) {
                return true;
            }
            icarus.setContent("<p style=\\\"margin-left: 40px;\\\">该富文本编辑器目前支持一下功能：</p><ol><li>加粗</li><li>编号</li><li>项目符号</li><li>大段引用</li><li>快速换段落</li><li>左对齐</li><li>居中</li><li>右对齐</li><li>斜体</li><li>右缩进</li><li>左缩进</li><li>引用</li><li>下划线</li><li>删除线</li><li>字体大小调整</li></ol>");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //开关事件监听
        switch (buttonView.getId()) {
            case R.id.switch1:
                if (isChecked) {
                    switch_state = 1;
                } else {
                    switch_state = 0;
                }
                break;
        }
    }
}