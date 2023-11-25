package com.example.success.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 房间和用户之间的多对多关系，需要单独一个表来记录
 */
@Entity
public class UserInRoom {

    @Id(autoincrement = true)
    private Long id;
    private Long userId;
    private Long roomId;
    @Generated(hash = 1787359765)
    public UserInRoom(Long id, Long userId, Long roomId) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
    }
    @Generated(hash = 1076001714)
    public UserInRoom() {
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
    public Long getRoomId() {
        return this.roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}
