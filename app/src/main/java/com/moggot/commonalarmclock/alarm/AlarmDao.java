package com.moggot.commonalarmclock.alarm;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALARM".
*/
public class AlarmDao extends AbstractDao<Alarm, Long> {

    public static final String TABLENAME = "ALARM";

    /**
     * Properties of entity Alarm.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Date = new Property(1, java.util.Date.class, "date", false, "DATE");
        public final static Property RequestCodes = new Property(2, String.class, "requestCodes", false, "REQUEST_CODES");
        public final static Property IsSnoozeEnable = new Property(3, Boolean.class, "setIsSnoozeEnable", false, "IS_SNOOZE_ENABLE");
        public final static Property IsMathEnable = new Property(4, Boolean.class, "setIsMathEnable", false, "IS_MATH_ENABLE");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
        public final static Property MusicPath = new Property(6, String.class, "musicPath", false, "MUSIC_PATH");
        public final static Property MusicType = new Property(7, int.class, "musicType", false, "MUSIC_TYPE");
        public final static Property State = new Property(8, boolean.class, "state", false, "STATE");
    }


    public AlarmDao(DaoConfig config) {
        super(config);
    }
    
    public AlarmDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALARM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"DATE\" INTEGER NOT NULL ," + // 1: date
                "\"REQUEST_CODES\" TEXT NOT NULL ," + // 2: requestCodes
                "\"IS_SNOOZE_ENABLE\" INTEGER," + // 3: setIsSnoozeEnable
                "\"IS_MATH_ENABLE\" INTEGER," + // 4: setIsMathEnable
                "\"NAME\" TEXT," + // 5: name
                "\"MUSIC_PATH\" TEXT NOT NULL ," + // 6: musicPath
                "\"MUSIC_TYPE\" INTEGER NOT NULL ," + // 7: musicType
                "\"STATE\" INTEGER NOT NULL );"); // 8: state
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALARM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Alarm entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDate().getTime());
        stmt.bindString(3, entity.getRequestCodes());
 
        Boolean isSnoozeEnable = entity.getIsSnoozeEnable();
        if (isSnoozeEnable != null) {
            stmt.bindLong(4, isSnoozeEnable ? 1L: 0L);
        }
 
        Boolean isMathEnable = entity.getIsMathEnable();
        if (isMathEnable != null) {
            stmt.bindLong(5, isMathEnable ? 1L: 0L);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
        stmt.bindString(7, entity.getMusicPath());
        stmt.bindLong(8, entity.getMusicType());
        stmt.bindLong(9, entity.getState() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Alarm entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDate().getTime());
        stmt.bindString(3, entity.getRequestCodes());
 
        Boolean isSnoozeEnable = entity.getIsSnoozeEnable();
        if (isSnoozeEnable != null) {
            stmt.bindLong(4, isSnoozeEnable ? 1L: 0L);
        }
 
        Boolean isMathEnable = entity.getIsMathEnable();
        if (isMathEnable != null) {
            stmt.bindLong(5, isMathEnable ? 1L: 0L);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
        stmt.bindString(7, entity.getMusicPath());
        stmt.bindLong(8, entity.getMusicType());
        stmt.bindLong(9, entity.getState() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Alarm readEntity(Cursor cursor, int offset) {
        Alarm entity = new Alarm( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            new java.util.Date(cursor.getLong(offset + 1)), // date
            cursor.getString(offset + 2), // requestCodes
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0, // setIsSnoozeEnable
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0, // setIsMathEnable
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // name
            cursor.getString(offset + 6), // musicPath
            cursor.getInt(offset + 7), // musicType
            cursor.getShort(offset + 8) != 0 // state
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Alarm entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDate(new java.util.Date(cursor.getLong(offset + 1)));
        entity.setRequestCodes(cursor.getString(offset + 2));
        entity.setIsSnoozeEnable(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
        entity.setIsMathEnable(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMusicPath(cursor.getString(offset + 6));
        entity.setMusicType(cursor.getInt(offset + 7));
        entity.setState(cursor.getShort(offset + 8) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Alarm entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Alarm entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Alarm entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
