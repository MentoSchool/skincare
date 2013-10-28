package com.collage.goddessofskin.prototype.fragment.settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDbAdapter {
    public final static String TABLE_NAME = "notes3";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String CURWHEATERCODE = "curwheathercode";
    
    public final static String DB_NAME = "lab3.db";
    public final static String CREATE_TABLE = 
      "create table if not exists  " + TABLE_NAME 
      + " (" + _ID + " integer primary key autoincrement, " 
      + TITLE+ " text, " + BODY+ " text, " +  CURWHEATERCODE + " text )";

    Context mContext;
    SQLiteOpenHelper mHelper = null;
    SQLiteDatabase mDb = null;    
    
    public NotesDbAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                 int newVersion) {
            db.execSQL("drop table if exits " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void open() {
        mHelper = new DBHelper(mContext);
        mDb = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    // 디비에 저장된 모든 데이터를 얻어옴
    public Cursor fetchAllNotes() {
        return mDb.query(TABLE_NAME, 
                new String[] { _ID, TITLE, BODY , CURWHEATERCODE}, // projection
                null, // selection
                null, // selectionArgs
                null, // group By
                null, // having
                _ID + " DESC");// order By
    }

    public Cursor check(long id) {
        String[] selectionArgs = {String.valueOf(id) };
        Cursor c = mDb.query(TABLE_NAME, null, _ID + "=?", 
                    selectionArgs, null,  null, null);
        return c;
    }

    // 디비에 데이터 추가
    public long insertNote(String title, String body, String curwheathercode) {
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(BODY, body);
        values.put(CURWHEATERCODE, curwheathercode);
        return mDb.insert(TABLE_NAME, null, values);
    }

    // 디비의 내용 갱신
    public boolean updateNote(String title, String body, long id) {
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(BODY, body);
        String[] selectionArgs = {String.valueOf(id) };
        return mDb.update(TABLE_NAME, values, _ID + "=?", 
                           selectionArgs) > 0;
    }

    // 지정한 id를 갖는 데이터 삭제
    public boolean deleteNote(long id) {
        String[] selectionArgs = {String.valueOf(id) };
        return mDb.delete(TABLE_NAME, _ID + "=?", selectionArgs) > 0;
    }
}