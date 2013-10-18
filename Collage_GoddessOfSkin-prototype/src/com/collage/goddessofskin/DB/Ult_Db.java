package com.collage.goddessofskin.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Ult_Db {

	Context mContext;
	//생성자
	SQLiteDatabase db;
	
	String text_1 = "태양에 노출시 극도로 위험하여, 모든 예방조치가 필요함. 노출된 피부는 몇 분 내에 탈수 있음.11시와 4시 사이에는 태양에의 노출을 피하고, 그늘에 있거나 겉옷을 입고 모자와 선글라스를 쓰고적어도 SPF-15의 자외선 차단제를 2시간마다 충분히 발라야 함.";
	String text_2 = "태양에 노출시 매우 위험하여, 추가적인 예방조치가 필요함.노출된 피부는 빠르게 타서 위험해 질 수 있음.11시부터 4시 사이인 한낮동안 태양에의 노출을 최소화하고, 그늘에 있거나 겉옷을 입고모자와 선글라스를 쓰고 적어도 SPF-15의 자외선 차단제를 충분히 발라 피부를 보호해야함.";
	String text_3 = "태양에 노출시 위험하여, 햇볕에 노출시 보호가 필요함.11시부터 4시사이의 시간에 태양에 노출되는 시간을 줄이고, 겉옷을 입고,모자와 선글라스를 쓰고 적어도 SPF-15의 자외선 차단제를 발라야 함.";
	String text_4 = "태양에 노출시한 위험 보통. 만약 외출을 한다면 겉옷을 입고 자외선 차단제를 바르는 등노출에 주의하고, 태양이 강한 한낮에는 그늘에 머물러야 함.";
	String text_5 = "보통의 사람의 경우 자외선 복사로 인한 위험 낮음.";
	String text_6 = "일몰후 태양광선이 없습니다.....           Good Evening.!! Have a Good Time !!";
	
	
	public Ult_Db(Context mContext){
		super();
		
		this.mContext = mContext;
	
		
		
		db = mContext.openOrCreateDatabase("Ult.db", Context.MODE_PRIVATE, null);
		db.execSQL("drop table if exists ult");
		db.execSQL("create table if not exists ult( _id integer primary key autoincrement, title text )");
		
		db.execSQL("insert into ult(title) values('"+ text_1 +"')");
		db.execSQL("insert into ult(title) values('"+ text_2 +"')");
		db.execSQL("insert into ult(title) values('"+ text_3 +"')");
		db.execSQL("insert into ult(title) values('"+ text_4 +"')");
		db.execSQL("insert into ult(title) values('"+ text_5 +"')");
		db.execSQL("insert into ult(title) values('"+ text_6 +"')");
		
	}
	
	public void appendData(String text){
		String sql = "insert into ult(title) values('"+ text +"')";
		
		db.execSQL(sql);
	}
	
	public Cursor fetchAllNotes(){
		
		Cursor cursor = null;
		
		cursor = db.rawQuery("select * from ult order by _id desc", null);
		
		return cursor;
		
	}
	
	public Cursor selectNotes(String id){
		
		Cursor cursor = null;
		
		cursor = db.rawQuery("select * from ult where _id = "+id, null);
		
		return cursor;
	}
	
}
