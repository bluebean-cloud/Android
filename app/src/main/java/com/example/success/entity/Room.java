package com.example.success.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import com.example.success.generatedDao.DaoSession;
import com.example.success.generatedDao.UserDao;
import com.example.success.generatedDao.RoomDao;


@Entity
public class Room {

    /**
     * 实体的属性
     */
    @Id(autoincrement = true)
    private Long id;
    @ToMany
    @JoinEntity(entity = UserInRoom.class,
            sourceProperty = "roomId",
            targetProperty = "userId")
    private List<User> joinUsers;
    private int maxUserNumber;  //人数上限
    private String roomName;    //房间名字
    private String sportType;
    private String roomDescribe;    //房间描述
    private String contact;     //联系方式
    private String startTime;
    private String endTime;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 740313876)
    private transient RoomDao myDao;
    @Generated(hash = 1963130574)
    public Room(Long id, int maxUserNumber, String roomName, String sportType,
            String roomDescribe, String contact, String startTime, String endTime) {
        this.id = id;
        this.maxUserNumber = maxUserNumber;
        this.roomName = roomName;
        this.sportType = sportType;
        this.roomDescribe = roomDescribe;
        this.contact = contact;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Generated(hash = 703125385)
    public Room() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMaxUserNumber() {
        return this.maxUserNumber;
    }
    public void setMaxUserNumber(int maxUserNumber) {
        this.maxUserNumber = maxUserNumber;
    }
    public String getRoomName() {
        return this.roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getSportType() {
        return this.sportType;
    }
    public void setSportType(String sportType) {
        this.sportType = sportType;
    }
    public String getRoomDescribe() {
        return this.roomDescribe;
    }
    public void setRoomDescribe(String roomDescribe) {
        this.roomDescribe = roomDescribe;
    }
    public String getContact() {
        return this.contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 541698020)
    public List<User> getJoinUsers() {
        if (joinUsers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            List<User> joinUsersNew = targetDao._queryRoom_JoinUsers(id);
            synchronized (this) {
                if (joinUsers == null) {
                    joinUsers = joinUsersNew;
                }
            }
        }
        return joinUsers;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 853259438)
    public synchronized void resetJoinUsers() {
        joinUsers = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1185512297)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRoomDao() : null;
    }

}