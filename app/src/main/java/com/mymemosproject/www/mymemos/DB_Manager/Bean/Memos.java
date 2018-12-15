package com.mymemosproject.www.mymemos.DB_Manager.Bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/*
 *@package:com.mymemosproject.www.mymemos.DB_Manager.Bean
 *@description:
 *@author: create by Cqh_i on 2018/12/5 0:16
 */
public class Memos extends LitePalSupport implements Serializable {
    private String title;//标题
    private Date create_date;//创建日期
    private int state;//0表示未完成，1表示已完成
    private String category;//类别
    private String html_memo_date;//内容

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getHtml_memo_date() {
        return html_memo_date;
    }

    public void setHtml_memo_date(String html_memo_date) {
        this.html_memo_date = html_memo_date;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Memos(String title, Date create_date, int state, String category, String html_memo_date) {
        this.title = title;
        this.create_date = create_date;
        this.state = state;
        this.category = category;
        this.html_memo_date = html_memo_date;
    }

    public Memos() {
    }
}
