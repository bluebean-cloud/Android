package com.example.success;

public class SportRecord {
    private String sportName;
    private String duration;
    private String sportLocation;
    private String createDate;
    // 构造函数、getter和setter方法

    public SportRecord(String sportName, String duration, String sportLocation, String createDate) {
        this.sportName = sportName;
        this.duration = duration;
        this.sportLocation = sportLocation;
        this.createDate = createDate;
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
}
