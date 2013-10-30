package com.collage.goddessofskin.prototype.db;

import java.util.Stack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FragScheduleBoard_Alram_Db {

	public final static String TABLE_NAME = "Skin_Life_0";
	
	public final static String _ID = "_id";
	
	public final static String Cur_Time = "cur_time";
	
	public final static String Selection_Time = "selection_time";

    public final static String Alram_Type = "alram_type";
	
	public final static String DB_NAME = "Skin_3.db";
	
	public final static String DROP_TABLE =
			"DROP TABLE IF EXISTS "+ TABLE_NAME;
	
	public final static String CREATE_TABLE = 
			
			 "create table if not exists " + TABLE_NAME 
			 
			 + "(" + _ID + " integer primary key autoincrement, " 
			 
			 + Cur_Time + " text,"+ Selection_Time + " text," + Alram_Type + " text)";

	Context mContext;

	// ���� Ŭ������ �����ͺ��̽��� ���� �ݴ� ���� �����ִ� Ŭ�����̴� �̷� ��Ȱ�� �ϴ� ���� Ŭ������ �̿��ϱ� ���ؼ��� ���� Ŭ����
	// ���� Ŭ���� ���¸� ������ �ν��Ͻ��� �־���Ѵ�.
	// �̷��� ������ ���� Ŭ������ �Ƚ��Ͻ��� �����ͺ��̽� ����� Ŭ���� ���� ������ ���̽��� ���� �޼���, �ݴ� �޼��忡�� ���ǰ�
	// �ȴ�.

	SQLiteOpenHelper mHelper = null;

	SQLiteDatabase mDb = null;

	public FragScheduleBoard_Alram_Db(Context mContext) {
		// TODO Auto-generated constructor stub

		super();
		this.mContext = mContext;
	}

	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			// TODO Auto-generated constructor stub
			super(context, DB_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DROP_TABLE);
			
			db.execSQL(CREATE_TABLE);
		}

		// SQLiteDatabase �ν��Ͻ� ������
		// inserNote��� ������ �߰�
		// fetchAINotes ��� ����� ��� �����͸� ����
		// deleteNote ������ title�� ���� ������ ����
		// updeteNote ����� ���� ����
		// check ���ǿ� �´� �����͸� ������ ��
		// ����� ���� ����
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("drop table if exits" + TABLE_NAME);
			onCreate(mDb);
		}

	}

	public void open() {
		
		mHelper = new DBHelper(mContext);
		
		mDb = mHelper.getWritableDatabase();
	}

	public void close() {
		mHelper.close();
	}

	// ��� ����� ��� �����͸� ����

	public Cursor fetchAllNote() {
		return mDb.query(TABLE_NAME, new String[] { _ID, Cur_Time, Selection_Time, Alram_Type }, null,// selection
				null,// selectionArgs
				null,// qroup By
				null,// having
				_ID + " DESC");// order By
	}

	// check ���ǿ� �´� �����͸� ������ ��
	public Cursor check(long id) {
		String[] selectionArgs = { String.valueOf(id) };

		Cursor c = mDb.query(TABLE_NAME, null, _ID + "=?", selectionArgs, null,
				null, null);

		return c;
	}
	//���� Ÿ�Կ� �´� �����͸� ������ ��
	public Cursor check(String id) {
		String[] selectionArgs = { String.valueOf(id) };
		
		Cursor c = mDb.query(TABLE_NAME, null, Alram_Type + "=?", selectionArgs, null,
				null, null);
		
		return c;
	}

	// ��� ������ �߰�

	public long insertNote(String cur_time, String selection_time,String alram_type) {
		
		ContentValues values = new ContentValues();
		
		values.put(Cur_Time, cur_time);
		
		values.put(Selection_Time, selection_time);
		
		values.put(Alram_Type, alram_type);

		return mDb.insert(TABLE_NAME, null, values);
	}

	// ����� ���� ����
	public boolean updateNote(String cur_time, String selection_time,String alram_type, long id){
		ContentValues values = new ContentValues();
		
		values.put(Cur_Time,cur_time );
		
		values.put(Selection_Time, selection_time);

		values.put(Alram_Type, alram_type);
		
		String[] selectionArgs = {String.valueOf(id)};
		
		return mDb.update(TABLE_NAME, values, _ID + "=?", selectionArgs)>0;
		
	}
	
	public boolean deleteNote(long id){
		
		String[] selectionArgs = {String.valueOf(id)};
		
		return mDb.delete(TABLE_NAME, _ID + "=?", selectionArgs)>0;
	
	}
	
	
	
	
	
}
