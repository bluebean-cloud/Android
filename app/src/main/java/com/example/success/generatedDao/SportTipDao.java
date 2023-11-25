package com.example.success.generatedDao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.example.success.entity.SportTip;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SPORT_TIP".
*/
public class SportTipDao extends AbstractDao<SportTip, Long> {

    public static final String TABLENAME = "SPORT_TIP";

    /**
     * Properties of entity SportTip.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, Long.class, "userId", false, "USER_ID");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(3, String.class, "content", false, "CONTENT");
        public final static Property Photo = new Property(4, byte[].class, "Photo", false, "PHOTO");
    }

    private Query<SportTip> user_SportTipListQuery;

    public SportTipDao(DaoConfig config) {
        super(config);
    }
    
    public SportTipDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SPORT_TIP\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" INTEGER," + // 1: userId
                "\"TITLE\" TEXT," + // 2: title
                "\"CONTENT\" TEXT," + // 3: content
                "\"PHOTO\" BLOB);"); // 4: Photo
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SPORT_TIP\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SportTip entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        byte[] Photo = entity.getPhoto();
        if (Photo != null) {
            stmt.bindBlob(5, Photo);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SportTip entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        byte[] Photo = entity.getPhoto();
        if (Photo != null) {
            stmt.bindBlob(5, Photo);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SportTip readEntity(Cursor cursor, int offset) {
        SportTip entity = new SportTip( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // content
            cursor.isNull(offset + 4) ? null : cursor.getBlob(offset + 4) // Photo
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SportTip entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhoto(cursor.isNull(offset + 4) ? null : cursor.getBlob(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SportTip entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SportTip entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SportTip entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "sportTipList" to-many relationship of User. */
    public List<SportTip> _queryUser_SportTipList(Long userId) {
        synchronized (this) {
            if (user_SportTipListQuery == null) {
                QueryBuilder<SportTip> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                user_SportTipListQuery = queryBuilder.build();
            }
        }
        Query<SportTip> query = user_SportTipListQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }

}