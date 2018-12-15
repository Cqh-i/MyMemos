package com.mymemosproject.www.mymemos.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mymemosproject.www.mymemos.DB_Manager.Bean.List_Memos_Content;
import com.mymemosproject.www.mymemos.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 *@package:com.mymemosproject.www.mymemos.Adapter
 *@description:
 *@author: create by Cqh_i on 2018/12/8 14:32
 */
public class Memos_Item_Adapter extends ArrayAdapter<List_Memos_Content>{
    private int resourceId;
    private int flag = 0;
    List<List_Memos_Content> data;
    public Memos_Item_Adapter(@NonNull Context context, int resource, @NonNull List<List_Memos_Content> objects) {
        super(context, resource, objects);
        data = objects;
        resourceId = resource;
    }

    public Memos_Item_Adapter(@NonNull Context context, int resource, @NonNull List<List_Memos_Content> objects, int flag) {
        //flag == 1 表示是从搜索结果得到的
        super(context, resource, objects);
        data = objects;
        resourceId = resource;
        this.flag = flag;
    }


    @Override
    public int getCount() {
        if(data.size() == 0)return 0;
        if(flag == 1)return data.size();
        return data.size()+500;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        List_Memos_Content list_memos_content = null;
        if(data.size() > 0) list_memos_content = getItem(position%data.size());
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.kind_memos = view.findViewById(R.id.image_list);
            viewHolder.title = view.findViewById(R.id.title_list);
            viewHolder.date = view.findViewById(R.id.date);
            viewHolder.content = view.findViewById(R.id.content);
            viewHolder.isfinshed_text = view.findViewById(R.id.isfinished);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        if(list_memos_content != null) {
            viewHolder.kind_memos.setImageResource(list_memos_content.getImageId());
            viewHolder.title.setText(list_memos_content.getTitle());
            viewHolder.date.setText(list_memos_content.getDate());
            String list_html_content = list_memos_content.getContent();
            if (list_html_content.equals("{\"content\":\"\"}")) {
                list_html_content = "{\"content\":\"<p>无内容</p>\"}";
            }
            //Log.d("html_test", list_html_content+" "+list_html_content.substring(12,list_html_content.length()-2));
            viewHolder.content.loadDataWithBaseURL(null, list_html_content.substring(12, list_html_content.length() - 2), "text/html", "utf-8", null);
            viewHolder.isfinshed_text.setText(list_memos_content.getIsfinished());
            if (viewHolder.isfinshed_text.getText().toString().equals("未完成")) {
                viewHolder.isfinshed_text.setTextColor(Color.parseColor("#CD5555"));
            } else {
                viewHolder.isfinshed_text.setTextColor(Color.parseColor("#969696"));
            }
        }
        return view;
    }

    public void refreshData(List<List_Memos_Content>  newList){
        data = newList;
    }

    class ViewHolder {
        CircleImageView kind_memos;
        TextView title;
        TextView date;
        WebView content;
        TextView isfinshed_text;
    }
}
