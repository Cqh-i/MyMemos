package com.mymemosproject.www.mymemos.DB_Manager.Bean;

import java.io.Serializable;

/*
 *@package:com.mymemosproject.www.mymemos.DB_Manager.Bean
 *@description:
 *@author: create by Cqh_i on 2018/12/5 13:32
 */
public class List_Memos_Content implements Serializable {
    private int ImageId;
    private String title;
    private String date;
    private String content;
    private String isfinished;

    public List_Memos_Content(int ImageId, String title, String date, String content, String isfinished) {
        this.ImageId = ImageId;
        this.title = title;
        this.date = date;
        this.content = content;
        this.isfinished = isfinished;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int ImageId) {
        this.ImageId = ImageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsfinished() {
        return isfinished;
    }

    public void setIsfinished(String isfinished) {
        this.isfinished = isfinished;
    }
}
