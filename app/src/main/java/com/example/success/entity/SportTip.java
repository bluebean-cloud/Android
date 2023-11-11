package com.example.success.entity;

import android.net.Uri;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SportTip {

    @Id(autoincrement = true)
    private Long id;
    private Long userId;

    private String title;
    private String content; // 内容
    private byte[] Photo;
    @Generated(hash = 248624063)
    public SportTip(Long id, Long userId, String title, String content,
            byte[] Photo) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.Photo = Photo;
    }
    @Generated(hash = 274064279)
    public SportTip() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public byte[] getPhoto() {
        return this.Photo;
    }
    public void setPhoto(byte[] Photo) {
        this.Photo = Photo;
    }



}
