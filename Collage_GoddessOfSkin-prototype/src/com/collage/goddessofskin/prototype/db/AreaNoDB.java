package com.collage.goddessofskin.prototype.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 <p>
 * DB 연동 관련 클래스 조회
 * 
 * 
 * @author d.h.shin
 * @version 1.0
 * @date 2012. 5. 3.
 * @see
 */
public class AreaNoDB {
	SQLiteDatabase db;
	Context ctx;
	HistoryDBHelper mHelper;

	public AreaNoDB(Context ctx) {
		this.ctx = ctx;
		mHelper = new HistoryDBHelper(ctx);
	}
	
	public int getAreaNo(String city, String gu, String dong)
	{
		Log.d("AreaNoDB", "city ::" + city + ", gu :: " + gu + ", dong :: " + dong);
		
		int areaNo = 0;
		
		String table = "LIFE";
		String [] columns = new String[]{ "AREA_NO", "CITY", "GU", "DONG"};
		String selection = "CITY = ? and GU = ? and DONG = ? or CITY = ? and GU = ? and DONG is null or CITY = ? and GU is null and DONG is null";
		String [] selectionArgs = new String[]{ city, gu, dong, city, gu, city};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		
		db = mHelper.getReadableDatabase();
	
		
		Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
		if(c.moveToFirst())
		{
			for(;;)
			{
				Log.d("AreaNoDB", "table name :: " + c.getString(0));
				if(!c.moveToNext()) break;
			}
		}
		Log.d("AreaNoDB", db + "");
		
		
		Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
		if (cursor.moveToFirst())
		{
		    do {
		    	int areaNoIndex = cursor.getColumnIndex(columns[0]);
		    	areaNo = cursor.getInt(areaNoIndex);
		    }while (cursor.moveToNext());
		}else{
		}
		
		cursor.close();
		mHelper.close();    

		return areaNo;
	}

	/**
	 * 
	 <p>
	 * DB 생성 및 update
	 * </p>
	 * 
	 */
	class HistoryDBHelper extends SQLiteOpenHelper {
		Context mContext;

		public HistoryDBHelper(Context context) {
			// Database이름은 실제 단말상에서 생성될 파일이름입니다.
			// data/data/package명/databases/DATABASE_NAME식으로 저장
			super(context, DBUtils.DB_NAME, null, 1); // 제일 마지막 인자 : 버젼, 만약 버젼이
														// 높아지면 onUpgrade를 수행한다.
			mContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}