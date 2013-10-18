package com.collage.goddessofskin.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Ult_Db {

	Context mContext;
	//������
	SQLiteDatabase db;
	
	String text_1 = "�¾翡 ����� �ص��� �����Ͽ�, ��� ������ġ�� �ʿ���. ����� �Ǻδ� �� �� ���� Ż�� ����.11�ÿ� 4�� ���̿��� �¾翡�� ������ ���ϰ�, �״ÿ� �ְų� �ѿ��� �԰� ���ڿ� ���۶󽺸� ������� SPF-15�� �ڿܼ� �������� 2�ð����� ����� �߶�� ��.";
	String text_2 = "�¾翡 ����� �ſ� �����Ͽ�, �߰����� ������ġ�� �ʿ���.����� �Ǻδ� ������ Ÿ�� ������ �� �� ����.11�ú��� 4�� ������ �ѳ����� �¾翡�� ������ �ּ�ȭ�ϰ�, �״ÿ� �ְų� �ѿ��� �԰���ڿ� ���۶󽺸� ���� ��� SPF-15�� �ڿܼ� �������� ����� �߶� �Ǻθ� ��ȣ�ؾ���.";
	String text_3 = "�¾翡 ����� �����Ͽ�, �޺��� ����� ��ȣ�� �ʿ���.11�ú��� 4�û����� �ð��� �¾翡 ����Ǵ� �ð��� ���̰�, �ѿ��� �԰�,���ڿ� ���۶󽺸� ���� ��� SPF-15�� �ڿܼ� �������� �߶�� ��.";
	String text_4 = "�¾翡 ������� ���� ����. ���� ������ �Ѵٸ� �ѿ��� �԰� �ڿܼ� �������� �ٸ��� ����⿡ �����ϰ�, �¾��� ���� �ѳ����� �״ÿ� �ӹ����� ��.";
	String text_5 = "������ ����� ��� �ڿܼ� ����� ���� ���� ����.";
	String text_6 = "�ϸ��� �¾籤���� �����ϴ�.....           Good Evening.!! Have a Good Time !!";
	
	
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
