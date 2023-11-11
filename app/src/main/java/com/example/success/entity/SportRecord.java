package com.example.success.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SportRecord {
    @Id(autoincrement = true)
    private Long id;
    private Long userId;
    private String sportName;
    private String duration;
    private String sportLocation;
    private String createDate;
    private byte[] imageBit1 = null;
    private byte[] imageBit2 = null;

    public SportRecord(Long userId, String sportName, String duration,
                       String sportLocation, String createDate, byte[] imageBit1,
                       byte[] imageBit2) {
        this.userId = userId;
        this.sportName = sportName;
        this.duration = duration;
        this.sportLocation = sportLocation;
        this.createDate = createDate;
        this.imageBit1 = imageBit1;
        this.imageBit2 = imageBit2;
    }
    @Generated(hash = 391233404)
    public SportRecord(Long id, Long userId, String sportName, String duration,
            String sportLocation, String createDate, byte[] imageBit1,
            byte[] imageBit2) {
        this.id = id;
        this.userId = userId;
        this.sportName = sportName;
        this.duration = duration;
        this.sportLocation = sportLocation;
        this.createDate = createDate;
        this.imageBit1 = imageBit1;
        this.imageBit2 = imageBit2;
    }



    @Generated(hash = 1069159586)
    public SportRecord() {
    }

    public String getSportName() {
        return this.sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSportLocation() {
        return this.sportLocation;
    }

    public void setSportLocation(String sportLocation) {
        this.sportLocation = sportLocation;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public byte[] getImageBit1() {
        return this.imageBit1;
    }

    public void setImageBit1(byte[] imageBit1) {
        this.imageBit1 = imageBit1;
    }

    public byte[] getImageBit2() {
        return this.imageBit2;
    }

    public void setImageBit2(byte[] imageBit2) {
        this.imageBit2 = imageBit2;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
