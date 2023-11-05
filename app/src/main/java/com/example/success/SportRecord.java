package com.example.success;

public class SportRecord {
    private String sportName;
    private String duration;
    private String sportLocation;
    private String createDate;
    private int image_num = 0;
    private byte[] imageBit1 = null;
    private byte[] imageBit2 = null;

    // 构造函数、getter和setter方法

    public SportRecord(String sportName, String duration, String sportLocation, String createDate, byte[] imageBit1) {
        this.sportName = sportName;
        this.duration = duration;
        this.sportLocation = sportLocation;
        this.createDate = createDate;
        this.imageBit1 = imageBit1;
    }

    public SportRecord(String sportName, String duration, String sportLocation, String createDate, byte[] imageBit1, byte[] imageBit2) {
        this.sportName = sportName;
        this.duration = duration;
        this.sportLocation = sportLocation;
        this.createDate = createDate;
        this.imageBit1 = imageBit1;
        this.imageBit2 = imageBit2;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSportLocation() {
        return sportLocation;
    }

    public void setSportLocation(String sportLocation) {
        this.sportLocation = sportLocation;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public byte[] getImageBit1() {
        return imageBit1;
    }

    public byte[] getImageBit2() {
        return imageBit2;
    }

}
